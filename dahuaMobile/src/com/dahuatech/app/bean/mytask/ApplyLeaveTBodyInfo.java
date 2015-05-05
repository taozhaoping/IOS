package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyLeaveTBodyInfo
 * @Description 请假申请单据表体实体
 * @author 21291
 * @date 2015年1月12日 上午9:34:22
 */
public class ApplyLeaveTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FHolidayType;		//假期类型
	private String FStartDate;			//开始日期
	private String FStartTime;			//开始时间
	private String FEndDate;			//结束日期
	private String FEndTime;			//结束时间
	private String FTimeLength;			//时间长度
	private String FReason;				//原因
	
	public String getFHolidayType() {
		return FHolidayType;
	}
	public void setFHolidayType(String fHolidayType) {
		FHolidayType = fHolidayType;
	}
	public String getFStartDate() {
		return FStartDate;
	}
	public void setFStartDate(String fStartDate) {
		FStartDate = fStartDate;
	}
	public String getFStartTime() {
		return FStartTime;
	}
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	public String getFEndDate() {
		return FEndDate;
	}
	public void setFEndDate(String fEndDate) {
		FEndDate = fEndDate;
	}
	public String getFEndTime() {
		return FEndTime;
	}
	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}
	public String getFTimeLength() {
		return FTimeLength;
	}
	public void setFTimeLength(String fTimeLength) {
		FTimeLength = fTimeLength;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
