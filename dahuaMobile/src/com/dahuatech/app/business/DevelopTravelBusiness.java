package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DevelopTravelTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DevelopTravelBusiness
 * @Description 研发出差派遣单据业务逻辑类
 * @author 21291
 * @date 2014年8月15日 上午9:51:05
 */
public class DevelopTravelBusiness extends BaseBusiness<DevelopTravelTHeaderInfo> {

	/** 
	* @Name: DevelopTravelBusiness 
	* @Description: 默认构造函数  
	*/
	public DevelopTravelBusiness(Context context) {
		super(context,DevelopTravelTHeaderInfo.getDevelopTravelTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static DevelopTravelBusiness developTravelBusiness;
	public static DevelopTravelBusiness getDevelopTravelBusiness(Context context,String serviceUrl) {
		if (developTravelBusiness == null) {
			developTravelBusiness = new DevelopTravelBusiness(context);
		}
		developTravelBusiness.setServiceUrl(serviceUrl);
		return developTravelBusiness;
	}
	
	/** 
	* @Title: getDevelopTravelTHeaderInfo 
	* @Description: 获取研发出差派遣单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return DevelopTravelTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月15日 上午9:52:07
	*/
	public DevelopTravelTHeaderInfo getDevelopTravelTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
