package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExpensePublicTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExpensePublicBusiness
 * @Description 对公支付报销单据业务逻辑类
 * @author 21291
 * @date 2014年6月4日 下午3:31:52
 */
public class ExpensePublicTHeaderBusiness extends BaseBusiness<ExpensePublicTHeaderInfo> {

	public ExpensePublicTHeaderBusiness(Context context) {
		super(context,ExpensePublicTHeaderInfo.getExpensePublicTHeaderInfo());
	}

	// 单例模式(线程不安全写法)
	private static ExpensePublicTHeaderBusiness expensePublicTHeaderBusiness;
	public static ExpensePublicTHeaderBusiness getExpensePublicTHeaderBusiness(
			Context context,String serviceUrl) {
		if (expensePublicTHeaderBusiness == null) {
			expensePublicTHeaderBusiness = new ExpensePublicTHeaderBusiness(context);
		}
		expensePublicTHeaderBusiness.setServiceUrl(serviceUrl);
		return expensePublicTHeaderBusiness;
	}

	/**
	 * @Title: getExpensePublicTHeaderInfo
	 * @Description: 获取对公单据报销实体信息
	 * @param @param taskParam
	 * @param @return
	 * @return ExpensePublicTHeaderInfo
	 * @throws
	 * @author 21291
	 * @date 2014年6月4日 下午3:57:31
	 */
	public ExpensePublicTHeaderInfo getExpensePublicTHeaderInfo(TaskParamInfo taskParam) {
		return super.getEntityInfo(taskParam);
	}
}
