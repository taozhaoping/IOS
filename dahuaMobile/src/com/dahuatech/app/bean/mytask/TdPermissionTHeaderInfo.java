package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdPermissionTHeaderInfo
 * @Description TD权限申请单表头实体
 * @author 21291
 * @date 2014年9月22日 下午5:27:01
 */
public class TdPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人名称
	private String FApplyNumber;  		//申请人工号
	private String FApplyDate;			//申请日期
	private String FApplyDept;			//所属部门
	private String FTel;				//联系电话
	private String FPersonType;			//申请人员类型
	private String FBeforeAmount;		//TD(发布前)账号数量
	private String FAfterAmount;		//TD(发布后)账号数量
	private String FBeforeSubEntrys;	//TD(发布前)子集集合
	private String FAfterSubEntrys;		//TD(发布后)子集集合
	
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
	public String getFApplyNumber() {
		return FApplyNumber;
	}
	public void setFApplyNumber(String fApplyNumber) {
		FApplyNumber = fApplyNumber;
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
	public String getFPersonType() {
		return FPersonType;
	}
	public void setFPersonType(String fPersonType) {
		FPersonType = fPersonType;
	}
	public String getFBeforeAmount() {
		return FBeforeAmount;
	}
	public void setFBeforeAmount(String fBeforeAmount) {
		FBeforeAmount = fBeforeAmount;
	}
	public String getFAfterAmount() {
		return FAfterAmount;
	}
	public void setFAfterAmount(String fAfterAmount) {
		FAfterAmount = fAfterAmount;
	}
	public String getFBeforeSubEntrys() {
		return FBeforeSubEntrys;
	}
	public void setFBeforeSubEntrys(String fBeforeSubEntrys) {
		FBeforeSubEntrys = fBeforeSubEntrys;
	}
	public String getFAfterSubEntrys() {
		return FAfterSubEntrys;
	}
	public void setFAfterSubEntrys(String fAfterSubEntrys) {
		FAfterSubEntrys = fAfterSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static TdPermissionTHeaderInfo instance = new TdPermissionTHeaderInfo();  
    }
	private TdPermissionTHeaderInfo() {}
	public static TdPermissionTHeaderInfo getTdPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
