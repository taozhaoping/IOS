package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpensePrivateTBodyInfo
 * @Description 对私报销单据表体实体
 * @author 21291
 * @date 2014年5月23日 上午9:29:19
 */
public class ExpensePrivateTBodyInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private int FID; 	  		 //主键内码
	private String FConSmDate;	 //报销消费时间
	private String FConSmType;	 //报销消费类型
	private String FAmount;  	 //金额
	private String FProjectName; //项目名称
	private String FClientName;  //客户名称
	private String FUse;         //事由
	
	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}
	
	public String getFConSmDate() {
		return FConSmDate;
	}

	public void setFConSmDate(String fConSmDate) {
		FConSmDate = fConSmDate;
	}

	public String getFConSmType() {
		return FConSmType;
	}

	public void setFConSmType(String fConSmType) {
		FConSmType = fConSmType;
	}

	public String getFAmount() {
		return FAmount;
	}

	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}

	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public String getFClientName() {
		return FClientName;
	}

	public void setFClientName(String fClientName) {
		FClientName = fClientName;
	}

	public String getFUse() {
		return FUse;
	}

	public void setFUse(String fUse) {
		FUse = fUse;
	}
}
