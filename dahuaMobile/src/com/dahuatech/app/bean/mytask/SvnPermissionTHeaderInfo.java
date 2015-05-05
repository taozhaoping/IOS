package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName SvnPermissionTHeaderInfo
 * @Description SVN权限表头单据实体
 * @author 21291
 * @date 2014年8月12日 上午10:44:28
 */
public class SvnPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDept;			//所属部门
	private String FApplyDateStart;		//申请时间开始
	private String FApplyDateEnd;		//申请时间结束
	private String FSvnShow;			//SVN库负责人查看
	private String FSubEntrys;			//子集集合
	
	public int getFID() {
		return FID;
	}
	public void setFID(int fID) {
		FID = fID;
	}
	public String getFBillNo() {
		return FBillNo;
	}
	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
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
	public String getFApplyDateStart() {
		return FApplyDateStart;
	}
	public void setFApplyDateStart(String fApplyDateStart) {
		FApplyDateStart = fApplyDateStart;
	}
	public String getFApplyDateEnd() {
		return FApplyDateEnd;
	}
	public void setFApplyDateEnd(String fApplyDateEnd) {
		FApplyDateEnd = fApplyDateEnd;
	}
	public String getFSvnShow() {
		return FSvnShow;
	}
	public void setFSvnShow(String fSvnShow) {
		FSvnShow = fSvnShow;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static SvnPermissionTHeaderInfo instance = new SvnPermissionTHeaderInfo();  
    }
	private SvnPermissionTHeaderInfo() {}
	public static SvnPermissionTHeaderInfo getSvnPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
