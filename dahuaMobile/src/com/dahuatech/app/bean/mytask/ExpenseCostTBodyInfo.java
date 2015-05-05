package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseCostTBodyInfo
 * @Description 报销费用申请单据实体
 * @author 21291
 * @date 2014年6月17日 上午11:12:04
 */
public class ExpenseCostTBodyInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID;    			//明细内码
	private String FConSmType; 		//费用类型
	private String FConSmDate;  	//申请日期
	private String FAppAmt;			//预申请金额
	private String FLendType;		//借款类型
	private String FLDAmt;			//借款金额
	private String FProjectName;	//项目
	private String FUse;			//事由
	
	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFConSmType() {
		return FConSmType;
	}

	public void setFConSmType(String fConSmType) {
		FConSmType = fConSmType;
	}

	public String getFConSmDate() {
		return FConSmDate;
	}

	public void setFConSmDate(String fConSmDate) {
		FConSmDate = fConSmDate;
	}

	public String getFAppAmt() {
		return FAppAmt;
	}

	public void setFAppAmt(String fAppAmt) {
		FAppAmt = fAppAmt;
	}

	public String getFLendType() {
		return FLendType;
	}

	public void setFLendType(String fLendType) {
		FLendType = fLendType;
	}

	public String getFLDAmt() {
		return FLDAmt;
	}

	public void setFLDAmt(String fLDAmt) {
		FLDAmt = fLDAmt;
	}

	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public String getFUse() {
		return FUse;
	}

	public void setFUse(String fUse) {
		FUse = fUse;
	}

}
