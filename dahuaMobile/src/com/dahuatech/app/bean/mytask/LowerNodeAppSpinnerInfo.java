package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowerNodeAppSpinnerInfo
 * @Description 每个下级节点审批人下拉框实体
 * @author 21291
 * @date 2014年11月12日 上午9:23:49
 */
public class LowerNodeAppSpinnerInfo extends Base {

	private static final long serialVersionUID = 1L;
	
	private String FSpinnerType;			//下拉框类型
	private int FSpinnerIndex;				//下拉框索引
	private int FRoleSpinnerCount;			//角色下拉框总数
	private String FSpinnerValue;			//下拉框选中的文本值
	
	public String getFSpinnerType() {
		return FSpinnerType;
	}
	public void setFSpinnerType(String fSpinnerType) {
		FSpinnerType = fSpinnerType;
	}
	public int getFSpinnerIndex() {
		return FSpinnerIndex;
	}
	public void setFSpinnerIndex(int fSpinnerIndex) {
		FSpinnerIndex = fSpinnerIndex;
	}
	public int getFRoleSpinnerCount() {
		return FRoleSpinnerCount;
	}
	public void setFRoleSpinnerCount(int fRoleSpinnerCount) {
		FRoleSpinnerCount = fRoleSpinnerCount;
	}
	public String getFSpinnerValue() {
		return FSpinnerValue;
	}
	public void setFSpinnerValue(String fSpinnerValue) {
		FSpinnerValue = fSpinnerValue;
	}
	
	public LowerNodeAppSpinnerInfo() {
		this.FSpinnerValue="";
	}
}
