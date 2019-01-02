package com.oristartech.marketing.core.dao.hibernate5.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.Entity;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.oristartech.marketing.core.dao.GenericDaoHibernate;
import com.oristartech.marketing.core.exception.DataAccessRuntimeException;
import com.oristartech.rule.common.util.BlankUtil;
import com.oristartech.rule.common.util.Page;

public class RuleBaseDaoImpl<T, ID extends Serializable> extends GenericDaoHibernate<T, ID> {
	
	/**
	 * ThreadLocal属性，指定当前查询是否缓存，避免多线程的环境下影响了别的查询。
	 */
	private ThreadLocal<Boolean> cacheQueries = new ThreadLocal<Boolean>();;
	
	/**
	 * 是否启用了缓存查询
	 * @return
	 */
	public boolean isCacheQueries() {
		if(this.cacheQueries.get() != null){
			return this.cacheQueries.get();
		}
		return false;
	}
	
	/**
	 * 设置缓存查询
	 * @param cache
	 */
	public void setCacheQueries(boolean cache) {
		this.cacheQueries.set(cache);
	}
	
	//mysql like比较符号模糊匹配符号
	protected static final String MYSQL_LIKE_SPLITER = "%";
	

	/**
	 * 
	 * 
	 * 功能描述：分页查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param start
	 *            开始记录数
	 * @param limit
	 *            每页显示记录数
	 * @param queryParams
	 *            查询参数, 可以是Map
	 * @return
	 * @author <a href="mailto:zhaoyuxin@ceopen.cn">zhaoyuxin</a>
	 * @version 1.0
	 * @since 1.0 create on: 2010-10-28
	 * 
	 */
	protected Page searchPagedQuery(String hql, int start, int limit, final Object queryParams) {
		return pagedQuery(hql, (start / limit) + 1, limit, queryParams, null, true);
	}

	protected Page searchPagedQuery(String hql, int start, int limit, final Object queryParams, boolean backLastPage) {
		return pagedQuery(hql, (start / limit) + 1, limit, queryParams, null, backLastPage);
	}
	
	/**
	 * 
	 * 
	 * 功能描述：分页查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param start
	 *            开始记录数
	 * @param limit
	 *            每页显示记录数
	 * @param queryParams
	 *            查询参数, 可以是Map
	 * @return
	 * @author <a href="mailto:zhaoyuxin@ceopen.cn">zhaoyuxin</a>
	 * @version 1.0
	 * @since 1.0 create on: 2010-10-28
	 * 
	 */
	protected Page searchPagedQuery(String hql, int start, int limit, final Object queryParams, Class<?> resultClz) {
		return pagedQuery(hql, (start / limit) + 1, limit, queryParams, resultClz, true);
	}

	/**
	 * 
	 * 
	 * 功能描述：分页查询,没有条件查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param start
	 *            开始记录数
	 * @param limit
	 *            每页显示记录数
	 * @return
	 * @author <a href="mailto:zhaoyuxin@ceopen.cn">zhaoyuxin</a>
	 * @version 1.0
	 * @since 1.0 create on: 2010-10-28
	 * 
	 */
	protected Page searchPagedQuery(String hql, int start, int limit) {
		return searchPagedQuery(hql, start, limit, null);
	}
	
