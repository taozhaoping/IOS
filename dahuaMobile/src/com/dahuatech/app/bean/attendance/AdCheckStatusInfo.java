package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdCheckStatusInfo
 * @Description 考勤状态实体
 * @author 21291
 * @date 2014年12月29日 下午4:44:23
 */
public class AdCheckStatusInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private int FStatus;  			//考勤状态  0 代表未打卡 1代表上午未签入 下午已签出 2代表打卡未签出 3代表已打卡已签出
	private int FAttendId;			//考勤ID
	private String FCheckInTime;	//签入时间
	private String FCheckOutTime;	//签出时间
	
	public int getFStatus() {
		return FStatus;
	}
	public void setFStatus(int fStatus) {
		FStatus = fStatus;
	}
	public int getFAttendId() {
		return FAttendId;
	}
	public void setFAttendId(int fAttendId) {
		FAttendId = fAttendId;
	}
	public String getFCheckInTime() {
		return FCheckInTime;
	}
	public void setFCheckInTime(String fCheckInTime) {
		FCheckInTime = fCheckInTime;
	}
	public String getFCheckOutTime() {
		return FCheckOutTime;
	}
	public void setFCheckOutTime(String fCheckOutTime) {
		FCheckOutTime = fCheckOutTime;
	}
	
	//内部类单例模式
	private static class SingletonHolder {  
        private static AdCheckStatusInfo instance = new AdCheckStatusInfo();  
    }
	private AdCheckStatusInfo() {}
	public static AdCheckStatusInfo getAdCheckStatusInfo() {
		return SingletonHolder.instance;
	}
}
