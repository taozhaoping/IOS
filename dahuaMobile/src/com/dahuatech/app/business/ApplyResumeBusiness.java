package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyResumeTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyResumeBusiness
 * @Description 销假申请单据业务逻辑类
 * @author 21291
 * @date 2015年1月12日 上午10:29:37
 */
public class ApplyResumeBusiness extends BaseBusiness<ApplyResumeTHeaderInfo> {

	/** 
	* @Name: ApplyResumeBusiness 
	* @Description:  默认构造函数
	*/
	public ApplyResumeBusiness(Context context){
		super(context,ApplyResumeTHeaderInfo.getApplyResumeTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ApplyResumeBusiness applyOverTimeBusiness;
	public static ApplyResumeBusiness getApplyResumeBusiness(Context context,String serviceUrl) {
		if (applyOverTimeBusiness == null) {
			applyOverTimeBusiness = new ApplyResumeBusiness(context);
		}
		applyOverTimeBusiness.setServiceUrl(serviceUrl);
		return applyOverTimeBusiness;
	}
	
	/** 
	* @Title: getApplyResumeTHeaderInfo 
	* @Description: 获取销假申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ApplyResumeTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2015年1月12日 上午10:30:07
	*/
	public ApplyResumeTHeaderInfo getApplyResumeTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
