package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.ExAttendanceTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName ExAttendanceBusiness
 * @Description 异常考勤调整申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月23日 下午3:04:23
 */
public class ExAttendanceBusiness extends BaseBusiness<ExAttendanceTHeaderInfo> {

	/** 
	* @Name: ExAttendanceBusiness 
	* @Description:  默认构造函数
	*/
	public ExAttendanceBusiness(Context context){
		super(context,ExAttendanceTHeaderInfo.getExAttendanceTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static ExAttendanceBusiness exAttendanceBusiness;
	public static ExAttendanceBusiness getExAttendanceBusiness(Context context,String serviceUrl) {
		if (exAttendanceBusiness == null) {
			exAttendanceBusiness = new ExAttendanceBusiness(context);
		}
		exAttendanceBusiness.setServiceUrl(serviceUrl);
		return exAttendanceBusiness;
	}
	
	/** 
	* @Title: getExAttendanceTHeaderInfo 
	* @Description: 获取异常考勤调整申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return ExAttendanceTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月23日 下午3:04:20
	*/
	public ExAttendanceTHeaderInfo getExAttendanceTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
