package com.dahuatech.app.bean.market;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketSearchParamInfo
 * @Description 搜索参数实体
 * @author 21291
 * @date 2015年1月26日 下午2:38:37
 */
public class MarketSearchParamInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;  		//员工号
	private String FQueryText;  		//查询文本
	private int FPageIndex;  			//页数
	private int FPageSize;  			//页大小
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
	
	//内部类单例模式
	private static class singletonHolder {  
        private static MarketSearchParamInfo instance = new MarketSearchParamInfo();  
    }
	private MarketSearchParamInfo() {}
	public static MarketSearchParamInfo getMarketSearchParamInfo() {
		return singletonHolder.instance;
	}

	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(MarketSearchParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FQueryText", item.FQueryText);
			jsonObject.put("FPageIndex", item.FPageIndex);		
			jsonObject.put("FPageSize", item.FPageSize);	
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
