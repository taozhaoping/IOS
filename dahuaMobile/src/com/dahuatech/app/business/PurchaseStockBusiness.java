package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.PurchaseStockTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName PurchaseStockBusiness
 * @Description 采购备料单据业务逻辑类
 * @author 21291
 * @date 2014年8月15日 下午2:37:42
 */
public class PurchaseStockBusiness extends BaseBusiness<PurchaseStockTHeaderInfo> {

	/** 
	* @Name: PurchaseStockBusiness 
	* @Description: 默认构造函数  
	*/
	public PurchaseStockBusiness(Context context) {
		super(context,PurchaseStockTHeaderInfo.getPurchaseStockTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static PurchaseStockBusiness purchaseStockBusiness;
	public static PurchaseStockBusiness getPurchaseStockBusiness(Context context,String serviceUrl) {
		if (purchaseStockBusiness == null) {
			purchaseStockBusiness = new PurchaseStockBusiness(context);
		}
		purchaseStockBusiness.setServiceUrl(serviceUrl);
		return purchaseStockBusiness;
	}
	
	/** 
	* @Title: getPurchaseStockTHeaderInfo 
	* @Description: 获取采购备料单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return PurchaseStockTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月15日 下午2:38:25
	*/
	public PurchaseStockTHeaderInfo getPurchaseStockTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
