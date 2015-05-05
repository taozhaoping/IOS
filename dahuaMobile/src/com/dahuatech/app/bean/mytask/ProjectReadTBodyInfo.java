package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ProjectReadTBodyInfo
 * @Description 项目信息阅读权限申请单表体实体
 * @author 21291
 * @date 2014年9月23日 下午2:21:04
 */
public class ProjectReadTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FApplyNumber;  		//申请人工号
	private String FApplyName;  		//申请人名称
	private String FApplyDept;			//所属部门
	private String FEmail;				//邮箱地址
	private String FApplyReason;   		//申请原因
	private String FApplyType;			//申请类型
	private String FProgramPublic;		//程序发布
	private String FStartProject;		//立项通知
	private String FEndProject;			//结项通知
	private String FProductPublic;		//产品发布
	private String FMarketPublic;		//市场发布
	private String FRisk;				//含风险销售通知
	
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
	public String getFApplyReason() {
		return FApplyReason;
	}
	public void setFApplyReason(String fApplyReason) {
		FApplyReason = fApplyReason;
	}
	public String getFApplyType() {
		return FApplyType;
	}
	public void setFApplyType(String fApplyType) {
		FApplyType = fApplyType;
	}
	public String getFProgramPublic() {
		return FProgramPublic;
	}
	public void setFProgramPublic(String fProgramPublic) {
		FProgramPublic = fProgramPublic;
	}
	public String getFStartProject() {
		return FStartProject;
	}
	public void setFStartProject(String fStartProject) {
		FStartProject = fStartProject;
	}
	public String getFEndProject() {
		return FEndProject;
	}
	public void setFEndProject(String fEndProject) {
		FEndProject = fEndProject;
	}
	public String getFProductPublic() {
		return FProductPublic;
	}
	public void setFProductPublic(String fProductPublic) {
		FProductPublic = fProductPublic;
	}
	public String getFMarketPublic() {
		return FMarketPublic;
	}
	public void setFMarketPublic(String fMarketPublic) {
		FMarketPublic = fMarketPublic;
	}
	public String getFRisk() {
		return FRisk;
	}
	public void setFRisk(String fRisk) {
		FRisk = fRisk;
	}
}
