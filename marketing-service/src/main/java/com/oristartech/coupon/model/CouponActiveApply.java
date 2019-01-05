package com.oristartech.coupon.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 票券销售申请单
 */
@Entity
@Table(name = "COUPON_ACTIVE_APPLY")
public class CouponActiveApply {

	/**
	 * 销售申请单ID
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	/**
	 * 销售申请单名称
	 */
	@Column(name = "COUPONNAME")
	private String couponname;
	
	/**
	 * 申请单编码
	 */
	@Column(name = "APPLYCODE")
	private String applycode;
	
	/**
	 * 使用CouponActiveApplyState赋值.
	 * 申请单状态   0-未提交, 1-新建, 2-已激活, 3-已停用, 4-已作废, 5-已过期, 6-已删除, 7-验证中, 8-验证不通过, 9-票券生成中
	 */
	@Column(name = "STATE")
	private Integer state;
	
	/**
	 * 票劵数量
	 */
	@Column(name = "SALESNUM")
	private Integer salesnum;
	
	/**
	 * 起售数量
	 */
	@Column(name = "UP_SALE_NUM")
	private Integer upSaleNum;
	
	/**
	 * 票劵单价
	 */
	@Column(name = "SALESPRICE")
	private Double salesprice;
	
	/**
	 * 票券已发送数量
	 */
	@Column(name="SEND_NUM")
	private int sendNum;
	
	/**
	 * 已发送次数
	 * SEND_NUM:票券被发送数量  
	 * ALREADY_SEND_NUM: 申请单票券发送的总次数,用于限制短信提取
	 */
	@Column(name = "ALREADY_SEND_NUM")
	private int alreadySendNum;
	
	/**
	 * 票劵销售方式  1-影院零售, 2-营销活动, 3-大客户, 4-第三方合作
	 */
	@Column(name = "SALESMODE")
	private Integer salesmode;
	
	/**
	 * 票劵编号生成方式   0-系统生成, 1-外部导入, 2-使用预生成编号
	 */
	@Column(name = "COUPONGENERATION1")
	private Integer coupongeneration1;
	
	/**
	 * 票劵编号申请方式  0-审批后生成, 1-按需生成
	 */
	@Column(name = "COUPONGENERATION2")
	private Integer coupongeneration2;
	
	/**
	 * 备注
	 */
	@Column(name = "NOTE")
	private String note = "";

	/**
	 * 批次号
	 */
	@Column(name = "BATCH_NUM")
    private String batchnum;
	
    /**
     * 申请类型   0-新建申请, 1-修订申请
     */
    @Column(name = "APPLYSTATE")
	private Integer applystate;

	/**
	* 审核结果    0-通过,  1-不通过,  2-未审批
	*/
	@Column(name = "EXAMINESTATE")
	private Integer examinestate;
	
	/**
	 * 审批任务编号
	 */
	@Column(name = "APPROVALTASKSCODE")
	private String approvaltaskscode;
	
	/**
	 * 附件
	 */
	@Column(name = "ANNEX")
	private String annex;
	
	/**
	 * 合同协议号
	 */
	@Column(name = "CONTRACTCODE")
	private String contractcode;
	
