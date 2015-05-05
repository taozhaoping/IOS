package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName PurchaseStockTBodyInfo
 * @Description 采购备料单据表体实体
 * @author 21291
 * @date 2014年8月15日 下午2:26:35
 */
public class PurchaseStockTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FMaterialCode;   	//物料代码
	private String FMaterialName;		//物料名称
	private String FModel;				//规格型号
	private String FNumber;				//数量
	private String FUnit;				//单位
	private String FRdDate;				//要求到货日期
	private String FEstimatedPrice;	    //预计价格
	private String FNote;	    		//备注
	
	public String getFMaterialCode() {
		return FMaterialCode;
	}
	public void setFMaterialCode(String fMaterialCode) {
		FMaterialCode = fMaterialCode;
	}
	public String getFMaterialName() {
		return FMaterialName;
	}
	public void setFMaterialName(String fMaterialName) {
		FMaterialName = fMaterialName;
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
	public String getFUnit() {
		return FUnit;
	}
	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}
	public String getFRdDate() {
		return FRdDate;
	}
	public void setFRdDate(String fRdDate) {
		FRdDate = fRdDate;
	}
	public String getFEstimatedPrice() {
		return FEstimatedPrice;
	}
	public void setFEstimatedPrice(String fEstimatedPrice) {
		FEstimatedPrice = fEstimatedPrice;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
