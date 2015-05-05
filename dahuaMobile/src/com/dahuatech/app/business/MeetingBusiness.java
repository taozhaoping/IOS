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
import com.dahuatech.app.bean.meeting.MeetingDetailInfo;
import com.dahuatech.app.bean.meeting.MeetingInitParamInfo;
import com.dahuatech.app.bean.meeting.MeetingListInfo;
import com.dahuatech.app.bean.meeting.MeetingListParamInfo;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.meeting.MeetingRoomInfo;
import com.dahuatech.app.bean.meeting.MeetingSearchParamInfo;
import com.dahuatech.app.common.DESUtils;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName MeetingBusiness
 * @Description  我的会议业务逻辑类
 * @author 21291
 * @date 2014年9月11日 下午3:44:20
 */
public class MeetingBusiness {
	private Gson gson;
	private Context context;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private ResultMessage resultMessage;  
	private ResponseMessage responseMessage;
	private String serviceUrl;  					//服务地址
	
	private MeetingDetailInfo meetingDetailInfo;	//实例
	private Type meetingListType,personListType,roomListType;
	private List<MeetingListInfo> meetingList;
	private List<MeetingPersonInfo> personList;
	private List<MeetingRoomInfo> roomList;

	public void setMeetingDetailInfo(MeetingDetailInfo meetingDetailInfo) {
		this.meetingDetailInfo = meetingDetailInfo;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public MeetingBusiness(Context context) {
		this.context = context;
		this.gson=GsonHelper.getInstance();
		serviceUrl="";
		meetingListType=new TypeToken<ArrayList<MeetingListInfo>>() {}.getType();	
		personListType=new TypeToken<ArrayList<MeetingPersonInfo>>() {}.getType();	
		roomListType=new TypeToken<ArrayList<MeetingRoomInfo>>() {}.getType();	
	}
	
	public MeetingBusiness(Context context,MeetingDetailInfo meetingDetailInfo) {
		this(context);
		this.meetingDetailInfo=meetingDetailInfo;
	}
	
	//单例模式(线程不安全写法)
	private static MeetingBusiness meetingBusiness;	
	public static MeetingBusiness getMeetingBusiness(Context context,String serviceUrl){
		if(meetingBusiness==null)
		{
			meetingBusiness=new MeetingBusiness(context);
		}
		meetingBusiness.setServiceUrl(serviceUrl);
		return meetingBusiness;
	}
	
	/** 
	* @Title: getMeetingInitInfo 
	* @Description: 添加会议时，获取默认初始信息
	* @param @param fItemNumber 员工号
	* @param @return     
	* @return MeetingDetailInfo    
	* @throws 
	* @author 21291
	* @date 2014年9月16日 下午4:14:33
	*/
	public MeetingDetailInfo getMeetingInitInfo(String fItemNumber){
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
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
					meetingDetailInfo = (MeetingDetailInfo) gson.fromJson(jsonObject.toString(),meetingDetailInfo.getClass());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return meetingDetailInfo;
	}
	
	/** 
	* @Title: getMeetingDetailInfo 
	* @Description: 修改会议时获取初始化实体信息
	* @param @param fOrderId
	* @param @return     
	* @return MeetingDetailInfo    
	* @throws 
	* @author 21291
	* @date 2014年9月16日 下午4:12:05
	*/
	public MeetingDetailInfo getMeetingDetailInfo(String fOrderId){
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();

			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(fOrderId,DESUtils.DEFAULT_KEY));
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
					meetingDetailInfo = (MeetingDetailInfo) gson.fromJson(jsonObject.toString(),meetingDetailInfo.getClass());
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return meetingDetailInfo;
	}
	
	/** 
	* @Title: uploadMeetingDetail 
	* @Description: 创建或修改会议详情信息
	* @param @param meDetailInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 下午4:14:28
	*/
	public ResultMessage uploadMeetingDetail(MeetingDetailInfo meDetailInfo) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			// 设置参数
			String jsonData=MeetingDetailInfo.ConvertToJson(meDetailInfo);
			jsonData = DESUtils.toHexString(DESUtils.encrypt(jsonData,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// 获取响应信息
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return resultMessage;
	}	
	
	/** 
	* @Title: removeMeetingListItem 
	* @Description: 取消会议
	* @param @param fOrderId 会议主键内码
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年9月16日 下午4:12:58
	*/
	public ResultMessage removeMeetingListItem(String fOrderId) {
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			
			// 获取API实例
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl);
			String jsonData = DESUtils.toHexString(DESUtils.encrypt(fOrderId,DESUtils.DEFAULT_KEY));
			apiClient.AddParam("jsonData", jsonData);
			// 获取响应信息
			responseMessage = InvokeApi.getResponse(apiClient,RequestMethod.POST);
			if (responseMessage.getResponseCode() == HttpStatus.SC_OK) {
				jsonObject = new JSONObject(responseMessage.getResponseMessage());
				// 获取结果值
				resultMessage = gson.fromJson(jsonObject.toString(),ResultMessage.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return resultMessage;
	}	

	/** 
	* @Title: getMeetingList 
	* @Description: 获取我的会议列表集合
	* @param @param mParamInfo
	* @param @return     
	* @return List<MeetingListInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月16日 下午3:33:52
	*/
	public List<MeetingListInfo> getMeetingList(MeetingListParamInfo mParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			meetingList=new ArrayList<MeetingListInfo>();  //返回结果
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MeetingListParamInfo.ConvertToJson(mParamInfo);
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
					meetingList = gson.fromJson(jsonArray.toString(), meetingListType);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return meetingList;
	}
	
	/** 
	* @Title: getPersonList 
	* @Description: 获取人员列表集合
	* @param @param mSearchParamInfo
	* @param @return     
	* @return List<MeetingPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 下午4:09:37
	*/
	public List<MeetingPersonInfo> getPersonList(MeetingSearchParamInfo mSearchParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			personList=new ArrayList<MeetingPersonInfo>();  //返回结果
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MeetingSearchParamInfo.ConvertToJson(mSearchParamInfo);
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
					personList = gson.fromJson(jsonArray.toString(), personListType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personList;
	}
	
	/** 
	* @Title: getRoomList 
	* @Description: 获取选择会议列表集合
	* @param @param mSearchParamInfo
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 下午4:11:25
	*/
	public List<MeetingRoomInfo> getRoomList(MeetingSearchParamInfo mSearchParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			roomList=new ArrayList<MeetingRoomInfo>();  //返回结果
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MeetingSearchParamInfo.ConvertToJson(mSearchParamInfo);
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
					roomList = gson.fromJson(jsonArray.toString(), roomListType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomList;
	}
	
	/** 
	* @Title: getInitRoomList 
	* @Description: 过滤会议室历史记录状态
	* @param @param mInitParamInfo
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 下午2:49:57
	*/
	public List<MeetingRoomInfo> getInitRoomList(MeetingInitParamInfo mInitParamInfo){	
		try {
			resultMessage = new ResultMessage();
			responseMessage = new ResponseMessage();
			roomList=new ArrayList<MeetingRoomInfo>();  //返回结果
			
			ApiClient apiClient = InvokeApi.getApiClient(context,serviceUrl); // 获取API实例
			String jsonData=MeetingInitParamInfo.ConvertToJson(mInitParamInfo);
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
					roomList = gson.fromJson(jsonArray.toString(), roomListType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomList;
	}
}
