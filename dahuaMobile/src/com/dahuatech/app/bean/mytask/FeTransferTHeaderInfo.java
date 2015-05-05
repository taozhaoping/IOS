package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeEngravingTHeaderInfo
 * @Description 印鉴移交表头单据实体
 * @author 21291
 * @date 2014年10月10日 下午5:26:44
 */
public class FeTransferTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDept;			//所属部门
	private String FApplyDate;			//申请时间
	private String FTel;				//联系电话
	private String FnKeeper;   			//新保管人
	private String FnKeeperTel;   		//新保管人电话
	private String FnKeeperDept;   		//新保管人部门
	private String FnKeeperArea;   		//新保管人区域
	private String FAmount;   			//附件数量
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
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFnKeeper() {
		return FnKeeper;
	}
	public void setFnKeeper(String fnKeeper) {
		FnKeeper = fnKeeper;
	}
	public String getFnKeeperTel() {
		return FnKeeperTel;
	}
	public void setFnKeeperTel(String fnKeeperTel) {
		FnKeeperTel = fnKeeperTel;
	}
	public String getFnKeeperDept() {
		return FnKeeperDept;
	}
	public void setFnKeeperDept(String fnKeeperDept) {
		FnKeeperDept = fnKeeperDept;
	}
	public String getFnKeeperArea() {
		return FnKeeperArea;
	}
	public void setFnKeeperArea(String fnKeeperArea) {
		FnKeeperArea = fnKeeperArea;
	}
	public String getFAmount() {
		return FAmount;
	}
	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static FeTransferTHeaderInfo instance = new FeTransferTHeaderInfo();  
    }
	private FeTransferTHeaderInfo() {}
	public static FeTransferTHeaderInfo getFeTransferTHeaderInfo() {
		return singletonHolder.instance;
	}
}
