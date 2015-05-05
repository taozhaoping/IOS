package com.dahuatech.app.business;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.ContactInfo;
import com.dahuatech.app.bean.ContactInfo.ContactResultInfo;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactsBusiness extends BaseBusiness<ContactResultInfo> {
	private ContactResultInfo contactResult;
	private Type listType;
	private Gson gson;
	
	public ContactsBusiness(Context context) {
		super(context);
		contactResult=new ContactInfo.ContactResultInfo();
		listType = new TypeToken<ArrayList<ContactInfo>>() {}.getType();	
		this.gson=GsonHelper.getInstance();
	}
	
	//单例模式(线程安全写法)
	private static ContactsBusiness contactsBusiness;	
	public static ContactsBusiness getContactsBusiness(Context context,String serviceUrl){
		if(contactsBusiness==null)
		{
			contactsBusiness=new ContactsBusiness(context);
		}
		contactsBusiness.setServiceUrl(serviceUrl);
		return contactsBusiness;
	}
	
	/** 
	* @Title: getContactsList 
	* @Description: 根据查询条件获取通讯录信息
	* @param @param jsonData
	* @param @return     
	* @return ContactResultInfo resultStr值：1-查询失败，至少输入两个字  2 -查询失败，未查到信息
	* @throws 
	* @author 21291
	* @date 2014年6月26日 下午6:07:37
	*/
	public ContactResultInfo getContactsList(String jsonData){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData, DESUtils.DEFAULT_KEY));
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
					contactResult.contactList=gson.fromJson(jsonArray.toString(), listType);
					contactResult.resultStr="";
				}
				else {
					contactResult.contactList=new ArrayList<ContactInfo>();
					contactResult.resultStr=resultMessage.getResult();
				}		
			} else {
				responseErrorMessage = "状态码为:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactResult;
	}

}
