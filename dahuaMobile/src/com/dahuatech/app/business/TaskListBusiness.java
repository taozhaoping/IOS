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
import com.dahuatech.app.bean.StorageParameterInfo;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName TaskListBusiness
 * @Description 任务列表业务逻辑类
 * @author 21291
 * @date 2014年4月23日 下午2:32:56
 */
public class TaskListBusiness extends BaseBusiness<Void> {
	private List<TaskInfo> arrayList;
	private Type listType;
	
	private TaskListBusiness(Context context) {
		super(context);
		listType = new TypeToken<ArrayList<TaskInfo>>() {}.getType();	
	}
	
	//单例模式(线程安全写法)
	private static TaskListBusiness taskListBusiness;	
	public static TaskListBusiness getTaskListBusiness(Context context,String serviceUrl){
		if(taskListBusiness==null)
		{
			taskListBusiness=new TaskListBusiness(context);
		}
		taskListBusiness.setServiceUrl(serviceUrl);
		return taskListBusiness;
	}
	
	/** 
	* @Title: getTaskList 
	* @Description: 获取任务列表结果集
	* @param @param repository
	* @param @param storageParameter
	* @param @return     
	* @return List<TaskInfo>    
	* @throws 
	* @author 21291
	* @date 2014年4月23日 下午3:04:15
	*/
	public List<TaskInfo> getTaskList(RepositoryInfo repository,List<StorageParameterInfo> storageParameter){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			String jsonParams=StorageParameterInfo.ConvertToJson(storageParameter);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",jsonParams);
			arrayList=new ArrayList<TaskInfo>();  //返回结果
			
			// 请求方法
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// 说明返回成功
				if (resultMessage.isIsSuccess() && !StringUtils.isEmpty(resultMessage.getResult())) {
					jsonArray = new JSONArray(resultMessage.getResult());
					arrayList = gson.fromJson(jsonArray.toString(), listType);
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

}
