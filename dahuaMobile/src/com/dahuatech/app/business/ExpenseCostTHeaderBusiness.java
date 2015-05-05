package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpenseCostTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpenseCostTHeaderBusiness
 * @Description 费用申请单据业务逻辑类
 * @author 21291
 * @date 2014年6月16日 下午2:21:54
 */
public class ExpenseCostTHeaderBusiness extends BaseBusiness<ExpenseCostTHeaderInfo> {
	
	/** 
	* @Name: ExpenseCostTHeaderBusiness 
	* @Description: 默认构造函数 
	*/
	public ExpenseCostTHeaderBusiness(Context context) {
		super(context,ExpenseCostTHeaderInfo.getExpenseCostTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ExpenseCostTHeaderBusiness expenseCostTHeaderBusiness;
	public static ExpenseCostTHeaderBusiness getExpenseCostTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expenseCostTHeaderBusiness == null) {
			expenseCostTHeaderBusiness = new ExpenseCostTHeaderBusiness(context);
		}
		expenseCostTHeaderBusiness.setServiceUrl(serviceUrl);
		return expenseCostTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpenseCostTHeaderInfo 
	* @Description: 获取费用申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ExpenseCostTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年6月16日 下午2:27:35
	*/
	public ExpenseCostTHeaderInfo getExpenseCostTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
