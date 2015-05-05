package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;


/**
 * @ClassName DaHuaAssumeCostInfo
 * @Description 大华承担费用申请单据实体
 * @author 21291
 * @date 2014年7月15日 下午5:16:27
 */
public class DaHuaAssumeCostInfo extends Base {

	private static final long serialVersionUID = 1L;
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;				//申请时间
	private String FGoodsType;  		//货物类别
	private String FPiInfo;				//PI信息
	private String FBusinessName;  		//商务人员
	private String FFreightEstimate;	//运费预估
	private String FCause;				//费用大华承担原因
	
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

	public String getFGoodsType() {
		return FGoodsType;
	}

	public void setFGoodsType(String fGoodsType) {
		FGoodsType = fGoodsType;
	}

	public String getFPiInfo() {
		return FPiInfo;
	}

	public void setFPiInfo(String fPiInfo) {
		FPiInfo = fPiInfo;
	}

	public String getFBusinessName() {
		return FBusinessName;
	}

	public void setFBusinessName(String fBusinessName) {
		FBusinessName = fBusinessName;
	}

	public String getFFreightEstimate() {
		return FFreightEstimate;
	}

	public void setFFreightEstimate(String fFreightEstimate) {
		FFreightEstimate = fFreightEstimate;
	}

	public String getFCause() {
		return FCause;
	}

	public void setFCause(String fCause) {
		FCause = fCause;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static DaHuaAssumeCostInfo instance = new DaHuaAssumeCostInfo();  
    }  
	private DaHuaAssumeCostInfo() {}
	public static DaHuaAssumeCostInfo getDaHuaAssumeCostInfo() {
		return singletonHolder.instance;
	}
}
