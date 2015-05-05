package com.dahuatech.app.bean;


/**
 * @ClassName ResponseMessage
 * @Description WCF调用后，响应消息实体值
 * @author 21291
 * @date 2014年4月21日 上午10:04:30
 */
public class ResponseMessage extends Base {

	//序列化唯一码
    private static final long serialVersionUID = 1L;	
    public ResponseMessage() {
        responseCode=0;
        responseMessage="";
        responseErrorMessage="";
    }
    
    private Integer responseCode;  			//响应状态码 
    private String responseMessage;			//响应实体值
    private String responseErrorMessage;	//错误信息

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
    public String getResponseErrorMessage() {
		return responseErrorMessage;
	}

	public void setResponseErrorMessage(String responseErrorMessage) {
		this.responseErrorMessage = responseErrorMessage;
	}
}
