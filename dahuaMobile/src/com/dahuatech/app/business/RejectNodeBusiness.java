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
import com.dahuatech.app.bean.mytask.PlusCopyInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName RejectNodeBusiness
 * @Description 驳回节点业务逻辑类
 * @author 21291
 * @date 2014年7月28日 下午6:03:16
 */
public class RejectNodeBusiness extends BaseBusiness<Void> {
	private List<RejectNodeInfo> arrayList;
	private List<PlusCopyPersonInfo> plusCopyPersonList;
	private Type listType,plusCopyListType;
	private ApiClient apiClient;
	
	public RejectNodeBusiness(Context context){
		super(context);
		listType = new TypeToken<ArrayList<RejectNodeInfo>>() {}.getType();	
		plusCopyListType=new TypeToken<ArrayList<PlusCopyPersonInfo>>() {}.getType();	
	}
	
	//单例模式(线程不安全写法)
	private static RejectNodeBusiness rejectNodeBusiness;	
	public static RejectNodeBusiness getRejectNodeBusiness(Context context,String serviceUrl){
		if(rejectNodeBusiness==null)
		{
			rejectNodeBusiness=new RejectNodeBusiness(context);
		}
		rejectNodeBusiness.setServiceUrl(serviceUrl);
		return rejectNodeBusiness;
	}
	
	/** 
	* @Title: getRejectNodeInfo 
	* @Description: 获取驳回节点信息
	* @param @param repository
	* @param @param params
	* @param @return     
	* @return List<RejectNodeInfo>    
	* @throws 
	* @author 21291
	* @date 2014年7月29日 上午11:02:36
	*/
	public List<RejectNodeInfo> getRejectNodeInfo(RepositoryInfo repository,String params){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList = new ArrayList<RejectNodeInfo>();
			
			// 获取API实例
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
				// 说明返回成功
				if (resultMessage.isIsSuccess()) {
					String returnStr=resultMessage.getResult().replaceAll("(?s)&lt;.*?&gt;", "");
					if(!StringUtils.isEmpty(returnStr)){
						jsonArray = new JSONArray(returnStr);
						arrayList = gson.fromJson(jsonArray.toString(), listType);
					}
					else {
						arrayList = new ArrayList<RejectNodeInfo>();
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
	* @Title: getPlusCopyPersonList 
	* @Description: 获取加签/抄送查询人员信息
	* @param @param jsonData 查询参数
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午10:11:29
	*/
	public List<PlusCopyPersonInfo> getPlusCopyPersonList(String jsonData){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			plusCopyPersonList=new ArrayList<PlusCopyPersonInfo>();  //返回结果
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
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
					jsonArray = new JSONArray(resultMessage.getResult());
					plusCopyPersonList = gson.fromJson(jsonArray.toString(), plusCopyListType);
				}
			} else {
				responseErrorMessage = "状态码为:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plusCopyPersonList;
	}
	
	/** 
	* @Title: plusCopyApp 
	* @Description: 加签/抄送审批操作
	* @param @param plusCopyInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年9月26日 上午10:11:38
	*/
	public ResultMessage plusCopyApp(PlusCopyInfo plusCopyInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			String jsonData=PlusCopyInfo.ConvertToJson(plusCopyInfo);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
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
