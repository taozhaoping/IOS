package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseSpecialTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseSpecialTHeaderBusiness
 * @Description 报销特殊事务单据表头业务逻辑类
 * @author 21291
 * @date 2014年6月20日 上午10:02:59
 */
public class ExpenseSpecialTHeaderBusiness extends BaseBusiness<ExpenseSpecialTHeaderInfo> {

	public ExpenseSpecialTHeaderBusiness(Context context) {
		super(context,ExpenseSpecialTHeaderInfo.getExpenseSpecialTHeaderInfo());
	}

	//单例模式(线程不安全写法)
	private static ExpenseSpecialTHeaderBusiness expenseSpecialTHeaderBusiness;
	public static ExpenseSpecialTHeaderBusiness getExpenseSpecialTHeaderBusiness(Context context,String serviceUrl) {
		if(expenseSpecialTHeaderBusiness==null){
			expenseSpecialTHeaderBusiness=new ExpenseSpecialTHeaderBusiness(context);
		}
		expenseSpecialTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseSpecialTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseSpecialTHeaderInfo 
	* @Description: 获取特殊事务报销单据表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return ExpenseSpecialTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年6月20日 上午9:53:23
	*/
	public ExpenseSpecialTHeaderInfo getExpenseSpecialTHeaderInfo(TaskParamInfo taskParam){
		return getEntityInfo(taskParam);
	}
}
