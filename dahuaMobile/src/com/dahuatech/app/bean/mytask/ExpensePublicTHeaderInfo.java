package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName ExpensePublicInfo
 * @Description 对公支付报销单据实体
 * @author 21291
 * @date 2014年6月4日 上午10:34:11
 */
public class ExpensePublicTHeaderInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID; 				//主键内码(单据内码)
	private String FGeneralType; 		//单据小类（名称）
	private String FBillNo; 			//单据编号
	private String FConSmName; 			//报销人名称
	private String FCommitDate; 		//提单日期
	private String FAmountAll; 			//报销金额
	private String FRecAccName; 		//收款方账户名
	private String FContent; 			//事由
	
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

	public String getFRecAccName() {
		return FRecAccName;
	}

	public void setFRecAccName(String fRecAccName) {
		FRecAccName = fRecAccName;
	}

	public String getFContent() {
		return FContent;
	}

	public void setFContent(String fContent) {
		FContent = fContent;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static ExpensePublicTHeaderInfo instance = new ExpensePublicTHeaderInfo();  
    }
	private ExpensePublicTHeaderInfo() {}
	public static ExpensePublicTHeaderInfo getExpensePublicTHeaderInfo() {
		return singletonHolder.instance;
	}
}
