package com.dahuatech.app.bean.develophour;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DHConfirmSubClassInfo
 * @Description 研发工时列表项子项实体类
 * @author 21291
 * @date 2014年11月5日 上午10:46:12
 */
public class DHConfirmSubClassInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FItemNumber;			//确认人员员工号
	private String FItemName;			//确认人员员工名称
	
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
