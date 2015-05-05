package com.dahuatech.app.bean.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingInitParamInfo
 * @Description 会议室历史记录状态查询参数实体类
 * @author 21291
 * @date 2014年9月28日 下午2:46:18
 */
public class MeetingInitParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//员工号
	private String FRoomIdS; 		//会议室ID 多个主键字符串，以','分开
	private String FSelectedDate;	//已经选择会议日期
	private String FSelectedStart;	//已经选择会议起始时间
	private String FSelectedEnd;	//已经选择会议结束时间
	
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFRoomIdS() {
		return FRoomIdS;
	}

	public void setFRoomIdS(String fRoomIdS) {
		FRoomIdS = fRoomIdS;
	}

	public String getFSelectedDate() {
		return FSelectedDate;
	}

	public void setFSelectedDate(String fSelectedDate) {
		FSelectedDate = fSelectedDate;
	}

	public String getFSelectedStart() {
		return FSelectedStart;
	}

	public void setFSelectedStart(String fSelectedStart) {
		FSelectedStart = fSelectedStart;
	}

	public String getFSelectedEnd() {
		return FSelectedEnd;
	}

	public void setFSelectedEnd(String fSelectedEnd) {
		FSelectedEnd = fSelectedEnd;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static MeetingInitParamInfo instance = new MeetingInitParamInfo();  
    }
	private MeetingInitParamInfo() {}
	public static MeetingInitParamInfo getMeetingInitParamInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(MeetingInitParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FRoomIdS", item.FRoomIdS);
			jsonObject.put("FSelectedDate", item.FSelectedDate);
			jsonObject.put("FSelectedStart", item.FSelectedStart);		
			jsonObject.put("FSelectedEnd", item.FSelectedEnd);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
