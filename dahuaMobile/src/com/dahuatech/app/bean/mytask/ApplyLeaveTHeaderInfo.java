package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ApplyLeaveTHeaderInfo
 * @Description 请假申请单据表头实体
 * @author 21291
 * @date 2015年1月12日 上午9:30:37
 */
public class ApplyLeaveTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private String FBillNo;				//单据编号
	private String FApplyName;			//申请人
	private String FApplyDate;			//申请时间
	private String FApplyDept;			//所属部门
	private String FSubEntrys;			//子集集合
	
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
	
	public String getFApplyDept() {
		return FApplyDept;
	}

	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}

	public String getFSubEntrys() {
		return FSubEntrys;
	}

	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ApplyLeaveTHeaderInfo instance = new ApplyLeaveTHeaderInfo();  
    }  
	private ApplyLeaveTHeaderInfo() {}
	public static ApplyLeaveTHeaderInfo getApplyLeaveTHeaderInfo() {
		return singletonHolder.instance;
	}
}
