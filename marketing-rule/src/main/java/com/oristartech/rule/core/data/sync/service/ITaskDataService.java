package com.oristartech.rule.core.data.sync.service;

import java.util.List;

import com.oristartech.api.exception.ServiceException;
import com.oristartech.rule.core.data.sync.model.RuleTaskDefineType;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;
import com.oristartech.rule.vos.data.sync.vo.IssueTaskDataVO;


public interface ITaskDataService {
	
	
	/**
	 * 根据节点ID得到一个未执行任务
	 * @param ip	节点IP
	 * @param port	节点端口
	 * @return
	 */
	public List<IssueTaskDataVO> getFirstTaskDataByNoteId(String ip,String port);
	
	/**
	 * 更新任务状态
	 * @param taskId	任务ID
	 * @param status	任务状态
	 * @return
	 */
	public int updateTaskStatus(Long taskId,int status, String remark);
	
	/**
	 * 规则同步 - 插入同步任务数据
	 * @param groupVO  
	 * @param taskType  
	 * @return
	 */
	public void saveTaskData(RuleGroupVO groupVO,RuleTaskDefineType taskType) throws ServiceException;
	
	/**
	 * 规则同步 - 插入同步任务数据
	 * @param ruleGroupId 销售申请单/营销活动+规则引擎数据规则组
	 * @param taskType 任务类型 ADD/MODIFY/DISABLE/ENABLE/DELETE
	 * @return
	 */
	public void saveTaskData(Integer ruleGroupId ,RuleTaskDefineType taskType) throws ServiceException;
}
