package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowConsumableTBodyInfo
 * @Description 低值易耗物料代码单据表体实体
 * @author 21291
 * @date 2014年8月19日 下午4:36:07
 */
public class LowConsumableTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	private String FMaterialType;   	//物料类型
	private String FMaterialName;		//物料名称
	private String FModel;				//规格型号
	private String FUnit;				//单位
	private String FAccount;	    	//下放账套
	private String FNote;	    		//备注
	
	public String getFMaterialType() {
		return FMaterialType;
	}
	public void setFMaterialType(String fMaterialType) {
		FMaterialType = fMaterialType;
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
	public String getFUnit() {
		return FUnit;
	}
	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}
	public String getFAccount() {
		return FAccount;
	}
	public void setFAccount(String fAccount) {
		FAccount = fAccount;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
