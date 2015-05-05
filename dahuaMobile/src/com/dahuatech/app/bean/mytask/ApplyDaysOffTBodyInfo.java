package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyDaysOffTBodyInfo
 * @Description 普通部门调休申请单据表体实体
 * @author 21291
 * @date 2014年7月23日 下午2:47:57
 */
public class ApplyDaysOffTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FDate;			//调休时间
	private String FStartTime;		//上午时间
	private String FEndTime;		//下午时间
	private String FHours;			//调休时数
	private String FReason;			//调休原因
	
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

	public String getFHours() {
		return FHours;
	}

	public void setFHours(String fHours) {
		FHours = fHours;
	}

	public String getFReason() {
		return FReason;
	}

	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
