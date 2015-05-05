package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TrainComputerTHeaderInfo;

/**
 * @ClassName TrainComputerBusiness
 * @Description  培训电算化教室申请单据业务逻辑类
 * @author 21291
 * @date 2014年8月21日 上午9:30:14
 */
public class TrainComputerBusiness extends BaseBusiness<TrainComputerTHeaderInfo> {

	/** 
	* @Name: TrainComputerBusiness 
	* @Description:  
	*/
	public TrainComputerBusiness(Context context) {
		super(context,TrainComputerTHeaderInfo.getTrainComputerTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static TrainComputerBusiness trainComputerBusiness;
	public static TrainComputerBusiness getTrainComputerBusiness(Context context,String serviceUrl) {
		if (trainComputerBusiness == null) {
			trainComputerBusiness = new TrainComputerBusiness(context);
		}
		trainComputerBusiness.setServiceUrl(serviceUrl);
		return trainComputerBusiness;
	}
	
	/** 
	* @Title: getTrainComputerTHeaderInfo 
	* @Description: 培训电算化教室申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return TrainComputerTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月21日 上午9:31:02
	*/
	public TrainComputerTHeaderInfo getTrainComputerTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
