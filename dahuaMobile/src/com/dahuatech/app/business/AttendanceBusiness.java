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
import com.dahuatech.app.bean.attendance.AdAmapListInfo;
import com.dahuatech.app.bean.attendance.AdCheckInfo;
import com.dahuatech.app.bean.attendance.AdCheckStatusInfo;
import com.dahuatech.app.bean.attendance.AdListInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName AttendanceBusiness
 * @Description 考勤模块业务逻辑类
 * @author 21291
 * @date 2014年12月18日 下午1:57:03
 */
public class AttendanceBusiness extends BaseBusiness<Void>{
	
	private List<AdListInfo> arrayList;
	private Type listType;
	private ApiClient apiClient;
	private AdCheckStatusInfo adCheckStatusInfo;
	private AdAmapListInfo adAmapListInfo;

	private AttendanceBusiness(Context context) {
		super(context);
		arrayList = new ArrayList<AdListInfo>();
		listType = new TypeToken<ArrayList<AdListInfo>>() {}.getType();
		adCheckStatusInfo=AdCheckStatusInfo.getAdCheckStatusInfo();
		adAmapListInfo=AdAmapListInfo.getAdAmapListInfo();
	}
	
	//单例模式(线程不安全写法)
	private static AttendanceBusiness attendanceBusiness;	
	public static AttendanceBusiness getAttendanceBusiness(Context context,String serviceUrl){
		if(attendanceBusiness==null)
		{
			attendanceBusiness=new AttendanceBusiness(context);
		}
		attendanceBusiness.setServiceUrl(serviceUrl);
		return attendanceBusiness;
	}
	
	/** 
	* @Title: getCheckStausData 
	* @Description: 获取签入/签出状态
	* @param @param fItemNumber
	* @param @return     
	* @return AdCheckStatusInfo    
	* @throws 
	* @author 21291
	* @date 2014年12月30日 上午9:32:31
	*/
	public AdCheckStatusInfo getCheckStausData(String fItemNumber) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 实例化对象
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(fItemNumber,DESUtils.DEFAULT_KEY));
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
					adCheckStatusInfo = gson.fromJson(jsonObject.toString(), AdCheckStatusInfo.class);
				}
			} else {
				responseErrorMessage = "状态码为:"
						+ responseMessage.getResponseCode().toString() + " "
						+ "具体错误信息为:"+ responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adCheckStatusInfo;
	}
	
	/** 
	* @Title: getAdList 
	* @Description: 获取考勤历史记录
	* @param @param fItemNumber
	* @param @return     
	* @return List<AdListInfo>    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午2:07:42
	*/
	public List<AdListInfo> getAdList(String fItemNumber){		
		try {	
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			arrayList = new ArrayList<AdListInfo>();  //返回结果
			
			// 获取API实例
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(fItemNumber,DESUtils.DEFAULT_KEY));
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
	* @Title: checkHandle 
	* @Description: 签入或签出处理事件
	* @param @param checkInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午2:06:13
	*/
	public ResultMessage checkHandle(AdCheckInfo checkInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 实例化对象
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData=AdCheckInfo.ConvertToJson(checkInfo);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
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
	
	/** 
	* @Title: getAmapList 
	* @Description: 获取打卡地点集合
	* @param @param cacheKey 缓存版本号
	* @param @return     
	* @return AdAmapListInfo    
	* @throws 
	* @author 21291
	* @date 2015年1月5日 上午10:18:28
	*/
	public AdAmapListInfo getAmapList(String cacheKey) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			responseErrorMessage = "";
			
			// 获取API实例
			apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(cacheKey,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			
			// 获取响应信息
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
				// 说明返回成功
				if (resultMessage.isIsSuccess() && !StringUtils.isEmpty(resultMessage.getResult())) {
					jsonObject = new JSONObject(resultMessage.getResult());
					adAmapListInfo = gson.fromJson(jsonObject.toString(), AdAmapListInfo.class);
				}
				else{
					adAmapListInfo.setFAmapList("");
				}
			} else {
				responseErrorMessage = "状态码为:"
				+ responseMessage.getResponseCode().toString() + " "
				+ "具体错误信息为:" + responseMessage.getResponseErrorMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return adAmapListInfo;
	}	
}
