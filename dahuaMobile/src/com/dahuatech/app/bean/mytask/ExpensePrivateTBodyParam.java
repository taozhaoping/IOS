package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpensePrivateTBodyParam
 * @Description 获取对私单据表体明细参数类
 * @author 21291
 * @date 2014年5月26日 下午4:36:08
 */
public class ExpensePrivateTBodyParam extends Base {

	private static final long serialVersionUID = 1L;
	
	public String FSystemType; //系统类型ID
	public String FBillID; //单据内码
	public String FCostCode; //费用类型明细编码
	
	public String getFSystemType() {
		return FSystemType;
	}

	public void setFSystemType(String fSystemType) {
		FSystemType = fSystemType;
	}

	public String getFBillID() {
		return FBillID;
	}

	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}

	public String getFCostCode() {
		return FCostCode;
	}

	public void setFCostCode(String fCostCode) {
		FCostCode = fCostCode;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ExpensePrivateTBodyParam instance = new ExpensePrivateTBodyParam();  
    }
	public ExpensePrivateTBodyParam() {}	
	public static ExpensePrivateTBodyParam getExpensePrivateTBodyParam() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(ExpensePrivateTBodyParam item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FSystemType", item.FSystemType);
			jsonObject.put("FBillID", item.FBillID);
			jsonObject.put("FCostCode", item.FCostCode);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
