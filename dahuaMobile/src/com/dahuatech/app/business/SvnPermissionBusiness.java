package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.SvnPermissionTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName SvnPermissionBusiness
 * @Description SVN权限单据业务逻辑类
 * @author 21291
 * @date 2014年8月12日 上午10:56:23
 */
public class SvnPermissionBusiness extends BaseBusiness<SvnPermissionTHeaderInfo> {

	/** 
	* @Name: SvnPermissionBusiness 
	* @Description:  
	*/
	public SvnPermissionBusiness(Context context) {
		super(context,SvnPermissionTHeaderInfo.getSvnPermissionTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static SvnPermissionBusiness svnPermissionBusiness;
	public static SvnPermissionBusiness getSvnPermissionBusiness(Context context,String serviceUrl) {
		if (svnPermissionBusiness == null) {
			svnPermissionBusiness = new SvnPermissionBusiness(context);
		}
		svnPermissionBusiness.setServiceUrl(serviceUrl);
		return svnPermissionBusiness;
	}
	
	/** 
	* @Title: getSvnPermissionTHeaderInfo 
	* @Description: 获取SVN权限单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return SvnPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月12日 上午10:57:53
	*/
	public SvnPermissionTHeaderInfo getSvnPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
