package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdPermissionBeforeTBodyInfo
 * @Description TD权限申请单发布前表体实体
 * @author 21291
 * @date 2014年9月23日 上午11:48:46
 */
public class TdPermissionBeforeTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FApplyNumber;  		//申请人工号
	private String FApplyName;  		//申请人名称
	private String FApplyDept;			//所属部门
	private String FEmail;				//邮箱地址
	private String FTdDomain;			//TD域
	private String FTdProject;			//TD项目
	private String FCodeAndName;		//项目编号和名称
	private String FManager;			//项目经理
	private String FProjectPermission;	//项目权限
	
	public String getFApplyNumber() {
		return FApplyNumber;
	}
	public void setFApplyNumber(String fApplyNumber) {
		FApplyNumber = fApplyNumber;
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
	public String getFEmail() {
		return FEmail;
	}
	public void setFEmail(String fEmail) {
		FEmail = fEmail;
	}
	public String getFTdDomain() {
		return FTdDomain;
	}
	public void setFTdDomain(String fTdDomain) {
		FTdDomain = fTdDomain;
	}
	public String getFTdProject() {
		return FTdProject;
	}
	public void setFTdProject(String fTdProject) {
		FTdProject = fTdProject;
	}
	public String getFCodeAndName() {
		return FCodeAndName;
	}
	public void setFCodeAndName(String fCodeAndName) {
		FCodeAndName = fCodeAndName;
	}
	public String getFManager() {
		return FManager;
	}
	public void setFManager(String fManager) {
		FManager = fManager;
	}
	public String getFProjectPermission() {
		return FProjectPermission;
	}
	public void setFProjectPermission(String fProjectPermission) {
		FProjectPermission = fProjectPermission;
	}
}
