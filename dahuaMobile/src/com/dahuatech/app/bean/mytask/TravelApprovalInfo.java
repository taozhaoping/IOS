package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TravelApprovalInfo
 * @Description 出差审批单据实体
 * @author 21291
 * @date 2014年8月21日 上午9:52:51
 */
public class TravelApprovalInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请日期
	private String FApplyDept;			//所属部门
	private String FClientName;			//客户名称
	private String FStartTime;			//出发时间
	private String FBackTime;			//返回时间
	private String FTravelAddress;		//出差地点
	private String FTravelDays;			//出差天数
	private String FTravelCause;		//出差事由
	private String FArrangement;		//时间安排
	private String FTravelReport;		//出差报告
	
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
	public String getFClientName() {
		return FClientName;
	}
	public void setFClientName(String fClientName) {
		FClientName = fClientName;
	}
	public String getFStartTime() {
		return FStartTime;
	}
	public void setFStartTime(String fStartTime) {
		FStartTime = fStartTime;
	}
	public String getFBackTime() {
		return FBackTime;
	}
	public void setFBackTime(String fBackTime) {
		FBackTime = fBackTime;
	}
	public String getFTravelAddress() {
		return FTravelAddress;
	}
	public void setFTravelAddress(String fTravelAddress) {
		FTravelAddress = fTravelAddress;
	}
	public String getFTravelDays() {
		return FTravelDays;
	}
	public void setFTravelDays(String fTravelDays) {
		FTravelDays = fTravelDays;
	}
	public String getFTravelCause() {
		return FTravelCause;
	}
	public void setFTravelCause(String fTravelCause) {
		FTravelCause = fTravelCause;
	}
	public String getFArrangement() {
		return FArrangement;
	}
	public void setFArrangement(String fArrangement) {
		FArrangement = fArrangement;
	}
	public String getFTravelReport() {
		return FTravelReport;
	}
	public void setFTravelReport(String fTravelReport) {
		FTravelReport = fTravelReport;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static TravelApprovalInfo instance = new TravelApprovalInfo();  
    }
	private TravelApprovalInfo() {}
	public static TravelApprovalInfo getTravelApprovalInfo() {
		return singletonHolder.instance;
	}
}
