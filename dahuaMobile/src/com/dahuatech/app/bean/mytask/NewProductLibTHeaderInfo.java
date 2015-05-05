package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductLibTHeaderInfo
 * @Description 新产品转库表头单据实体
 * @author 21291
 * @date 2014年8月12日 上午10:32:20
 */
public class NewProductLibTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请时间
	private String FApplyDept;			//所属部门
	private String FTel;				//联系电话
	private String FMaterialType;   	//转库物料类型
	private String FProductLine;   		//产品线
	private String FDescribe;   		//描述
	private String FReason;   			//原因
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
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFMaterialType() {
		return FMaterialType;
	}
	public void setFMaterialType(String fMaterialType) {
		FMaterialType = fMaterialType;
	}
	public String getFProductLine() {
		return FProductLine;
	}
	public void setFProductLine(String fProductLine) {
		FProductLine = fProductLine;
	}
	public String getFDescribe() {
		return FDescribe;
	}
	public void setFDescribe(String fDescribe) {
		FDescribe = fDescribe;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static NewProductLibTHeaderInfo instance = new NewProductLibTHeaderInfo();  
    }
	private NewProductLibTHeaderInfo() {}
	public static NewProductLibTHeaderInfo getNewProductLibTHeaderInfo() {
		return singletonHolder.instance;
	}
}
