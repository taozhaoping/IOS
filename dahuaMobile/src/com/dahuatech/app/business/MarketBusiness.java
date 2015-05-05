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
import com.dahuatech.app.bean.market.MarketBidHistoryInfo;
import com.dahuatech.app.bean.market.MarketBidInfo;
import com.dahuatech.app.bean.market.MarketContractHistoryInfo;
import com.dahuatech.app.bean.market.MarketContractInfo;
import com.dahuatech.app.bean.market.MarketProductInfo;
import com.dahuatech.app.bean.market.MarketSearchParamInfo;
import com.dahuatech.app.bean.market.MarketWorkflowInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MarketBusiness extends BaseBusiness<Void> {

	private List<MarketBidInfo> bidList;
	private List<MarketContractInfo> contractList;
	private List<MarketProductInfo> productList;
	private List<MarketWorkflowInfo> workflowList;
	private Type bidListType,contractListType,productListType,workflowListType;
	private Gson gson;
	private ApiClient apiClient;
	
	public MarketBusiness(Context context) {
		super(context);
		bidListType = new TypeToken<ArrayList<MarketBidInfo>>() {}.getType();	
		contractListType = new TypeToken<ArrayList<MarketContractInfo>>() {}.getType();	
		productListType= new TypeToken<ArrayList<MarketProductInfo>>() {}.getType();
		workflowListType=new TypeToken<ArrayList<MarketWorkflowInfo>>() {}.getType();	
		this.gson=GsonHelper.getInstance();
	}
	
	//单例模式(线程安全写法)
	private static MarketBusiness marketBusiness;	
	public static MarketBusiness getMarketBusiness(Context context,String serviceUrl){
		if(marketBusiness==null)
		{
			marketBusiness=new MarketBusiness(context);
		}
		marketBusiness.setServiceUrl(serviceUrl);
		return marketBusiness;
	}
	
	/** 
	* @Title: getMarketBidList 
	* @Description: 获取报价查询结果
	* @param @param mParamInfo
	* @param @return     
	* @return List<MarketBidInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月26日 下午2:52:07
	*/
	public List<MarketBidInfo> getMarketBidList(MarketSearchParamInfo mParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			bidList=new ArrayList<MarketBidInfo>();  //返回结果
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MarketSearchParamInfo.ConvertToJson(mParamInfo);
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
					bidList = gson.fromJson(jsonArray.toString(), bidListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bidList;
	}
	
	/** 
	* @Title: getMarketContractList 
	* @Description: 获取合同查询结果
	* @param @param mParamInfo
	* @param @return     
	* @return List<MarketContractInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月26日 下午2:52:38
	*/
	public List<MarketContractInfo> getMarketContractList(MarketSearchParamInfo mParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			contractList=new ArrayList<MarketContractInfo>();  //返回结果
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MarketSearchParamInfo.ConvertToJson(mParamInfo);
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
					contractList = gson.fromJson(jsonArray.toString(), contractListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractList;
	}
	
	/** 
	* @Title: getMarketProductList 
	* @Description: 获取产品查询结果
	* @param @param mParamInfo
	* @param @return     
	* @return List<MarketProductInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月30日 上午9:20:42
	*/
	public List<MarketProductInfo> getMarketProductList(MarketSearchParamInfo mParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			productList=new ArrayList<MarketProductInfo>();  //返回结果
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MarketSearchParamInfo.ConvertToJson(mParamInfo);
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
					productList = gson.fromJson(jsonArray.toString(), productListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}
	
	/** 
	* @Title: getMarketWorkflowInfo 
	* @Description: 获取销售模块存在的审批记录
	* @param @param repository
	* @param @param sqlParameters
	* @param @return     
	* @return List<MarketWorkflowInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月29日 下午2:27:12
	*/
	public List<MarketWorkflowInfo> getMarketWorkflowInfo(RepositoryInfo repository,SqlParametersInfo sqlParameters){	
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			workflowList = new ArrayList<MarketWorkflowInfo>();  //返回结果
			
			// 获取API实例
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=RepositoryInfo.ConvertToJson(repository);
			String jsonParams=SqlParametersInfo.ConvertToJson(sqlParameters);
			apiClient.AddParam("jsonData", jsonData);
			apiClient.AddParam("jsonParams",jsonParams);
			
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
						workflowList = gson.fromJson(jsonArray.toString(), workflowListType);
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
		return workflowList;
	}

	/** 
	* @Title: getMarketBidHistoryList 
	* @Description: 获取历史报价查询记录
	* @param @param mHistory
	* @param @return     
	* @return List<MarketBidInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月29日 下午4:54:08
	*/
	public List<MarketBidInfo> getMarketBidHistoryList(List<MarketBidHistoryInfo> mHistory){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			bidList=new ArrayList<MarketBidInfo>();  //返回结果
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MarketBidHistoryInfo.ConvertToJson(mHistory);
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
					bidList = gson.fromJson(jsonArray.toString(), bidListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bidList;
	}
	
	/** 
	* @Title: getMarketContractHistoryList 
	* @Description: 获取历史合同查询记录
	* @param @param mHistory
	* @param @return     
	* @return List<MarketContractInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月29日 下午4:56:32
	*/
	public List<MarketContractInfo> getMarketContractHistoryList(List<MarketContractHistoryInfo> mHistory){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			contractList=new ArrayList<MarketContractInfo>();  //返回结果
			
			apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MarketContractHistoryInfo.ConvertToJson(mHistory);
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
					contractList = gson.fromJson(jsonArray.toString(), contractListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractList;
	}
}
