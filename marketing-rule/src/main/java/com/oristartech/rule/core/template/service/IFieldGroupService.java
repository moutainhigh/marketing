package com.oristartech.rule.core.template.service;

import java.util.List;

import com.oristartech.rule.vos.template.vo.FieldGroupVO;

/**
 * fieldGroup service类
 * @author chenjunfei
 *
 */
public interface IFieldGroupService {
//	Integer saveOrUpdate(FieldGroupVO groupVO);
//	
//	/**
//	 * 获取所有可以在在测试页面出现的fieldgroup
//	 * @return
//	 */
//	List<FieldGroupVO> getAllFieldGroupInTest();

	/**
	 * 获取所有可以在在测试页面出现的fieldgroup id
	 * @return
	 */
	List<Integer> getAllFieldGroupIdsInTest();

}
