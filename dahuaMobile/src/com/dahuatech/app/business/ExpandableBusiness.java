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
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.ExpandableInfo;
import com.dahuatech.app.bean.expense.FlowParamInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExpandableBusiness
 * @Description 我的流水业务逻辑类
 * @author 21291
 * @date 2014年8月27日 下午5:24:31
 */
public class ExpandableBusiness extends BaseBusiness<Void> {

	private List<ExpandableInfo> arrayList;
	private Type listType;
	
	private ExpandableBusiness(Context context) {
		super(context);
		listType = new TypeToken<ArrayList<ExpandableInfo>>() {}.getType();	
	}
	
	//单例模式(线程不安全写法)
	private static ExpandableBusiness expandableBusiness;	
	public static ExpandableBusiness getExpandableBusiness(Context context,String serviceUrl){
		if(expandableBusiness==null)
		{
			expandableBusiness=new ExpandableBusiness(context);
		}
		expandableBusiness.setServiceUrl(serviceUrl);
		return expandableBusiness;
	}
	
	/** 
	* @Title: getExpandableList 
	* @Description: 获取我的流水可展开实体集合
	* @param @param flowParamInfo
	* @param @return     
	* @return List<ExpandableInfo>    
	* @throws 
	* @author 21291
	* @date 2014年8月27日 下午6:10:36
	*/
	public List<ExpandableInfo> getExpandableList(FlowParamInfo flowParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList=new ArrayList<ExpandableInfo>();  //返回结果
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=FlowParamInfo.ConvertToJson(flowParamInfo);
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
