package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DoorPermissionTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DoorPermissionBusiness
 * @Description 门禁权限申请单据业务逻辑类
 * @author 21291
 * @date 2014年8月21日 下午2:20:46
 */
public class DoorPermissionBusiness extends BaseBusiness<DoorPermissionTHeaderInfo> {

	/** 
	* @Name: DoorPermissionBusiness 
	* @Description:  
	*/
	public DoorPermissionBusiness(Context context) {
		super(context,DoorPermissionTHeaderInfo.getDoorPermissionTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static DoorPermissionBusiness doorPermissionBusiness;
	public static DoorPermissionBusiness getDoorPermissionBusiness(Context context,String serviceUrl) {
		if (doorPermissionBusiness == null) {
			doorPermissionBusiness = new DoorPermissionBusiness(context);
		}
		doorPermissionBusiness.setServiceUrl(serviceUrl);
		return doorPermissionBusiness;
	}
	
	/** 
	* @Title: getDoorPermissionTHeaderInfo 
	* @Description: 门禁权限单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return DoorPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月21日 下午2:21:39
	*/
	public DoorPermissionTHeaderInfo getDoorPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
