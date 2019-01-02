package com.oristartech.marketing.components.constant;
/**
 * @author wangweiheng
 * @date 2018-08-17 18:15:11
 */
public class CommonRequest {
    /** 请求号 */
    private String requestNo;

    /** 请求时间 */
    private String requestTime;

    /** 版本号 */
    private String version = "1.0.0";

    /** 请求数据 */
    private String requestData;

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        return "CommonRequest{" +
                "requestNo='" + requestNo + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
