package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName TdBorrowTHeaderInfo
 * @Description 技术文件借阅申请单表头实体
 * @author 21291
 * @date 2014年8月28日 下午4:25:02
 */
public class TdBorrowTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请日期
	private String FApplyDept;			//所属部门
	private String FTel;				//联系电话
	private String FSecrecyDate;		//保密期限
	private String FDocumentType;		//文档类型
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
	public String getFApplyDate() {
		return FApplyDate;
	}
	public void setFApplyDate(String fApplyDate) {
		FApplyDate = fApplyDate;
	}
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFSecrecyDate() {
		return FSecrecyDate;
	}
	public void setFSecrecyDate(String fSecrecyDate) {
		FSecrecyDate = fSecrecyDate;
	}
	public String getFDocumentType() {
		return FDocumentType;
	}
	public void setFDocumentType(String fDocumentType) {
		FDocumentType = fDocumentType;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static TdBorrowTHeaderInfo instance = new TdBorrowTHeaderInfo();  
    }
	private TdBorrowTHeaderInfo() {}
	public static TdBorrowTHeaderInfo getTdBorrowTHeaderInfo() {
		return singletonHolder.instance;
	}
}
