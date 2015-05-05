package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DoorPermissionTHeaderInfo
 * @Description 门禁权限申请单据表头实体
 * @author 21291
 * @date 2014年8月21日 下午2:16:53
 */
public class DoorPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请日期
	private String FApplyDept;			//所属部门
	private String FApplyType;			//申请类型
	private String FTel;				//联系电话
	private String FIsOther;		    //申请其他部门或公共门禁权限
	private String FRegion;				//相应区域
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
	public String getFApplyType() {
		return FApplyType;
	}
	public void setFApplyType(String fApplyType) {
		FApplyType = fApplyType;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFIsOther() {
		return FIsOther;
	}
	public void setFIsOther(String fIsOther) {
		FIsOther = fIsOther;
	}
	public String getFRegion() {
		return FRegion;
	}
	public void setFRegion(String fRegion) {
		FRegion = fRegion;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	//内部类单例模式
	private static class singletonHolder {  
        private static DoorPermissionTHeaderInfo instance = new DoorPermissionTHeaderInfo();  
    }
	private DoorPermissionTHeaderInfo() {}
	public static DoorPermissionTHeaderInfo getDoorPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
