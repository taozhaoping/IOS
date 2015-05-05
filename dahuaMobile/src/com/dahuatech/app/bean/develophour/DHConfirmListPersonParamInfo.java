package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmListPersonParamInfo
 * @Description 研发工时确认列表项具体实体传递参数类
 * @author 21291
 * @date 2014年11月5日 下午2:47:08
 */
public class DHConfirmListPersonParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FProjectNumber;   	//项目经理员工号
	private int FWeekIndex;				//周次
	private int FYear;					//年份
	private String FProjectCode;	 	//项目编码
	private String FConfrimNumber;	 	//确认人员员工号
	
	public String getFProjectNumber() {
		return FProjectNumber;
	}
	public void setFProjectNumber(String fProjectNumber) {
		FProjectNumber = fProjectNumber;
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
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFConfrimNumber() {
		return FConfrimNumber;
	}
	public void setFConfrimNumber(String fConfrimNumber) {
		FConfrimNumber = fConfrimNumber;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static DHConfirmListPersonParamInfo instance = new DHConfirmListPersonParamInfo();  
    }
	private DHConfirmListPersonParamInfo() {}
	public static DHConfirmListPersonParamInfo getDHConfirmListPersonParamInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(DHConfirmListPersonParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FProjectNumber", item.FProjectNumber);
			jsonObject.put("FWeekIndex", item.FWeekIndex);
			jsonObject.put("FYear", item.FYear);
			jsonObject.put("FProjectCode", item.FProjectCode);
			jsonObject.put("FConfrimNumber", item.FConfrimNumber);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
