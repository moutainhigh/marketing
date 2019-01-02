package com.oristartech.setting.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.api.exception.DaoException;
import com.oristartech.api.exception.ServiceException;
import com.oristartech.setting.dao.IPropertyDao;
import com.oristartech.setting.model.Property;
import com.oristartech.setting.service.IPropertyService;

@Service
@Transactional
public class PropertyServiceImpl implements IPropertyService {

	@Autowired
	IPropertyDao propertyDao;
	
	/**
	 * 会员营销活动编号时间
	 */
	public static final String MEMBER_ACTIVITY_CODE_NUM="MEMBER_ACTIVITY_CODE_NUM";
	/**
	 * 营销活动编号序列号
	 */
	public static final String ACTIVITY_CODE_NUM_SEQ="ACTIVITY_CODE_NUM_SEQ";
	
	/**
	 * 会员营销活动编号序列号
	 */
	public static final String MEMBER_ACTIVITY_CODE_NUM_SEQ="MEMBER_ACTIVITY_CODE_NUM_SEQ";
	
	/**
	 * 营销活动编号
	 * 规则：MAT+时间+4位序号
	 */
	public static final String ACTIVITY_CODE = "MAT";
	
	/**
	 * 会员营销活动编号
	 * 规则：YXHD，后六位顺序流水号
	 */
	public static final String MEMBER_ACTIVITY_CODE = "YXHD";
	/**
	 * 营销活动编号时间
	 */
	public static final String ACTIVITY_CODE_NUM="ACTIVITY_CODE_NUM";
	
	
	/**
	 * 生成会员营销活动编号
	 * 规则：YXHD+时间+序号，后4位顺序流水号
	 * @throws DaoException 
	 */
	public  String generateMemberActivityCode() throws ServiceException{
		return generateCode(MEMBER_ACTIVITY_CODE_NUM, MEMBER_ACTIVITY_CODE_NUM_SEQ, MEMBER_ACTIVITY_CODE);
	}
	
	private String generateCode(String key,String seq,String code) throws ServiceException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		DecimalFormat numberFormat = new DecimalFormat("0000");
		String nowDateStr = simpleDateFormat.format(new Date());

		Property tmpCardNum = propertyDao.getPropertyByKey(key);

		if (tmpCardNum == null) {
			tmpCardNum = new Property();
			tmpCardNum.setKey(key);
			tmpCardNum.setValue(nowDateStr);
			Property flownumseq = new Property();
			flownumseq.setKey(seq);
			flownumseq.setValue(numberFormat.format(1));
			propertyDao.saveOrUpdate(tmpCardNum);
			propertyDao.saveOrUpdate(flownumseq);
			return code +nowDateStr + flownumseq.getValue();
		} else {
			Property flownumseq = propertyDao.getPropertyByKey(seq);
			if (nowDateStr.equals(tmpCardNum.getValue())) {
				int updateNum = propertyDao.updateFlowNumSeq(flownumseq.getKey());
				if(updateNum < 1)
					throw new ServiceException(BizExceptionEnum.MKT_INVALID_MEMBER_ACTIVITY_CODE);
				return code +nowDateStr + numberFormat.format(Long.valueOf(flownumseq.getValue()));
			} else {

				tmpCardNum.setValue(nowDateStr);
				flownumseq.setValue(numberFormat.format(1));
				propertyDao.saveOrUpdate(tmpCardNum);
				propertyDao.saveOrUpdate(flownumseq);
				return code +nowDateStr + flownumseq.getValue();

			}

		}
	}
	/**
	 * 生成营销活动编号
	 * 规则：MAT+时间+序号，后4位顺序流水号
	 * @throws DaoException 
	 */
	public  String generateActivityCode() throws ServiceException{
		return generateCode(ACTIVITY_CODE_NUM, ACTIVITY_CODE_NUM_SEQ, ACTIVITY_CODE);
	}
}
