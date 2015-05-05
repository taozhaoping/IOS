package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName LowConsumableTHeaderInfo
 * @Description 低值易耗物料代码单据表头实体
 * @author 21291
 * @date 2014年8月19日 下午4:35:25
 */
public class LowConsumableTHeaderInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FID;    				//主键内码
	private String FBillNo; 			//单据编号
	private String FApplyName;  		//申请人
	private String FApplyDate;			//申请日期
	private String FApplyDept;			//所属部门
	private String FTel;				//联系电话
	private String FBillType;			//单据类型
	private String FApplyCause;			//申请事由
	private String FSubEntrys;			//子集集合
	
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
	public String getFApplyDept() {
		return FApplyDept;
	}
	public void setFApplyDept(String fApplyDept) {
		FApplyDept = fApplyDept;
	}
	public String getFTel() {
		return FTel;
	}
	public void setFTel(String fTel) {
		FTel = fTel;
	}
	public String getFBillType() {
		return FBillType;
	}
	public void setFBillType(String fBillType) {
		FBillType = fBillType;
	}
	public String getFApplyCause() {
		return FApplyCause;
	}
	public void setFApplyCause(String fApplyCause) {
		FApplyCause = fApplyCause;
	}
	public String getFSubEntrys() {
		return FSubEntrys;
	}
	public void setFSubEntrys(String fSubEntrys) {
		FSubEntrys = fSubEntrys;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static LowConsumableTHeaderInfo instance = new LowConsumableTHeaderInfo();  
    }
	private LowConsumableTHeaderInfo() {}
	public static LowConsumableTHeaderInfo getLowConsumableTHeaderInfo() {
		return singletonHolder.instance;
	}
}
