package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ProjectReadTHeaderInfo
 * @Description 项目信息阅读权限申请单表头实体
 * @author 21291
 * @date 2014年9月23日 下午2:20:49
 */
public class ProjectReadTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyNumber;  		//申请人工号
	private String FApplyDate;			//申请日期
	private String FApplyDept;			//所属部门
	private String FTel;				//联系电话
	private String FPermissionType;		//权限修改类型
	private String FProjectManage;		//项目管理
	private String FProgramPublic;		//程序发布
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
	public String getFPermissionType() {
		return FPermissionType;
	}
	public void setFPermissionType(String fPermissionType) {
		FPermissionType = fPermissionType;
	}
	public String getFProjectManage() {
		return FProjectManage;
	}
	public void setFProjectManage(String fProjectManage) {
		FProjectManage = fProjectManage;
	}
	public String getFProgramPublic() {
		return FProgramPublic;
	}
	public void setFProgramPublic(String fProgramPublic) {
		FProgramPublic = fProgramPublic;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ProjectReadTHeaderInfo instance = new ProjectReadTHeaderInfo();  
    }
	private ProjectReadTHeaderInfo() {}
	public static ProjectReadTHeaderInfo getProjectReadTHeaderInfo() {
		return singletonHolder.instance;
	}
}
