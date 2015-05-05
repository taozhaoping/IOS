package com.dahuatech.app.bean.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketContractHistoryInfo
 * @Description 合同历史查询实体
 * @author 21291
 * @date 2015年1月29日 下午4:46:42
 */
public class MarketContractHistoryInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FCustomerName;  		//客户名称
	private String FContractCode;  		//合同单号
	public String getFCustomerName() {
		return FCustomerName;
	}
	public void setFCustomerName(String fCustomerName) {
		FCustomerName = fCustomerName;
	}
	public String getFContractCode() {
		return FContractCode;
	}
	public void setFContractCode(String fContractCode) {
		FContractCode = fContractCode;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static MarketContractHistoryInfo instance = new MarketContractHistoryInfo();  
    }
	public MarketContractHistoryInfo() {}
	public static MarketContractHistoryInfo getMarketContractHistoryInfo() {
		return singletonHolder.instance;
	}
	
	//将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<MarketContractHistoryInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (MarketContractHistoryInfo item : items) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("FCustomerName", item.FCustomerName);
						jsonObject.put("FContractCode", item.FContractCode);
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
