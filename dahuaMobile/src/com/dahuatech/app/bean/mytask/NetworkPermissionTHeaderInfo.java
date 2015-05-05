package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NetworkPermissionTHeaderInfo
 * @Description 网络权限申请单据实体
 * @author 21291
 * @date 2014年7月9日 下午3:42:51
 */
public class NetworkPermissionTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyerDeptName;	//申请部门
	private String FApplyerType;		//申请类型
	private String FDate;				//申请时间
	private String FComboBox4;			//信息安全
	private String FNote;				//申请事由
	private String FTelphone;			//联系电话
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
	public String getFApplyerType() {
		return FApplyerType;
	}
	public void setFApplyerType(String fApplyerType) {
		FApplyerType = fApplyerType;
	}
	public String getFDate() {
		return FDate;
	}
	public void setFDate(String fDate) {
		FDate = fDate;
	}
	public String getFComboBox4() {
		return FComboBox4;
	}
	public void setFComboBox4(String fComboBox4) {
		FComboBox4 = fComboBox4;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
	public String getFTelphone() {
		return FTelphone;
	}
	public void setFTelphone(String fTelphone) {
		FTelphone = fTelphone;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static NetworkPermissionTHeaderInfo instance = new NetworkPermissionTHeaderInfo();  
    }
	private NetworkPermissionTHeaderInfo() {}
	public static NetworkPermissionTHeaderInfo getNetworkPermissionTHeaderInfo() {
		return singletonHolder.instance;
	}
}
