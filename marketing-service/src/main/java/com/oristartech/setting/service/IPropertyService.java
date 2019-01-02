package com.oristartech.setting.service;

import com.oristartech.api.exception.DaoException;
import com.oristartech.api.exception.ServiceException;

public interface IPropertyService {

	/**
	 * 生成营销活动编号
	 * 规则：MAT+时间+序号，后4位顺序流水号
	 * @throws DaoException 
	 */
	public  String generateActivityCode() throws ServiceException;
	
	/**
	 * 生成会员营销活动编号
	 * 规则：YXHD+时间+序号，后4位顺序流水号
	 * @throws DaoException 
	 */
	public  String generateMemberActivityCode() throws ServiceException;
}
