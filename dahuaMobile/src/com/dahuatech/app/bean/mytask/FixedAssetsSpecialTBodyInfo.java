package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FixedAssetsSpecialTBodyInfo
 * @Description 固定资产特殊紧急采购需求单据表体实体
 * @author 21291
 * @date 2014年8月19日 下午1:52:25
 */
public class FixedAssetsSpecialTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FBillNo; 		//固定资产编号
	private String FName; 			//固定资产名称
	private String FModel; 			//品牌型号
	private String FNumber; 		//数量
	private String FDate; 			//要求到货日期
	private String FPerson; 		//责任人姓名
	private String FNote; 			//备注
	
	public String getFBillNo() {
		return FBillNo;
	}
	public void setFBillNo(String fBillNo) {
		FBillNo = fBillNo;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getFModel() {
		return FModel;
	}
	public void setFModel(String fModel) {
		FModel = fModel;
	}
	public String getFNumber() {
		return FNumber;
	}
	public void setFNumber(String fNumber) {
		FNumber = fNumber;
	}
	public String getFDate() {
		return FDate;
	}
	public void setFDate(String fDate) {
		FDate = fDate;
	}
	public String getFPerson() {
		return FPerson;
	}
	public void setFPerson(String fPerson) {
		FPerson = fPerson;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
