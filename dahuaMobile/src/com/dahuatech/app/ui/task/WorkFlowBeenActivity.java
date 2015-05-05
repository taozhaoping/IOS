package com.dahuatech.app.ui.task;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.WorkFlowAdapter;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.SqlParametersInfo;
import com.dahuatech.app.bean.mytask.WorkFlowInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.WorkFlowBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

public class WorkFlowBeenActivity extends MenuActivity {

	private ListView mListView; 	// 列表控件对象
	private ProgressDialog dialog;		//进程框
	
	private String FSystemType,fClassTypeID,fBillID,fItemNumber;  //系统类型，单据ID，单据主键内码,员工号
	private String serviceUrl; // 服务地址
	private HashMap<String, String> oldHashMap,newHashMap; // 配置信息
	private WorkFlowBusiness workFlowBusiness; //业务逻辑类
	private AppContext appContext;// 全局Context
	private SharedPreferences sp;  //获取登陆信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workflow_been);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//初始化全局变量
		appContext = (AppContext)getApplication();
		//网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		sp= getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE, MODE_PRIVATE); 	
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(WorkFlowBeenActivity.this);
		workFlowBusiness=(WorkFlowBusiness)factoryBusiness.getInstance("WorkFlowBusiness","");  
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			FSystemType=extras.getString(AppContext.WORKFLOW_FSYSTEMTYPE_KEY);
			fClassTypeID=extras.getString(AppContext.WORKFLOW_FCLASSTYPEID_KEY);
			fBillID=extras.getString(AppContext.WORKFLOW_FBILLID_KEY);		
		}	
		iniHasMap();   //初始化服务地址
		//获取员工号
		fItemNumber=sp.getString(AppContext.USER_NAME_KEY, "");
		
		if(!StringUtils.isEmpty(FSystemType) && !StringUtils.isEmpty(fClassTypeID) && !StringUtils.isEmpty(fBillID)&& !StringUtils.isEmpty(fItemNumber)){
			// 声明一个等待框以提示用户等待
			dialog = new ProgressDialog(this);
			dialog.setMessage(getResources().getString(R.string.dialog_loading));
			dialog.setCancelable(false);
			
			new WorkFlowBeenAsyncClient().execute(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(WorkFlowBeenActivity.this, R.string.workflow_paramerror);
			return;
		}
	}
	
	/** 
	* @Title: iniHasMap 
	* @Description: 初始化配置文件信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月21日 下午2:23:00
	*/
	private void iniHasMap(){
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_WORKFLOWACTIVITY;
		//工程商圈定单据审批、报销系统 单据审批、考勤单据服务地址
		if("19".equals(FSystemType)){
			oldHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "OldWorkFlowActivity");
		}
		
		//新版工作流平台单据服务地址
		if("18".equals(FSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "NewWorkFlowActivity");
		}
		
		//HR版工作流平台单据服务地址
		if("38".equals(FSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "HrWorkFlowActivity");
		}
		
		//报销版工作流平台单据服务地址
		if("8".equals(FSystemType)){
			newHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "EpWorkFlowActivity");
		}
	}
	
	/**
	 * @ClassName WorkFlowBeenAsyncClient
	 * @Description 异步加载审批记录数据
	 * @author 21291
	 * @date 2014年5月12日 上午10:13:59
	 */
	private class WorkFlowBeenAsyncClient extends AsyncTask<String, Integer, List<WorkFlowInfo>> {	
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // 显示等待框
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<WorkFlowInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // 销毁等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<WorkFlowInfo> doInBackground(String... params) {
			return getListByPost(params[0]);
		}
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 通过POST方式获取数据源
	* @param @param serviceUrl 服务地址
	* @param @return     
	* @return List<WorkFlowInfo>    
	* @throws 
	* @author 21291
	* @date 2014年5月12日 上午10:14:14
	*/
	private List<WorkFlowInfo> getListByPost(String serviceUrl) {
		// 参数实体-仓储类
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		// 参数实体-参数类
		SqlParametersInfo sqlParameters= SqlParametersInfo.getSqlParametersInfo();
		WorkFlowParam(repository,sqlParameters);
		workFlowBusiness.setServiceUrl(serviceUrl);
		return workFlowBusiness.getWorkFlowInfo(repository, sqlParameters);
	}

	/** 
	* @Title: WorkFlowParam 
	* @Description: 工作流审批记录参数配置
	* @param @param repository 仓储实体
	* @param @param sqlParameters sql参数类     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月12日 上午10:14:28
	*/
	private void WorkFlowParam(RepositoryInfo repository,SqlParametersInfo sqlParameters) {
		//默认是旧版工作流配置信息
		HashMap<String, String> hashMap=oldHashMap;
		if("18".equals(FSystemType) || "38".equals(FSystemType) || "8".equals(FSystemType))
		{
			hashMap=newHashMap;
		}		
		// 参数值	
		repository.setClassType(hashMap.get("ClassType"));
		repository.setIsTest(hashMap.get("IsTest"));
		repository.setServiceName(hashMap.get("ServiceName"));
		repository.setServiceType(hashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(hashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(hashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);
	
		sqlParameters.setParameterCount(3);
		sqlParameters.setParameter1(FSystemType);
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
	private void renderListView(List<WorkFlowInfo> arrayList) {
		if(arrayList.size()==0){ //如果获取不到数据
			UIHelper.ToastMessage(WorkFlowBeenActivity.this, R.string.workflow_netparseerror);		
			return;
		}
		// 实例化ListView
		mListView = (ListView) findViewById(R.id.workflow_been_listView);
		// 使用数据集构造adapter对象
		WorkFlowAdapter adapter = new WorkFlowAdapter(this, arrayList,R.layout.workflowlayout);
		// 设置ListView的adapter
		mListView.setAdapter(adapter);
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
