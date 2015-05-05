package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PurchaseStockTHeaderInfo
 * @Description 采购备料单据表头实体
 * @author 21291
 * @date 2014年8月15日 下午2:25:42
 */
public class PurchaseStockTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请时间
	private String FDept;				//所属部门
	private String FProjectCode;   		//项目编码
	private String FProjectName;   		//项目名称
	private String FProcessName;   		//工艺负责人
	private String FTechnologyName;   	//技术负责人
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
	public String getFDept() {
		return FDept;
	}
	public void setFDept(String fDept) {
		FDept = fDept;
	}
	public String getFProjectCode() {
		return FProjectCode;
	}
	public void setFProjectCode(String fProjectCode) {
		FProjectCode = fProjectCode;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFProcessName() {
		return FProcessName;
	}
	public void setFProcessName(String fProcessName) {
		FProcessName = fProcessName;
	}
	public String getFTechnologyName() {
		return FTechnologyName;
	}
	public void setFTechnologyName(String fTechnologyName) {
		FTechnologyName = fTechnologyName;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static PurchaseStockTHeaderInfo instance = new PurchaseStockTHeaderInfo();  
    }
	private PurchaseStockTHeaderInfo() {}
	public static PurchaseStockTHeaderInfo getPurchaseStockTHeaderInfo() {
		return singletonHolder.instance;
	}
}
