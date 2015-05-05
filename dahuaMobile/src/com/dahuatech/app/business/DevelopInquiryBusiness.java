package com.dahuatech.app.business;

import android.content.Context;

import com.dahuatech.app.bean.mytask.DevelopInquiryTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;

/**
 * @ClassName DevelopInquiryBusiness
 * @Description 研发中心询价申请单据业务逻辑类
 * @author 21291
 * @date 2014年7月16日 上午9:36:48
 */
public class DevelopInquiryBusiness extends BaseBusiness<DevelopInquiryTHeaderInfo> {
	
	//传递参数
	public static final String KEY_FMASTERIALNAME 		= "fMasterialName"; 	//物料名称
	public static final String KEY_FSUPPLIER 	  		= "fSupplier"; 	  		//供应商
	public static final String KEY_FMANUFACTURER 	  	= "fManufacturer"; 	  	//制造商
	public static final String KEY_FOFFER 		  		= "fOffer";		   		//报价
	public static final String KEY_FCURRENCY		    = "fCurrency";    		//币别
	public static final String KEY_FORDERQUANTITYFROM   = "fOrderQuantityFrom"; //订货量(从)
	public static final String KEY_FORDERQUANTITYTO 	= "fOrderQuantityTo";	//订货量(到)
	public static final String KEY_FUNIT	 			= "fUnit";  			//计量单位
	public static final String KEY_FORDERFORWARD	    = "fOrderForward"; 		//订货提前期
	public static final String KEY_FMINI	 			= "fMini";   			//最小包装量
	public static final String KEY_FMINIORDER 	 		= "fMiniOrder";   		//最小起订量
	public static final String KEY_FPAYMENT 	 		= "fPayment";	   		//账期
	
	/** 
	* @Name: DevelopInquiryBusiness 
	* @Description:  默认构造函数
	*/
	public DevelopInquiryBusiness(Context context) {
		super(context,DevelopInquiryTHeaderInfo.getDevelopInquiryTHeaderInfo());
	}
	
	// 单例模式(线程不安全写法)
	private static DevelopInquiryBusiness developInquiryBusiness;
	public static DevelopInquiryBusiness getDevelopInquiryBusiness(Context context,String serviceUrl) {
		if (developInquiryBusiness == null) {
			developInquiryBusiness = new DevelopInquiryBusiness(context);
		}
		developInquiryBusiness.setServiceUrl(serviceUrl);
		return developInquiryBusiness;
	}
	
	/** 
	* @Title: getDevelopInquiryTHeaderInfo 
	* @Description: 获取研发中心询价申请单据表头实体信息
	* @param @param taskParam
	* @param @return     
	* @return DevelopInquiryTHeaderInfo    
	* @throws 
	* @author 21291
	* @date 2014年7月16日 上午9:40:44
	*/
	public DevelopInquiryTHeaderInfo getDevelopInquiryTHeaderInfo(TaskParamInfo taskParam){
		return super.getEntityInfo(taskParam);	
	}
}
