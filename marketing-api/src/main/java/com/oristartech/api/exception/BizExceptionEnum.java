package com.oristartech.api.exception;

/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {

    /**
     * 字典
     */
    DICT_EXISTED(400, "字典已经存在"),
    ERROR_CREATE_DICT(500, "创建字典失败"),
    ERROR_WRAPPER_FIELD(500, "包装字典属性失败"),
    ERROR_CODE_EMPTY(500, "字典类型不能为空"),

    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "FILE_READING_ERROR!"),
    FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"),
    UPLOAD_ERROR(400, "上传出错"),

    FILE_TYPE_ERROR(400,"文件类型不允许"),
    FILE_SIZE_BIG(400,"文件过大"),

    /**
     * 权限和数据问题
     */
    DB_RESOURCE_NULL(400, "数据库中没有该资源"),
    NO_PERMITION(405, "权限异常"),
    REQUEST_INVALIDATE(400, "请求数据格式不正确"),
    INVALID_KAPTCHA(400, "验证码不正确"),
    CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
    CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
    CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),

    /**
     * 账户问题
     */
    USER_ALREADY_REG(401, "该用户已经注册"),
    NO_THIS_USER(400, "没有此用户"),
    USER_NOT_EXISTED(400, "没有此用户"),
    ACCOUNT_FREEZED(401, "账号被冻结"),
    OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
    TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),

    /**
     * 错误的请求
     */
    MENU_PCODE_COINCIDENCE(400, "菜单编号和副编号不能一致"),
    EXISTED_THE_MENU(400, "菜单编号重复，不能添加"),
    DICT_MUST_BE_NUMBER(400, "字典的值必须为数字"),
    REQUEST_NULL(400, "请求有错误"),
    SESSION_TIMEOUT(400, "会话超时"),
    SERVER_ERROR(500, "服务器异常"),

    /**
     * 商户模块
     */
    MERCHANT_OVERSIZE_ERROR(602,"附件大小不能超过50M"),
    MERCHANT_PAGE_ERROR(603,"页码不能为空"),
    MERCHANT_PARAM_NULL_ERROR(604,"参数不能为空"),
    MERCHANT_ACCOUNT_ERROR(605,"不存在此账号，请确认后输入"),
    MERCHANT_PASSWORD_ERROR(605,"密码错误，请重新登录"),
    MERCHANT_IDENTIFYING_CODE_ERROR(605,"验证失败，请重新输入"),
    MERCHANT_ACCOUNT_STOP_ERROR(605,"此账号已被停用"),
    MERCHANT_STATUS_ERROR(605,"此商户状态已更新，请刷新后重试"),
    MERCHANT_SEND_SMS_ERROR(606,"请求过于频繁，请60s后获取"),
    MERCHANT_AGREEMENT_EXIST_ERROR(607,"当前时间内已存在协议"),
    MERCHANT_AGREEMENT_DATE_ERROR(607,"协议结束时间不能小于开始时间"),
    MERCHANT_AGREEMENT_STOP_ERROR(607,"协议无效"),
    MERCHANT_DELETE_EXIST_AGREEMENT_ERROR(607,"请删除商户相关协议后，再做操作"),
    MERCHANT_CINEMA_EXIST_ERROR(608,"添加影院已被其他商户添加，请刷新列表再做操作"),
    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_EXPIRED(800, "请求超时"),
    SIGN_ERROR(800, "签名验证失败"),

    /**
     * 公司异常
     */
    SYSTEM_ERROR(400,"系统异常"),
    ADD_ERROR(400,"添加失败"),
    EDIT_ERROR(400,"修改失败"),
    DEL_ERROR(400,"删除失败"),
    DEL_IS(400,"删除失败,已被删除"),
    COMPANY_NAME_ERROR(400,"公司名称已经存在"),
    PARAMETER_ERROR(400,"参数异常"),
    UNICODE_ERROR(400,"专资码不能重复"),
    DEL_ERROR_IS_USE(400,"删除失败！数据使用中"),


    CINEMA_INVESTS_ERROR(400,"最多设置5个"),
    CINEMA_NUM_ERROR(400,"最多设置100个"),

    /**
     * 通用错误类型
     */

    COMMON_ADD_ERROR(20002,"添加异常"),
    COMMON_DEL_ERROR(20002,"删除异常"),
    COMMON_EDIT_ERROR(20002,"修改异常"),
    COMMON_SEARCH_ERROR(20002,"查询异常"),

    MISSING_PARAMETER(40001,"缺少必选参数"),
    INVALID_PARAMETER(40002,"参数无效"),

    COMMON_ADD_FAIL(40004,"添加失败"),
    COMMON_DEL_FAIL(40004,"删除失败"),
    COMMON_EDIT_FAIL(40004,"修改失败"),
    COMMON_SEARCH_FAIL(40004,"查询失败"),

    MISSING_MOVIE_ID_EMPTY(MISSING_PARAMETER.getCode(),"影片id不能为空"),
    MISSING_COMPANY_ID_EMPTY(MISSING_PARAMETER.getCode(),"公司id不能为空"),
    MISSING_MOVIEID(MISSING_PARAMETER.getCode(),"movieId不能为空"),
    MISSING_FILE(MISSING_PARAMETER.getCode(),"上传文件不能为空"),
    INVALID_FILE_TYPE(INVALID_PARAMETER.getCode(),"文件类型无效"),

    INVALID_SORT_VAlUE(INVALID_PARAMETER.getCode(),"排序字段无效"),
    INVALID_ORDER(INVALID_PARAMETER.getCode(),"排序类型无效"),

    INVALID_FILE_EXTENSION(INVALID_PARAMETER.getCode(),"文件扩展名无效"),

    INVALID_END_GREATER_BEGIN(INVALID_PARAMETER.getCode(),"开始时间不能大于结束时间"),
    INVALID_EMOJI_NOT_SUPPORT(INVALID_PARAMETER.getCode(),"不支持保存表情符号"),

    /**
     *影片
     */
    MISSING_MOVIE_NAME(MISSING_PARAMETER.getCode(),"影片名称不能为空"),
    INVALID_MOVIE_FABRICATION_STYLE(MISSING_PARAMETER.getCode(),"%s制式重复,专资编码和制式一对一"),
    INVALID_MOVIE_UNICODE(MISSING_PARAMETER.getCode(),"%s编码重复,专资编码和制式一对一"),

    MOVIE_NOT_EXIST(INVALID_PARAMETER.getCode(),"影片不存在"),

    MOVIE_UNICODE_EXIST(MISSING_PARAMETER.getCode(),"%s专资编码已存在"),

    /**
     * 影人
     */
    MISSING_ACTOR_NAME(MISSING_PARAMETER.getCode(),"影人姓名不能为空"),
    ACTOR_NOT_EXIST(INVALID_PARAMETER.getCode(),"影人不存在"),
    ACTOR_DEL_REPEAT_FAIL(COMMON_DEL_FAIL.getCode(),"请勿重复操作"),
    ACTOR_DEL_MOVIE_NOT_EMPTY(COMMON_DEL_FAIL.getCode(),"影人有参与影片不允许删除"),
    INVALID_BIRTHDAY_GREATER_DEATHDAY(INVALID_END_GREATER_BEGIN.getCode(),"身故日需晚于出生日期"),

    /**
     * 影片演职人员
     */
    MISSING_MOVIE_ACTOR_EDIT_DATA(COMMON_EDIT_FAIL.getCode(),"删除演职人员和添加演职人员不能都为空"),
    INVALID_MOVIE_ACTOR_ADD_REPEAT(COMMON_EDIT_FAIL.getCode(),"%s重复添加"),
    INVALID_MOVIE_ACTOR_NOT_EXIST(COMMON_EDIT_FAIL.getCode(),"修改演职人员失败,%s记录不存在"),
    INVALID_MOVIE_ACTOR_EXIST(COMMON_EDIT_FAIL.getCode(),"已存在"),
    MOVIE_ACTOR_DEL_NOT_MATCH(COMMON_DEL_FAIL.getCode(),"演职人员删除失败,删除记录不匹配"),


    /**
     * 影片相关公司
     */
    MISSING_MOVIE_COMPANY_EDIT_DATA(COMMON_EDIT_FAIL.getCode(),"删除公司和添加公司不能都为空"),
    INVALID_MOVIE_COMPANY_ADD_REPEAT(COMMON_EDIT_FAIL.getCode(),"%s重复添加"),
    INVALID_MOVIE_COMPANY_NOT_EXIST(COMMON_EDIT_FAIL.getCode(),"修改相关公司失败,记录不存在%s"),
    INVALID_MOVIE_COMPANY_EXIST(COMMON_EDIT_FAIL.getCode(),"已存在"),
    MOVIE_COMPANY_DEL_NOT_MATCH(COMMON_DEL_FAIL.getCode(),"相关公司删除失败,删除记录不匹配"),

    /**
     * 影片剧照
     */
    MISSING_MOVIE_PIC_EDIT_DATA(COMMON_EDIT_FAIL.getCode(),"删除图片和添加图片不能都为空"),
    MOVIE_PIC_EDIT_FAIL(COMMON_EDIT_FAIL.getCode(),"影片剧照修改失败"),

    /**
     * 影片备案
     */
    INVALID_REMARK_CODE(INVALID_PARAMETER.getCode(),"备案号格式不正确"),
    MOVIE_REMARK_CODE_EXIST(COMMON_EDIT_FAIL.getCode(),"备案号已存在"),
    MOVIE_REMARK_NOT_EXIST(COMMON_EDIT_FAIL.getCode(),"备案记录不存在"),
    MOVIE_REMARK_EXIST(COMMON_EDIT_FAIL.getCode(),"影片备案信息已存在，请勿重复添加"),
    MOVIE_REMARK_COMPANY_EXIST(COMMON_EDIT_FAIL.getCode(),"%s备案公司重复"),
    MOVIE_REMARK_ADD_FAIL(COMMON_ADD_FAIL.getCode(),"备案信息添加失败"),

    MOVIE_REMARK_EDIT_ID_NOT_MATCH(COMMON_EDIT_FAIL.getCode(),"备案公司删除失败，remove id 记录数不匹配"),
    MOVIE_REMARK_DEL_ID_NOT_MATCH(COMMON_DEL_FAIL.getCode(),"备案公司更新失败，remove id 记录数不匹配"),

    /**
	 * 营销
	 */
    MKT_MISSING_ID(MISSING_PARAMETER.getCode(),"活动ID不能为空"),
	MKT_MISSING_TENANT_ID(MISSING_PARAMETER.getCode(),"租户ID不能为空"),
	MKT_INVALID_MEMBER_ACTIVITY_CODE(INVALID_PARAMETER.getCode(),"生成会员营销活动编号失败"),
	MKT_INVALID_ACTIVITY_CODE(INVALID_PARAMETER.getCode(),"生成营销活动编号失败"),
	
	MKT_SAVE_RULETASK_FAIL(INVALID_PARAMETER.getCode(),"保存规则任务失败"),
	MKT_MISSING_RULE_TEMPLATE_TYPE(MISSING_PARAMETER.getCode(),"缺少规则模板类型参数"),
    /**
     *系统管理
     */
    SYSTEM_VALIDATE_FAIL(201,"参数不正确"),
    SYSTEM_EXCEPTION(201,"系统异常"),
    SYSTEM_INITPASSOWRD_EXCEPTION(202,"你的密码为初始密码，存在较大安全隐患请尽快修改您的密码"),
    SYSTEM_NO_AUTH(444, "非法请求，请重新登录"),

    /**
     * 其他
     */
    AUTH_REQUEST_EXCEPTION(401, "退出异常"),
    AUTH_USER_NOTACTIVE(401, "用户无效,请联系管理员!"),
    AUTH_REQUEST_ERROR(401, "账号密码错误"),
    AUTH_SMS_EXPIRE(401, "动态密码错误");


    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
