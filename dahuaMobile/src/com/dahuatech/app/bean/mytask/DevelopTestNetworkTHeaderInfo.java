package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopTestNetworkInfo
 * @Description 研发项目测试网络权限表头单据实体
 * @author 21291
 * @date 2014年7月15日 下午2:44:40
 */
public class DevelopTestNetworkTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyerDeptName;	//申请部门
	private String FDate;				//申请时间
	private String FApplyerPermisson;	//申请权限
	private String FTelphone;			//联系电话
	private String FPermissionRequre;   //申请事由
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

	public String getFApplyerDeptName() {
		return FApplyerDeptName;
	}

	public void setFApplyerDeptName(String fApplyerDeptName) {
		FApplyerDeptName = fApplyerDeptName;
	}

	public String getFDate() {
		return FDate;
	}

	public void setFDate(String fDate) {
		FDate = fDate;
	}

	public String getFApplyerPermisson() {
		return FApplyerPermisson;
	}

	public void setFApplyerPermisson(String fApplyerPermisson) {
		FApplyerPermisson = fApplyerPermisson;
	}

	public String getFTelphone() {
		return FTelphone;
	}
	
	public void setFTelphone(String fTelphone) {
		FTelphone = fTelphone;
	}

	public String getFPermissionRequre() {
		return FPermissionRequre;
	}

	public void setFPermissionRequre(String fPermissionRequre) {
		FPermissionRequre = fPermissionRequre;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static DevelopTestNetworkTHeaderInfo instance = new DevelopTestNetworkTHeaderInfo();  
    }
	private DevelopTestNetworkTHeaderInfo() {}
	public static DevelopTestNetworkTHeaderInfo getDevelopTestNetworkTHeaderInfo() {
		return singletonHolder.instance;
	}	
}
