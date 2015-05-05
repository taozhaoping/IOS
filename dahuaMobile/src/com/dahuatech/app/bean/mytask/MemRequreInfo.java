package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MemRequreInfo
 * @Description MEM流程申请单据实体
 * @author 21291
 * @date 2014年7月17日 下午2:36:41
 */
public class MemRequreInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;					//主键内码
	private String FBillNo;				//单据编号
	private String FApplyerName;		//申请人姓名
	private String FApplyerDeptName;	//申请部门
	private String FApplyDate;			//申请时间
	private String FVersion;			//版本
	private String FValidDate;			//有效日期
	private String FUpgradeReason;		//升级原因
	private String FOtherReason;		//其他原因
	private String FUpgradeNote;		//升级说明
	private String FSubject;			//主题
	private String FMemReasonNote;		//MEM发出原因
	private String FMemProduct;			//MEM涉及产品型号/名称
	private String FTechnology;			//原工艺文件号
	private String FMemScope;			//MEM发放范围
	private String FNote;				//MEM具体内容
	
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

	public String getFApplyerName() {
		return FApplyerName;
	}

	public void setFApplyerName(String fApplyerName) {
		FApplyerName = fApplyerName;
	}

	public String getFApplyerDeptName() {
		return FApplyerDeptName;
	}

	public void setFApplyerDeptName(String fApplyerDeptName) {
		FApplyerDeptName = fApplyerDeptName;
	}

	public String getFApplyDate() {
		return FApplyDate;
	}

	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}

	public String getFVersion() {
		return FVersion;
	}

	public void setFVersion(String fVersion) {
		FVersion = fVersion;
	}

	public String getFValidDate() {
		return FValidDate;
	}

	public void setFValidDate(String fValidDate) {
		FValidDate = fValidDate;
	}

	public String getFUpgradeReason() {
		return FUpgradeReason;
	}

	public void setFUpgradeReason(String fUpgradeReason) {
		FUpgradeReason = fUpgradeReason;
	}

	public String getFOtherReason() {
		return FOtherReason;
	}

	public void setFOtherReason(String fOtherReason) {
		FOtherReason = fOtherReason;
	}

	public String getFUpgradeNote() {
		return FUpgradeNote;
	}

	public void setFUpgradeNote(String fUpgradeNote) {
		FUpgradeNote = fUpgradeNote;
	}

	public String getFSubject() {
		return FSubject;
	}

	public void setFSubject(String fSubject) {
		FSubject = fSubject;
	}

	public String getFMemReasonNote() {
		return FMemReasonNote;
	}

	public void setFMemReasonNote(String fMemReasonNote) {
		FMemReasonNote = fMemReasonNote;
	}

	public String getFMemProduct() {
		return FMemProduct;
	}

	public void setFMemProduct(String fMemProduct) {
		FMemProduct = fMemProduct;
	}

	public String getFTechnology() {
		return FTechnology;
	}

	public void setFTechnology(String fTechnology) {
		FTechnology = fTechnology;
	}

	public String getFMemScope() {
		return FMemScope;
	}

	public void setFMemScope(String fMemScope) {
		FMemScope = fMemScope;
	}

	public String getFNote() {
		return FNote;
	}

	public void setFNote(String fNote) {
		FNote = fNote;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static MemRequreInfo instance = new MemRequreInfo();  
    }
	private MemRequreInfo() {}
	public static MemRequreInfo getMemRequreInfo() {
		return singletonHolder.instance;
	}
}
