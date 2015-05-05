package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DocumentApproveTHeaderInfo
 * @Description 文件审批流表头单据实体
 * @author 21291
 * @date 2014年8月12日 上午10:21:15
 */
public class DocumentApproveTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请时间
	private String FApplyTel;			//申请人电话
	private String FPendingApp;   		//待审批文件名称与版本
	private String FDocumentType;   	//文件类型
	private String FDocumentStatus;   	//文件状态
	private String FReason;   			//原因
	private String FDocumentPost;   	//文件涉及岗位
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
	public String getFApplyTel() {
		return FApplyTel;
	}
	public void setFApplyTel(String fApplyTel) {
		FApplyTel = fApplyTel;
	}
	public String getFPendingApp() {
		return FPendingApp;
	}
	public void setFPendingApp(String fPendingApp) {
		FPendingApp = fPendingApp;
	}
	public String getFDocumentType() {
		return FDocumentType;
	}
	public void setFDocumentType(String fDocumentType) {
		FDocumentType = fDocumentType;
	}
	public String getFDocumentStatus() {
		return FDocumentStatus;
	}
	public void setFDocumentStatus(String fDocumentStatus) {
		FDocumentStatus = fDocumentStatus;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFDocumentPost() {
		return FDocumentPost;
	}
	public void setFDocumentPost(String fDocumentPost) {
		FDocumentPost = fDocumentPost;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	//内部类单例模式
	private static class singletonHolder {  
        private static DocumentApproveTHeaderInfo instance = new DocumentApproveTHeaderInfo();  
    }
	private DocumentApproveTHeaderInfo() {}
	public static DocumentApproveTHeaderInfo getDocumentApproveTHeaderInfo() {
		return singletonHolder.instance;
	}
}
