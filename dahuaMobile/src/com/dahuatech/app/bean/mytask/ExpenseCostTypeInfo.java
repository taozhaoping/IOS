package com.dahuatech.app.bean.mytask;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ExpenseCostTypeInfo
 * @Description 报销费用类型实体类
 * @author 21291
 * @date 2014年5月23日 上午9:22:03
 */
public class ExpenseCostTypeInfo extends Base {
	
	private static final long serialVersionUID = 1L;
	
	private int FCostId;  		//费用类型明细主键ID
	private int FCostCode;		//费用类型编码
	private String FCostValue;  //费用金额
		
	public int getFCostId() {
		return FCostId;
	}

	public void setFCostId(int fCostId) {
		FCostId = fCostId;
	}

	public int getFCostCode() {
		return FCostCode;
	}

	public void setFCostCode(int fCostCode) {
		FCostCode = fCostCode;
	}

	public String getFCostValue() {
		return FCostValue;
	}

	public void setFCostValue(String fCostValue) {
		FCostValue = fCostValue;
	}
	
	//内部类单例模式
	private static class singletonHolder {  
        private static ExpenseCostTypeInfo instance = new ExpenseCostTypeInfo();  
    }
	private ExpenseCostTypeInfo() {}
	public static ExpenseCostTypeInfo getExpenseCostTypeInfo() {
		return singletonHolder.instance;
	}
}
