package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeDestroyTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeDestroyBusiness
 * @Description 印鉴销毁业务逻辑类
 * @author 21291
 * @date 2014年10月11日 上午10:54:30
 */
public class FeDestroyBusiness extends BaseBusiness<FeDestroyTHeaderInfo> {
	
	public FeDestroyBusiness(Context context) {
		super(context,FeDestroyTHeaderInfo.getFeDestroyTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static FeDestroyBusiness feDestroyBusiness;
	public static FeDestroyBusiness getFeDestroyBusiness(Context context,String serviceUrl) {
		if (feDestroyBusiness == null) {
			feDestroyBusiness = new FeDestroyBusiness(context);
		}
		feDestroyBusiness.setServiceUrl(serviceUrl);
		return feDestroyBusiness;
	}
	
	/** 
	* @Title: getFeDestroyTHeaderInfo 
	* @Description: 获取印鉴销毁表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return FeDestroyTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年10月11日 上午10:55:26
	*/
	public FeDestroyTHeaderInfo getFeDestroyTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
