package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
* @Title: ExpenseSpecialThingHeaderInfo.java 
* @Package com.dahuatech.app.bean.mytask 
* @Description: 特批事务报销单据表头实体
* @date 2015年4月7日 下午2:46:54 
* @author taozhaoping 26078
* @author mail taozhaoping@gmail.com
* @version V1.0
 */
public class ExpenseSpecialThingHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FID;   		 //单据内码
	private String FGeneralType; //单据小类（内码）
	private String FGeneralCode; //单据小类（编码）
	private String FGeneralName; //单据小类（内码）
	private String FBillNo;      //单据编号
	private String FConSmName;   //报销人名称
	private String FCommitDate;  //提单时间
	private String FAmountAll;   //金额
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

	public String getFAmountAll() {
		return FAmountAll;
	}

	public void setFAmountAll(String fAmountAll) {
		FAmountAll = fAmountAll;
	}

	public String getFCostType() {
		return FCostType;
	}

	public void setFCostType(String fCostType) {
		FCostType = fCostType;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ExpenseSpecialThingHeaderInfo instance = new ExpenseSpecialThingHeaderInfo();  
    }
	private ExpenseSpecialThingHeaderInfo() {}
	public static ExpenseSpecialThingHeaderInfo getExpenseSpecialThingHeaderInfo() {
		return singletonHolder.instance;
	}
}
