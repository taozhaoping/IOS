package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseMarketBidTHeaderInfo
 * @Description 市场投标报销实体类信息
 * @author 21291
 * @date 2014年6月25日 下午1:50:51
 */
public class ExpenseMarketBidTHeaderInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FID;   			//单据内码
	private String FBillNo;      	//单据编号
	private String FConSmName;   	//报销人名称
	private String FCommitDate;  	//提单时间
	private String FAmountAll;   	//支付总金额
	private String FRecAccName;  	//收款方账户名
	private String FPubPayNo;    	//投标申请单号
	private String FProjectName; 	//项目名称
	private String FConSmTypeName;  //费用类型
	private String FCaseType;		//情况分类
	private String FSetOffType;		//冲账类型
	
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

	public String getFPubPayNo() {
		return FPubPayNo;
	}

	public void setFPubPayNo(String fPubPayNo) {
		FPubPayNo = fPubPayNo;
	}

	public String getFProjectName() {
		return FProjectName;
	}

	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}

	public String getFConSmTypeName() {
		return FConSmTypeName;
	}

	public void setFConSmTypeName(String fConSmTypeName) {
		FConSmTypeName = fConSmTypeName;
	}

	public String getFCaseType() {
		return FCaseType;
	}

	public void setFCaseType(String fCaseType) {
		FCaseType = fCaseType;
	}

	public String getFSetOffType() {
		return FSetOffType;
	}

	public void setFSetOffType(String fSetOffType) {
		FSetOffType = fSetOffType;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static ExpenseMarketBidTHeaderInfo instance = new ExpenseMarketBidTHeaderInfo();  
    }
	private ExpenseMarketBidTHeaderInfo() {}
	public static ExpenseMarketBidTHeaderInfo getExpenseMarketBidTHeaderInfo() {
		return singletonHolder.instance;
	}
}
