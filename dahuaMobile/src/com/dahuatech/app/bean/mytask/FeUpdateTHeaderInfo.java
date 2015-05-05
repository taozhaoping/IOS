package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeUpdateTHeaderInfo
 * @Description 印鉴更换表头单据实体
 * @author 21291
 * @date 2014年10月11日 上午10:47:13
 */
public class FeUpdateTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDept;			//所属部门
	private String FApplyDate;			//申请时间
	private String FTel;				//联系电话
	private String FDestroy;   			//销毁人
	private String FDestroyWitness;   	//销毁见证人
	private String FAmount;   			//附件数量
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
	public String getFDestroy() {
		return FDestroy;
	}
	public void setFDestroy(String fDestroy) {
		FDestroy = fDestroy;
	}
	public String getFDestroyWitness() {
		return FDestroyWitness;
	}
	public void setFDestroyWitness(String fDestroyWitness) {
		FDestroyWitness = fDestroyWitness;
	}
	public String getFAmount() {
		return FAmount;
	}
	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static FeUpdateTHeaderInfo instance = new FeUpdateTHeaderInfo();  
    }
	private FeUpdateTHeaderInfo() {}
	public static FeUpdateTHeaderInfo getFeUpdateTHeaderInfo() {
		return singletonHolder.instance;
	}
}
