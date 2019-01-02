package com.oristartech.rule.core.data.sync.service;

import java.util.Map;

import com.oristartech.rule.vos.ws.vo.RuleWSResultVO;

public interface ITaskDataWebService {
	/**
	 * 根据节点ID得到一个未执行任务
	 * @param conditions 包含 节点IP，节点端口port
	 * @return RuleWSResultVO
	 */
	public RuleWSResultVO getFirstTaskDataByNoteId(Map<String, Object> conditions);
	
	/**
	 * 更新任务状态
	 * @param conditions 包含 taskId	任务ID， status	任务状态
	 * @return RuleWSResultVO
	 */
	public RuleWSResultVO updateTaskStatus(Map<String, Object> conditions);
	
	/**
	 * 注册节点
	 * @return RuleWSResultVO
	 */
	public RuleWSResultVO registerTaskNote(Map<String, Object> conditions);
}
