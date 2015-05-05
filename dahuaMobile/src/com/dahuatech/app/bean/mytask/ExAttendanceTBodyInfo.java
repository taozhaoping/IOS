package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExAttendanceTBodyInfo
 * @Description 异常考勤调整申请单据表体实体
 * @author 21291
 * @date 2014年7月23日 下午2:40:21
 */
public class ExAttendanceTBodyInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FDate;				//加班日期
	private String FStartTime;			//上午时间
	private String FOldStartResult;		//上午结果
	private String FChangeStartTime;	//上午改签后时间
	private String FStartResult;		//上午改签后结果
	private String FEndTime;			//下午时间
	private String FOldEndResult;		//下午结果
	private String FChangeEndTime;		//下午改签后时间
	private String FEndResult;			//下午改签后结果
	private String FReason;				//改签事由
	
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

	public String getFOldStartResult() {
		return FOldStartResult;
	}

	public void setFOldStartResult(String fOldStartResult) {
		FOldStartResult = fOldStartResult;
	}

	public String getFChangeStartTime() {
		return FChangeStartTime;
	}

	public void setFChangeStartTime(String fChangeStartTime) {
		FChangeStartTime = fChangeStartTime;
	}

	public String getFStartResult() {
		return FStartResult;
	}

	public void setFStartResult(String fStartResult) {
		FStartResult = fStartResult;
	}

	public String getFEndTime() {
		return FEndTime;
	}

	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}

	public String getFOldEndResult() {
		return FOldEndResult;
	}

	public void setFOldEndResult(String fOldEndResult) {
		FOldEndResult = fOldEndResult;
	}

	public String getFChangeEndTime() {
		return FChangeEndTime;
	}

	public void setFChangeEndTime(String fChangeEndTime) {
		FChangeEndTime = fChangeEndTime;
	}

	public String getFEndResult() {
		return FEndResult;
	}

	public void setFEndResult(String fEndResult) {
		FEndResult = fEndResult;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
