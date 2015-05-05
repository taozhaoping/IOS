package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppConfigInfo
 * @Description 选取的下级节点审批配置实体
 * @author 21291
 * @date 2014年11月12日 下午4:57:47
 */
public class LowerNodeAppConfigInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FAppKey;				//应用程序KEY
	private int FSystemId;				//系统内码
	private String FClassTypeId;		//单据类型ID
	private String FBillId;				//单据内码
	private String FItemNumber;			//当前员工号
	
	public String getFAppKey() {
		return FAppKey;
	}
	public void setFAppKey(String fAppKey) {
		FAppKey = fAppKey;
	}
	public int getFSystemId() {
		return FSystemId;
	}
	public void setFSystemId(int fSystemId) {
		FSystemId = fSystemId;
	}
	public String getFClassTypeId() {
		return FClassTypeId;
	}
	public void setFClassTypeId(String fClassTypeId) {
		FClassTypeId = fClassTypeId;
	}
	public String getFBillId() {
		return FBillId;
	}
	public void setFBillId(String fBillId) {
		FBillId = fBillId;
	}
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static LowerNodeAppConfigInfo instance = new LowerNodeAppConfigInfo();  
    }
	private LowerNodeAppConfigInfo() {};
	public static LowerNodeAppConfigInfo getLowerNodeAppConfigInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(LowerNodeAppConfigInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {	
			jsonObject.put("FAppKey", item.FAppKey);
			jsonObject.put("FSystemId", item.FSystemId);
			jsonObject.put("FClassTypeId", item.FClassTypeId);
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
}
