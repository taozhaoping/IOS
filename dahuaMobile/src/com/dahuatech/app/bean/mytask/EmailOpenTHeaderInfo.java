package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName EmailOpenTHeaderInfo
 * @Description 邮箱开通表头单据实体
 * @author 21291
 * @date 2014年8月18日 上午10:43:16
 */
public class EmailOpenTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyDate;			//申请时间
	private String FEmailType;  		//邮箱类别
	private String FApplyName;			//申请人
	private String FApplyDept;			//所属部门
	private String FReason;				//事由
	private String FApplyPermission;	//申请权限
	private String FInfoSafe;			//信息安全
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
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFEmailType() {
		return FEmailType;
	}
	public void setFEmailType(String fEmailType) {
		FEmailType = fEmailType;
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
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFApplyPermission() {
		return FApplyPermission;
	}
	public void setFApplyPermission(String fApplyPermission) {
		FApplyPermission = fApplyPermission;
	}
	public String getFInfoSafe() {
		return FInfoSafe;
	}
	public void setFInfoSafe(String fInfoSafe) {
		FInfoSafe = fInfoSafe;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	//内部类单例模式
	private static class singletonHolder {  
        private static EmailOpenTHeaderInfo instance = new EmailOpenTHeaderInfo();  
    }
	private EmailOpenTHeaderInfo() {}
	public static EmailOpenTHeaderInfo getEmailOpenTHeaderInfo() {
		return singletonHolder.instance;
	}
}
