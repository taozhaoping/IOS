package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName AttendanceListInfo
 * @Description 考勤记录列表实体
 * @author 21291
 * @date 2014年12月18日 下午1:39:28
 */
public class AdListInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FAttendanceDate;  	//考勤日期
	private String FType;				//类型
	private String FAmCheckInTime;		//上午签入时间
	private String FPmCheckOutTime;		//下午签出时间
	private String FAmResult;			//上午签入结果
	private String FPmResult;			//下午签出结果
	public String getFAttendanceDate() {
		return FAttendanceDate;
	}
	public void setFAttendanceDate(String fAttendanceDate) {
		FAttendanceDate = fAttendanceDate;
	}
	public String getFType() {
		return FType;
	}
	public void setFType(String fType) {
		FType = fType;
	}
	public String getFAmCheckInTime() {
		return FAmCheckInTime;
	}
	public void setFAmCheckInTime(String fAmCheckInTime) {
		FAmCheckInTime = fAmCheckInTime;
	}
	public String getFPmCheckOutTime() {
		return FPmCheckOutTime;
	}
	public void setFPmCheckOutTime(String fPmCheckOutTime) {
		FPmCheckOutTime = fPmCheckOutTime;
	}
	public String getFAmResult() {
		return FAmResult;
	}
	public void setFAmResult(String fAmResult) {
		FAmResult = fAmResult;
	}
	public String getFPmResult() {
		return FPmResult;
	}
	public void setFPmResult(String fPmResult) {
		FPmResult = fPmResult;
	}

}
