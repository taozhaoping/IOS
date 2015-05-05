package com.dahuatech.app.business;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.Gson;

/**
 * @ClassName BaseBusiness
 * @Description 业务逻辑基础类
 * @author 21291
 * @date 2014年6月4日 下午1:46:25
 */
public class BaseBusiness<T> {
	protected Gson gson;
	protected Context context;
	protected JSONArray jsonArray;
	protected JSONObject jsonObject;
	protected ResultMessage resultMessage;  
	protected ResponseMessage responseMessage;
	protected String responseErrorMessage;
	protected String serviceUrl;  			//服务地址
	private T t;				 			//泛型类型
	
	public T getT() {
		return t;
	}
	
	public void setT(T t) {
		this.t = t;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public BaseBusiness(Context context) {
		this.context = context;
		this.gson=GsonHelper.getInstance();
		serviceUrl="";
	}
	
	public BaseBusiness(Context context,T t) {
		this(context);
		this.t=t;
	}
	
	/** 
	* @Title: getEntityInfo 
	* @Description: 获取实体信息
	* @param @param taskParam
	* @param @return     
	* @return T    
	* @throws 
	* @author 21291
	* @date 2014年7月16日 上午9:53:11
	*/
	@SuppressWarnings("unchecked")
	public T getEntityInfo(TaskParamInfo taskParam){
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = TaskParamInfo.ConvertToJson(taskParam);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
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
					t = (T) gson.fromJson(jsonObject.toString(),t.getClass());
				}
			} else {
				responseErrorMessage = "状态码为:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
