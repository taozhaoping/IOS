package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseMarketPayTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseMarketPayTHeaderBusiness
 * @Description 市场投标支付业务逻辑类
 * @author 21291
 * @date 2014年6月25日 下午1:51:09
 */
public class ExpenseMarketPayTHeaderBusiness extends BaseBusiness<ExpenseMarketPayTHeaderInfo> {
	
	/** 
	* @Name: ExpenseMarketPayTHeaderBusiness 
	* @Description:  构造默认函数
	*/
	public ExpenseMarketPayTHeaderBusiness(Context context) {
		super(context,ExpenseMarketPayTHeaderInfo.getExpenseMarketPayTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ExpenseMarketPayTHeaderBusiness expenseMarketPayTHeaderBusiness;
	public static ExpenseMarketPayTHeaderBusiness getExpenseMarketPayTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expenseMarketPayTHeaderBusiness == null) {
			expenseMarketPayTHeaderBusiness = new ExpenseMarketPayTHeaderBusiness(context);
		}
		expenseMarketPayTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseMarketPayTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseMarketPayTHeaderInfo 
	* @Description: 获取报销市场支付单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ExpenseMarketPayTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年6月25日 下午2:09:09
	*/
	public ExpenseMarketPayTHeaderInfo getExpenseMarketPayTHeaderInfo(TaskParamInfo taskParam) {
		return super.getEntityInfo(taskParam);
	}


}
