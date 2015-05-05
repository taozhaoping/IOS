package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHProjectParamInfo
 * @Description 研发工时项目搜索参数实体类
 * @author 21291
 * @date 2014年10月30日 下午5:07:22
 */
public class DHProjectParamInfo extends Base {
	private static final long serialVersionUID = 1L;

	private String FQueryText; // 查询文本
	private int FPageIndex; // 页数
	private int FPageSize; // 页大小

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

	// 内部类单例模式
	private static class SingletonHolder {
		private static DHProjectParamInfo instance = new DHProjectParamInfo();
	}
	private DHProjectParamInfo() {}
	public static DHProjectParamInfo getDHProjectParamInfo() {
		return SingletonHolder.instance;
	}

	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(DHProjectParamInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
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
