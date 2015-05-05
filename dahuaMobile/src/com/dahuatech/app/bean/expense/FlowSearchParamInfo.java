package com.dahuatech.app.bean.expense;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FlowSearchParamInfo
 * @Description 我的流水客户/项目列表查询参数传递实体类
 * @author 21291
 * @date 2014年9月1日 下午7:43:52
 */
public class FlowSearchParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; //员工号
	private String FQueryItem; 	//查询文本
	private String fQueryType; 	//查询类别
  	private int FPageIndex; 	//页数
	private int FPageSize; 	//页大小
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFQueryItem() {
		return FQueryItem;
	}
	public void setFQueryItem(String fQueryItem) {
		FQueryItem = fQueryItem;
	}
	public String getfQueryType() {
		return fQueryType;
	}
	public void setfQueryType(String fQueryType) {
		this.fQueryType = fQueryType;
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
        private static FlowSearchParamInfo instance = new FlowSearchParamInfo();  
    }
	private FlowSearchParamInfo() {}
	public static FlowSearchParamInfo getFlowSearchParamInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(FlowSearchParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {	
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryItem", item.FQueryItem);
			jsonObject.put("fQueryType", item.fQueryType);
			jsonObject.put("FPageIndex", item.FPageIndex);
			jsonObject.put("FPageSize", item.FPageSize);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
