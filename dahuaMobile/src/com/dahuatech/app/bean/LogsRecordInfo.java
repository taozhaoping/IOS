package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName LogsRecordInfo
 * @Description 日志统计记录实体
 * @author 21291
 * @date 2014年7月31日 上午9:48:43
 */
public class LogsRecordInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;		//员工号
	private String FAccessTime;		//访问时间
	private String FModuleName;		//模块名称
	private String FActionName;		//动作名称
	private String FNote;			//描述详细内容
	
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFAccessTime() {
		return FAccessTime;
	}

	public void setFAccessTime(String fAccessTime) {
		FAccessTime = fAccessTime;
	}

	public String getFModuleName() {
		return FModuleName;
	}

	public void setFModuleName(String fModuleName) {
		FModuleName = fModuleName;
	}

	public String getFActionName() {
		return FActionName;
	}

	public void setFActionName(String fActionName) {
		FActionName = fActionName;
	}

	public String getFNote() {
		return FNote;
	}

	public void setFNote(String fNote) {
		FNote = fNote;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static LogsRecordInfo instance = new LogsRecordInfo();  
    }
	private LogsRecordInfo() {}
	public static LogsRecordInfo getLogsRecordInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(LogsRecordInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FAccessTime", item.FAccessTime);
			jsonObject.put("FModuleName", item.FModuleName);
			jsonObject.put("FActionName", item.FActionName);
			jsonObject.put("FNote", item.FNote);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
