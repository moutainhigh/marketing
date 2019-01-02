package com.oristartech.rule.core.data.sync.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.RuleRuntimeBaseException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.JsonUtil;
import com.oristartech.rule.core.data.sync.service.ITaskDataService;
import com.oristartech.rule.core.data.sync.service.ITaskDataWebService;
import com.oristartech.rule.core.data.sync.service.ITaskNoteService;
import com.oristartech.rule.vos.data.sync.vo.IssueTaskDataVO;
import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

/**
 * 方便提供给http方式，bean方式 调用的service
 * @author chenjunfei
 *
 */
@Component
@Transactional
public class TaskDataWebServiceImpl extends RuleBaseServiceImpl implements ITaskDataWebService {

	private static Logger log = LoggerFactory.getLogger(TaskDataWebServiceImpl.class);
	private static final String IP = "ip"; 
	private static final String PORT = "port"; 
	private static final String IS_SAVE = "isSave"; 
	private static final String REMARK = "remark"; 
	private static final String TASK_ID = "taskId"; 
	private static final String STATUS = "status"; 
	
	@Autowired
	private ITaskNoteService ruleTaskNoteService;
	@Autowired
	private ITaskDataService ruleTaskDataService;
	
	public void setRuleTaskNoteService(ITaskNoteService ruleTaskNoteService) {
    	this.ruleTaskNoteService = ruleTaskNoteService;
    }

	public void setRuleTaskDataService(ITaskDataService ruleTaskDataService) {
    	this.ruleTaskDataService = ruleTaskDataService;
    }


	/**
	 * 根据节点ID得到一个未执行任务
	 * @param conditions 包含 节点IP，节点端口port
	 * @return RuleWSResultVO
	 */
	public RuleWSResultVO getFirstTaskDataByNoteId(Map<String, Object> conditions) {
		RuleWSResultVO validResult = validateGetTaskParam(conditions);
		if(validResult != null) {
			return validResult;
		}
		String ip = conditions.get(IP) != null ? conditions.get(IP).toString() : "" ;
		String port = conditions.get(PORT) != null ? conditions.get(PORT).toString() : "" ;
		
		RuleWSResultVO resultVO = new RuleWSResultVO();
		try {
			List<IssueTaskDataVO> list = ruleTaskDataService.getFirstTaskDataByNoteId(ip, port);
			resultVO.setOk();
			resultVO.setData(JsonUtil.objToJson(list));
		} catch(RuleRuntimeBaseException e) {
			resultVO.setFail();
			resultVO.setMessage(e.getMessage());
		} 
		return resultVO;
	}
	
	/**
	 * 更新任务状态
	 * @param conditions 包含 taskId	任务ID， status	任务状态
	 * @return RuleWSResultVO
	 */
	public RuleWSResultVO registerTaskNote(Map<String, Object> conditions) {
		RuleWSResultVO validResult = validateSaveNodeParam(conditions);
		if(validResult != null) {
			return validResult;
		}
		String ip = (String)conditions.get(IP)   ;
		String port = (String)conditions.get(PORT)   ;
		String isSave =  (String)conditions.get(IS_SAVE)  ;
		String remark = (String)conditions.get(REMARK)  ;
		
		log.info("==========接收到规则节点注册请求=====， ip=" + ip + ",poart=" + port + ",isSave=" + isSave);
		
		boolean saveFlag = false;
		if ("true".equals(isSave)) {
			saveFlag = true;
		}
		
		boolean registerFlag = ruleTaskNoteService.saveTaskNote(ip, port, remark, saveFlag);
		RuleWSResultVO resultVO = new RuleWSResultVO();
		if(!registerFlag) {
			resultVO.setMessage("节点已经存在");
			resultVO.setCode("RULE_NOTE_EXIST");
			resultVO.setFail();
		} else {
			resultVO.setOk();
		}
		return resultVO;
	}

	/**
	 * 规则同步 - 插入同步任务数据
	 * @param conditions 包含  paramJson 销售申请单/营销活动+规则引擎数据 json格式， 
	 *        taskType 任务类型 ADD/MODIFY/DISABLE/ENABLE/DELETE
	 * @return RuleWSResultVO
	 */
	public RuleWSResultVO updateTaskStatus(Map<String, Object> conditions) {
		RuleWSResultVO validResult = validateUpdateTaskParam(conditions);
		if(validResult != null) {
			return validResult;
		}
		Object taskId = conditions.get(TASK_ID);
		Object status = conditions.get(STATUS);
		
		RuleWSResultVO resultVO = new RuleWSResultVO();
		try {
			ruleTaskDataService.updateTaskStatus(Long.valueOf(taskId.toString()), Integer.valueOf(status.toString()), null);
			resultVO.setOk();
		} catch(Exception e) {
			resultVO.setFail();
			resultVO.setMessage(e.getMessage());
		}
		return resultVO;
	}
	
	private RuleWSResultVO validateGetTaskParam(Map<String, Object> conditions) {
		if(BlankUtil.isBlank(conditions)){
			return RuleWSResultVO.createFailResultVo("请提供参数");
		}
		
		if(BlankUtil.isBlank((String)conditions.get(IP))){
			return RuleWSResultVO.createFailResultVo("请提供节点IP");
		}
		if(BlankUtil.isBlank((String)conditions.get(PORT))){
			return RuleWSResultVO.createFailResultVo("请提供节点端口");
		}
		return null;
	}
	
	private RuleWSResultVO validateSaveNodeParam(Map<String, Object> conditions) {
		if(BlankUtil.isBlank(conditions)){
			return RuleWSResultVO.createFailResultVo("请提供参数");
		}
		RuleWSResultVO result = null;
		if(BlankUtil.isBlank((String)conditions.get(IP))){
			result = RuleWSResultVO.createFailResultVo("请提供节点IP");
		} else if(BlankUtil.isBlank((String)conditions.get(PORT))){
			result = RuleWSResultVO.createFailResultVo("请提供节点端口");
		} else if(BlankUtil.isBlank((String)conditions.get(IS_SAVE))){
			result = RuleWSResultVO.createFailResultVo("请提供是否保存标识");
		}
		if(result != null) {
			result.setCode("PARAM_REQUIRED");
		}
		
		return result;
	}
	
	private RuleWSResultVO validateUpdateTaskParam(Map<String, Object> conditions) {
		if(BlankUtil.isBlank(conditions)){
			return RuleWSResultVO.createFailResultVo("请提供参数");
		}
		
		Object taskId = conditions.get(TASK_ID) != null ? conditions.get(TASK_ID).toString() : null;
		Object status = conditions.get(STATUS) != null ? conditions.get(STATUS).toString() : null;
		
		if(BlankUtil.isBlank(taskId)){
			return RuleWSResultVO.createFailResultVo("请提供下发任务ID");
		}
		if(BlankUtil.isBlank(status)){
			return RuleWSResultVO.createFailResultVo("请提供修改后状态");
		}
		return null;
	}

}
