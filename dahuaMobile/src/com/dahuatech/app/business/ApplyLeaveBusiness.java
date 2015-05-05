package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyLeaveTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyLeaveBusiness
 * @Description 请假申请单据业务逻辑类
 * @author 21291
 * @date 2015年1月12日 上午9:35:32
 */
public class ApplyLeaveBusiness extends BaseBusiness<ApplyLeaveTHeaderInfo> {

	/** 
	* @Name: ApplyLeaveBusiness 
	* @Description:  默认构造函数
	*/
	public ApplyLeaveBusiness(Context context){
		super(context,ApplyLeaveTHeaderInfo.getApplyLeaveTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ApplyLeaveBusiness applyOverTimeBusiness;
	public static ApplyLeaveBusiness getApplyLeaveBusiness(Context context,String serviceUrl) {
		if (applyOverTimeBusiness == null) {
			applyOverTimeBusiness = new ApplyLeaveBusiness(context);
		}
		applyOverTimeBusiness.setServiceUrl(serviceUrl);
		return applyOverTimeBusiness;
	}
	
	/** 
	* @Title: getApplyLeaveTHeaderInfo 
	* @Description: 获取请假申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ApplyLeaveTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2015年1月12日 上午9:36:37
	*/
	public ApplyLeaveTHeaderInfo getApplyLeaveTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
