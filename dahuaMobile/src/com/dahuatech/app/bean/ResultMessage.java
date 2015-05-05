package com.dahuatech.app.bean;


/**
 * @ClassName ResultMessage
 * @Description 从WCF调用数据，返回一个JSON结果信息对象
 * @author 21291
 * @date 2014年4月21日 上午10:02:23
 */
public class ResultMessage extends Base {
    
	//序列化唯一码
    private static final long serialVersionUID = 1L;
    private boolean IsSuccess;  //返回标志
    private String Result;      //结果
    public boolean isIsSuccess() {
        return IsSuccess;
    }
    public void setIsSuccess(boolean isSuccess) {
        IsSuccess = isSuccess;
    }
    public String getResult() {
        return Result;
    }
    public void setResult(String result) {
        Result = result;
    }
    
    public ResultMessage(){
        IsSuccess=false;
        Result="";
    }
}
