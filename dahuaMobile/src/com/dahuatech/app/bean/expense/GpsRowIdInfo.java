package com.dahuatech.app.bean.expense;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName GpsIdInfo
 * @Description 待主键ID的GPS实体类
 * @author 21291
 * @date 2014年5月20日 下午4:35:04
 */
public class GpsRowIdInfo extends Base {

	//序列化唯一码
	private static final long serialVersionUID = 1L;	
	
	private String rowId; //主键内码
	private String userId; //用户工号
	private String startTime; //开始时间
	private String endTime;    //结束时间
	private String startLocation;//开始坐标
	private String endLocation;  //结束坐标
	private String startPlace;  //开始地点
	private String endPlace;    //结束地点
	private String cause;  //补录原因
	private String autoFlag; //手动自动标志
	private String amount;	 //金额
	
	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
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

	public GpsRowIdInfo() {
		super();
	}

	public GpsRowIdInfo(String rowId,String userId, String startTime, String endTime,
			String startLocation, String endLocation, String startPlace,
			String endPlace, String cause, String autoFlag,String amount) {
		this.setRowId(rowId);
		this.setUserId(userId);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setStartLocation(startLocation);
		this.setEndLocation(endLocation);
		this.setStartPlace(startPlace);
		this.setEndPlace(endPlace);
		this.setCause(cause);
		this.setAutoFlag(autoFlag);
		this.setAmount(amount);
	}
	
	// 将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<GpsRowIdInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (GpsRowIdInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("rowId", item.rowId);
					jsonObject.put("userId", item.userId);
					jsonObject.put("startTime", item.startTime);
					jsonObject.put("endTime", item.endTime);
					jsonObject.put("startLocation", item.startLocation);
					jsonObject.put("endLocation", item.endLocation);
					jsonObject.put("startPlace", item.startPlace);
					jsonObject.put("endPlace", item.endPlace);
					jsonObject.put("cause", item.cause);
					jsonObject.put("autoFlag", item.autoFlag);
					jsonObject.put("amount", item.amount);
					jsonArray.put(jsonObject);
				}
				jsonString = jsonArray.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
