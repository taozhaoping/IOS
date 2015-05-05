package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TdPermissionTHeaderInfo;

/**
 * @ClassName TdPermissionBusiness
 * @Description TD权限申请单据业务逻辑类
 * @author 21291
 * @date 2014年9月23日 上午10:19:30
 */
public class TdPermissionBusiness extends BaseBusiness<TdPermissionTHeaderInfo> {

	/** 
	* @Name: TdPermissionBusiness 
	* @Description:  
	*/
	public TdPermissionBusiness(Context context) {
		super(context,TdPermissionTHeaderInfo.getTdPermissionTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static TdPermissionBusiness tdPermissionBusiness;
	public static TdPermissionBusiness getTdPermissionBusiness(Context context,String serviceUrl) {
		if (tdPermissionBusiness == null) {
			tdPermissionBusiness = new TdPermissionBusiness(context);
		}
		tdPermissionBusiness.setServiceUrl(serviceUrl);
		return tdPermissionBusiness;
	}
	
	/** 
	* @Title: getTdPermissionTHeaderInfo 
	* @Description: TD权限申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return TdPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年9月23日 上午10:20:27
	*/
	public TdPermissionTHeaderInfo getTdPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
