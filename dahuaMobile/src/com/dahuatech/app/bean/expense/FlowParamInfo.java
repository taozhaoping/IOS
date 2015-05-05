package com.dahuatech.app.bean.expense;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FlowParamInfo
 * @Description 获取服务器流水列表参数传递实体类
 * @author 21291
 * @date 2014年8月27日 下午6:04:10
 */
public class FlowParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; //员工号
	private String FQueryText; 	//查询参数
	private String FCurrentDate; //当前5条的记录 最新消费时间
	private int FPageIndex; 	//页数
	private int FPageSize; 		//页大小
	
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
	public String getFCurrentDate() {
		return FCurrentDate;
	}
	public void setFCurrentDate(String fCurrentDate) {
		FCurrentDate = fCurrentDate;
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
	
	//内部类单例模式
	private static class singletonHolder {  
        private static FlowParamInfo instance = new FlowParamInfo();  
    }
	private FlowParamInfo() {}
	public static FlowParamInfo getFlowParamInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(FlowParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryText", item.FQueryText);
			jsonObject.put("FCurrentDate", item.FCurrentDate);
			jsonObject.put("FPageIndex", item.FPageIndex);
			jsonObject.put("FPageSize", item.FPageSize);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
