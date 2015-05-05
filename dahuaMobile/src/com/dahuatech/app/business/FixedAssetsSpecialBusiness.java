package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.FixedAssetsSpecialTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName FixedAssetsSpecialBusiness
 * @Description 固定资产特殊紧急采购需求单据业务逻辑类
 * @author 21291
 * @date 2014年8月19日 下午1:53:15
 */
public class FixedAssetsSpecialBusiness extends BaseBusiness<FixedAssetsSpecialTHeaderInfo> {

	/** 
	* @Name: FixedAssetsSpecialBusiness 
	* @Description:  
	*/
	public FixedAssetsSpecialBusiness(Context context) {
		super(context,FixedAssetsSpecialTHeaderInfo.getFixedAssetsSpecialTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static FixedAssetsSpecialBusiness fixedAssetsSpecialBusiness;
	public static FixedAssetsSpecialBusiness getFixedAssetsSpecialBusiness(Context context,String serviceUrl) {
		if (fixedAssetsSpecialBusiness == null) {
			fixedAssetsSpecialBusiness = new FixedAssetsSpecialBusiness(context);
		}
		fixedAssetsSpecialBusiness.setServiceUrl(serviceUrl);
		return fixedAssetsSpecialBusiness;
	}

	/** 
	* @Title: getFixedAssetsSpecialTHeaderInfo 
	* @Description: 固定资产特殊紧急采购需求单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return FixedAssetsSpecialTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月19日 下午1:54:16
	*/
	public FixedAssetsSpecialTHeaderInfo getFixedAssetsSpecialTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
