package com.oristartech.rule.core.init.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.exception.ServiceRuntimeException;
import com.oristartech.marketing.core.service.impl.RuleBaseServiceImpl;
import com.oristartech.marketing.core.thread.SynchedExitOnlyThread;
import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.config.RuleProperties;
import com.oristartech.rule.core.core.service.IRuleFinderService;
import com.oristartech.rule.core.executor.IRuleLoaderService;
import com.oristartech.rule.core.init.dao.IRuleInitDao;
import com.oristartech.rule.core.init.service.IRuleExecutorInitService;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

@Component
@Transactional
public class RuleExecutorInitServiceImpl extends RuleBaseServiceImpl implements IRuleExecutorInitService {

	private static final Logger log = LoggerFactory.getLogger(RuleExecutorInitServiceImpl.class);
	
	
	@Autowired
	IRuleInitDao ruleInitDao;
	@Autowired
	IRuleFinderService ruleFinderService;
	
	/**
	 * 初始化，规则是否加载完毕。
	 */
	private static boolean initCompleted = false;
	
	public boolean isInitCompleted() {
		return initCompleted;
	}

	public void setInitCompleted(boolean initCompleted) {
		RuleExecutorInitServiceImpl.initCompleted = initCompleted;
	}
	
	public Page<RuleGroupVO> findEngineExeRuleVOs(RuleSearchCondition searchCondition) {
		Page page = ruleInitDao.findEngineExeRuleVOs(searchCondition);
		List<RuleGroupVO> vos = new ArrayList<RuleGroupVO>();
		for(Object ruleGroup : page.getResult()){
			RuleGroupVO vo = getMapper().map(ruleGroup, RuleGroupVO.class);
			vos.add(vo);
		}
		try{
			if(vos != null && !BlankUtil.isBlank(vos)) {
				List<RuleGroupVO> groupVos = ruleFinderService.assembleRuleGroup(vos);
				Page<RuleGroupVO> pageVo = new Page<RuleGroupVO>(page.getStartIndex(), page.getTotalCount(), page.getPageSize(), groupVos);
				return pageVo;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceRuntimeException(e.getMessage());
		}
		return null;
	}
}
