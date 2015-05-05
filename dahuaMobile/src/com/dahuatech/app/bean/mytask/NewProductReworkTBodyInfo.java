package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName NewProductReworkTBodyInfo
 * @Description 新产品返工表体单据实体
 * @author 21291
 * @date 2014年8月27日 上午9:42:52
 */
public class NewProductReworkTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FLocation;   		//库位
	private String FType;				//类别
	private String FMaterialCode;   	//物料编码
	private String FLocationCode;   	//入库料号
	private String FMaterialName;		//物料名称
	private String FModel;				//规格型号
	private String FAmount;				//数量
	private String FContent;			//返工内容
	private String FRequireTime;		//要求完成时间
	private String FExpectedTime;		//预计完成时间
	
	public String getFLocation() {
		return FLocation;
	}
	public void setFLocation(String fLocation) {
		FLocation = fLocation;
	}
	public String getFType() {
		return FType;
	}
	public void setFType(String fType) {
		FType = fType;
	}
	public String getFMaterialCode() {
		return FMaterialCode;
	}
	public void setFMaterialCode(String fMaterialCode) {
		FMaterialCode = fMaterialCode;
	}
	public String getFLocationCode() {
		return FLocationCode;
	}
	public void setFLocationCode(String fLocationCode) {
		FLocationCode = fLocationCode;
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
	public String getFAmount() {
		return FAmount;
	}
	public void setFAmount(String fAmount) {
		FAmount = fAmount;
	}
	public String getFContent() {
		return FContent;
	}
	public void setFContent(String fContent) {
		FContent = fContent;
	}
	public String getFRequireTime() {
		return FRequireTime;
	}
	public void setFRequireTime(String fRequireTime) {
		FRequireTime = fRequireTime;
	}
	public String getFExpectedTime() {
		return FExpectedTime;
	}
	public void setFExpectedTime(String fExpectedTime) {
		FExpectedTime = fExpectedTime;
	}
}
