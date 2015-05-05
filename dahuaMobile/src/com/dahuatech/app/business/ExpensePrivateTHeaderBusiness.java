package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpensePrivateTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpensePrivateTHeaderBusiness
 * @Description 报销对私单据表头实体业务逻辑类
 * @author 21291
 * @date 2014年5月26日 下午2:35:27
 */
public class ExpensePrivateTHeaderBusiness extends BaseBusiness<ExpensePrivateTHeaderInfo> {
	/** 
	* @Name: ExpensePrivateTHeaderBusiness 
	* @Description:  默认构造函数
	*/
	private ExpensePrivateTHeaderBusiness(Context context) {
		super(context,ExpensePrivateTHeaderInfo.getExpensePrivateTHeaderInfo());
	}
	
	//单例模式(线程不安全写法)
	private static ExpensePrivateTHeaderBusiness expensePrivateTHeaderBusiness;
	public static ExpensePrivateTHeaderBusiness getExpensePrivateTHeaderBusiness(Context context,String serviceUrl) {
		if(expensePrivateTHeaderBusiness==null){
			expensePrivateTHeaderBusiness=new ExpensePrivateTHeaderBusiness(context);
		}
		expensePrivateTHeaderBusiness.setServiceUrl(serviceUrl);
		return expensePrivateTHeaderBusiness;
	}
	
	/** 
	* @Title: getExpensePrivateTHeaderInfo 
	* @Description: 获取对私报销单据表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return ExpensePrivateTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年5月23日 上午9:44:25
	*/
	public ExpensePrivateTHeaderInfo getExpensePrivateTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}

}
