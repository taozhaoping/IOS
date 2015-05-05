package com.dahuatech.app.bean.mytask;

import java.util.List;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppRoleInfo
 * @Description 审批人角色信息实体
 * @author 21291
 * @date 2014年11月10日 下午4:17:12
 */
public class LowerNodeAppRoleInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FRoleName;									//角色名称
	private List<LowerNodeAppUserInfo> FUserList;				//员工集合 

	public String getFRoleName() {
		return FRoleName;
	}
	public void setFRoleName(String fRoleName) {
		FRoleName = fRoleName;
	}
	public List<LowerNodeAppUserInfo> getFUserList() {
		return FUserList;
	}
	public void setFUserList(List<LowerNodeAppUserInfo> fUserList) {
		FUserList = fUserList;
	}
}
