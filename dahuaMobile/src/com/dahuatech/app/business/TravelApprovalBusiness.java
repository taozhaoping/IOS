package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TravelApprovalInfo;

/**
 * @ClassName TravelApprovalBusiness
 * @Description 出差审批单据业务逻辑类
 * @author 21291
 * @date 2014年8月21日 上午9:54:10
 */
public class TravelApprovalBusiness extends BaseBusiness<TravelApprovalInfo> {

	/** 
	* @Name: TravelApprovalBusiness 
	* @Description:  
	*/
	public TravelApprovalBusiness(Context context) {
		super(context,TravelApprovalInfo.getTravelApprovalInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static TravelApprovalBusiness travelApprovalBusiness;
	public static TravelApprovalBusiness getTravelApprovalBusiness(Context context,String serviceUrl) {
		if (travelApprovalBusiness == null) {
			travelApprovalBusiness = new TravelApprovalBusiness(context);
		}
		travelApprovalBusiness.setServiceUrl(serviceUrl);
		return travelApprovalBusiness;
	}
	
	/** 
	* @Title: getTravelApprovalInfo 
	* @Description: 出差审批单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return TravelApprovalInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月21日 上午9:54:54
	*/
	public TravelApprovalInfo getTravelApprovalInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
