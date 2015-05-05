package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeEngravingTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeEngravingBusiness
 * @Description 印鉴刻制业务逻辑类
 * @author 21291
 * @date 2014年10月11日 上午10:56:50
 */
public class FeEngravingBusiness extends BaseBusiness<FeEngravingTHeaderInfo> {
	
	public FeEngravingBusiness(Context context) {
		super(context,FeEngravingTHeaderInfo.getFeEngravingTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static FeEngravingBusiness feEngravingBusiness;
	public static FeEngravingBusiness getFeEngravingBusiness(Context context,String serviceUrl) {
		if (feEngravingBusiness == null) {
			feEngravingBusiness = new FeEngravingBusiness(context);
		}
		feEngravingBusiness.setServiceUrl(serviceUrl);
		return feEngravingBusiness;
	}
	
	/** 
	* @Title: getFeEngravingTHeaderInfoo 
	* @Description: 获取印鉴刻制表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return FeEngravingTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年10月11日 上午10:57:37
	*/
	public FeEngravingTHeaderInfo getFeEngravingTHeaderInfoo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
