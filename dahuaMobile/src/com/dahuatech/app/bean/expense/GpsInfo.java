package com.dahuatech.app.bean.expense;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName GpsInfo
 * @Description GPS实体类
 * @author 21291
 * @date 2014年5月12日 下午3:32:41
 */
public class GpsInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String userId; //用户工号
	private String startTime; //开始时间
	private String endTime;    //结束时间
	private String startLocation;//开始坐标
	private String endLocation;  //结束坐标
	private String startPlace;  //开始地点
	private String endPlace;    //结束地点
	private String cause;  //补录原因
	private String autoFlag; //手动自动标志
	private String amount;   //金额

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(String autoFlag) {
		this.autoFlag = autoFlag;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public GpsInfo(){
		super();
	}
	
	public GpsInfo(String userId,String startTime,String endTime,String startLocation,String endLocation,
			String startPlace, String endPlace,  String cause,String autoFlag,String amount){
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.startPlace = startPlace;
		this.endPlace = endPlace;
		this.cause = cause;
		this.autoFlag = autoFlag;
		this.amount=amount;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(GpsInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("userId", item.userId);
			jsonObject.put("startTime", item.startTime);
			jsonObject.put("endTime", item.startTime);
			jsonObject.put("startLocation", item.startLocation);
			jsonObject.put("endLocation", item.endLocation);
			jsonObject.put("startPlace", item.startPlace);
			jsonObject.put("endPlace", item.endPlace);
			jsonObject.put("cause", item.cause);
			jsonObject.put("autoFlag", item.autoFlag);
			jsonObject.put("amount", item.amount);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
