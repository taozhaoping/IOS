package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.UserInfo;
import com.dahuatech.app.common.DESUtils;

/**
 * @ClassName UserLoginBussiness
 * @Description 登陆验证业务逻辑类
 * @author 21291
 * @date 2014年3月31日 上午9:41:21
 */
public class UserLoginBussiness extends BaseBusiness<Void> {
	private UserLoginBussiness(Context context) {
		super(context);
	}

	//单例模式(线程不安全写法)
	private static UserLoginBussiness userLoginBussiness=null;	
	public static UserLoginBussiness getUserLoginBussiness(Context context,String serviceUrl){
		if(userLoginBussiness==null){
			userLoginBussiness = new UserLoginBussiness(context);	
		}
		userLoginBussiness.setServiceUrl(serviceUrl);
		return userLoginBussiness;
	}
	
	/** 
	* @Title: loginVerify 
	* @Description: 登陆验证逻辑
	* @param @param userInfo
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月21日 下午12:02:31
	*/
	public String loginVerify(UserInfo userInfo) {
		String returnString="";
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			String jsonData=UserInfo.ConvertToJson(userInfo);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData, DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// 获取响应信息
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				if(resultMessage.isIsSuccess()){
					returnString=resultMessage.getResult();
				}
			} else {
				responseErrorMessage = "状态码为:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "具体错误信息为:" + responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return returnString;
	}
	
	/** 
	* @Title: verifyValid 
	* @Description: 验证员工工号是否有效
	* @param @param fItemNumber
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2015年2月5日 下午4:41:00
	*/
	public ResultMessage verifyValid(String fItemNumber) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			String jsonData=DESUtils.toHexString(DESUtils.encrypt(fItemNumber,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// 获取响应信息
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
			} else {
				responseErrorMessage = "状态码为:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "具体错误信息为:" + responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return resultMessage;
	}	
}