	/**
	 * 
	 * 
	 * 功能描述：获取实体名称
	 * 
	 * @param entityClass
	 * @return
	 * @author <a href="mailto:zhaoyuxin@ceopen.cn">zhaoyuxin</a>
	 * @version 1.0
	 * @since 1.0 create on: 2010-10-11
	 * 
	 */
	protected String getEntityName(Class<? extends Object> entityClass) {
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity != null && entity.name() != null && !"".equals(entity.name())) {
			entityname = entity.name();
		}
		return entityname;
	}

	/**
	 * 功能描述：执行HQL语句
	 * 
	 * @param hql
	 *            hql语句
	 * @param params
	 *            查询参数
	 * @return 结果集
	 */
	protected List<T> executeFindHql(final String hql, final List<?> params) {
		try {
			return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					org.hibernate.query.Query query = session.createQuery(hql);
					prepareQuery(query);
					if (null != params && params.size() > 0) {
						for (int i = 0; i < params.size(); i++) {
							query.setParameter(i, params.get(i));
						}
					}
					return query.list();
				}

			});
		} catch (Exception e) {
			throw new DataAccessRuntimeException(e);
		}
	}
	
	/**
	 * 功能:执行新增或修改或删除的Hql语句
	 * 
	 * @param hql
	 *            hql语句
	 * @param params
	 *            查询参数
	 * @return 更新记录条数
	 */
	protected Integer executeSaveOrUpdate(String hql, Object ... params) {
		return executeSaveOrUpdate(hql, Arrays.asList(params));
	}
	
	@SuppressWarnings("unchecked")
	protected Integer executeSqlUpdate(final String hql, final List<?> params) {
		try {
			return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createSQLQuery(hql);
					if (null != params && params.size() > 0) {
						for (int i = 0; i < params.size(); i++) {
							query.setParameter(i, params.get(i));
						}
					}
					return query.executeUpdate();
				}
			});
		} catch (Exception e) {
			throw new DataAccessRuntimeException(e);
		}
	}
	
	/**
	 * 功能:执行新增或修改或删除的Hql语句
	 * 
	 * @param hql
	 *            hql语句
	 * @param params
	 *            查询参数
	 * @return 更新记录条数
	 */
	@SuppressWarnings("unchecked")
	protected Integer executeSaveOrUpdate(final String hql, final List<?> params) {
		try {
			return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					org.hibernate.query.Query query = session.createQuery(hql);

					if (null != params && params.size() > 0) {
						for (int i = 0; i < params.size(); i++) {
							query.setParameter(i, params.get(i));
						}
					}

					return query.executeUpdate();
				}

			});

		} catch (Exception e) {
			throw new DataAccessRuntimeException(e);

		}

	}

	/**
	 * 功能:执行新增或修改或删除的Hql语句
	 * 
	 * @param hql
	 *            hql语句
	 * @param paramMap
	 *            查询参数
	 * @return 更新记录条数
	 */
	protected Integer executeSaveOrUpdate(final String hql, final Map<String, Object> paramMap) {
		try {
			return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql);
					if (!BlankUtil.isBlank(paramMap)) {
						Set<String> paramNames = (Set<String>) paramMap.keySet();
						for (String paramName : paramNames) {
							applyNamedParameterToQuery(query, paramName, paramMap.get(paramName));
						}
					}
					return query.executeUpdate();
				}

			});

		} catch (Exception e) {
			throw new DataAccessRuntimeException(e);

		}
	}

	/**
	 * 功能:执行新增或修改或删除的Hql语句
	 * 
	 * @param hql
	 *            hql语句
	 * @param paramMap
	 *            查询参数
	 * @return 更新记录条数
	 */
	protected Integer executeSaveOrUpdate(final String hql, final String param, final Object paramValue) {
		try {
			return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(hql);
					if (!BlankUtil.isBlank(param) && !BlankUtil.isBlank(paramValue)) {
						applyNamedParameterToQuery(query, param, paramValue);

					}
					return query.executeUpdate();
				}

			});

		} catch (Exception e) {
			throw new DataAccessRuntimeException(e);

		}
	}

	/**
	 * 
	 * 
	 * 功能描述：获取唯一结果 
	 * 
	 * @param queryString
	 * @param paramMap
	 * @return 
	 * @author <a href="mailto:chenjunfei@ceopen.cn">chenjunfei</a>
	 * @version
	 * @since 程序版本号
	 * create on: 2012-4-5
	 *
	 */
	protected Object uniqueResult(String queryString, final Object params) {
		return uniqueResult(queryString, params, null);
	}

	/**
	 * 
	 * 
	 * 功能描述：获取唯一结果，resultClz可null
	 * 
	 * @param queryString
	 * @param params 参数, 可以用map或object数组
	 * @param resultClz 查询语句返回的结果class，可空
	 * @return 
	 * @author <a href="mailto:chenjunfei@ceopen.cn">chenjunfei</a>
	 * @version
	 * @since 程序版本号
	 * create on: 2012-4-5
	 *
	 */
	protected Object uniqueResult(String queryString, final Object params, Class<?> resultClz) {
		List list = this.findByNamedParam(queryString, params, resultClz);
		Object result = null;
		if (!BlankUtil.isBlank(list)) {
			result = list.get(0);
		}
		return result;
	}

	/**
	 * 功能:按参数名查询Hql语句
	 * 
	 * @param queryString hql语句
	 * @param params 参数, 可以用map或object数组
	 * @return 查询结果集
	 */
	protected List<?> findByNamedParam(final String queryString, final Object params) {
		return findByNamedParam(queryString, params, null);
	}

	/**
	 * 功能:按参数名查询Hql语句
	 * 
	 * @param queryString
	 *            hql语句
	 * @param params 参数, 可以用map或object数组
	 * @return 查询结果集
	 */
	protected List<?> findByNamedParam(final String queryString, final Object params, final Class<?> resultClz) {
		return (List<?>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(queryString);
				prepareQuery(query);

				if (!BlankUtil.isBlank(params)) {
					if (params instanceof Map<?, ?>) {
						Map<String, Object> args = (Map<String, Object>) params;
						Set<String> keys = args.keySet();
						for (String key : keys) {
							Object obj = args.get(key);
							applyNamedParameterToQuery(query, key, obj);
						}
					} else if (params.getClass().isArray()){
						Object[] args = (Object[]) params;
						for (int position = 0; position < args.length; position++) {
							query.setParameter(position, args[position]);
						}
					} else if (params instanceof Collection) {
						Collection<Object> args = (Collection<Object>) params;
						Iterator it = args.iterator();
						int i = 0;
						while(it.hasNext()) {
							query.setParameter(i, it.next());
							i++;
						}
					} else {
						query.setParameter(0, params);
					}
				}
				return execute(query, resultClz);
			}

		});
	}

	/**
	 * 查询分页
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param sourceArgs
	 * @param resultClz
	 * @param backLastPage 分页号实际的分页数是否返回最后一页
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Page pagedQuery(final String hql, final int pageNo, final int pageSize, 
			                final Object sourceArgs, final Class resultClz, final boolean backLastPage) {
		return (Page) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				String totalCountString = getCountString(hql);
				logger.debug(totalCountString);

				Query totalQuery = session.createQuery(totalCountString);
				Query listQuery = session.createQuery(hql);
				prepareQuery(listQuery);

				if (!BlankUtil.isBlank(sourceArgs)) {
					if (sourceArgs instanceof Map<?, ?>) {
						Map<String, Object> args = (Map<String, Object>) sourceArgs;
						Set<String> keys = args.keySet();
						for (String key : keys) {
							Object obj = args.get(key);
							applyNamedParameterToQuery(totalQuery, key, obj);
							applyNamedParameterToQuery(listQuery, key, obj);
						}
					} else if (sourceArgs instanceof Collection) {
						Collection<Object> args = (Collection<Object>) sourceArgs;
						Iterator it = args.iterator();
						int i = 0;
						while(it.hasNext()) {
							listQuery.setParameter(i, it.next());
							totalQuery.setParameter(i, it.next());
							i++;
						}
					} else if (sourceArgs.getClass().isArray()){
						Object[] args = (Object[]) sourceArgs;
						for (int position = 0; position < args.length; position++) {
							listQuery.setParameter(position, args[position]);
							totalQuery.setParameter(position, args[position]);
						}
					} else {
						listQuery.setParameter(0, sourceArgs);
					}
				}

				Iterator<Long> countlist = totalQuery.iterate();
				long totalCount = 0;
				if (countlist.hasNext()) {
					totalCount = countlist.next();
					logger.debug("TotalCount: " + totalCount);
				}

				if (totalCount < 1L) {
					return new Page();
				}

				// 添加页码判断，判断当前提交过来的页码是否处于有效的页码范围之内
				long size = (totalCount / pageSize) + (totalCount % pageSize != 0 ? 1 : 0);
				int newPageNo = pageNo;
				if (size < pageNo && backLastPage) {
					newPageNo = (int) size;
				} else if(size < pageNo){
					return new Page();
				}
				if (pageNo <= 0) {
					newPageNo = 1;
				}

				int startIndex = Page.getStartOfPage(newPageNo, pageSize);
				if (resultClz != null) {
					listQuery.setResultTransformer(Transformers.aliasToBean(resultClz));
				}
				List resultList = listQuery.setFirstResult(startIndex).setMaxResults(pageSize).list();
				if (logger.isDebugEnabled()) {
					logger.debug("[paged query--->totalCount: " + totalCount + "\tstartIndex: " + startIndex);
				}

				return new Page(startIndex, totalCount, pageSize, resultList);
			}

		});

	}

	protected String getCountString(String queryString) {

		StringBuffer totalQuery = new StringBuffer("SELECT ");

		String selectItem = "*";
		queryString = queryString.replaceAll("(?i)fetch", "");
		int fromIndex = queryString.toUpperCase().indexOf("FROM");

		boolean hasSelect = Pattern.matches("(?i)^SELECT.*", queryString);
		if (hasSelect) {
			int commaIndex = queryString.indexOf(",");
			String selectString = queryString.substring("SELECT".length(), ((commaIndex == -1 || commaIndex > fromIndex) ? fromIndex : commaIndex)).trim();

			selectItem = selectString.replaceAll("(?i)(NEW\\s+[A-Z]+(\\.[A-Z]+)*\\s*\\()|(^\\s+)|(\\s+AS.*$)", "");
			if (Pattern.matches(".*\\(.*\\).*", selectItem)) {
				selectItem = selectItem.replaceAll("(?i)\\s*DISTINCT\\s*", "");
			}

		}

		if (hasGroupBy(queryString)) {
			totalQuery.append("SUM(COUNT(" + selectItem + ")) ");

		} else {
			totalQuery.append("COUNT(" + selectItem + ") ");
		}

		return totalQuery.append(queryString.substring(fromIndex)).toString();
	}

	protected boolean hasGroupBy(String queryString) {
		return Pattern.matches("(?i).*GROUP BY.*", queryString);
	}

	private void prepareQuery(Query queryObject) {
		if (isCacheQueries()) {
			queryObject.setCacheable(true);

			String queryCacheRegion = getHibernateTemplate().getQueryCacheRegion();

			if (queryCacheRegion != null) {
				queryObject.setCacheRegion(queryCacheRegion);
			}
		}
	}

	private void applyNamedParameterToQuery(Query queryObject, String paramName, Object value) throws HibernateException {
		if (value instanceof Collection<?>) {
			queryObject.setParameterList(paramName, (Collection<?>) value);

		} else if (value !=null && value.getClass().isArray()) {
			queryObject.setParameterList(paramName, (Object[]) value);

		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	@SuppressWarnings( { "unchecked" })
	private List<?> execute(Query query, Class<?> resultClz) {
		prepareQuery(query);

		if (resultClz != null) {
			query = query.setResultTransformer(Transformers.aliasToBean(resultClz));
		}


		return query.list();

	}
	
	/**
	 * SQL查询Map结果集(返回的列名会取自定义别名)
	 * @param sql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	protected Page queryLabelMapPageBySql(String sql,int pageNo,int pageSize, Object... values) throws Exception {
		Page page = new Page(pageSize);
		String countSql = "select count(*) from ("+sql+") p ";
		NativeQuery query = this.getSession().createNativeQuery(countSql);
		if (values != null) {
			for (int i = 1; i <= values.length; i++) {
				query.setParameter(i, values[i-1]);
			}
		}
		int totalCount = 0;
		Object o = query.uniqueResult();
	
		if(o != null){
			totalCount = Integer.valueOf(o.toString());
		}
		page.setTotalCount(totalCount);
		NativeQuery query1 = this.getSession().createNativeQuery("select * from ("+sql+" limit "+((pageNo-1)*pageSize)+","+pageSize+") p");
		if (values != null) {
			for (int i = 1; i <= values.length; i++) {
				query1.setParameter(i, values[i-1]);
			}
		}
		query1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		page.setResult(query1.list());
		return page;
	}
}
