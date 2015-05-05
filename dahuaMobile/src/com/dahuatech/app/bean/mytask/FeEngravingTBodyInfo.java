package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeEngravingTBodyInfo
 * @Description 印鉴刻制表体单据实体
 * @author 21291
 * @date 2014年10月11日 下午2:59:26
 */
public class FeEngravingTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FCompany;			//所属公司
	private String FeType; 				//印鉴类型
	private String FKeeper;				//保管人
	private String FKeeperTel;			//保管人电话
	private String FKeeperDept;			//保管部门
	private String FKeeperArea;   		//保管区域
	private String FeName;  			//印鉴名称
	private String FReason;				//申请原因
	private String FNote;				//备注
	
	public String getFCompany() {
		return FCompany;
	}
	public void setFCompany(String fCompany) {
		FCompany = fCompany;
	}
	public String getFeType() {
		return FeType;
	}
	public void setFeType(String feType) {
		FeType = feType;
	}
	public String getFKeeper() {
		return FKeeper;
	}
	public void setFKeeper(String fKeeper) {
		FKeeper = fKeeper;
	}
	public String getFKeeperTel() {
		return FKeeperTel;
	}
	public void setFKeeperTel(String fKeeperTel) {
		FKeeperTel = fKeeperTel;
	}
	public String getFKeeperDept() {
		return FKeeperDept;
	}
	public void setFKeeperDept(String fKeeperDept) {
		FKeeperDept = fKeeperDept;
	}
	public String getFKeeperArea() {
		return FKeeperArea;
	}
	public void setFKeeperArea(String fKeeperArea) {
		FKeeperArea = fKeeperArea;
	}
	public String getFeName() {
		return FeName;
	}
	public void setFeName(String feName) {
		FeName = feName;
	}
	public String getFReason() {
		return FReason;
	}
	public void setFReason(String fReason) {
		FReason = fReason;
	}
	public String getFNote() {
		return FNote;
	}
	public void setFNote(String fNote) {
		FNote = fNote;
	}
}
