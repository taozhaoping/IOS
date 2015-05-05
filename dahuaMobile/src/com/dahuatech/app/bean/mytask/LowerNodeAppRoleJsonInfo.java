package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppRoleJsonInfo
 * @Description 审批人角色信息实体(JSON字符串类)
 * @author 21291
 * @date 2014年11月11日 上午11:46:42
 */
public class LowerNodeAppRoleJsonInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FRoleName;		//角色名称
	private String FRoleApproveUser;		//角色审批人基本信息 (员工集合) 

	public String getFRoleName() {
		return FRoleName;
	}
	public void setFRoleName(String fRoleName) {
		FRoleName = fRoleName;
	}
	public String getFRoleApproveUser() {
		return FRoleApproveUser;
	}
	public void setFRoleApproveUser(String fRoleApproveUser) {
		FRoleApproveUser = fRoleApproveUser;
	}
}
