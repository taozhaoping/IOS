package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyResumeTBodyInfo
 * @Description 销假申请单据表体实体
 * @author 21291
 * @date 2015年1月12日 上午10:23:24
 */
public class ApplyResumeTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FStartDate;			//开始日期
	private String FAmCheckTime;		//上午签入时间
	private String FAmCheckResult;		//上午签入结果
	private String FAmResume;			//上午销假
	private String FPmCheckTime;		//下午签出时间
	private String FPmCheckResult;		//下午签出结果
	private String FPmResume;			//下午销假
	private String FReason;				//销假原因
	
	public String getFStartDate() {
		return FStartDate;
	}
	public void setFStartDate(String fStartDate) {
		FStartDate = fStartDate;
	}
	public String getFAmCheckTime() {
		return FAmCheckTime;
	}
	public void setFAmCheckTime(String fAmCheckTime) {
		FAmCheckTime = fAmCheckTime;
	}
	public String getFAmCheckResult() {
		return FAmCheckResult;
	}
	public void setFAmCheckResult(String fAmCheckResult) {
		FAmCheckResult = fAmCheckResult;
	}
	public String getFAmResume() {
		return FAmResume;
	}
	public void setFAmResume(String fAmResume) {
		FAmResume = fAmResume;
	}
	public String getFPmCheckTime() {
		return FPmCheckTime;
	}
	public void setFPmCheckTime(String fPmCheckTime) {
		FPmCheckTime = fPmCheckTime;
	}
	public String getFPmCheckResult() {
		return FPmCheckResult;
	}
	public void setFPmCheckResult(String fPmCheckResult) {
		FPmCheckResult = fPmCheckResult;
	}
	public String getFPmResume() {
		return FPmResume;
	}
	public void setFPmResume(String fPmResume) {
		FPmResume = fPmResume;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
}
