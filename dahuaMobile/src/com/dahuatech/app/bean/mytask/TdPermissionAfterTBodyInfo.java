package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdPermissionAfterTBodyInfo
 * @Description TD权限申请单发布后表体实体
 * @author 21291
 * @date 2014年9月23日 上午11:48:25
 */
public class TdPermissionAfterTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FApplyNumber;  		//申请人工号
	private String FApplyName;  		//申请人名称
	private String FApplyDept;			//所属部门
	private String FEmail;				//邮箱地址
	private String FProductName;		//产品线名称
	private String FProductType;		//产品线大类
	private String FCodeAndName;		//项目编号和名称
	private String FTestPermission;		//测试人员权限
	private String FDevelopPermission;	//研发人员权限
	private String FManagerPermission;	//项目经理权限
	private String FReadOnly;			//只读权限
	
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
	public String getFProductName() {
		return FProductName;
	}
	public void setFProductName(String fProductName) {
		FProductName = fProductName;
	}
	public String getFProductType() {
		return FProductType;
	}
	public void setFProductType(String fProductType) {
		FProductType = fProductType;
	}
	public String getFCodeAndName() {
		return FCodeAndName;
	}
	public void setFCodeAndName(String fCodeAndName) {
		FCodeAndName = fCodeAndName;
	}
	public String getFTestPermission() {
		return FTestPermission;
	}
	public void setFTestPermission(String fTestPermission) {
		FTestPermission = fTestPermission;
	}
	public String getFDevelopPermission() {
		return FDevelopPermission;
	}
	public void setFDevelopPermission(String fDevelopPermission) {
		FDevelopPermission = fDevelopPermission;
	}
	public String getFManagerPermission() {
		return FManagerPermission;
	}
	public void setFManagerPermission(String fManagerPermission) {
		FManagerPermission = fManagerPermission;
	}
	public String getFReadOnly() {
		return FReadOnly;
	}
	public void setFReadOnly(String fReadOnly) {
		FReadOnly = fReadOnly;
	}
	
}
