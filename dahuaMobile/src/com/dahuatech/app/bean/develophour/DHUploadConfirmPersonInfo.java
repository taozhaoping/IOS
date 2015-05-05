package com.dahuatech.app.bean.develophour;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DHUploadConfirmInfo
 * @Description 研发工时确认人员参数实体
 * @author 21291
 * @date 2014年11月19日 上午11:08:42
 */
public class DHUploadConfirmPersonInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	public String FProjectCode;     //选中的项目编号,
	public String FConfirmNumber;   //确认人员工号,多个员工号拼接，用","分割
	
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFConfirmNumber() {
		return FConfirmNumber;
	}
	public void setFConfirmNumber(String fConfirmNumber) {
		FConfirmNumber = fConfirmNumber;
	}
	
	public DHUploadConfirmPersonInfo() {}
	
	//将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<DHUploadConfirmPersonInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (DHUploadConfirmPersonInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("FProjectCode", item.FProjectCode);
					jsonObject.put("FConfirmNumber", item.FConfirmNumber);
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
