package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyDaysOffTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyDaysOffBusiness
 * @Description 普通部门调休申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月23日 下午3:07:21
 */
public class ApplyDaysOffBusiness extends BaseBusiness<ApplyDaysOffTHeaderInfo> {
	
	/** 
	* @Name: ApplyDaysOffBusiness 
	* @Description:  默认构造函数
	*/
	public ApplyDaysOffBusiness(Context context){
		super(context,ApplyDaysOffTHeaderInfo.getApplyDaysOffTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ApplyDaysOffBusiness applyDaysOffBusiness;
	public static ApplyDaysOffBusiness getApplyDaysOffBusiness(Context context,String serviceUrl) {
		if (applyDaysOffBusiness == null) {
			applyDaysOffBusiness = new ApplyDaysOffBusiness(context);
		}
		applyDaysOffBusiness.setServiceUrl(serviceUrl);
		return applyDaysOffBusiness;
	}
	
	/** 
	* @Title: getApplyDaysOffTHeaderInfo 
	* @Description: 获取普通部门调休申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ApplyDaysOffTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月23日 下午3:06:54
	*/
	public ApplyDaysOffTHeaderInfo getApplyDaysOffTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
