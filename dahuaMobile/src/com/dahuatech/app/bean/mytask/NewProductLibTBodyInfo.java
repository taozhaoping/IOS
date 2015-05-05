package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductLibTBodyInfo
 * @Description 新产品转库表体单据实体
 * @author 21291
 * @date 2014年8月12日 上午10:37:56
 */
public class NewProductLibTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FModel;   			//规格型号
	private String FProductName;		//产品名称
	private String FUnit;				//计量单位
	private String FAmount;				//数量
	private String FOutLocation;		//待转出库位
	private String FInLocation;			//待转入库位
	private String FNote;				//备注
	
	public String getFModel() {
		return FModel;
	}
	public void setFModel(String fModel) {
		FModel = fModel;
	}
	public String getFProductName() {
		return FProductName;
	}
	public void setFProductName(String fProductName) {
		FProductName = fProductName;
	}
	public String getFUnit() {
		return FUnit;
	}
	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}
	public String getFAmount() {
		return FAmount;
	}
	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}
	public String getFOutLocation() {
		return FOutLocation;
	}
	public void setFOutLocation(String fOutLocation) {
		FOutLocation = fOutLocation;
	}
	public String getFInLocation() {
		return FInLocation;
	}
	public void setFInLocation(String fInLocation) {
		FInLocation = fInLocation;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
