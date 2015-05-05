package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.EngineeringInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.common.StringUtils;

/**
 * @ClassName EngineeringBusiness
 * @Description 工程商单据实体业务逻辑类
 * @author 21291
 * @date 2014年4月24日 下午4:24:33
 */
public class EngineeringBusiness extends BaseBusiness<EngineeringInfo> {
	private EngineeringInfo engineeringInfo;
	private EngineeringBusiness(Context context) {
		super(context);
		engineeringInfo=EngineeringInfo.getEngineeringInfo();
	}
	
	//单例模式(线程不安全写法)
	private static EngineeringBusiness engineeringBusiness;
	public static EngineeringBusiness getEngineeringBusiness(Context context,String serviceUrl) {
		if(engineeringBusiness==null){
			engineeringBusiness=new EngineeringBusiness(context);
		}
		engineeringBusiness.setServiceUrl(serviceUrl);
		return engineeringBusiness;
	}
	
	/** 
	* @Title: getEngineeringInfo 
	* @Description: 根据任务参数实体项，获取工程商实体信息
	* @param @param taskParam
	* @param @return     
	* @return EngineeringInfo    
	* @throws 
	* @author 21291
	* @date 2014年4月24日 下午4:54:10
	*/
	public EngineeringInfo getEngineeringInfo(TaskParamInfo taskParam) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=TaskParamInfo.ConvertToJson(taskParam);
			apiClient.AddParam("jsonData", jsonData);
			// 请求方法
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// 说明返回成功
				if (resultMessage.isIsSuccess() && !StringUtils.isEmpty(resultMessage.getResult())) {
					jsonObject = new JSONObject(resultMessage.getResult());
					engineeringInfo = gson.fromJson(jsonObject.toString(), EngineeringInfo.class);
				}
			} else {
				responseErrorMessage = "状态码为:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return engineeringInfo;
	}

}
