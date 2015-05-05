package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FeTakeOutTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FeTakeOutBusiness
 * @Description 印鉴外带业务逻辑类
 * @author 21291
 * @date 2014年10月11日 上午10:58:54
 */
public class FeTakeOutBusiness extends BaseBusiness<FeTakeOutTHeaderInfo> {
	
	public FeTakeOutBusiness(Context context) {
		super(context,FeTakeOutTHeaderInfo.getFeTakeOutTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static FeTakeOutBusiness feTakeOutBusiness;
	public static FeTakeOutBusiness getFeTakeOutBusiness(Context context,String serviceUrl) {
		if (feTakeOutBusiness == null) {
			feTakeOutBusiness = new FeTakeOutBusiness(context);
		}
		feTakeOutBusiness.setServiceUrl(serviceUrl);
		return feTakeOutBusiness;
	}
	
	/** 
	* @Title: getFeTakeOutTHeaderInfo 
	* @Description: 获取印鉴外带表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return FeTakeOutTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年10月11日 上午10:59:29
	*/
	public FeTakeOutTHeaderInfo getFeTakeOutTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
