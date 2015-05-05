package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyOverTimeTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyOverTimeBusiness
 * @Description 加班申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月23日 下午3:00:48
 */
public class ApplyOverTimeBusiness extends BaseBusiness<ApplyOverTimeTHeaderInfo> {

	/** 
	* @Name: ApplyOverTimeBusiness 
	* @Description:  默认构造函数
	*/
	public ApplyOverTimeBusiness(Context context){
		super(context,ApplyOverTimeTHeaderInfo.getApplyOverTimeTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ApplyOverTimeBusiness applyOverTimeBusiness;
	public static ApplyOverTimeBusiness getApplyOverTimeBusiness(Context context,String serviceUrl) {
		if (applyOverTimeBusiness == null) {
			applyOverTimeBusiness = new ApplyOverTimeBusiness(context);
		}
		applyOverTimeBusiness.setServiceUrl(serviceUrl);
		return applyOverTimeBusiness;
	}
	
	/** 
	* @Title: getApplyOverTimeTHeaderInfo 
	* @Description: 获取加班申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ApplyOverTimeTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月23日 下午3:02:23
	*/
	public ApplyOverTimeTHeaderInfo getApplyOverTimeTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
