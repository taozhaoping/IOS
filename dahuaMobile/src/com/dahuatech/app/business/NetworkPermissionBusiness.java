package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.NetworkPermissionTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName NetworkPermissionBusiness
 * @Description 网络权限申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月9日 下午3:55:58
 */
public class NetworkPermissionBusiness extends BaseBusiness<NetworkPermissionTHeaderInfo> {
	
	/** 
	* @Name: NetworkPermissionBusiness 
	* @Description:  默认构造函数
	*/
	public NetworkPermissionBusiness(Context context) {
		super(context,NetworkPermissionTHeaderInfo.getNetworkPermissionTHeaderInfo());
	}

	// 单例模式(线程不安全写法)
	private static NetworkPermissionBusiness networkPermissionBusiness;
	public static NetworkPermissionBusiness getNetworkPermissionBusiness(Context context,String serviceUrl) {
		if (networkPermissionBusiness == null) {
			networkPermissionBusiness = new NetworkPermissionBusiness(context);
		}
		networkPermissionBusiness.setServiceUrl(serviceUrl);
		return networkPermissionBusiness;
	}
	
	/** 
	* @Title: getNetworkPermissionTHeaderInfo 
	* @Description: 获取网络权限申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return NetworkPermissionTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月9日 下午3:57:09
	*/
	public NetworkPermissionTHeaderInfo getNetworkPermissionTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
