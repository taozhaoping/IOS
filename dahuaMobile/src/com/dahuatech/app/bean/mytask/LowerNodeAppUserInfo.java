package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppUserInfo
 * @Description 审批人基本信息实体
 * @author 21291
 * @date 2014年11月10日 下午4:12:46
 */
public class LowerNodeAppUserInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;		//员工号
	private String FItemName;		//员工名称
	
	public String getFItemNumber() {
		return FItemNumber;
	}
	public void setFItemNumber(String fItemNumber) {
		FItemNumber = fItemNumber;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
}
