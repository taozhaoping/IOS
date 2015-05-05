package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DevelopTravelTHeaderInfo
 * @Description 研发出差派遣表头单据实体
 * @author 21291
 * @date 2014年8月15日 上午9:45:38
 */
public class DevelopTravelTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请时间
	private String FAssumeCost;			//费用承担人姓名
	private String FAssumeDept;			//费用承担人部门
	private String FTravelAim;			//出差目的
	private String FTravelAddress;		//出差地点
	private String FTravelStartTime;	//出差开始时间
	private String FTravelEndTime;		//出差结束时间
	private String FProjectName;		//项目名称
	private String FProjectCode;		//项目编号
	private String FTravelReason;		//出差事由
	private String FTravelWay;			//出差方式
	private String FPublicNoteBook;	    //公共笔记本申请
	private String FSubEntrysOne;		//子集集合
	private String FSubEntrysTwo;		//子集集合
	
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
	public String getFAssumeCost() {
		return FAssumeCost;
	}
	public void setFAssumeCost(String fAssumeCost) {
		FAssumeCost = fAssumeCost;
	}
	public String getFAssumeDept() {
		return FAssumeDept;
	}
	public void setFAssumeDept(String fAssumeDept) {
		FAssumeDept = fAssumeDept;
	}
	public String getFTravelAim() {
		return FTravelAim;
	}
	public void setFTravelAim(String fTravelAim) {
		FTravelAim = fTravelAim;
	}
	public String getFTravelAddress() {
		return FTravelAddress;
	}
	public void setFTravelAddress(String fTravelAddress) {
		FTravelAddress = fTravelAddress;
	}
	public String getFTravelStartTime() {
		return FTravelStartTime;
	}
	public void setFTravelStartTime(String fTravelStartTime) {
		FTravelStartTime = fTravelStartTime;
	}
	public String getFTravelEndTime() {
		return FTravelEndTime;
	}
	public void setFTravelEndTime(String fTravelEndTime) {
		FTravelEndTime = fTravelEndTime;
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
	public String getFTravelReason() {
		return FTravelReason;
	}
	public void setFTravelReason(String fTravelReason) {
		FTravelReason = fTravelReason;
	}
	public String getFTravelWay() {
		return FTravelWay;
	}
	public void setFTravelWay(String fTravelWay) {
		FTravelWay = fTravelWay;
	}
	public String getFPublicNoteBook() {
		return FPublicNoteBook;
	}
	public void setFPublicNoteBook(String fPublicNoteBook) {
		FPublicNoteBook = fPublicNoteBook;
	}
	public String getFSubEntrysOne() {
		return FSubEntrysOne;
	}
	public void setFSubEntrysOne(String fSubEntrysOne) {
		FSubEntrysOne = fSubEntrysOne;
	}
	public String getFSubEntrysTwo() {
		return FSubEntrysTwo;
	}
	public void setFSubEntrysTwo(String fSubEntrysTwo) {
		FSubEntrysTwo = fSubEntrysTwo;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static DevelopTravelTHeaderInfo instance = new DevelopTravelTHeaderInfo();  
    }
	private DevelopTravelTHeaderInfo() {}
	public static DevelopTravelTHeaderInfo getDevelopTravelTHeaderInfo() {
		return singletonHolder.instance;
	}
}
