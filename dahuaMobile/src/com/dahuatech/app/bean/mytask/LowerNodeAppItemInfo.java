package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppItemInfo
 * @Description 审批人基本信息集合包装类
 * @author 21291
 * @date 2014年11月10日 下午4:11:23
 */
public class LowerNodeAppItemInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FApproveUser;		//审批人基本信息实体集合
	private String FApproveRole;		//审批人角色信息实体集合
	
	public String getFApproveUser() {
		return FApproveUser;
	}
	public void setFApproveUser(String fApproveUser) {
		FApproveUser = fApproveUser;
	}
	public String getFApproveRole() {
		return FApproveRole;
	}
	public void setFApproveRole(String fApproveRole) {
		FApproveRole = fApproveRole;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static LowerNodeAppItemInfo instance = new LowerNodeAppItemInfo();  
    }
	private LowerNodeAppItemInfo() {}
	public static LowerNodeAppItemInfo getLowerNodeAppItemInfo() {
		return singletonHolder.instance;
	}
}
