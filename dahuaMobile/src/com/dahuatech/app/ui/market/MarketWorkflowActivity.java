package com.dahuatech.app.ui.market;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MarketWorkFlowAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.SqlParametersInfo;
import com.dahuatech.app.bean.market.MarketWorkflowInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MarketBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName MarketWorkflowActivity
 * @Description 报价/合同工作流页面
 * @author 21291
 * @date 2015年1月26日 下午1:52:10
 */
public class MarketWorkflowActivity extends MenuActivity {
	private String fSystemType,fClassTypeID,fBillID,fItemNumber;  //系统类型，单据ID，单据主键内码,员工号
	
	private ListView mListView; 					//列表控件对象
	private ProgressDialog dialog;					//进程框
	private MarketBusiness marketBusiness;			//业务逻辑类
	private MarketWorkFlowAdapter mAdapter;			//适配器类
	
	private String serviceUrl,typeName; 			//服务地址,操作类型名称
	private HashMap<String, String> hashMap; 		//配置信息
	
	private AppContext appContext; 					//全局Context

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.market_workflow);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fSystemType=extras.getString(AppContext.WORKFLOW_FSYSTEMTYPE_KEY);
			fClassTypeID=extras.getString(AppContext.WORKFLOW_FCLASSTYPEID_KEY);
			fBillID=extras.getString(AppContext.WORKFLOW_FBILLID_KEY);		
			typeName=extras.getString(AppContext.MARKET_WORKFLOW_TYPE);	
		}
		
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_WORKFLOWACTIVITY;
		initView();
		if(!StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fClassTypeID) && !StringUtils.isEmpty(fBillID)&& !StringUtils.isEmpty(fItemNumber)){
			// 声明一个等待框以提示用户等待
			dialog = new ProgressDialog(this);
			dialog.setMessage(getResources().getString(R.string.dialog_loading));
			dialog.setCancelable(false);
			
			new MarketWorkflowAsync().execute();
			sendLogs(typeName);   //发送日记进行统计
		}
		else {
			UIHelper.ToastMessage(MarketWorkflowActivity.this, R.string.market_workflow_paramerror);
			return;
		}
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月29日 下午2:42:46
	*/
	private void initView(){
		mListView=(ListView)findViewById(R.id.market_workflow_listView);
		hashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "CrmWorkFlowActivity");
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MarketWorkflowActivity.this);
		marketBusiness=(MarketBusiness)factoryBusiness.getInstance("MarketBusiness",serviceUrl); 
	}	

	/**
	 * @ClassName MarketWorkflowAsync
	 * @Description 异步加载审批记录数据
	 * @author 21291
	 * @date 2015年1月29日 下午3:01:53
	 */
	private class MarketWorkflowAsync extends AsyncTask<Void, Void, List<MarketWorkflowInfo>> {	
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // 显示等待框
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MarketWorkflowInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // 销毁等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<MarketWorkflowInfo> doInBackground(Void... params) {
			return getListByPost();
		}
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 通过POST方式获取数据源
	* @param @return     
	* @return List<MarketWorkflowInfo>    
	* @throws 
	* @author 21291
	* @date 2015年1月29日 下午3:11:28
	*/
	private List<MarketWorkflowInfo> getListByPost() {
		// 参数实体-仓储类
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		// 参数实体-参数类
		SqlParametersInfo sqlParameters= SqlParametersInfo.getSqlParametersInfo();
		setWorkFlowParam(repository,sqlParameters);
		return marketBusiness.getMarketWorkflowInfo(repository, sqlParameters);
	}

	/** 
	* @Title: setWorkFlowParam 
	* @Description: 设置工作流记录参数
	* @param @param repository
	* @param @param sqlParameters     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年1月29日 下午3:04:07
	*/
	private void setWorkFlowParam(RepositoryInfo repository,SqlParametersInfo sqlParameters) {		
		//参数值	
		repository.setClassType(hashMap.get("ClassType"));
		repository.setIsTest(hashMap.get("IsTest"));
		repository.setServiceName(hashMap.get("ServiceName"));
		repository.setServiceType(hashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(hashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(hashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);
	
		sqlParameters.setParameterCount(3);
		sqlParameters.setParameter1(fSystemType);
		sqlParameters.setParameter2(fClassTypeID);
		sqlParameters.setParameter3(fBillID);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: 初始化界面数据
	* @param @param arrayList 列表集合     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月12日 上午10:14:59
	*/
	private void renderListView(List<MarketWorkflowInfo> arrayList) {
		if(arrayList.size()==0){ //如果获取不到数据
			UIHelper.ToastMessage(MarketWorkflowActivity.this, R.string.market_workflow_netparseerror);		
			return;
		}
		// 使用数据集构造adapter对象
		mAdapter = new MarketWorkFlowAdapter(this, arrayList,R.layout.market_workflow_item);
		// 设置ListView的adapter
		mListView.setAdapter(mAdapter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;				
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 发送日志记录到服务器
	* @param @param typeName     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2015年2月2日 上午11:23:57
	*/
	private void sendLogs(final String typeName){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		if("bidflowquery".equals(typeName)){  //说明是报价查询
			logInfo.setFModuleName(getResources().getString(R.string.log_market_bid));
		}
		else{  //说明是合同查询
			logInfo.setFModuleName(getResources().getString(R.string.log_market_contract));
		}
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(MarketWorkflowActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MarketWorkflowActivity.this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}
}
