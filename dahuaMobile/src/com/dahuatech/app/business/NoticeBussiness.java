package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;

/**
 * @ClassName MainBusiness
 * @Description 通知栏的业务逻辑类
 * @author 21291
 * @date 2014年5月20日 上午11:20:19
 */
public class NoticeBussiness extends BaseBusiness<Void> {
	private NoticeBussiness(Context context) {
		super(context);
	}

	//单例模式(线程不安全写法)
	private static NoticeBussiness noticeBussiness=null;
	public static NoticeBussiness getNoticeBussiness(Context context,String serviceUrl){
		if(noticeBussiness==null){
			noticeBussiness = new NoticeBussiness(context);	
		}
		noticeBussiness.setServiceUrl(serviceUrl);
		return noticeBussiness;
	}
	
	/** 
	* @Title: getTaskCount 
	* @Description: 获取登陆人待办审批任务数
	* @param @param fItemNumber
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年5月20日 上午11:22:33
	*/
	public String getTaskCount(String fItemNumber) {
		String returnString="0";
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			apiClient.AddParam("jsonData", fItemNumber);
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

}
