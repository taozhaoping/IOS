package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopInquiryTHeaderInfo
 * @Description 研发中心询价申请表头实体信息
 * @author 21291
 * @date 2014年7月16日 上午9:25:22
 */
public class DevelopInquiryTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FDate;  				//申请时间
	private String FApplyerDeptName;	//申请部门
	private String FEmployeeNumber;     //申请人工号
	private String FMaterialType;		//物料类别
	private String FOfferExplain;		//报价说明
	private String FSubEntrys;			//子集集合

	public int getFID() {
		return FID;
	}

	public void setFID(int fID) {
		FID = fID;
	}

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

	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getFApplyerDeptName() {
		return FApplyerDeptName;
	}

	public void setFApplyerDeptName(String fApplyerDeptName) {
		FApplyerDeptName = fApplyerDeptName;
	}

	public String getFEmployeeNumber() {
		return FEmployeeNumber;
	}

	public void setFEmployeeNumber(String fEmployeeNumber) {
		FEmployeeNumber = fEmployeeNumber;
	}

	public String getFMaterialType() {
		return FMaterialType;
	}

	public void setFMaterialType(String fMaterialType) {
		FMaterialType = fMaterialType;
	}

	public String getFOfferExplain() {
		return FOfferExplain;
	}

	public void setFOfferExplain(String fOfferExplain) {
		FOfferExplain = fOfferExplain;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类 单例模式
	private static class singletonHolder {  
        private static DevelopInquiryTHeaderInfo instance = new DevelopInquiryTHeaderInfo();  
    } 
	private DevelopInquiryTHeaderInfo() {}
	public static DevelopInquiryTHeaderInfo getDevelopInquiryTHeaderInfo() {
		return singletonHolder.instance;
	}
}
