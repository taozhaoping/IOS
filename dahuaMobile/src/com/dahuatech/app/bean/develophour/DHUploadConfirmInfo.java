package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHUploadConfirmInfo
 * @Description 研发工时确认操作参数实体
 * @author 21291
 * @date 2014年11月19日 上午11:08:42
 */
public class DHUploadConfirmInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FItemNumber;      //项目经理员工号
	public String FItemName;      	//项目经理员工名称
    public int FYear; 		 		//年份
    public int FWeekIndex; 		 	//周次
    
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
	public int getFYear() {
		return FYear;
	}
	public void setFYear(int fYear) {
		FYear = fYear;
	}
	public int getFWeekIndex() {
		return FWeekIndex;
	}
	public void setFWeekIndex(int fWeekIndex) {
		FWeekIndex = fWeekIndex;
	}
	//内部类单例模式
	private static class SingletonHolder {  
        private static DHUploadConfirmInfo instance = new DHUploadConfirmInfo();  
    }
	public DHUploadConfirmInfo() {}
	public static DHUploadConfirmInfo getDHUploadConfirmInfo() {
		return SingletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(DHUploadConfirmInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FItemName", item.FItemName);
			jsonObject.put("FWeekIndex", item.FWeekIndex);
			jsonObject.put("FYear", item.FYear);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
