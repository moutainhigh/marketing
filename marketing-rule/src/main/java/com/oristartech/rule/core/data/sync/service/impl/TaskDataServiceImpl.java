package com.oristartech.rule.core.data.sync.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.marketing.core.exception.RuleRuntimeBaseException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.config.RuleProperties;
import com.oristartech.rule.core.config.service.ISystemConfigService;
import com.oristartech.rule.core.core.service.IRuleFinderService;
import com.oristartech.rule.core.data.sync.dao.ITaskContentDao;
import com.oristartech.rule.core.data.sync.dao.ITaskDataDao;
import com.oristartech.rule.core.data.sync.dao.ITaskDefineDao;
import com.oristartech.rule.core.data.sync.dao.ITaskNoteDao;
import com.oristartech.rule.core.data.sync.model.RuleTaskDefineType;
import com.oristartech.rule.core.data.sync.model.TaskContent;
import com.oristartech.rule.core.data.sync.model.TaskData;
import com.oristartech.rule.core.data.sync.model.TaskDefine;
import com.oristartech.rule.core.data.sync.model.TaskNote;
import com.oristartech.rule.core.data.sync.service.ITaskDataService;
import com.oristartech.rule.core.executor.IRuleLoaderService;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.data.sync.enums.TaskStatus;
import com.oristartech.rule.vos.data.sync.vo.IssueTaskDataVO;

@Component
@Transactional
public class TaskDataServiceImpl extends RuleBaseServiceImpl  implements ITaskDataService {
	
	private static final Logger log = LoggerFactory.getLogger(TaskDataServiceImpl.class);
	
	@Autowired
	private ITaskDataDao ruleTaskDataDao;
	@Autowired
	private ITaskNoteDao ruleTaskNoteDao;
	@Autowired
	private ITaskContentDao ruleTaskContentDao;
	@Autowired
	private ITaskDefineDao ruleTaskDefineDao;
	@Autowired
	private RuleProperties ruleSystemConfigProps;
	@Autowired
	private IRuleFinderService ruleFinderService;
	@Autowired
	private IRuleLoaderService ruleLoaderService;
	@Autowired
	private ISystemConfigService ruleSystemConfigService;
	
	private static final String LOAD_RULE_AT_ONCE_NO_TASK = "LOAD_RULE_AT_ONCE_NO_TASK";
	
	/**
	 * 根据节点记录保存任务记录
	 * @param taskData
	 */
	private void saveByNoteList(TaskDefine taskDefine,TaskContent taskContent,TaskStatus taskStatus) {
		List<TaskNote> noteList = this.ruleTaskNoteDao.getAll();
		if(noteList.size()>0){
			for(TaskNote taskNote : noteList){
				TaskData taskData = new TaskData();
				taskData.setDestNote(taskNote);
				taskData.setCreateTime(new Date());
				taskData.setTaskDefine(taskDefine);
				taskData.setStatus(taskStatus);
//				taskData.setTaskContent(taskContent);
				ruleTaskDataDao.saveOrUpdate(taskData);
			}
		}
		
	}
	
	/**
	 * 根据节点ID得到一个未执行任务
	 * @param ip	节点IP
	 * @param port	节点端口
	 * @return
	 */
	public List<IssueTaskDataVO> getFirstTaskDataByNoteId(String ip,String port){
		//根据IP和port得到唯一节点
		TaskNote taskNote = ruleTaskNoteDao.getTaskNoteByIpAndPost(ip, port);
		if(taskNote != null){
			String taskNumStr = ruleSystemConfigProps.getClientTaskNum();
			int taskNum = 5;
			if(!BlankUtil.isBlank(taskNumStr)){
				try{
					taskNum = Integer.valueOf(taskNumStr);
				}catch(Exception e){
					
				}
			}
			List<TaskData> list = ruleTaskDataDao.getTaskDatasByNoteId(taskNote.getId(), taskNum);
			List<IssueTaskDataVO> results = new ArrayList<IssueTaskDataVO>();
			if(!BlankUtil.isBlank(list)){
				for(TaskData taskData : list){
					IssueTaskDataVO vo =  this.getMapper().map(taskData, IssueTaskDataVO.class);
					if(!BlankUtil.isBlank(vo.getTaskContent())){
						vo.setMd5TaskContent(DigestUtils.md5Hex(vo.getTaskContent()));
					}
					results.add(vo);
				}
				return results;
			}else{
				return null;
			}
		}else{
			throw new RuleRuntimeBaseException("ip为:"+ip+",端口为:"+port+"的节点不存在");
		}
	}
	
	/**
	 * 更新任务状态
	 * @param taskId	任务ID
	 * @param status	任务状态
	 * @return
	 */
	public int updateTaskStatus(Long taskId,int status, String remark){
		return this.ruleTaskDataDao.updateTaskStatus(taskId, TaskStatus.values()[status],remark);		
	}
	
	/**
	 * 规则同步 - 插入同步任务数据
	 * @param groupVO groupVO
	 * @param taskType 
	 * @return
	 * @throws ServiceException 
	 */
	public void saveTaskData(RuleGroupVO groupVO, RuleTaskDefineType taskType) throws ServiceException{
		try{
			String cfgValue = ruleSystemConfigService.getValueByName(LOAD_RULE_AT_ONCE_NO_TASK);
			boolean isNoTask = Boolean.valueOf(cfgValue);
			if(isNoTask) {
				if(RuleTaskDefineType.ENABLE.equals(taskType)) {
					ruleLoaderService.loadRuleGroup(groupVO);
				} else if(RuleTaskDefineType.DISABLE.equals(taskType)){
					ruleLoaderService.removeRuleGroup(groupVO);
				}
				
			} else {
				TaskContent taskContent = new TaskContent();//任务内容
				taskContent.setContent(JsonUtil.objToJson(groupVO));
				taskContent = ruleTaskContentDao.saveOrUpdate(taskContent);
				TaskDefine taskDefine = ruleTaskDefineDao.getTaskdeDefineByName(taskType.name());
				saveByNoteList(taskDefine, taskContent, TaskStatus.CREATE);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(BizExceptionEnum.MKT_SAVE_RULETASK_FAIL);
		}
	}
	
	public void saveTaskData(Integer ruleGroupId, RuleTaskDefineType taskType) throws ServiceException {
		RuleGroupVO groupVO = ruleFinderService.getRuleGroupForExe(ruleGroupId);
		if(groupVO != null) {
			saveTaskData(groupVO, taskType);
		}
	}
}

