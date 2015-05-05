package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeUpdateTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeUpdateBusiness
 * @Description 印鉴更换业务逻辑类
 * @author 21291
 * @date 2014年10月11日 上午11:06:18
 */
public class FeUpdateBusiness extends BaseBusiness<FeUpdateTHeaderInfo> {
	
	public FeUpdateBusiness(Context context) {
		super(context,FeUpdateTHeaderInfo.getFeUpdateTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static FeUpdateBusiness feUpdateBusiness;
	public static FeUpdateBusiness getFeUpdateBusiness(Context context,String serviceUrl) {
		if (feUpdateBusiness == null) {
			feUpdateBusiness = new FeUpdateBusiness(context);
		}
		feUpdateBusiness.setServiceUrl(serviceUrl);
		return feUpdateBusiness;
	}
	
	/** 
	* @Title: getFeUpdateTHeaderInfo 
	* @Description: 获取印鉴更换表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return FeUpdateTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年10月11日 上午11:07:05
	*/
	public FeUpdateTHeaderInfo getFeUpdateTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
