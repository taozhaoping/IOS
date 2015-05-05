package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName UserInfo
 * @Description 用户实体
 * @author 21291
 * @date 2014年4月17日 上午11:27:16
 */
public class UserInfo extends Entity {
	
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber; 	//员工号
	private String FItemName; 		//员工名称
	private String FPassword;		//密码
	private boolean IsRememberMe; 	//是否记住登陆信息
		
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
		this.FItemName = fItemName;
	}

	public String getFPassword() {
		return FPassword;
	}

	public void setFPassword(String fPassword) {
		FPassword = fPassword;
	}

	public boolean isIsRememberMe() {
		return IsRememberMe;
	}

	public void setIsRememberMe(boolean isRememberMe) {
		IsRememberMe = isRememberMe;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static UserInfo instance = new UserInfo();  
    }
	private UserInfo() {
		IsRememberMe=false;
	}
	private static UserInfo userInfo;
	public static UserInfo getUserInfo() {
		return singletonHolder.instance;
	}

	public static UserInfo getUserInfo(String fItemNumber,String fPassword,boolean isRememberMe) {
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		userInfo.setFItemNumber(fItemNumber);
		userInfo.setFPassword(fPassword);
		userInfo.setIsRememberMe(isRememberMe);
		return userInfo;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(UserInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FPassword", item.FPassword);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