	/**
	 * 审核通过时间
	 */
	@Column(name="AUDIT_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditTime;
	
	/**
	 * 申请单创建时间
	 */
	@Column(name="CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	/**
	 * 票券生成状态 0-待生成, 1-已生成 , 2-生成中
	 */
	@Column(name="GEN_STATUS")
	private int	genStatus;
	
	/**
	 * 下次回款日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NEXTRECEIPTDATE", length = 0)
	private Date nextReceiptDate;
	
	/**
	 * 已收金额
	 */
	@Column(name = "RECEIPTAMOUNT", precision = 10, scale = 0)
	private BigDecimal receiptAmount =new BigDecimal(0);
	
	/**
	 * 已收款收款状态    0-未收完, 1-已收完, 2-终止收款
	 */
	@Column(name = "RECEIPTSTATE")
	private int receiptstate;
	
	/**
	 * 申请人
	 */
	@Column(name = "APPLICANTUSER")
	private String applicantUser;
	
	/**
	 * 申请部门
	 */
	@Column(name = "APPLYUNITNAME")
	private String applyUnitName;
	
	/**
	 * 申请部门ID
	 */
	@Column(name="APPLY_UNIT_ID")
	private Long applyUnitId;
	
	/**
	 * 已导出次数
	 */
	@Column(name="EXPORTED_NUM")
	private int exportedNum;
	
	/**
	 * 打印授权码 - 任意字符
	 */
	@Column(name="PRINT_AUTH_CODE")
	private String printAuthCode;
	
	/**
	 * 规则ID
	 */
	@Column(name = "RULEID")
	private String ruleID;
	
	/**
	 * 指定影院名称
	 */
	@Column(name = "CINEMA_PARA")
	private String cinemapara;
	
	/**
	 * 指定影院编码
	 */
	@Column(name="CINEMA_CODES")
	private String cinemaCodes;
	
	/**
	 * 是否被修订过  
	 * 0-未被修订  1-修订过   2-修订历史单  3-正在修订中
	 */
	@Column(name="IS_REVISION")
	private Integer isRevise = new Integer(0);

	/**
	 * 提取锁定  0-未锁定, 1-已锁定; 锁定无法提取
	 */
	@Column(name="EXTRACT_LOCK")
	private boolean extractLock;

	/**
     * 入账影院信息
     */
	@Column(name="INCOME_CINEMA_ID",nullable=true)
	private Long cinemaId;

	/**
	 * 客户信息
	 */
	@Column( name="CUSTOMER_ID", nullable = true)
	private Long customerId;

	/**
	 * 申请人信息
	 */
	@Column(name="APPLY_USER_ID",nullable=false)
	private Long commonAccountId;
	
	/**
	 * 票券分类信息
	 */
	@Column(name="COUPON_TYPE_ID",nullable=false)
	private Long couponTypeId;
	
    /**
     * 	票劵文件名称
     */
	@Column(name="IMPORT_FILE_NAME")
	private String importfilename;
	
	/**
	 * 合同文件名称
	 */
	@Column(name="ATTACH_FILE_NAME")
	private String attachfilename;
	
	/**
	 * 重复票券编号写入的exl文件地址
	 */
	@Column(name = "EXL_FILE_PATH" )
	private String exlFilePath;
	
	/**
	 * 终止收款操作人
	 * 暂时不做关联,只做保留,为以后准备
	 */
	@Column(name="STOP_RECEIPT_USER")
	private Long stopReceiptUser;
	
	/**
	 * 有效起始时间
	 */
	@Column(name="VALID_DATE_START")
	private Date validDateStart;
	
	/**
	 * 有效结束时间
	 */
	@Column(name="VALID_DATE_END")
	private Date validDateEnd;
	
	/**
	 * 代金券面值(票券类型为代金券时有值)
	 */
	@Column(name="VOUCHERV")
	private BigDecimal voucherv;
	
	/**
	 * 已消费数量
	 */
	@Column(name="CONSUME_NUM")
	private Integer consumeNum = 0;
	
	/**
	 * 已消费金额
	 */
	@Column(name="CONSUME_SUM")
	private BigDecimal consumeSum = new BigDecimal(0);
	
	/**
	 * 代金券是否需要密码  1-不需要, 2-需要
	 */
	@Column(name="VOUCHE_IS_PASSWORD")
	private Integer voucheIsPassWord=1;
	
	/**
	 * 代金券消费密码类型  1-随机, 2-统一密码
	 */
	@Column(name="VOUCHE_PASSWORD_TYPE")
	private Integer vouchePassWordType=0;
	
	/**
	 * 代金券消费密码
	 */
	@Column(name="VOUCHE_PASSWORD")
	private String vouchePassWord;
	
	/**
	 * 商品范围   1-不限，2-电影票，3-卖品
	 */
	@Column(name="MER_RANGE")
	private Integer merRange;
	
	/**
	 * 消费金额限制
	 */
	@Column(name="VOUCHE_AMOUNT_LIMIT")
	private Double voucheAmountLimit;
	
	/**
	 * 提取锁定sessionId
	 */
	@Column(name="LOCK_SESSION_ID")
	private String lockSessionId;
	
	/**
	 * 已打印票券数量
	 */
	@Column(name="PRINT_COUNT")
	private int printCount;
	
	/**
	 * 最后打印的票券序号
	 */
	@Column(name="LAST_PRINT_SEQ")
	private int lastPrintSeq;
	
	/**
	 * 打印锁定用户
	 */
	@Column(name="PRINT_LOCK_USER")
	private Long printLockUser;
	
	/**
	 * 销售申请单ID
	 */
	@Column(name="REVISON_ID")
	private Long revisionId;

	/**
	 * 票券价格打印选项 0：数字价格  1：价格编码
	 * 奥斯卡专用
	 */
	@Column(name="PRICE_PRINT_TYPE")
	private Integer pricePrintType;

	/**
	 * 票券申请单激活时间(第一次审批通过时间)
	 */
	@Column(name="ACT_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date actTime;
	

	public CouponActiveApply() {
	}

	public CouponActiveApply(String couponname,
			Integer salesnum, Double salesprice, Integer salesmode,
			Integer coupongeneration, String applycode, Integer state,
			 Integer applystate, Integer examinestate,
			String approvaltaskscode, int receiptstate, String applicantUser,
			String applyUnitName,Long commonAccountId,Long couponTypeId) {
		this.couponname = couponname;
		this.salesnum = salesnum;
		this.salesprice = salesprice;
		this.salesmode = salesmode;
		this.applycode = applycode;
		this.state = state;
		this.applystate = applystate;
		this.examinestate = examinestate;
		this.approvaltaskscode = approvaltaskscode;
		this.receiptstate = receiptstate;
		this.applicantUser = applicantUser;
		this.applyUnitName = applyUnitName;
		this.commonAccountId=commonAccountId;
		this.couponTypeId=couponTypeId;
	}

	public CouponActiveApply(String couponname,
			Integer salesnum, Double salesprice, Integer salesmode,
			Integer coupongeneration, String note, String applycode,
			Integer state, Integer applystate,
			Integer examinestate, String approvaltaskscode, String annex,
			String contractcode, Date nextReceiptDate,
			BigDecimal receiptAmount, int receiptstate, String applicantUser,
			String applyUnitName,Long commonAccountId,Long couponTypeId) {
		this.couponname = couponname;
		this.salesnum = salesnum;
		this.salesprice = salesprice;
		this.salesmode = salesmode;
		this.note = note;
		this.applycode = applycode;
		this.state = state;
		this.applystate = applystate;
		this.examinestate = examinestate;
		this.approvaltaskscode = approvaltaskscode;
		this.annex = annex;
		this.contractcode = contractcode;
		this.nextReceiptDate = nextReceiptDate;
		this.receiptAmount = receiptAmount;
		this.receiptstate = receiptstate;
		this.applicantUser = applicantUser;
		this.applyUnitName = applyUnitName;
		this.commonAccountId=commonAccountId;
		this.couponTypeId=couponTypeId;
	}
	
	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int printCount) {
		this.printCount = printCount;
	}
	
	public int getLastPrintSeq() {
		return lastPrintSeq;
	}

	public void setLastPrintSeq(int lastPrintSeq) {
		this.lastPrintSeq = lastPrintSeq;
	}
	
	public String getLockSessionId() {
		return lockSessionId;
	}

	public void setLockSessionId(String lockSessionId) {
		this.lockSessionId = lockSessionId;
	}
	
	public Integer getConsumeNum() {
		return consumeNum;
	}

	public void setConsumeNum(Integer consumeNum) {
		this.consumeNum = consumeNum;
	}
	
	public BigDecimal getConsumeSum() {
		return consumeSum;
	}

	public void setConsumeSum(BigDecimal consumeSum) {
		this.consumeSum = consumeSum;
	}

	public Date getValidDateStart() {
		return validDateStart;
	}

	public void setValidDateStart(Date validDateStart) {
		this.validDateStart = validDateStart;
	}

	public Date getValidDateEnd() {
		return validDateEnd;
	}

	public void setValidDateEnd(Date validDateEnd) {
		this.validDateEnd = validDateEnd;
	}
	
	public BigDecimal getVoucherv() {
		return voucherv;
	}

	public void setVoucherv(BigDecimal voucherv) {
		this.voucherv = voucherv;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCouponname() {
		return this.couponname;
	}

	public void setCouponname(String couponname) {
		this.couponname = couponname;
	}

	public Integer getSalesnum() {
		return this.salesnum;
	}

	public void setSalesnum(Integer salesnum) {
		this.salesnum = salesnum;
	}
	
	public Integer getUpSaleNum() {
		return upSaleNum;
	}

	public void setUpSaleNum(Integer upSaleNum) {
		this.upSaleNum = upSaleNum;
	}

	public Double getSalesprice() {
		return this.salesprice;
	}

	public void setSalesprice(Double salesprice) {
		this.salesprice = salesprice;
	}
	
	public Integer getSalesmode() {
		return this.salesmode;
	}

	public void setSalesmode(Integer salesmode) {
		this.salesmode = salesmode;
	}
	
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getApplycode() {
		return this.applycode;
	}

	public void setApplycode(String applycode) {
		this.applycode = applycode;
	}
	
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public Integer getApplystate() {
		return this.applystate;
	}

	public void setApplystate(Integer applystate) {
		this.applystate = applystate;
	}

	public Integer getExaminestate() {
		return this.examinestate;
	}

	public void setExaminestate(Integer examinestate) {
		this.examinestate = examinestate;
	}
	
	public String getApprovaltaskscode() {
		return this.approvaltaskscode;
	}

	public void setApprovaltaskscode(String approvaltaskscode) {
		this.approvaltaskscode = approvaltaskscode;
	}
	
	public String getAnnex() {
		return this.annex;
	}

	public void setAnnex(String annex) {
		this.annex = annex;
	}
	
	public String getContractcode() {
		return this.contractcode;
	}

	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
	}
	
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public int getSendNum() {
		return sendNum;
	}

	public void setSendNum(int sendNum) {
		this.sendNum = sendNum;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGenStatus() {
		return genStatus;
	}

	public void setGenStatus(int genStatus) {
		this.genStatus = genStatus;
	}
	
	public Date getNextReceiptDate() {
		return nextReceiptDate;
	}

	public void setNextReceiptDate(Date nextReceiptDate) {
		this.nextReceiptDate = nextReceiptDate;
	}
	
	public BigDecimal getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(BigDecimal receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	
	public int getReceiptstate() {
		return receiptstate;
	}

	public void setReceiptstate(int receiptstate) {
		this.receiptstate = receiptstate;
	}
	
	public String getApplicantUser() {
		return applicantUser;
	}

	public void setApplicantUser(String applicantUser) {
		this.applicantUser = applicantUser;
	}
	
	public String getApplyUnitName() {
		return applyUnitName;
	}

	public void setApplyUnitName(String applyUnitName) {
		this.applyUnitName = applyUnitName;
	}
	
	public int getExportedNum() {
		return exportedNum;
	}

	public void setExportedNum(int exportedNum) {
		this.exportedNum = exportedNum;
	}
	
	public String getPrintAuthCode() {
		return printAuthCode;
	}

	public void setPrintAuthCode(String printAuthCode) {
		this.printAuthCode = printAuthCode;
	}

	public int getAlreadySendNum() {
		return alreadySendNum;
	}

	public void setAlreadySendNum(int alreadySendNum) {
		this.alreadySendNum = alreadySendNum;
	}
    
	public String getRuleID() {
		return ruleID;
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}
	
	public boolean isExtractLock() {
		return extractLock;
	}

	public void setExtractLock(boolean extractLock) {
		this.extractLock = extractLock;
	}
	
	public Integer getCoupongeneration1() {
		return coupongeneration1;
	}

	public void setCoupongeneration1(Integer coupongeneration1) {
		this.coupongeneration1 = coupongeneration1;
	}
	
	public Integer getCoupongeneration2() {
		return coupongeneration2;
	}

	public void setCoupongeneration2(Integer coupongeneration2) {
		this.coupongeneration2 = coupongeneration2;
	}
	
	public String getCinemapara() {
		return cinemapara;
	}

	public void setCinemapara(String cinemapara) {
		this.cinemapara = cinemapara;
	}
	
	public String getExlFilePath() {
		return exlFilePath;
	}

	public void setExlFilePath(String exlFilePath) {
		this.exlFilePath = exlFilePath;
	}
	
	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	public Long getStopReceiptUser() {
		return stopReceiptUser;
	}

	public void setStopReceiptUser(Long stopReceiptUser) {
		this.stopReceiptUser = stopReceiptUser;
	}
	
	public String getImportfilename() {
		return importfilename;
	}

	public void setImportfilename(String importfilename) {
		this.importfilename = importfilename;
	}
	
	public String getAttachfilename() {
		return attachfilename;
	}

	public void setAttachfilename(String attachfilename) {
		this.attachfilename = attachfilename;
	}
	
	public Integer getIsRevise() {
		return isRevise;
	}

	public void setIsRevise(Integer isRevise) {
		this.isRevise = isRevise;
	}

	public Integer getVoucheIsPassWord() {
		return voucheIsPassWord;
	}

	public void setVoucheIsPassWord(Integer voucheIsPassWord) {
		this.voucheIsPassWord = voucheIsPassWord;
	}

	public Integer getVouchePassWordType() {
		return vouchePassWordType;
	}

	public void setVouchePassWordType(Integer vouchePassWordType) {
		this.vouchePassWordType = vouchePassWordType;
	}

	public String getVouchePassWord() {
		return vouchePassWord;
	}

	public void setVouchePassWord(String vouchePassWord) {
		this.vouchePassWord = vouchePassWord;
	}
	
	public Integer getMerRange() {
		return merRange;
	}

	public void setMerRange(Integer merRange) {
		this.merRange = merRange;
	}
	
	public Double getVoucheAmountLimit() {
		return voucheAmountLimit;
	}

	public void setVoucheAmountLimit(Double voucheAmountLimit) {
		this.voucheAmountLimit = voucheAmountLimit;
	}

	public String getCinemaCodes() {
		return cinemaCodes;
	}

	public void setCinemaCodes(String cinemaCodes) {
		this.cinemaCodes = cinemaCodes;
	}
	
	public Long getApplyUnitId() {
		return applyUnitId;
	}

	public void setApplyUnitId(Long applyUnitId) {
		this.applyUnitId = applyUnitId;
	}

	public Long getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(Long revisionId) {
		this.revisionId = revisionId;
	}
	
	public Integer getPricePrintType() {
		return pricePrintType;
	}

	public void setPricePrintType(Integer pricePrintType) {
		this.pricePrintType = pricePrintType;
	}

	public Date getActTime() {
		return actTime;
	}

	public void setActTime(Date actTime) {
		this.actTime = actTime;
	}

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCommonAccountId() {
		return commonAccountId;
	}

	public void setCommonAccountId(Long commonAccountId) {
		this.commonAccountId = commonAccountId;
	}

	public Long getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(Long couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	public Long getPrintLockUser() {
		return printLockUser;
	}

	public void setPrintLockUser(Long printLockUser) {
		this.printLockUser = printLockUser;
	}
	
}
