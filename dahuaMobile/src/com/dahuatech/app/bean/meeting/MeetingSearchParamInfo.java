package com.dahuatech.app.bean.meeting;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MeetingSearchParamInfo
 * @Description 人员列表和会议选择列表 查询参数实体类
 * @author 21291
 * @date 2014年9月11日 下午3:52:04
 */
public class MeetingSearchParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//员工号
	private String FQueryText; 		//查询参数
	private int FPageIndex; 		//页数
	private int FPageSize; 			//页大小	
	private String FSelectedDate;	//已经选择会议日期
	private String FSelectedStart;	//已经选择会议起始时间
	private String FSelectedEnd;	//已经选择会议结束时间
	
	public String getFItemNumber() {
		return FItemNumber;
	}

	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	public String getFQueryText() {
		return FQueryText;
	}

	public void setFQueryText(String fQueryText) {
		FQueryText = fQueryText;
	}

	public int getFPageIndex() {
		return FPageIndex;
	}

	public void setFPageIndex(int fPageIndex) {
		FPageIndex = fPageIndex;
	}

	public int getFPageSize() {
		return FPageSize;
	}

	public void setFPageSize(int fPageSize) {
		FPageSize = fPageSize;
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
        private static MeetingSearchParamInfo instance = new MeetingSearchParamInfo();  
    }
	private MeetingSearchParamInfo() {}
	public static MeetingSearchParamInfo getMeetingSearchParamInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(MeetingSearchParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryText", item.FQueryText);
			jsonObject.put("FPageIndex", item.FPageIndex);		
			jsonObject.put("FPageSize", item.FPageSize);
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
