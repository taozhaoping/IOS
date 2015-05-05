package com.dahuatech.app.ui.develop.hour;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHTypeAdapter;
import com.dahuatech.app.bean.develophour.DHTypeInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName DHTypeListActivity
 * @Description 任务类型列表
 * @author 21291
 * @date 2014年10月29日 上午10:46:25
 */
public class DHTypeListActivity extends MenuActivity {

	private ListView mListView; 					//列表控件对象
	
	private ProgressDialog dialog;      			//弹出框
	private DevelopHourBusiness dBusiness;			//业务逻辑类
	private DHTypeAdapter dAdapter;					//适配器类	
	private DHTypeInfo dhTypeInfo;					//点击实体
	private List<DHTypeInfo> dArrayList;			//数据源
	
	private String fProjectCode;					//项目编号
	private String serviceUrl;						//服务地址
	private AppContext appContext; 					//全局Context
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.dh_type_list);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
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
			fProjectCode=extras.getString(AppContext.DEVELOP_HOURS_DETAIL_PASS_PROJECTCODE);
		}
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHTYPELISTACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		initView();
		new getListAsync().execute();
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化视图控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月4日 上午10:12:41
	*/
	private void initView(){
		mListView=(ListView)findViewById(R.id.dh_type_list);
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHTypeListActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness",serviceUrl);
		dArrayList=new ArrayList<DHTypeInfo>();
	}
	
	/**
	 * @ClassName getServerAsync
	 * @Description 异步获取服务器集合
	 * @author 21291
	 * @date 2014年11月4日 上午10:30:15
	 */
	private class getListAsync extends AsyncTask<Void,Void,List<DHTypeInfo>>{
		@Override
		protected void onPreExecute() { // 表示任务执行之前的操作
			super.onPreExecute();
			dialog.show(); // 显示等待框
		}
		
		// 主要是完成耗时操作
		@Override
		protected List<DHTypeInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHTypeInfo> result) {
			super.onPostExecute(result);
			renderList(result);
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 获取任务类型服务器实体集合
	* @param @return     
	* @return List<DHTypeInfo>    
	* @throws 
	* @author 21291
	* @date 2014年11月4日 上午11:02:27
	*/
	private List<DHTypeInfo> getListByPost(){
		return dBusiness.getDHType(fProjectCode);
	}
	
	private void renderList(final List<DHTypeInfo> listData){
		if(listData.size()==0){
			UIHelper.ToastMessage(DHTypeListActivity.this, getString(R.string.dh_type_list_netparseerror),3000);
			return;
		}
		dArrayList.addAll(listData);
		dAdapter=new DHTypeAdapter(DHTypeListActivity.this,dArrayList,R.layout.dh_type_list_item);
		mListView.setAdapter(dAdapter);
		//子项点击事件
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==-1) { //点击其他无效
					return;	
				}
				
				dhTypeInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					dhTypeInfo=(DHTypeInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.dh_type_list_item);
					dhTypeInfo=(DHTypeInfo)reLayout.getTag();
				}
				if(dhTypeInfo==null){
					return;	
				}
				Intent intent = new Intent();
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FTYPEID, dhTypeInfo.getFTypeId());
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FTYPENAME, dhTypeInfo.getFTypeName());	
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		finish();
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
