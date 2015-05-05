package com.dahuatech.app.ui.develop.hour;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHProjectAdapter;
import com.dahuatech.app.bean.develophour.DHProjectInfo;
import com.dahuatech.app.bean.develophour.DHProjectParamInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName DHProjectSearchActivity
 * @Description 项目搜索页面
 * @author 21291
 * @date 2014年10月27日 上午10:53:42
 */
public class DHProjectSearchActivity extends MenuActivity implements OnRefreshListener<ListView>{

	private EditText searchEditText;
	private ImageButton searchImageButton;
	private PullToRefreshListView mPullRefreshListView; 	//列表控件
	
	private List<DHProjectInfo> dArrayList;					//数据源
	private DbManager mDbHelper;							//数据库管理类
	private DevelopHourBusiness dBusiness;					//业务逻辑类
	private DHProjectInfo dhProjectInfo;					//点击实体
	private DHProjectAdapter dAdapter;						//适配器类	
	private ProgressDialog dialog;      					//弹出框
	
	private String fQueryText;								//查询字符串
	private int fPageIndex=1;								//页数
	private static final int fPageSize=10;					//页大小
	
	private String searchUrl;  								//服务地址
	private AppContext appContext; 							//全局Context
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//打开数据库
		
		setContentView(R.layout.dh_project_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取服务地址
		searchUrl=AppUrl.URL_API_HOST_ANDROID_DHPROJECTSEARCHACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		initView();
		setOnListener();
		new getLocalAsync().execute();
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化视图控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月30日 下午3:18:54
	*/
	private void initView(){
		searchEditText=(EditText)findViewById(R.id.dh_project_searchEditText);
		searchEditText.setFocusable(true);
		searchImageButton=(ImageButton)findViewById(R.id.dh_project_searchImageButton);
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.dh_project_search_pullToRefreshListView);
	
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHProjectSearchActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness",searchUrl);
	
		dArrayList=new ArrayList<DHProjectInfo>();
		fQueryText="";
	}
	
	/** 
	* @Title: setOnListener 
	* @Description: 监听视图事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月30日 下午5:28:53
	*/
	private void setOnListener(){
		
		searchImageButton.setOnClickListener(new OnClickListener() {  //选择项目
			
			@Override
			public void onClick(View v) {
				fQueryText=searchEditText.getText().toString();
				if(StringUtils.isEmpty(fQueryText)){
					UIHelper.ToastMessage(DHProjectSearchActivity.this, getResources().getString(R.string.dh_project_search_empty));
					return;
				}
				dArrayList.clear();
				new getServerAsync().execute(fPageIndex);
				searchEditText.setText("");
				searchEditText.setHint(getResources().getString(R.string.dh_project_search_edit));
			}
		});
	}

	/**
	 * @ClassName getLocalAsync
	 * @Description 异步获取本地项目历史记录集合
	 * @author 21291
	 * @date 2014年10月30日 下午3:30:18
	 */
	private class getLocalAsync extends AsyncTask<Void,Void,List<DHProjectInfo>>{

		// 主要是完成耗时操作
		@Override
		protected List<DHProjectInfo> doInBackground(Void... params) {
			return getLocalByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHProjectInfo> result) {
			super.onPostExecute(result);
			renderLocalList(result);
		}	
	}
	
	/** 
	* @Title: getRoomLocalByPost 
	* @Description: 获取本地会议室历史记录实体集合
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 上午10:40:09
	*/
	private List<DHProjectInfo> getLocalByPost(){
		List<DHProjectInfo> list=mDbHelper.queryDHProjectAllList();
		int length=list.size();
		if(list.size() > 0){
			DHProjectInfo dhProjectInfo=new DHProjectInfo();
			dhProjectInfo.setFProjectCode("-1");
			dhProjectInfo.setFProjectName(getResources().getString(R.string.history_record_search_clear));
			list.add(length,dhProjectInfo);
		}
		return list;
	}
	
	/** 
	* @Title: renderLocalList 
	* @Description: 初始化本地项目搜索历史记录列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月30日 下午4:35:45
	*/
	private void renderLocalList(final List<DHProjectInfo> listData){
		dArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		dAdapter=new DHProjectAdapter(DHProjectSearchActivity.this,dArrayList,R.layout.dh_project_search_item);
		mPullRefreshListView.setAdapter(dAdapter);
		
		//子项点击事件
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //点击其他无效
					return;	
				}
			
				dhProjectInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					dhProjectInfo=(DHProjectInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.dh_project_search_item);
					dhProjectInfo=(DHProjectInfo)reLayout.getTag();
				}
				if(dhProjectInfo==null){
					return;	
				}
				if("-1".equals(dhProjectInfo.getFProjectCode())){	//清除搜索记录
					mDbHelper.deleteDHProjectSearchAll();
					dArrayList.clear();
					dAdapter.refreshView();
					UIHelper.ToastMessage(DHProjectSearchActivity.this, getString(R.string.history_record_clear_success));
				}
				else {
					Intent intent = new Intent();
					intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTCODE, dhProjectInfo.getFProjectCode());
					intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTNAME, dhProjectInfo.getFProjectName());	
					setResult(RESULT_OK, intent);
					finish();
				}	
			}
		});
	}
	
	/**
	 * @ClassName getServerAsync
	 * @Description 异步获取服务器集合
	 * @author 21291
	 * @date 2014年10月30日 下午4:54:55
	 */
	private class getServerAsync extends AsyncTask<Integer,Void,List<DHProjectInfo>>{

		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 显示等待框
			dialog.show();
		}
		
		// 主要是完成耗时操作
		@Override
		protected List<DHProjectInfo> doInBackground(Integer... params) {
			return getServerByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHProjectInfo> result) {
			super.onPostExecute(result);
			renderServerList(result);
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getServerByPost 
	* @Description: 获取项目服务器实体集合
	* @param @param pageIndex
	* @param @return     
	* @return List<DHProjectInfo>    
	* @throws 
	* @author 21291
	* @date 2014年10月30日 下午4:56:33
	*/
	private  List<DHProjectInfo> getServerByPost(final int pageIndex){
		DHProjectParamInfo dParamInfo=DHProjectParamInfo.getDHProjectParamInfo();
		dParamInfo.setFQueryText(fQueryText);
		dParamInfo.setFPageIndex(pageIndex);
		dParamInfo.setFPageSize(fPageSize);
		return dBusiness.getDHProject(dParamInfo);
	}
	
	/** 
	* @Title: renderServerList 
	* @Description: 初始化项目服务器列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月30日 下午5:13:57
	*/
	private void renderServerList(final List<DHProjectInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(DHProjectSearchActivity.this, getString(R.string.dh_project_search_list_netparseerror),3000);
			return;
		}
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		dArrayList.addAll(listData);
		dAdapter=new DHProjectAdapter(DHProjectSearchActivity.this,dArrayList,R.layout.dh_project_search_item);
		mPullRefreshListView.setAdapter(dAdapter);
		
		//子项点击事件
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //点击其他无效
					return;	
				}
				
				dhProjectInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					dhProjectInfo=(DHProjectInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.dh_project_search_item);
					dhProjectInfo=(DHProjectInfo)reLayout.getTag();
				}
				if(dhProjectInfo==null){
					return;	
				}
				mDbHelper.insertDHProjectSearch(dhProjectInfo); //添加搜索记录到本地数据库中
				mDbHelper.closeSqlLite();
				Intent intent = new Intent();
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTCODE, dhProjectInfo.getFProjectCode());
				intent.putExtra(AppContext.DEVELOP_HOURS_DETAIL_BACK_FPROJECTNAME, dhProjectInfo.getFProjectName());	
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		//上拉加载更多
		mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		mPullRefreshListView.getLoadingLayoutProxy().setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label)); 
        onPullUpListView();
	}
	
	/** 
	* @Title: onPullUpListView 
	* @Description: 上拉加载更多 
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月30日 下午5:26:01
	*/
	public void onPullUpListView(){
		fPageIndex++;   //分页
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description 上拉异步加载更多
	 * @author 21291
	 * @date 2014年9月2日 上午10:05:04
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<DHProjectInfo>>{

		@Override
		protected List<DHProjectInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getServerByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHProjectInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(DHProjectSearchActivity.this, getString(R.string.dh_project_search_list_netparseerror),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			dArrayList.addAll(result);
			dAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	protected void onDestroy() {
		if(mDbHelper != null){
			mDbHelper.closeSqlLite();
		}
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
