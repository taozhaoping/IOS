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
import com.dahuatech.app.bean.mytask.ExpensePrivateTBodyParam;
import com.dahuatech.app.bean.mytask.ExpenseSpecialTBodyInfo;
import com.dahuatech.app.common.DESUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExpenseSpecialTBodyBusiness
 * @Description 报销特殊事务单据表体业务逻辑类
 * @author 21291
 * @date 2014年6月20日 上午10:02:35
 */
public class ExpenseSpecialTBodyBusiness extends BaseBusiness<Void> {
	private List<ExpenseSpecialTBodyInfo> arrayList;
	private Type listType;
	public ExpenseSpecialTBodyBusiness(Context context) {
		super(context);
		arrayList = new ArrayList<ExpenseSpecialTBodyInfo>();
		listType = new TypeToken<ArrayList<ExpenseSpecialTBodyInfo>>() {}.getType();
	}
	
	//单例模式(线程不安全写法)
	private static ExpenseSpecialTBodyBusiness expenseSpecialTBodyBusiness;
	public static ExpenseSpecialTBodyBusiness getExpenseSpecialTBodyBusiness(Context context,String serviceUrl) {
		if(expenseSpecialTBodyBusiness==null){
			expenseSpecialTBodyBusiness=new ExpenseSpecialTBodyBusiness(context);
		}
		expenseSpecialTBodyBusiness.setServiceUrl(serviceUrl);
		return expenseSpecialTBodyBusiness;
	}
	
	/** 
	* @Title: getExpenseSpecialTBodyList 
	* @Description: 获取特殊事务报销单据表体实体集合
	* @param @param eBodyParam
	* @param @return     
	* @return List<ExpensePrivateTBodyInfo>    
	* @throws 
	* @author 21291
	* @date 2014年6月20日 上午9:56:36
	*/
	public List<ExpenseSpecialTBodyInfo> getExpenseSpecialTBodyList(ExpensePrivateTBodyParam eBodyParam){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=ExpensePrivateTBodyParam.ConvertToJson(eBodyParam);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData, DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// 请求方法
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);			
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// 说明返回成功
				if (resultMessage.isIsSuccess()) {
					String returnStr=resultMessage.getResult().replaceAll("(?s)&lt;.*?&gt;", "");
					jsonArray = new JSONArray(returnStr);
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
