package com.dahuatech.app.ui.task;

import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.WorkFlowBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ICheckNextNode;

/**
 * @ClassName LowerNodeAppCheck
 * @Description 是否需要下级节点审批功能类
 * @author 21291
 * @date 2014年11月10日 下午5:04:04
 */
public class LowerNodeAppCheck {
	
	private Context context;				   		//调用上下文环境
	private String serviceUrl;					  	//服务地址
	private HashMap<String, String> checkHashMap; 	//配置信息
	
	private ICheckNextNode iNodeAppCheck;		//检查接口
	public ICheckNextNode getiNodeAppCheck() {
		return iNodeAppCheck;
	}

	public void setiNodeAppCheck(ICheckNextNode iNodeAppCheck) {
		this.iNodeAppCheck = iNodeAppCheck;
	}

	private LowerNodeAppCheck() {}
	//内部类单例模式
	private static class singletonHolder {  
        private static LowerNodeAppCheck instance = new LowerNodeAppCheck();  
    }
	private static LowerNodeAppCheck lowerNodeAppCheck=null;
	public static LowerNodeAppCheck getLowerNodeAppCheck(Context context,ICheckNextNode iNodeAppCheck) {
		lowerNodeAppCheck=singletonHolder.instance;
		lowerNodeAppCheck.context=context;
		lowerNodeAppCheck.serviceUrl=AppUrl.URL_API_HOST_ANDROID_NEWWORKFLOWAPPSERVICEURL;
		lowerNodeAppCheck.setiNodeAppCheck(iNodeAppCheck);
		return lowerNodeAppCheck;
	}
	
	/** 
	* @Title: checkStatusAsync 
	* @Description: 检查单据下级节点是否需要启用
	* @param @param fSystemId 系统ID
	* @param @param fClassTypeId 单据ID
	* @param @param fBillId 单据类型ID
	* @param @param fItemNumber 员工号        
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月14日 上午11:29:42
	*/
	public void checkStatusAsync(String fSystemId,String fClassTypeId,String fBillId,String fItemNumber){
		String jsonParam=fSystemId+","+fClassTypeId+","+fBillId+","+fItemNumber;
		new WorkFlowHandleAsync().execute(fItemNumber,jsonParam,serviceUrl);
	}
	
	/**
	 * @ClassName WorkFlowHandleAsync
	 * @Description 异步检查操作
	 * @author 21291
	 * @date 2014年11月10日 下午5:31:23
	 */
	public class WorkFlowHandleAsync extends AsyncTask<String, Void, ResultMessage> {

		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			iNodeAppCheck.setCheckResult(result);
		}
		
		@Override
		protected ResultMessage doInBackground(String... params) {		
			return workFlowHandle(params[0],params[1],params[2]);
		}
	}	
	
	/** 
	* @Title: workFlowHandle 
	* @Description: 异步检查操作
	* @param @param fItemNumber 员工号
	* @param @param jsonParam 	参数值
	* @param @param serviceUrl  服务地址
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年11月14日 下午2:06:42
	*/
	private ResultMessage workFlowHandle(String fItemNumber, String jsonParam, String serviceUrl) {
		checkHashMap=ParseXmlService.xmlPullParser(context.getResources().getXml(R.xml.configfile), "LowerNodeAppCheck");
		
		// 参数值	
		RepositoryInfo repository=RepositoryInfo.getRepositoryInfo();
		repository.setClassType(checkHashMap.get("ClassType"));
		repository.setIsTest(checkHashMap.get("IsTest"));
		repository.setServiceName(checkHashMap.get("ServiceName"));
		repository.setServiceType(checkHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(checkHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(checkHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(context);
		WorkFlowBusiness workFlowBusiness=(WorkFlowBusiness)factoryBusiness.getInstance("WorkFlowBusiness",""); 	
		workFlowBusiness.setServiceUrl(serviceUrl);
		return workFlowBusiness.approveHandle(repository, jsonParam);
	}
	
	/** 
	* @Title: showNextNode 
	* @Description: 显示下级节点
	* @param @param resultMessage 结果值
	* @param @param nextNode 下级节点按钮
	* @param @param context 当前上下文环境
	* @param @param fSystemId 系统ID
	* @param @param fClassTypeId 单据ID
	* @param @param fBillId 单据ID 
	* @param @param fItemNumber 员工号
	* @param @param fBillName 单据名称     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月14日 下午2:33:10
	*/
	public void showNextNode(ResultMessage resultMessage,Button nextNode,final Context context, final String fSystemId,final String fClassTypeId,final String fBillId,final String fItemNumber,final String fBillName){
		if(resultMessage.isIsSuccess()){  //说明启用了
			nextNode.setBackgroundResource(R.drawable.imgbtn_blue);
			nextNode.setOnClickListener(new OnClickListener() { //说明启用了
				
				@Override
				public void onClick(View v) {
					UIHelper.showLowerNodeApp(context,fSystemId,fClassTypeId,fBillId,fItemNumber,fBillName);
				}
			});
		}
		else{ //说明没启用 
			nextNode.setEnabled(false);
		}
	}
}
