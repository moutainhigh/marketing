package com.oristartech.rule.drools.executor.context;

import java.util.HashMap;
import java.util.Map;

import com.oristartech.marketing.core.exception.CacheRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;

/**
 * 在一次匹配过程中缓存需要缓存的信息, 为了统一管理所有的缓存key。 而不能直接用map去缓存。避免缓存的key冲突. 
 * 因为在自定义比较符号或执行方法都需要缓存信息。
 * 放在RuleExecuteContext属性中
 * @author chenjunfei
 *
 */
public class RuleContextCacheManager {
	
	private final String KEY_SEPARATOR = "_";
	private final String CONDITION_KEY_PREFIX = "condition";
	private final String FUNC_KEY_PREFIX = "function";
	/**
	 * 不同的规则匹配可能需要暂缓一些数据，而且时效在一次匹配过程中。 无需并发控制。
	 */
	private Map<String, Object> contextCache = new HashMap<String, Object>();
	
	/**
	 * cache 的内容和condition相关 modelCategory， fieldName，customKey共同组装为cacheKey
	 * @param modelCategory key前缀
	 * @param fieldName  key前缀
	 * @param customKey 自定义的额外的key
	 * @param data
	 */
	public void cacheByCondition(String modelCategory, String fieldName, String customKey, Object data) {
		String key = assembleKeyByCondition(modelCategory, fieldName, customKey);
		if(data == null)  {
			throw new CacheRuntimeException("提供cache的数据");
		}
		this.contextCache.put(key, data);
	}
	
	public boolean containsKeyByCondition(String modelCategory, String fieldName, String customKey) {
		String key = assembleKeyByCondition(modelCategory, fieldName, customKey);
		return this.contextCache.containsKey(key);
	}
	
	public Object getCacheDataByCondition(String modelCategory, String fieldName, String customKey) {
		String key = assembleKeyByCondition(modelCategory, fieldName, customKey);
		return this.contextCache.get(key);
	}
	
	private String assembleKeyByCondition(String modelCategory, String fieldName, String customKey) {
		if(BlankUtil.isBlank(modelCategory) || BlankUtil.isBlank(fieldName) || BlankUtil.isBlank(customKey)) {
			throw new CacheRuntimeException("提供完整的key");
		}
		return CONDITION_KEY_PREFIX + KEY_SEPARATOR + modelCategory + KEY_SEPARATOR + fieldName + KEY_SEPARATOR + customKey ;
	}
	
	/**
	 * cache 的内容和执行方法或自定义操作符相关, function class name, customKey共同组装为cacheKey
	 * @param funcClz  class Name
	 * @param customKey
	 * @param data
	 */
	public void cacheByClz(Class<?> clz, String customKey, Object data) {
		String key = assembleKeyByFunc(clz, customKey);
		if(data == null)  {
			return ;
		}
		this.contextCache.put(key, data);
	}
	
	public boolean containsKeyByClz(Class<?> funcClz, String customKey) {
		String key = assembleKeyByFunc(funcClz, customKey);
		return this.contextCache.containsKey(key);
	}
	
	public Object getCacheDataByClz(Class<?> funcClz, String customKey) {
		String key = assembleKeyByFunc(funcClz, customKey);
		return this.contextCache.get(key);
	}
	
	private String assembleKeyByFunc(Class<?> funcClz, String customKey) {
		if(funcClz == null || BlankUtil.isBlank(customKey) ) {
			throw new CacheRuntimeException("提供完整的key");
		}
		return FUNC_KEY_PREFIX + KEY_SEPARATOR + funcClz.getName() + KEY_SEPARATOR + customKey;
	}
}
 