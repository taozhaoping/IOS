package com.dahuatech.app.bean.meeting;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingPersonInfo
 * @Description 会议人员实体类
 * @author 21291
 * @date 2014年9月11日 上午9:20:32
 */
public class MeetingPersonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//员工号
	private String FItemName; 		//员工名称
	private String FDeptName; 		//部门名称
	private int FRecordCount; 		//总的人员记录
	
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

	public String getFDeptName() {
		return FDeptName;
	}

	public void setFDeptName(String fDeptName) {
		FDeptName = fDeptName;
	}
	
	public int getFRecordCount() {
		return FRecordCount;
	}

	public void setFRecordCount(int fRecordCount) {
		FRecordCount = fRecordCount;
	}
	
	//默认构造函数
	public MeetingPersonInfo() {}
	
	public MeetingPersonInfo(String fItemNumber,String fItemName,String fDeptName,int fRecordCount){
		this.FItemNumber=fItemNumber;
		this.FItemName=fItemName;
		this.FDeptName=fDeptName;
		this.FRecordCount=fRecordCount;
	}
	
	// 将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<MeetingPersonInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (MeetingPersonInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FItemNumber", item.FItemNumber);
					jsonObject.put("FItemName", item.FItemName);
					jsonObject.put("FDeptName", item.FDeptName);
					jsonObject.put("FRecordCount", item.FRecordCount);
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
