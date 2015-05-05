package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.LowConsumableTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName LowConsumableBusiness
 * @Description 低值易耗物料代码单据业务逻辑类
 * @author 21291
 * @date 2014年8月19日 下午4:37:06
 */
public class LowConsumableBusiness extends BaseBusiness<LowConsumableTHeaderInfo> {

	/** 
	* @Name: LowConsumableBusiness 
	* @Description:  
	*/
	public LowConsumableBusiness(Context context) {
		super(context,LowConsumableTHeaderInfo.getLowConsumableTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static LowConsumableBusiness lowConsumableBusiness;
	public static LowConsumableBusiness getLowConsumableBusiness(Context context,String serviceUrl) {
		if (lowConsumableBusiness == null) {
			lowConsumableBusiness = new LowConsumableBusiness(context);
		}
		lowConsumableBusiness.setServiceUrl(serviceUrl);
		return lowConsumableBusiness;
	}

	/** 
	* @Title: getLowConsumableTHeaderInfo 
	* @Description: 低值易耗物料代码单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return LowConsumableTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月19日 下午4:38:29
	*/
	public LowConsumableTHeaderInfo getLowConsumableTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
