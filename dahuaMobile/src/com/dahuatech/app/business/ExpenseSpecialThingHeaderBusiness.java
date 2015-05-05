package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseSpecialTHeaderInfo;
import com.dahuatech.app.bean.mytask.ExpenseSpecialThingHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseSpecialTHeaderBusiness
 * @Description 报销特批事务单据表头业务逻辑类
 * @author 21291
 * @date 2014年6月20日 上午10:02:59
 */
public class ExpenseSpecialThingHeaderBusiness extends BaseBusiness<ExpenseSpecialThingHeaderInfo> {

	public ExpenseSpecialThingHeaderBusiness(Context context) {
		super(context,ExpenseSpecialThingHeaderInfo.getExpenseSpecialThingHeaderInfo());
	}

	//单例模式(线程不安全写法)
	private static ExpenseSpecialThingHeaderBusiness expenseSpecialThingHeaderBusiness;
	public static ExpenseSpecialThingHeaderBusiness getExpenseSpecialTHeaderBusiness(Context context,String serviceUrl) {
		if(expenseSpecialThingHeaderBusiness==null){
			expenseSpecialThingHeaderBusiness=new ExpenseSpecialThingHeaderBusiness(context);
		}
		expenseSpecialThingHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseSpecialThingHeaderBusiness;
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
	public ExpenseSpecialThingHeaderInfo getExpenseSpecialThingHeaderInfo(TaskParamInfo taskParam){
		return getEntityInfo(taskParam);
	}
}
