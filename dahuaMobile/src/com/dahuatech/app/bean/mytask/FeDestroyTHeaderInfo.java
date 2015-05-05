package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeDestroyTHeaderInfo
 * @Description 印鉴销毁表头单据实体
 * @author 21291
 * @date 2014年10月10日 下午4:01:27
 */
public class FeDestroyTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDept;			//所属部门
	private String FApplyDate;			//申请时间
	private String FTel;				//联系电话
	private String FReason;   			//申请原因
	private String FDestroy;   			//销毁人
	private String FWitnesses;   		//见证人
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
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFDestroy() {
		return FDestroy;
	}
	public void setFDestroy(String fDestroy) {
		FDestroy = fDestroy;
	}
	public String getFWitnesses() {
		return FWitnesses;
	}
	public void setFWitnesses(String fWitnesses) {
		FWitnesses = fWitnesses;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static FeDestroyTHeaderInfo instance = new FeDestroyTHeaderInfo();  
    }
	private FeDestroyTHeaderInfo() {}
	public static FeDestroyTHeaderInfo getFeDestroyTHeaderInfo() {
		return singletonHolder.instance;
	}
}
