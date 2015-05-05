package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Entity;


/**
 * @ClassName EngineeringInfo
 * @Description 工程商单据实体类
 * @author 21291
 * @date 2014年4月22日 下午6:14:14
 */
public class EngineeringInfo extends Entity {

	private static final long serialVersionUID = 1L;
	
	private String FBillNo;			//单据编码
	private String FApplyName;		//申请人
	private String FApplyDate;		//申请时间
	private String FEngineereName;  //工程商名称
	private String FAddress; 		//地址
	private String FAmount;			//预计大华金额
	private String FContact; 		//联系人
	private String FTel;			//联系电话
	private String FComboBox; 		//圈定类型
	private String FText; 			//原圈定级别
	private String FComboBox1; 		//目标圈定级别
	private String FBase1; 			//圈定给谁
	private String FNote;	 		//原因说明

	public String getFBillNo() {
		return FBillNo;
	}

	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}

	public String getFApplyName() {
		return FApplyName;
	}

	public void setFApplyName(String fApplyName) {
		FApplyName = fApplyName;
	}

	public String getFApplyDate() {
		return FApplyDate;
	}

	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}

	public String getFEngineereName() {
		return FEngineereName;
	}

	public void setFEngineereName(String fEngineereName) {
		FEngineereName = fEngineereName;
	}

	public String getFAddress() {
		return FAddress;
	}

	public void setFAddress(String fAddress) {
		FAddress = fAddress;
	}

	public String getFAmount() {
		return FAmount;
	}

	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}

	public String getFContact() {
		return FContact;
	}
	
	public String getFTel() {
		return FTel;
	}

	public void setFTel(String fTel) {
		FTel = fTel;
	}

	public void setFContact(String fContact) {
		FContact = fContact;
	}

	public String getFComboBox() {
		return FComboBox;
	}

	public void setFComboBox(String fComboBox) {
		FComboBox = fComboBox;
	}

	public String getFText() {
		return FText;
	}

	public void setFText(String fText) {
		FText = fText;
	}

	public String getFComboBox1() {
		return FComboBox1;
	}

	public void setFComboBox1(String fComboBox1) {
		FComboBox1 = fComboBox1;
	}

	public String getFBase1() {
		return FBase1;
	}

	public void setFBase1(String fBase1) {
		FBase1 = fBase1;
	}

	public String getFNote() {
		return FNote;
	}

	public void setFNote(String fNote) {
		FNote = fNote;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static EngineeringInfo instance = new EngineeringInfo();  
    }
	private EngineeringInfo() {}
	public static EngineeringInfo getEngineeringInfo() {
		return singletonHolder.instance;
	}
}
