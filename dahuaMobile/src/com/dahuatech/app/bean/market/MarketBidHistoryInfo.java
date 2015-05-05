package com.dahuatech.app.bean.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketBidHistoryInfo
 * @Description 报价历史查询实体
 * @author 21291
 * @date 2015年1月29日 下午4:38:55
 */
public class MarketBidHistoryInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FCustomerName;  		//客户名称
	private String FBidCode;  			//报价单号
	public String getFCustomerName() {
		return FCustomerName;
	}
	public void setFCustomerName(String fCustomerName) {
		FCustomerName = fCustomerName;
	}
	public String getFBidCode() {
		return FBidCode;
	}
	public void setFBidCode(String fBidCode) {
		FBidCode = fBidCode;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static MarketBidHistoryInfo instance = new MarketBidHistoryInfo();  
    }
	public MarketBidHistoryInfo() {}
	public static MarketBidHistoryInfo getMarketBidHistoryInfo() {
		return singletonHolder.instance;
	}
	
	//将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<MarketBidHistoryInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (MarketBidHistoryInfo item : items) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("FCustomerName", item.FCustomerName);
						jsonObject.put("FBidCode", item.FBidCode);
						jsonArray.put(jsonObject);
					}
					jsonString = jsonArray.toString();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonString;
		}
}
