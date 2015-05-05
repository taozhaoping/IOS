package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.bean.mytask.TdBorrowTHeaderInfo;

/**
 * @ClassName TdBorrowBusiness
 * @Description 技术文件借阅申请单据业务逻辑类
 * @author 21291
 * @date 2014年8月28日 下午4:44:02
 */
public class TdBorrowBusiness extends BaseBusiness<TdBorrowTHeaderInfo> {

	/** 
	* @Name: TdBorrowBusiness 
	* @Description:  
	*/
	public TdBorrowBusiness(Context context) {
		super(context,TdBorrowTHeaderInfo.getTdBorrowTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static TdBorrowBusiness tdBorrowBusiness;
	public static TdBorrowBusiness getTdBorrowBusiness(Context context,String serviceUrl) {
		if (tdBorrowBusiness == null) {
			tdBorrowBusiness = new TdBorrowBusiness(context);
		}
		tdBorrowBusiness.setServiceUrl(serviceUrl);
		return tdBorrowBusiness;
	}
	
	/** 
	* @Title: getTdBorrowTHeaderInfo 
	* @Description: 技术文件借阅申请单据实体信息
	* @param @param taskParam
	* @param @return     
	* @return TdBorrowTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年8月28日 下午4:45:36
	*/
	public TdBorrowTHeaderInfo getTdBorrowTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);
	}
}
