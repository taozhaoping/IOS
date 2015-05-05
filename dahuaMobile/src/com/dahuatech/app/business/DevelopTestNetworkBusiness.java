package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DevelopTestNetworkTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DevelopTestNetworkBusiness
 * @Description 研发项目测试网络权限业务逻辑类
 * @author 21291
 * @date 2014年7月15日 下午4:41:37
 */
public class DevelopTestNetworkBusiness extends BaseBusiness<DevelopTestNetworkTHeaderInfo> {
	
	public DevelopTestNetworkBusiness(Context context) {
		super(context,DevelopTestNetworkTHeaderInfo.getDevelopTestNetworkTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static DevelopTestNetworkBusiness developTestNetworkBusiness;
	public static DevelopTestNetworkBusiness getDevelopTestNetworkBusiness(Context context,String serviceUrl) {
		if (developTestNetworkBusiness == null) {
			developTestNetworkBusiness = new DevelopTestNetworkBusiness(context);
		}
		developTestNetworkBusiness.setServiceUrl(serviceUrl);
		return developTestNetworkBusiness;
	}
	
	/** 
	* @Title: getDevelopTestNetworkTHeaderInfo 
	* @Description: 获取研发项目测试网络权限表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return DevelopTestNetworkTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月15日 下午4:53:51
	*/
	public DevelopTestNetworkTHeaderInfo getDevelopTestNetworkTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
