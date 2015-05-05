package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName ExpensePrivateTHeaderInfo
 * @Description 对私报销单据表头实体
 * @author 21291
 * @date 2014年5月22日 下午5:22:45
 */
public class ExpensePrivateTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FID; 				//主键内码(单据内码)
	private String FBillNo; 			//单据编号
	private String FAmountAll; 			//报销金额
	private String FConSmAmountAll; 	//费用金额合计
	private String FConSmName; 			//报销人名称
	private String FCommitDate; 		//提单日期
	private String FCostType; 			//费用类型

	public String getFID() {
		return FID;
	}

	public void setFID(String fID) {
		FID = fID;
	}

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFAmountAll() {
		return FAmountAll;
	}

	public void setFAmountAll(String fAmountAll) {
		FAmountAll = fAmountAll;
	}

	public String getFConSmAmountAll() {
		return FConSmAmountAll;
	}

	public void setFConSmAmountAll(String fConSmAmountAll) {
		FConSmAmountAll = fConSmAmountAll;
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

	public String getFCostType() {
		return FCostType;
	}

	public void setFCostType(String fCostType) {
		FCostType = fCostType;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static ExpensePrivateTHeaderInfo instance = new ExpensePrivateTHeaderInfo();  
    }
	private ExpensePrivateTHeaderInfo() {}
	public static ExpensePrivateTHeaderInfo getExpensePrivateTHeaderInfo() {
		return singletonHolder.instance;
	}
}
