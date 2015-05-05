package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName FeDestroyTBodyInfo
 * @Description 印鉴销毁表体单据实体
 * @author 21291
 * @date 2014年10月10日 下午4:02:14
 */
public class FeDestroyTBodyInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FeCode;    			//印鉴代码
	private String FeType; 				//印鉴类型
	private String FeName;  			//印鉴名称
	private String FCompany;			//所属公司
	private String FStatus;				//状态
	private String FKeeper;				//保管人
	private String FKeeperDept;			//保管部门
	private String FKeeperArea;   		//保管区域
	private String FDestroyTime;		//销毁时间
	
	public String getFeCode() {
		return FeCode;
	}
	public void setFeCode(String feCode) {
		FeCode = feCode;
	}
	public String getFeType() {
		return FeType;
	}
	public void setFeType(String feType) {
		FeType = feType;
	}
	public String getFeName() {
		return FeName;
	}
	public void setFeName(String feName) {
		FeName = feName;
	}
	public String getFCompany() {
		return FCompany;
	}
	public void setFCompany(String fCompany) {
		FCompany = fCompany;
	}
	public String getFStatus() {
		return FStatus;
	}
	public void setFStatus(String fStatus) {
		FStatus = fStatus;
	}
	public String getFKeeper() {
		return FKeeper;
	}
	public void setFKeeper(String fKeeper) {
		FKeeper = fKeeper;
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
	public String getFDestroyTime() {
		return FDestroyTime;
	}
	public void setFDestroyTime(String fDestroyTime) {
		FDestroyTime = fDestroyTime;
	}
}
