package com.dahuatech.app.bean.market;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName MarketBidInfo
 * @Description 合同搜索实体
 * @author 21291
 * @date 2015年1月26日 下午2:19:11
 */
public class MarketContractInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FSystemType;  		    //系统ID
	private String FClassTypeID;  			//单据类型ID
	private String FBillID;			   		//单据ID
	private String FCustomerName;  			//客户名称
	private String FContractCode;  			//合同单号
	private String FNodeName;  				//当前节点名称
	private String FTasker;  				//当前待处理人
	
	public String getFSystemType() {
		return FSystemType;
	}
	public void setFSystemType(String fSystemType) {
		FSystemType = fSystemType;
	}
	public String getFClassTypeID() {
		return FClassTypeID;
	}
	public void setFClassTypeID(String fClassTypeID) {
		FClassTypeID = fClassTypeID;
	}
	public String getFBillID() {
		return FBillID;
	}
	public void setFBillID(String fBillID) {
		FBillID = fBillID;
	}
	public String getFCustomerName() {
		return FCustomerName;
	}
	public void setFCustomerName(String fCustomerName) {
		FCustomerName = fCustomerName;
	}
	public String getFContractCode() {
		return FContractCode;
	}
	public void setFContractCode(String fContractCode) {
		FContractCode = fContractCode;
	}
	public String getFNodeName() {
		return FNodeName;
	}
	public void setFNodeName(String fNodeName) {
		FNodeName = fNodeName;
	}
	public String getFTasker() {
		return FTasker;
	}
	public void setFTasker(String fTasker) {
		FTasker = fTasker;
	}
}
