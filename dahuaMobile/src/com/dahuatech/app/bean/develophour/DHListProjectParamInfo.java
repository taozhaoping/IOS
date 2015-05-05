package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHListProjectParamInfo
 * @Description 获取研发工时列表项目集合 参数传递实体类
 * @author 21291
 * @date 2014年10月23日 上午9:21:01
 */
public class DHListProjectParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FBillId; //周单据Id
	private String FWeekValue; 	//每周值
	
	public int getFBillId() {
		return FBillId;
	}
	public void setFBillId(int fBillId) {
		FBillId = fBillId;
	}
	public String getFWeekValue() {
		return FWeekValue;
	}
	public void setFWeekValue(String fWeekValue) {
		FWeekValue = fWeekValue;
	}
	
	//单例模式-线程安全写法
	private DHListProjectParamInfo() {}
	private static DHListProjectParamInfo dHListProjectParamInfo;
	public static DHListProjectParamInfo getDHListProjectParamInfo() {
		if (dHListProjectParamInfo == null) {
			dHListProjectParamInfo = new DHListProjectParamInfo();
		}
		return dHListProjectParamInfo;
	}
	
	//将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(DHListProjectParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FWeekValue", item.FWeekValue);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
