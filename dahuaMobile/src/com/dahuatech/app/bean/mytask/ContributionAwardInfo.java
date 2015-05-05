package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @Title: ContributionAwardHeaderInfo.java
 * @Package com.dahuatech.app.bean.mytask
 * @Description: 长期贡献奖金申请
 * @date 2015-3-16 下午4:03:24
 * @author taozhaoping 26078
 * @author mail taozhaoping@gmail.com
 * @version V1.0
 */
public class ContributionAwardInfo extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String FBillNo; // 单据编号
	private String FBillerName; // 申请人姓名

	private String FBillerDeptName;// 部门编号
	private String FApplyDate; // 申请时间

	private String FDate; // 入司时间

	private String FText; // 岗位
	private String FAmount; // 申请金额（元）
	private String FText2; // 开户银行与帐号信息
	private String FCheckBox; // 生活补助申请事由（或说明）
	private String FInteger; // 工作年数

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFBillerName() {
		return FBillerName;
	}

	public void setFBillerName(String fBillerName) {
		FBillerName = fBillerName;
	}

	public String getFBillerDeptName() {
		return FBillerDeptName;
	}

	public void setFBillerDeptName(String fBillerDeptName) {
		FBillerDeptName = fBillerDeptName;
	}

	public String getFApplyDate() {
		return FApplyDate;
	}

	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}

	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getFText() {
		return FText;
	}

	public void setFText(String fText) {
		FText = fText;
	}

	public String getFAmount() {
		return FAmount;
	}

	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}

	public String getFText2() {
		return FText2;
	}

	public void setFText2(String fText2) {
		FText2 = fText2;
	}

	public String getFCheckBox() {
		return FCheckBox;
	}

	public void setFCheckBox(String fCheckBox) {
		FCheckBox = fCheckBox;
	}

	public String getFInteger() {
		return FInteger;
	}

	public void setFInteger(String fInteger) {
		FInteger = fInteger;
	}

	// 内部类单例模式
	private static class singletonHolder {
		private static ContributionAwardInfo instance = new ContributionAwardInfo();
	}

	private ContributionAwardInfo() {
	}

	public static ContributionAwardInfo getContributionAwardInfo() {
		return singletonHolder.instance;
	}

}
