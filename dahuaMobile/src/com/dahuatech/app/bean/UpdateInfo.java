package com.dahuatech.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName Update
 * @Description 应用程序更新实体类
 * @author 21291
 * @date 2014年4月17日 下午3:47:15
 */
public class UpdateInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private int VersionCode; 	//版本代码
	private String VersionName; //版本名称
	private String DownloadUrl; //下载地址
	private String UpdateLog;	//更新日志

	public int getVersionCode() {
		return VersionCode;
	}

	public void setVersionCode(int versionCode) {
		VersionCode = versionCode;
	}

	public String getVersionName() {
		return VersionName;
	}

	public void setVersionName(String versionName) {
		VersionName = versionName;
	}

	public String getDownloadUrl() {
		return DownloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		DownloadUrl = downloadUrl;
	}

	public String getUpdateLog() {
		return UpdateLog;
	}

	public void setUpdateLog(String updateLog) {
		UpdateLog = updateLog;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static UpdateInfo instance = new UpdateInfo();  
    }
	private UpdateInfo() {}	
	public static UpdateInfo getUpdate() {
		return singletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(UpdateInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("VersionCode", item.VersionCode);
			jsonObject.put("VersionName", item.VersionName);
			jsonObject.put("DownloadUrl", item.DownloadUrl);
			jsonObject.put("UpdateLog", item.UpdateLog);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
