package com.dahuatech.app.bean.expense;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseFlowItemInfo
 * @Description 流水客户/项目列表返回实体
 * @author 21291
 * @date 2014年9月1日 下午6:40:39
 */
public class ExpenseFlowItemInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FId; 		//主键内码
	private String FItemName; 	//员工或项目名称
	public String getFId() {
		return FId;
	}
	public void setFId(String fId) {
		FId = fId;
	}
	public String getFItemName() {
		return FItemName;
	}
	public void setFItemName(String fItemName) {
		FItemName = fItemName;
	}
	
	//默认构造函数
	public ExpenseFlowItemInfo(){}
}
