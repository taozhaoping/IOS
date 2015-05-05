package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DaHuaAssumeCostInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DaHuaAssumeCostBusiness
 * @Description 大华承担费用申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月15日 下午5:25:36
 */
public class DaHuaAssumeCostBusiness extends BaseBusiness<DaHuaAssumeCostInfo> {
	
	/** 
	* @Name: DaHuaAssumeCostBusiness 
	* @Description:  默认构造函数
	*/
	public DaHuaAssumeCostBusiness(Context context) {
		super(context,DaHuaAssumeCostInfo.getDaHuaAssumeCostInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static DaHuaAssumeCostBusiness daHuaAssumeCostBusiness;
	public static DaHuaAssumeCostBusiness getDaHuaAssumeCostBusiness(Context context,String serviceUrl) {
		if (daHuaAssumeCostBusiness == null) {
			daHuaAssumeCostBusiness = new DaHuaAssumeCostBusiness(context);
		}
		daHuaAssumeCostBusiness.setServiceUrl(serviceUrl);
		return daHuaAssumeCostBusiness;
	}
	
	/** 
	* @Title: getDaHuaAssumeCostInfo 
	* @Description: 获取大华承担费用申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return DaHuaAssumeCostInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月15日 下午5:29:34
	*/
	public DaHuaAssumeCostInfo getDaHuaAssumeCostInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}

}
