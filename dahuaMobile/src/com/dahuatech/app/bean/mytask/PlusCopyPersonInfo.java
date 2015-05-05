package com.dahuatech.app.bean.mytask;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PlusCopyPersonInfo
 * @Description  加签或抄送人员实体类
 * @author 21291
 * @date 2014年9月16日 上午9:45:01
 */
public class PlusCopyPersonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; //加签或抄送人员员工号
	private String FItemName; 	//加签或抄送人员名称
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	
	//默认构造函数
	public PlusCopyPersonInfo() {}
	
	public PlusCopyPersonInfo(String fItemNumber,String fItemName) {
		this.FItemNumber=fItemNumber;
		this.FItemName=fItemName;
	}
	
	// 将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<PlusCopyPersonInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (PlusCopyPersonInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FItemNumber", item.FItemNumber);
					jsonObject.put("FItemName", item.FItemName);
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
