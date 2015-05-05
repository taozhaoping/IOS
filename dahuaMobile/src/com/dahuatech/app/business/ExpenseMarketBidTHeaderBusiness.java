package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseMarketBidTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseMarketBidTHeaderBusiness
 * @Description 市场投标报销业务逻辑类
 * @author 21291
 * @date 2014年6月25日 下午1:51:25
 */
public class ExpenseMarketBidTHeaderBusiness extends BaseBusiness<ExpenseMarketBidTHeaderInfo> {
	
	/** 
	* @Name: ExpenseMarketBidTHeaderBusiness 
	* @Description:构造默认函数  
	*/
	public ExpenseMarketBidTHeaderBusiness(Context context) {
		super(context,ExpenseMarketBidTHeaderInfo.getExpenseMarketBidTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ExpenseMarketBidTHeaderBusiness expenseMarketBidTHeaderBusiness;
	public static ExpenseMarketBidTHeaderBusiness getExpenseMarketBidTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expenseMarketBidTHeaderBusiness == null) {
			expenseMarketBidTHeaderBusiness = new ExpenseMarketBidTHeaderBusiness(context);
		}
		expenseMarketBidTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseMarketBidTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseMarketBidTHeaderInfo 
	* @Description: 获取报销市场投标单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ExpenseMarketBidTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年6月25日 下午2:08:16
	*/
	public ExpenseMarketBidTHeaderInfo getExpenseMarketBidTHeaderInfo(TaskParamInfo taskParam) {
		return super.getEntityInfo(taskParam);
	}

}
