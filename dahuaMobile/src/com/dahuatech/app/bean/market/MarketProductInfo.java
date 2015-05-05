package com.dahuatech.app.bean.market;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketProductInfo
 * @Description 产品搜索实体
 * @author 21291
 * @date 2015年1月30日 上午9:09:59
 */
public class MarketProductInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FModel;  		//产品型号
	private String FName ;  		//产品名称 
	private String FFirstLine;		//一级产品线
	private String FSecLine;  		//二级产品线
	private String FStandPrice;  	//价格
	public String getFModel() {
		return FModel;
	}
	public void setFModel(String fModel) {
		FModel = fModel;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getFFirstLine() {
		return FFirstLine;
	}
	public void setFFirstLine(String fFirstLine) {
		FFirstLine = fFirstLine;
	}
	public String getFSecLine() {
		return FSecLine;
	}
	public void setFSecLine(String fSecLine) {
		FSecLine = fSecLine;
	}
	public String getFStandPrice() {
		return FStandPrice;
	}
	public void setFStandPrice(String fStandPrice) {
		FStandPrice = fStandPrice;
	}
	
}
