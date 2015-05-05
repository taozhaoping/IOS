package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseCostTHeaderInfo
 * @Description 费用申请单据实体
 * @author 21291
 * @date 2014年6月16日 下午1:53:04
 */
public class ExpenseCostTHeaderInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID;   //单据内码
	private String FGeneralType; //单据小类（内码）
	private String FGeneralCode; //单据小类（编码）
	private String FGeneralName; //单据小类（内码）
	private String FBillNo;      //单据编号
	private String FConSmName;   //报销人名称
	private String FCommitDate;  //提单时间
	private String FAppAmt;  	 //预申请金额
	private String FLDAmt;		 //借款金额
	private String FCostType;    //费用类型
	
	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFGeneralType() {
		return FGeneralType;
	}

	public void setFGeneralType(String fGeneralType) {
		FGeneralType = fGeneralType;
	}
	
	public String getFGeneralCode() {
		return FGeneralCode;
	}

	public void setFGeneralCode(String fGeneralCode) {
		FGeneralCode = fGeneralCode;
	}

	public String getFGeneralName() {
		return FGeneralName;
	}

	public void setFGeneralName(String fGeneralName) {
		FGeneralName = fGeneralName;
	}

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFConSmName() {
		return FConSmName;
	}

	public void setFConSmName(String fConSmName) {
		FConSmName = fConSmName;
	}

	public String getFCommitDate() {
		return FCommitDate;
	}

	public void setFCommitDate(String fCommitDate) {
		FCommitDate = fCommitDate;
	}

	public String getFAppAmt() {
		return FAppAmt;
	}

	public void setFAppAmt(String fAppAmt) {
		FAppAmt = fAppAmt;
	}

	public String getFLDAmt() {
		return FLDAmt;
	}

	public void setFLDAmt(String fLDAmt) {
		FLDAmt = fLDAmt;
	}

	public String getFCostType() {
		return FCostType;
	}

	public void setFCostType(String fCostType) {
		FCostType = fCostType;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ExpenseCostTHeaderInfo instance = new ExpenseCostTHeaderInfo();  
    }
	private ExpenseCostTHeaderInfo() {}
	public static ExpenseCostTHeaderInfo getExpenseCostTHeaderInfo() {
		return singletonHolder.instance;
	}
}
