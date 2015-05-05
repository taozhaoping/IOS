package com.dahuatech.app.bean;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName StorageParameterInfo
 * @Description 存储过程参数类
 * @author 21291
 * @date 2014年4月23日 下午2:51:43
 */
public class StorageParameterInfo extends Base {
	
	private static final long	serialVersionUID	= 1L;
	public String Key; //存储参数名
	public String Value; //存储参数值
	public String SqlDbType; //存储参数类型
	public int ValueLength; //存储参数值长度
	public boolean IsOutput; //是否输出

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getSqlDbType() {
		return SqlDbType;
	}

	public void setSqlDbType(String sqlDbType) {
		SqlDbType = sqlDbType;
	}

	public int getValueLength() {
		return ValueLength;
	}

	public void setValueLength(int valueLength) {
		ValueLength = valueLength;
	}

	public boolean isIsOutput() {
		return IsOutput;
	}

	public void setIsOutput(boolean isOutput) {
		IsOutput = isOutput;
	}
	
	public StorageParameterInfo() {
		IsOutput = false;
	}
	
	public StorageParameterInfo(String key,String value,String sqlDbType,int valueLength,boolean isOutput) {
		this.Key=key;
		this.Value=value;
		this.SqlDbType=sqlDbType;
		this.ValueLength=valueLength;
		this.IsOutput=isOutput;
	}

	// 将List转化成json以便于在网络中传输
	public static String ConvertToJson(List<StorageParameterInfo> items) {
		String jsonString = "";
		JSONArray jsonArray = new JSONArray();
		try {
			if (items.size() > 0) {
				for (StorageParameterInfo item : items) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Key", item.Key);
					jsonObject.put("Value", item.Value);
					jsonObject.put("SqlDbType", item.SqlDbType);
					jsonObject.put("ValueLength", item.ValueLength);
					jsonObject.put("IsOutput", item.IsOutput);
					jsonArray.put(jsonObject);
				}
				jsonString = jsonArray.toString();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(StorageParameterInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Key", item.Key);
			jsonObject.put("Value", item.Value);
			jsonObject.put("SqlDbType", item.SqlDbType);
			jsonObject.put("ValueLength", item.ValueLength);
			jsonObject.put("IsOutput", item.IsOutput);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
