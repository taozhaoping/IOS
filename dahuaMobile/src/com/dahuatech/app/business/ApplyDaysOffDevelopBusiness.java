package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ApplyDaysOffDevelopInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ApplyDaysOffDevelopBusiness
 * @Description 研发部门调休申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月23日 下午3:09:44
 */
public class ApplyDaysOffDevelopBusiness extends BaseBusiness<ApplyDaysOffDevelopInfo> {

	/** 
	* @Name: ApplyDaysOffDevelopBusiness 
	* @Description:  默认构造函数
	*/
	public ApplyDaysOffDevelopBusiness(Context context){
		super(context,ApplyDaysOffDevelopInfo.getApplyDaysOffDevelopInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ApplyDaysOffDevelopBusiness applyDaysOffDevelopBusiness;
	public static ApplyDaysOffDevelopBusiness getApplyDaysOffDevelopBusiness(Context context,String serviceUrl) {
		if (applyDaysOffDevelopBusiness == null) {
			applyDaysOffDevelopBusiness = new ApplyDaysOffDevelopBusiness(context);
		}
		applyDaysOffDevelopBusiness.setServiceUrl(serviceUrl);
		return applyDaysOffDevelopBusiness;
	}
	
	/** 
	* @Title: getApplyDaysOffDevelopInfo 
	* @Description: 获取研发部门调休申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ApplyDaysOffDevelopInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月23日 下午3:09:20
	*/
	public ApplyDaysOffDevelopInfo getApplyDaysOffDevelopInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
