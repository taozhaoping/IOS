package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName TaskParamInfo
 * @Description 某个单据传递的参数实体类
 * @author 21291
 * @date 2014年4月24日 下午4:43:09
 */
public class TaskParamInfo {
	
	private String FMenuID; //菜单ID
	private String FSystemType; //系统ID
	private String FBillID; //单据内码
	
	public String getFMenuID() {
		return FMenuID;
	}

	public void setFMenuID(String fMenuID) {
		FMenuID = fMenuID;
	}

	public String getFSystemType() {
		return FSystemType;
	}

	public void setFSystemType(String fSystemType) {
		FSystemType = fSystemType;
	}

	public String getFBillID() {
		return FBillID;
	}

	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static TaskParamInfo instance = new TaskParamInfo();  
    }
	private TaskParamInfo() {}
	public static TaskParamInfo getTaskParamInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(TaskParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FMenuID", item.FMenuID);
			jsonObject.put("FSystemType", item.FSystemType);
			jsonObject.put("FBillID", item.FBillID);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
