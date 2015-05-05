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
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;
import com.dahuatech.app.bean.expense.FlowSearchParamInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExpenseFlowItemBusiness
 * @Description 我的流水客户/项目业务逻辑类
 * @author 21291
 * @date 2014年9月1日 下午6:48:14
 */
public class ExpenseFlowItemBusiness extends BaseBusiness<Void> {

	private List<ExpenseFlowItemInfo> arrayList;
	private Type listType;
	
	private ExpenseFlowItemBusiness(Context context) {
		super(context);
		listType = new TypeToken<ArrayList<ExpenseFlowItemInfo>>() {}.getType();	
	}
	
	//单例模式(线程安全写法)
	private static ExpenseFlowItemBusiness expenseFlowItemBusiness;	
	public static ExpenseFlowItemBusiness getExpenseFlowItemBusiness(Context context,String serviceUrl){
		if(expenseFlowItemBusiness==null)
		{
			expenseFlowItemBusiness=new ExpenseFlowItemBusiness(context);
		}
		expenseFlowItemBusiness.setServiceUrl(serviceUrl);
		return expenseFlowItemBusiness;
	}
	
	/** 
	* @Title: getExpenseFlowItemList 
	* @Description: 获取客户/项目列表集合
	* @param @param flowSearchParamInfo
	* @param @return     
	* @return List<ExpenseFlowItemInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月1日 下午7:38:23
	*/
	public List<ExpenseFlowItemInfo> getExpenseFlowItemList(FlowSearchParamInfo flowSearchParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList=new ArrayList<ExpenseFlowItemInfo>();  //返回结果
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=FlowSearchParamInfo.ConvertToJson(flowSearchParamInfo);
			jsonData=DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
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
