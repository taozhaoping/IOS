package com.dahuatech.app.business;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.SqlParametersInfo;
import com.dahuatech.app.bean.mytask.WorkFlowInfo;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName WorkFlowBusiness
 * @Description 工作流业务逻辑类
 * @author 21291
 * @date 2014年4月25日 下午1:40:32
 */
public class WorkFlowBusiness extends BaseBusiness<Void>{
	private List<WorkFlowInfo> arrayList;
	private Type listType;
	private ApiClient apiClient;

	private WorkFlowBusiness(Context context) {
		super(context);
		arrayList = new ArrayList<WorkFlowInfo>();
		listType = new TypeToken<ArrayList<WorkFlowInfo>>() {}.getType();
	}
	
	//单例模式(线程不安全写法)
	private static WorkFlowBusiness workFlowBusiness;	
	public static WorkFlowBusiness getWorkFlowBusiness(Context context){
		if(workFlowBusiness==null)
		{
			workFlowBusiness=new WorkFlowBusiness(context);
		}
		return workFlowBusiness;
	}
	
	/** 
	* @Title: getWorkFlowInfo 
	* @Description: 获取存在的审批记录
	* @param @param repository
	* @param @param sqlParameters
	* @param @return     
	* @return List<WorkFlowInfo>    
	* @throws 
	* @author 21291
	* @date 2014年4月28日 上午10:00:47
	*/
	public List<WorkFlowInfo> getWorkFlowInfo(RepositoryInfo repository,SqlParametersInfo sqlParameters){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			String jsonParams=SqlParametersInfo.ConvertToJson(sqlParameters);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",jsonParams);
			arrayList = new ArrayList<WorkFlowInfo>();  //返回结果
			
			// 请求方法
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// 说明返回成功
				if (resultMessage.isIsSuccess()) {
					String returnStr=resultMessage.getResult().replaceAll("(?s)&lt;.*?&gt;", "");
					if(!StringUtils.isEmpty(returnStr)){
						jsonArray = new JSONArray(returnStr);
						arrayList = gson.fromJson(jsonArray.toString(), listType);
					}
				}
			} else {
				responseErrorMessage = "状态码为:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}
	
	/** 
	* @Title: approveHandle 
	* @Description: 审批操作 通过/驳回
	* @param @param repository
	* @param @param params
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年4月28日 上午10:28:07
	*/
	public ResultMessage approveHandle(RepositoryInfo repository,String params) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 实例化对象
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",params);
			// 请求方法
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
			} else {
				responseErrorMessage = "状态码为:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMessage;
	}
}
