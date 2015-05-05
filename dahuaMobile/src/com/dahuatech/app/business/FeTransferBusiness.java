package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeTransferTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeTransferBusiness
 * @Description 印鉴移交业务逻辑类
 * @author 21291
 * @date 2014年10月11日 上午11:00:53
 */
public class FeTransferBusiness extends BaseBusiness<FeTransferTHeaderInfo> {
	
	public FeTransferBusiness(Context context) {
		super(context,FeTransferTHeaderInfo.getFeTransferTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static FeTransferBusiness feTransferBusiness;
	public static FeTransferBusiness getFeTransferBusiness(Context context,String serviceUrl) {
		if (feTransferBusiness == null) {
			feTransferBusiness = new FeTransferBusiness(context);
		}
		feTransferBusiness.setServiceUrl(serviceUrl);
		return feTransferBusiness;
	}
	
	/** 
	* @Title: getFeTransferTHeaderInfo 
	* @Description: 获取印鉴移交表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return FeTransferTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年10月11日 上午11:01:30
	*/
	public FeTransferTHeaderInfo getFeTransferTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
