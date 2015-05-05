package com.dahuatech.app.ui.attendance;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.AttendanceListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.attendance.AdListInfo;
import com.dahuatech.app.business.AttendanceBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName AttendanceListActivity
 * @Description 考勤记录历史列表
 * @author 21291
 * @date 2014年12月17日 下午4:04:24
 */
public class AdListActivity extends MenuActivity {
	private ListView mListView;						//列表控件
	private List<AdListInfo> mArrayList;			//数据源
	private AttendanceBusiness aBusiness;			//业务逻辑类
	private AttendanceListAdapter adAdapter;		//适配器类
	private ProgressDialog dialog;      			//弹出框

	private String fItemNumber;  					//员工号
	private String serviceUrl;  					//服务地址
	private AppContext appContext; 					//全局Context
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.attendance_list);
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
		}
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_ATTENDANCELISTACTIVITY;	//获取服务地址
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initView();														//初始化视图控件
		new getListAsync().execute();
		sendLogs();
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化视图控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午2:44:27
	*/
	private void initView(){
		mListView=(ListView)findViewById(R.id.attendance_list_listView);
		mArrayList=new ArrayList<AdListInfo>();
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(AdListActivity.this);
		aBusiness= (AttendanceBusiness)factoryBusiness.getInstance("AttendanceBusiness",serviceUrl);
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description 异步获取实体集合信息
	 * @author 21291
	 * @date 2014年12月18日 下午3:01:17
	 */
	private class getListAsync extends AsyncTask<Void, Void, List<AdListInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<AdListInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<AdListInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 获取实体集合信息
	* @param @return     
	* @return List<AdListInfo>    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午3:02:10
	*/
	private  List<AdListInfo> getListByPost(){
		return aBusiness.getAdList(fItemNumber);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: 加载视图数据
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午3:23:58
	*/
	private void renderListView(final List<AdListInfo> listData){
		if(listData.size()==0){ //没有数据
			UIHelper.ToastMessage(AdListActivity.this, getString(R.string.attendance_list_netparseerror),3000);
			return;
		}
		mArrayList.addAll(listData);
		adAdapter=new AttendanceListAdapter(AdListActivity.this,mArrayList,R.layout.attendance_list_item);//加载适配器数据
		mListView.setAdapter(adAdapter);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击我的考勤时，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年12月18日 下午3:34:49
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_attendance));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(AdListActivity.this,logInfo);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		commonMenu.setContext(AdListActivity.this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(adAdapter!=null && mArrayList.size() > 0){
			adAdapter.refreshView(mArrayList);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
