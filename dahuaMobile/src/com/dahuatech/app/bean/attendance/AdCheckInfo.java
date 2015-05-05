package com.dahuatech.app.bean.attendance;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdCheckInfo
 * @Description 签入或签出实体
 * @author 21291
 * @date 2014年12月18日 下午1:48:53
 */
public class AdCheckInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FAttendId;			//考勤ID
	private int FAttendStatus;		//考勤状态
	private String FItemNumber;  	//员工号
	private String FItemName;  		//员工名称
	private String FLatitude;  		//坐标纬度
	private String FLongitude;  	//坐标经度
	private String FAddress;		//具体地址
	
	public int getFAttendId() {
		return FAttendId;
	}
	public void setFAttendId(int fAttendId) {
		FAttendId = fAttendId;
	}
	public int getFAttendStatus() {
		return FAttendStatus;
	}
	public void setFAttendStatus(int fAttendStatus) {
		FAttendStatus = fAttendStatus;
	}
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	public String getFLatitude() {
		return FLatitude;
	}
	public void setFLatitude(String fLatitude) {
		FLatitude = fLatitude;
	}
	public String getFLongitude() {
		return FLongitude;
	}
	public void setFLongitude(String fLongitude) {
		FLongitude = fLongitude;
	}
	public String getFAddress() {
		return FAddress;
	}
	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}

	//内部类单例模式
	private static class SingletonHolder {  
        private static AdCheckInfo instance = new AdCheckInfo();  
    }
	public AdCheckInfo() {}
	public static AdCheckInfo getAdCheckInfo() {
		return SingletonHolder.instance;
	}
	
	//将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(AdCheckInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {			
			jsonObject.put("FAttendId", item.FAttendId);
			jsonObject.put("FAttendStatus", item.FAttendStatus);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FLatitude", item.FLatitude);
			jsonObject.put("FLongitude", item.FLongitude);
			jsonObject.put("FAddress", item.FAddress);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
