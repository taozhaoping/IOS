package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeTakeOutTHeaderInfo
 * @Description 印鉴外带表头单据实体
 * @author 21291
 * @date 2014年10月11日 上午9:21:26
 */
public class FeTakeOutTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDept;			//所属部门
	private String FApplyDate;			//申请时间
	private String FTel;				//申请人电话
	private String FeName;   			//申请外带印鉴名称
	private String FeCode;				//印鉴代码
	private String FStartTime;			//开始时间
	private String FEndTime;			//结束时间
	private String FeCarry;				//印鉴携带人
	private String FeCarryTel;			//携带人电话
	private String FDestination;		//携带目的地
	
	private String FProjectName;		//项目/事项名称
	private String FSealData;			//拟用印资料
	private String FeUseCarry;			//印鉴携带人
	private String FeUseDestination;    //携带目的地
	
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
	public String getFeName() {
		return FeName;
	}
	public void setFeName(String feName) {
		FeName = feName;
	}
	public String getFeCode() {
		return FeCode;
	}
	public void setFeCode(String feCode) {
		FeCode = feCode;
	}
	public String getFStartTime() {
		return FStartTime;
	}
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	public String getFEndTime() {
		return FEndTime;
	}
	public void setFEndTime(String fEndTime) {
		FEndTime = fEndTime;
	}
	public String getFeCarry() {
		return FeCarry;
	}
	public void setFeCarry(String feCarry) {
		FeCarry = feCarry;
	}
	public String getFeCarryTel() {
		return FeCarryTel;
	}
	public void setFeCarryTel(String feCarryTel) {
		FeCarryTel = feCarryTel;
	}
	public String getFDestination() {
		return FDestination;
	}
	public void setFDestination(String fDestination) {
		FDestination = fDestination;
	}
	public String getFProjectName() {
		return FProjectName;
	}
	public void setFProjectName(String fProjectName) {
		FProjectName = fProjectName;
	}
	public String getFSealData() {
		return FSealData;
	}
	public void setFSealData(String fSealData) {
		FSealData = fSealData;
	}
	public String getFeUseCarry() {
		return FeUseCarry;
	}
	public void setFeUseCarry(String feUseCarry) {
		FeUseCarry = feUseCarry;
	}
	public String getFeUseDestination() {
		return FeUseDestination;
	}
	public void setFeUseDestination(String feUseDestination) {
		FeUseDestination = feUseDestination;
	}
	//内部类单例模式
	private static class singletonHolder {  
        private static FeTakeOutTHeaderInfo instance = new FeTakeOutTHeaderInfo();  
    }
	private FeTakeOutTHeaderInfo() {}
	public static FeTakeOutTHeaderInfo getFeTakeOutTHeaderInfo() {
		return singletonHolder.instance;
	}
}
