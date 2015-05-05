package com.dahuatech.app.bean.develophour;

import org.json.JSONException;
import org.json.JSONObject;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHDetailInfo
 * @Description 研发工时详情实体类
 * @author 21291
 * @date 2014年10月28日 下午5:27:12
 */
public class DHDetailInfo extends Base {
	private static final long serialVersionUID = 1L;

	public int FBillId;				//周单据Id
	public String FItemNumber;		//员工号
	public String FWeekDate;		//工作日期
	public String FProjectCode;		//项目编号
	public String FProjectName;		//项目名称
	public String FTypeId;			//任务类别Id
	public String FTypeName;		//任务类别名称
	public String FHours;			//工时
	public String FNote;			//备注
	
	public int getFBillId() {
		return FBillId;
	}
	public void setFBillId(int fBillId) {
		FBillId = fBillId;
	}
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFWeekDate() {
		return FWeekDate;
	}
	public void setFWeekDate(String fWeekDate) {
		FWeekDate = fWeekDate;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFTypeId() {
		return FTypeId;
	}
	public void setFTypeId(String fTypeId) {
		FTypeId = fTypeId;
	}
	public String getFTypeName() {
		return FTypeName;
	}
	public void setFTypeName(String fTypeName) {
		FTypeName = fTypeName;
	}
	public String getFHours() {
		return FHours;
	}
	public void setFHours(String fHours) {
		FHours = fHours;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
	
	//内部类单例模式
	private static class SingletonHolder {  
        private static DHDetailInfo instance = new DHDetailInfo();  
    }
	public DHDetailInfo() {}
	public static DHDetailInfo getDHDetailInfo() {
		return SingletonHolder.instance;
	}
	
	// 将实体对象转化成json以便于在网络中传输
	public static String ConvertToJson(DHDetailInfo item) {
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		try {			
			jsonObject.put("FBillId", item.FBillId);
			jsonObject.put("FItemNumber", item.FItemNumber);
			jsonObject.put("FWeekDate", item.FWeekDate);
			jsonObject.put("FProjectCode", item.FProjectCode);
			jsonObject.put("FProjectName", item.FProjectName);
			jsonObject.put("FTypeId", item.FTypeId);
			jsonObject.put("FTypeName", item.FTypeName);
			jsonObject.put("FHours", item.FHours);
			jsonObject.put("FNote", item.FNote);
			jsonString = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
