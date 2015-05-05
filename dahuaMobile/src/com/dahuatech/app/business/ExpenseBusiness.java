package com.dahuatech.app.business;

import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import android.content.Context;

import com.dahuatech.app.api.ApiClient;
import com.dahuatech.app.api.InvokeApi;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.expense.GpsInfo;
import com.dahuatech.app.bean.expense.GpsRowIdInfo;
import com.dahuatech.app.common.DESUtils;

/**
 * @ClassName ExpenseBusiness
 * @Description 报销打车业务逻辑类
 * @author 21291
 * @date 2014年6月4日 下午2:40:50
 */
public class ExpenseBusiness extends BaseBusiness<Void> {
	private ExpenseBusiness(Context context) {
		super(context);
	}

	// 单例模式(线程不安全写法)
	private static ExpenseBusiness expenseBusiness;
	public static ExpenseBusiness getExpenseBusiness(Context context,String serviceUrl) {
		if (expenseBusiness == null) {
			expenseBusiness = new ExpenseBusiness(context);
		}
		expenseBusiness.setServiceUrl(serviceUrl);
		return expenseBusiness;
	}
	
	/** 
	* @Title: upload 
	* @Description: 乘车记录上传报销服务
	* @param @param gpsInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年5月13日 下午6:12:33
	*/
	public ResultMessage upload(GpsInfo gpsInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			String jsonData=GpsInfo.ConvertToJson(gpsInfo);
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

	/** 
	* @Title: batchUpload 
	* @Description: 批量上传乘车记录
	* @param @param gpsIdInfoList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年5月20日 下午5:01:14
	*/
	public ResultMessage batchUpload(List<GpsRowIdInfo> gpsIdInfoList) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			String jsonData=GpsRowIdInfo.ConvertToJson(gpsIdInfoList);
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
	
	/** 
	* @Title: flowBatchUpload 
	* @Description: 我的流水批量上传
	* @param @param efList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年9月10日 下午3:13:52
	*/
	public ResultMessage flowBatchUpload(List<ExpenseFlowDetailInfo> efList) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			String jsonData=ExpenseFlowDetailInfo.ConvertToJson(efList);
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
