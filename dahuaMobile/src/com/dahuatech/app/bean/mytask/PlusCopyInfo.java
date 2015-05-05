package com.dahuatech.app.bean.mytask;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PlusCopyInfo
 * @Description 加签或抄送实体类
 * @author 21291
 * @date 2014年9月16日 上午9:45:11
 */
public class PlusCopyInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FAppKey;				//应用程序KEY
	private String FSystemId;			//系统内码
	private String FClassTypeId;		//单据类型ID
	private String FBillId;				//单据内码
	private String FItemNumber;			//当前员工号
	private String FType;			    //类型 "0"-代表加签  "1"-代表抄送
	private String FNodeIds; 			//附加环节字符串
	private String FPersonNumbers;		//人员工号
	private String FContent; 	 		//文本值
	
	public String getFAppKey() {
		return FAppKey;
	}

	public void setFAppKey(String fAppKey) {
		FAppKey = fAppKey;
	}

	public String getFSystemId() {
		return FSystemId;
	}

	public void setFSystemId(String fSystemId) {
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

	public String getFType() {
		return FType;
	}

	public void setFType(String fType) {
		FType = fType;
	}

	public String getFNodeIds() {
		return FNodeIds;
	}

	public void setFNodeIds(String fNodeIds) {
		FNodeIds = fNodeIds;
	}

	public String getFPersonNumbers() {
		return FPersonNumbers;
	}

	public void setFPersonNumbers(String fPersonNumbers) {
		FPersonNumbers = fPersonNumbers;
	}

	public String getFContent() {
		return FContent;
	}

	public void setFContent(String fContent) {
		FContent = fContent;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static PlusCopyInfo instance = new PlusCopyInfo();  
    }
	private PlusCopyInfo() {};
	public static PlusCopyInfo getPlusCopyInfo() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(PlusCopyInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {	
			jsonObject.put("FAppKey", item.FAppKey);
			jsonObject.put("FSystemId", item.FSystemId);
			jsonObject.put("FClassTypeId", item.FClassTypeId);
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FType", item.FType);
			jsonObject.put("FNodeIds", item.FNodeIds);
			jsonObject.put("FPersonNumbers", item.FPersonNumbers);
			jsonObject.put("FContent", item.FContent);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
