package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName DevelopInquiryTBodyInfo
 * @Description 研发中心询价申请表体实体信息
 * @author 21291
 * @date 2014年7月16日 上午9:26:11
 */
public class DevelopInquiryTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;

	public String FMasterialName;		//物料名称
	public String FSupplier;			//供应商
	public String FManufacturer;		//制造商
	public String FOffer;				//报价
	public String FCurrency;			//币别
	public String FOrderQuantityFrom;	//订货量(从)
	public String FOrderQuantityTo;		//订货量(到)
	public String FUnit;				//计量单位
	public String FOrderForward;		//订货提前期
	public String FMini;				//最小包装量
	public String FMiniOrder;			//最小起订量
	public String FPayment;				//账期
	
	public String getFMasterialName() {
		return FMasterialName;
	}

	public void setFMasterialName(String fMasterialName) {
		FMasterialName = fMasterialName;
	}

	public String getFSupplier() {
		return FSupplier;
	}

	public void setFSupplier(String fSupplier) {
		FSupplier = fSupplier;
	}
	
	public String getFManufacturer() {
		return FManufacturer;
	}

	public void setFManufacturer(String fManufacturer) {
		FManufacturer = fManufacturer;
	}

	public String getFOffer() {
		return FOffer;
	}

	public void setFOffer(String fOffer) {
		FOffer = fOffer;
	}

	public String getFCurrency() {
		return FCurrency;
	}

	public void setFCurrency(String fCurrency) {
		FCurrency = fCurrency;
	}

	public String getFOrderQuantityFrom() {
		return FOrderQuantityFrom;
	}

	public void setFOrderQuantityFrom(String fOrderQuantityFrom) {
		FOrderQuantityFrom = fOrderQuantityFrom;
	}

	public String getFOrderQuantityTo() {
		return FOrderQuantityTo;
	}

	public void setFOrderQuantityTo(String fOrderQuantityTo) {
		FOrderQuantityTo = fOrderQuantityTo;
	}

	public String getFUnit() {
		return FUnit;
	}

	public void setFUnit(String fUnit) {
		FUnit = fUnit;
	}

	public String getFOrderForward() {
		return FOrderForward;
	}

	public void setFOrderForward(String fOrderForward) {
		FOrderForward = fOrderForward;
	}

	public String getFMini() {
		return FMini;
	}

	public void setFMini(String fMini) {
		FMini = fMini;
	}

	public String getFMiniOrder() {
		return FMiniOrder;
	}

	public void setFMiniOrder(String fMiniOrder) {
		FMiniOrder = fMiniOrder;
	}

	public String getFPayment() {
		return FPayment;
	}

	public void setFPayment(String fPayment) {
		FPayment = fPayment;
	}
}
