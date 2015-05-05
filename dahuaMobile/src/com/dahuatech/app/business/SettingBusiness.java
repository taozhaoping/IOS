package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.AppException;
import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.UpdateInfo;
import com.google.gson.JsonParseException;

/**
 * @ClassName SettingBusiness
 * @Description 系统参数业务逻辑类
 * @author 21291
 * @date 2014年4月22日 上午10:00:00
 */
public class SettingBusiness extends BaseBusiness<Void> {
	private SettingBusiness(Context context) {
		super(context);
	}
	// 单例模式(线程不安全写法)
	private static SettingBusiness settingBusiness;
	public static SettingBusiness getSettingBusiness(Context context,String serviceUrl) {
		if (settingBusiness == null) {
			settingBusiness = new SettingBusiness(context);
		}
		settingBusiness.setServiceUrl(serviceUrl);
		return settingBusiness;
	}

	/**
	 * @Title: checkVersion
	 * @Description: 检查版本更新
	 * @param @return
	 * @return Update
	 * @throws
	 * @author 21291
	 * @date 2014年4月22日 上午11:10:27
	 */
	public UpdateInfo checkVersion() throws AppException {
		UpdateInfo updateInfo=null;
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 获取响应信息
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.GET);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				if (resultMessage.isIsSuccess()) {
					try {
						updateInfo = gson.fromJson(resultMessage.getResult(), UpdateInfo.class);
					}
					catch (JsonParseException e) {
						e.printStackTrace();			
					}
				}
			} else {
				responseErrorMessage = "状态码为:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "具体错误信息为:"
						+ responseMessage.getResponseErrorMessage();
			}

		} catch (Exception e) {
			if(e instanceof AppException)
				throw (AppException)e;
			throw AppException.network(e);
		}

		return updateInfo;
	}
	
	/** 
	* @Title: SendLogRecord 
	* @Description: 发送日志统计实体信息
	* @param @param logInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月31日 上午10:30:19
	*/
	public void SendLogRecord(LogsRecordInfo logInfo){
		try {
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = LogsRecordInfo.ConvertToJson(logInfo);
			apiClient.AddParam("jsonData", jsonData);
			// 请求方法
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() != HttpStatus.SC_OK) {
				responseErrorMessage = "状态码为:"+ responseMessage.getResponseCode().toString() + " "
				+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
