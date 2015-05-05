package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyOverTimeTBodyInfo
 * @Description 加班申请单据表体实体
 * @author 21291
 * @date 2014年7月23日 下午2:26:26
 */
public class ApplyOverTimeTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FDate;				//加班日期
	private String FStartTime;			//上午
	private String FEndTime;			//下午
	private String FUse;				//用途
	private String FReason;				//加班原因
	private String FAttenTime;			//当日考勤
	private String FAttendance;			//额外出勤
	
	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getFStartTime() {
		return FStartTime;
	}

	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}

	public String getFEndTime() {
		return FEndTime;
	}

	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}

	public String getFUse() {
		return FUse;
	}

	public void setFUse(String fUse) {
		FUse = fUse;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}

	public String getFAttenTime() {
		return FAttenTime;
	}

	public void setFAttenTime(String fAttenTime) {
		FAttenTime = fAttenTime;
	}

	public String getFAttendance() {
		return FAttendance;
	}

	public void setFAttendance(String fAttendance) {
		FAttendance = fAttendance;
	}
}
