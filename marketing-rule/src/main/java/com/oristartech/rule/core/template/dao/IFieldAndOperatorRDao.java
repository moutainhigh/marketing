package com.oristartech.rule.core.template.dao;

import java.util.List;

import com.oristartech.marketing.core.dao.hibernate5.IRuleBaseDao;
import com.oristartech.rule.core.template.model.FieldAndOperatorRelation;

/**
 * FieldAndOperatorRelation dao操作接口
 * @author chenjunfei
 *
 */
public interface IFieldAndOperatorRDao  extends IRuleBaseDao<FieldAndOperatorRelation, Integer> {
	/**
	 * 找出fieldgroup id列表下的所有modelField和operator的关系
	 * @param fgids FieldGroup id 列表
	 * @return
	 */
	List<FieldAndOperatorRelation> findFieldAndOpByGroupIds(List<Integer> groupIds);
}
