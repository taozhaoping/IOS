package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmParamInfo
 * @Description 获取研发工时确认列表参数传递实体类
 * @author 21291
 * @date 2014年10月21日 上午11:04:19
 */
public class DHConfirmParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//员工号
	private int FWeekIndex; 		//周次(从当前周递减)
	private int FYear; 				//年份
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public int getFWeekIndex() {
		return FWeekIndex;
	}
	public void setFWeekIndex(int fWeekIndex) {
		FWeekIndex = fWeekIndex;
	}
	public int getFYear() {
		return FYear;
	}
	public void setFYear(int fYear) {
		FYear = fYear;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static DHConfirmParamInfo instance = new DHConfirmParamInfo();  
    }
	private DHConfirmParamInfo() {}
	public static DHConfirmParamInfo getDHParamInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(DHConfirmParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FWeekIndex", item.FWeekIndex);
			jsonObject.put("FYear", item.FYear);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
