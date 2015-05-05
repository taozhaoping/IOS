package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.MemRequreInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName MemRequreBusiness
 * @Description MEM流程申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月17日 下午3:32:31
 */
public class MemRequreBusiness extends BaseBusiness<MemRequreInfo> {
	
	/** 
	* @Name: MemRequreBusiness 
	* @Description:  默认构造函数
	*/
	public MemRequreBusiness(Context context) {
		super(context,MemRequreInfo.getMemRequreInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static MemRequreBusiness memRequreBusiness;
	public static MemRequreBusiness getMemRequreBusiness(Context context,String serviceUrl) {
		if (memRequreBusiness == null) {
			memRequreBusiness = new MemRequreBusiness(context);
		}
		memRequreBusiness.setServiceUrl(serviceUrl);
		return memRequreBusiness;
	}
	
	/** 
	* @Title: getMemRequreInfo 
	* @Description: 获取MEM流程申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return MemRequreInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月17日 下午3:33:59
	*/
	public MemRequreInfo getMemRequreInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}
}
