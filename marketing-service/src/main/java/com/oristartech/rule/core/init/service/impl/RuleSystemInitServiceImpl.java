package com.oristartech.rule.core.init.service.impl;

import java.util.concurrent.CyclicBarrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oristartech.marketing.core.thread.SynchedExitOnlyThread;
import com.oristartech.marketing.service.IMarketingService;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.Page;
import com.oristartech.rule.config.RuleProperties;
import com.oristartech.rule.core.data.sync.service.ITaskNoteRegisterService;
import com.oristartech.rule.core.executor.IRuleLoaderService;
import com.oristartech.rule.core.init.service.IRuleExecutorInitService;
import com.oristartech.rule.core.init.service.IRuleSystemInitService;
import com.oristartech.rule.search.RuleSearchCondition;
import com.oristartech.rule.vos.core.vo.RuleGroupVO;

@Component
public class RuleSystemInitServiceImpl implements IRuleSystemInitService {

	private static final Logger log = LoggerFactory.getLogger(RuleSystemInitServiceImpl.class);
	
	@Autowired
	private IRuleLoaderService ruleLoaderService;
	@Autowired
	private IRuleExecutorInitService ruleExecutorInitService;
	@Autowired
	private ITaskNoteRegisterService ruleTaskNoteRegisterService;
	@Autowired
	private RuleProperties ruleSystemConfigProps;
	
	private static String INIT_LOAD_VALUE = "1";
	private final int DEFALULT_LOAD_RULE_THREAD_COUNT = 1;
	private final String INIT_LOAD_RULE_THREAD_COUNT_PROP = "INIT_LOAD_RULE_THREAD_COUNT";
	private final String LOAD_RULE_THREAD_NAME = "initLoadRuleThreadName";
	private final String THREAD_NAME_SPLITER = "-";
	
	private static final int MAX_ONE_PAGE_COUNT = 50;
	//因为规则数量是固定的，不需要同步
	private long totalRule = 0;
		
	/**
	 * 初始化系统
	 */
	public void init() {
		initExecutor();
		initRegisterThread();
	}
	
	private void initExecutor() {
		String initConfig = ruleSystemConfigProps.getInitLoad();
		if(INIT_LOAD_VALUE.equals(initConfig) && ruleExecutorInitService != null) {
			initRules();
		}
	}
	
	private void initRegisterThread() {
		Thread thread = new Thread(ruleTaskNoteRegisterService);
		thread.start();
	}

	/**
	 * 获取加载规则线程数
	 * @return
	 */
	private int getLoadRuleThreadCount() {
//		String threadCountStr = ruleSystemConfigProps.getProperty(INIT_LOAD_RULE_THREAD_COUNT_PROP);
		int threadCount = DEFALULT_LOAD_RULE_THREAD_COUNT;
//		if(!BlankUtil.isBlank(threadCountStr)) {
//			try {
//				threadCount = Integer.parseInt(threadCountStr);
//			} catch(Exception e) {
//				log.error("无法转换加载规则线程数，使用默认线程数" + threadCount);
//			}
//		}
		return threadCount;
	}
	
	/**
	 * 加载所有有效规则
	 */
	private void initRules() {
		int threadCount = getLoadRuleThreadCount();
		
		log.info("--------------开始加载规则数据, 并生成drools规则, 加载线程数= " + threadCount + "------------");
		long start = System.currentTimeMillis();
		try {
			initAndRunLoadThread(threadCount);
			
			if(log.isInfoEnabled()) {
				log.info("--------------加载规则数据完毕, 共加载有效规则 : " + totalRule + ". ---------------");
				log.info("加载所有规则时间 : " + (System.currentTimeMillis() - start));
			}
			
		} catch(Exception e) {
			log.error("初始化加载规则错误", e);
		}
	}
	
	private void initAndRunLoadThread(final int threadCount) throws Exception {
		CyclicBarrier exitBarrier = new CyclicBarrier(threadCount + 1);
		
		Runnable runnable = new Runnable() {
			public void run() {
				
				String name = Thread.currentThread().getName();
				int threadIndex = Integer.valueOf(name.split(THREAD_NAME_SPLITER)[1]);
				
				RuleSearchCondition condition = new RuleSearchCondition();
				condition.setLimit(MAX_ONE_PAGE_COUNT);
				condition.setStart(threadIndex * MAX_ONE_PAGE_COUNT);
				Page<RuleGroupVO> page = null;
				//一页一页查出来处理
				do {
					page = ruleExecutorInitService.findEngineExeRuleVOs(condition);
					if(page != null && !BlankUtil.isBlank(page.getResult())) {
						ruleLoaderService.loadRuleGroups(page.getResult());
						int startPos = condition.getStart() + threadCount * MAX_ONE_PAGE_COUNT ;
						condition.setStart(startPos);
						totalRule = page.getTotalCount();
					}
				} while (page != null && page.hasNextPage());
			}
		};
		
		for(int i=0; i < threadCount ; i++) {
			SynchedExitOnlyThread thread = new SynchedExitOnlyThread(runnable, exitBarrier);
			thread.setName(LOAD_RULE_THREAD_NAME + THREAD_NAME_SPLITER + i);
			thread.start();
		}
		//本线程等待加载完成。
		exitBarrier.await();
		
		ruleExecutorInitService.setInitCompleted(true);
	}
}
