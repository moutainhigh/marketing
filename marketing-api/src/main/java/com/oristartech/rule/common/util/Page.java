/*
 *
 * Copyright (c) 2006- CE, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * CE Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with CE.
 */
package com.oristartech.rule.common.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象. 包含当前页数据及分页信息.
 *
 * @author todd
 * @version 1.2 , 2008/5/6
 * @since JDK1.5
 */
public class Page<E> implements Serializable {

	private static final long serialVersionUID = -9181476861687970568L;

	/**
	 * 每页数据容量的默认：20
	 */
	private static final int DEFAULT_PAGE_SIZE = 20;
	
	private static final Page<Object> EMPTY_PAGE = new Page<Object>();

	/**
	 * 每页数据容量
	 */
	private int pageSize;

	/**
	 * 当前页第一条数据在List中的位置,默认0
	 */
	private int start;

	/**
	 * 当前页中存放的记录
	 */
	private List<E> data;

	/**
	 * 总记录数
	 */
	private long totalCount;

	/**
	 * 默认构造方法，只构造空值，默认PageSize为20
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<E>());
	}

	/**
	 * 构造方法，只构造空值
	 * @param pageSize
	 */
	public Page(int pageSize) {
		this(0, 0, pageSize, new ArrayList<E>());
	}
	
	/**
	 * 构造方法
	 * @param start 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize 本页容量
	 * @param data 本页包含的数据
	 */
	public Page(int start, long totalSize, int pageSize, List<E> data) {
		if (pageSize <= 0 || start < 0 || totalSize < 0) {
			throw new IllegalArgumentException(
					"Illegal Arguments to Initiate Page Object");
		}
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}

	/**
	* 方法用途和描述: 获取一个空的Page对象，带泛型支持。
	* @param <T> 泛型类型
	* @return 指定泛型类型的空Page对象
	* @author 张宪新 新增日期：2010-7-15
	*/
	@SuppressWarnings("unchecked")
	public static final <T>Page<T> emptyPage(){
		return (Page<T>) EMPTY_PAGE;
	}
	/**
	* 方法用途和描述: 获取其他泛型实例
	* @param <T> 泛型类
	* @param list Page对象中的数据集对象
	* @return
	*/
	public <T>Page<T> getOtherGenericInstance(List<T> list){
		return new Page<T>(start, totalCount, pageSize, list);
	}
	
	/**
	* 方法用途和描述: 将当前Page对象中的内容拷贝到其他的Page对象中<BR>
	* 	注意：必需保证其他的Page对象也存在有这个如下参数的构造器<BR>
	* 	T(int start, long totalSize, int pageSize, List<E> data)
	* @param <T>
	* @param pageClass
	* @return
	* @throws SecurityException
	* @throws NoSuchMethodException
	* @throws IllegalArgumentException
	* @throws InstantiationException
	* @throws IllegalAccessException
	* @throws InvocationTargetException
	*/
	public <T> T copyToPage(Class<T> pageClass) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Constructor<T> constructor = pageClass.getConstructor(new Class[] {
				int.class, long.class, int.class, List.class });
		T newInstance = constructor.newInstance(start, totalCount, pageSize, data);
		return newInstance;
	}
	
	/**
	 * 取数据库中包含的总记录数
	 * @return 总记录数
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 取总页数
	 * @return 总页数
	 */
	public long getTotalPageCount() {
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount
				/ pageSize + 1;
	}

	/**
	 * 取每页数据容量
	 * @return 每页数据容量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置当前页中的纪录
	 */
	public void setResult(List<E> data) {
		this.data = data;
	}

	/**
	 * 获取当前页中的记录
	 * @return 当前页中的记录
	 */
	public List<E> getResult() {
		return data;
	}

	/**
	 * 取当前页码,页码默认1
	 * @return 当前页码
	 */
	public int getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 是否有下页
	 * @return 有下页返回true，否则返回false
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 是否有上页
	 * @return 有上页返回true，否则返回false
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 数据是否为空
	 * @return 为空返回true, 否则返回false
	 */
	public boolean isEmpty() {
		return data == null || data.isEmpty();
	}

	/**
	 * 获取本页第一条数据在整个结果中的位置
	 * @return 位置序号（从0开始）
	 */
	public int getStartIndex() {
		return (getCurrentPageNo() - 1) * pageSize;
	}

	/**
	 * 获取本页最后一条数据在整个结果中的位置
	 * @return 位置序号（从0开始）
	 */
	public int getEndIndex() {
		int endIndex = getCurrentPageNo() * pageSize - 1;
		return endIndex >= totalCount ? (int) totalCount - 1 : endIndex;
	}

	/**
	 * 获取任一页第一条数据在整个结果中的位置（每页数据容量使用默认）
	 * @param pageNo 页码，从1开始
	 * @return 位置序号（从0开始）
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在整个结果中的位置（每页数据容量使用指定）
	 * @param pageNo 页码，从1开始
	 * @param pageSize 页面数据容量
	 * @return 位置序号（从0开始）
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
