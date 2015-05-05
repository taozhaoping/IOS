package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductReworkTHeaderInfo
 * @Description 新产品返工表头单据实体
 * @author 21291
 * @date 2014年8月27日 上午9:36:02
 */
public class NewProductReworkTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请时间
	private String FApplyDept;			//所属部门
	private String FProductLine;   		//产品线
	private String FProductName;   		//产品名称
	private String FProductModel;   	//产品型号
	private String FProjectName;   		//项目名称
	private String FProjectCode;   		//项目编号
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
	public String getFProductLine() {
		return FProductLine;
	}
	public void setFProductLine(String fProductLine) {
		FProductLine = fProductLine;
	}
	public String getFProductName() {
		return FProductName;
	}
	public void setFProductName(String fProductName) {
		FProductName = fProductName;
	}
	public String getFProductModel() {
		return FProductModel;
	}
	public void setFProductModel(String fProductModel) {
		FProductModel = fProductModel;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
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
        private static NewProductReworkTHeaderInfo instance = new NewProductReworkTHeaderInfo();  
    }
	private NewProductReworkTHeaderInfo() {}
	public static NewProductReworkTHeaderInfo getNewProductReworkTHeaderInfo() {
		return singletonHolder.instance;
	}
}
