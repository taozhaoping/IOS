package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHDetailParamInfo
 * @Description 研发工时详情参数传递实体类
 * @author 21291
 * @date 2014年10月30日 上午11:19:34
 */
public class DHDetailParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FBillId;		 	//周单据Id
	private String FWeekDate; 	 	//工作日期
	private String FProjectCode; 	//项目编号
	private String FTypeId; 	    //类型Id

	public int getFBillId() {
		return FBillId;
	}
	public void setFBillId(int fBillId) {
		FBillId = fBillId;
	}
	public String getFWeekDate() {
		return FWeekDate;
	}
	public void setFWeekDate(String fWeekDate) {
		FWeekDate = fWeekDate;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFTypeId() {
		return FTypeId;
	}
	public void setFTypeId(String fTypeId) {
		FTypeId = fTypeId;
	}

	//内部类单例模式
	private static class SingletonHolder {  
        private static DHDetailParamInfo instance = new DHDetailParamInfo();  
    }
	private DHDetailParamInfo() {}
	public static DHDetailParamInfo getDHDetailParamInfo() {
		return SingletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(DHDetailParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FWeekDate", item.FWeekDate);
			jsonObject.put("FProjectCode", item.FProjectCode);
			jsonObject.put("FTypeId", item.FTypeId);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
