package com.dahuatech.app.ui.develop.hour;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.develophour.DHListInfo;
import com.dahuatech.app.bean.develophour.DHListParamInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DateHelper;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.ISpinnerListener;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.DHWeekSpinnerDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName DHListActivity
 * @Description 研发工时列表
 * @author 21291
 * @date 2014年10月21日 下午5:34:18
 */
public class DHListActivity extends MenuActivity implements OnRefreshListener2<ListView> {
	private static DHListActivity mInstance;				
	
	private EditText searchEditText;						//搜索文本框
	private ImageButton searchImage;						//搜索按钮
	private PullToRefreshListView mPullRefreshListView;		//列表控件
	private ProgressDialog dialog;      					//弹出框
	
	private DevelopHourBusiness dBusiness;					//业务逻辑类
	private List<DHListInfo> dArrayList;					//数据源
	private DHListAdapter dAdapter;							//适配器类
	
	private String fItemNumber;  							//员工号
	private String serviceUrl;  							//服务地址
	private AppContext appContext; 							//全局Context

	private Calendar cal;									//日期类
	private int fCurrentYear;								//当前年份
	private int fWeekIndex;									//周次
	private int fCurrentWeekIndex;							//当前周次
	
	public static DHListActivity getInstance() {
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.dh_list);
		
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
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHLISTACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initListView();
		setListener();
		new getListAsync().execute(fWeekIndex);	
		sendLogs();	//发送日志信息进行统计
	}
	
	/** 
	* @Title: initListView 
	* @Description: 初始化列表页面控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月23日 下午3:20:57
	*/
	private void initListView(){
		searchEditText=(EditText)findViewById(R.id.dh_list_searchEditText);
		searchEditText.setInputType(InputType.TYPE_NULL);
		searchImage=(ImageButton)findViewById(R.id.dh_list_searchImageButton);
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.dh_list_PullToRefreshListView);
	
		//隐藏软件键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHListActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness","");
	    dArrayList=new ArrayList<DHListInfo>();
	
	    cal = new GregorianCalendar(); 
	    fCurrentYear=cal.get(Calendar.YEAR);  		 		 	        						//获取当前年份  							
	    fCurrentWeekIndex=fWeekIndex= DateHelper.getWeekOfYear(cal, cal.getTime());				//获取当前日期是第几周
	}
	
	/** 
	* @Title: setListener 
	* @Description: 设置事件处理
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月17日 上午8:48:10
	*/
	private void setListener(){
		searchImage.setOnClickListener(new OnClickListener() { //搜索按钮
			@Override
			public void onClick(View v) {
				showWeekSpinner();
			}
		});
	}
	
	/** 
	* @Title: showWeekSpinner 
	* @Description: 显示周次弹出框
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月24日 上午10:54:50
	*/
	private void showWeekSpinner(){
		final DHWeekSpinnerDialog dSpinnerDialog=new DHWeekSpinnerDialog(DHListActivity.this,fCurrentWeekIndex,new ISpinnerListener(){

			@Override
			public void rejectOk(int n, RejectNodeInfo reInfo) {}

			@Override
			public void adAmapOk(int n, AdAmapInfo adAmapInfo) {}	
			
			@Override
			public void cancelled() {
				searchEditText.setText("");
			}

			@Override
			public void dHWeekOk(int n,String itemText, DHWeekInfo dhWeekInfo) { //选择了一个周对象
				searchEditText.setText(itemText);
				fCurrentYear=dhWeekInfo.getFYear();
				fWeekIndex=dhWeekInfo.getFIndex();
				dArrayList.clear();
				new getListAsync().execute(fWeekIndex);	
			}

		
		});
		dSpinnerDialog.setTitle(getResources().getString(R.string.spinner_week_prompt));
		dSpinnerDialog.setSpinnerOk(getResources().getString(R.string.spinner_sure));
		dSpinnerDialog.setSpinnerCancle(getResources().getString(R.string.spinner_cancle));
		dSpinnerDialog.show();	
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description 异步获取实体集合信息
	 * @author 21291
	 * @date 2014年10月23日 下午4:00:28
	 */
	private class getListAsync extends AsyncTask<Integer, Void, List<DHListInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<DHListInfo> doInBackground(Integer... params) {
			return getListByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHListInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 获取实体集合信息
	* @param @param fWeekIndex
	* @param @return     
	* @return List<DHListInfo>    
	* @throws 
	* @author 21291
	* @date 2014年10月24日 下午2:18:52
	*/
	private List<DHListInfo> getListByPost(final Integer fWeekIndex){
		DHListParamInfo dhListParamInfo=DHListParamInfo.getDHListParamInfo();
		dhListParamInfo.setFItemNumber(fItemNumber);
		dhListParamInfo.setFWeekIndex(fWeekIndex);
		dhListParamInfo.setFYear(fCurrentYear);
		
		dBusiness.setServiceUrl(serviceUrl);
		return dBusiness.getDHList(dhListParamInfo);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: 初始化列表集合
	* @param @param listData 列表集合      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月28日 上午10:07:35
	*/
	private void renderListView(final List<DHListInfo> listData){
		dArrayList.addAll(listData);
		dAdapter = new DHListAdapter(fItemNumber,DHListActivity.this, dArrayList, R.layout.dh_list_item); //加载适配器数据
		mPullRefreshListView.setAdapter(dAdapter);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setOnRefreshListener(this);
	}
	
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		 //下拉刷新
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label));
	     onPullDownListView();
	
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		//上拉加载更多
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label));
        onPullUpListView();	
	}
	
	/** 
	* @Title: onPullDownListView 
	* @Description: 下拉刷新，刷新最新数据
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月23日 下午4:19:26
	*/
	public void onPullDownListView(){
		dArrayList.clear();
		fCurrentYear=cal.get(Calendar.YEAR);  									//当前年份
		fWeekIndex= DateHelper.getWeekOfYear(cal, cal.getTime());				//获取当前日期是第几周
		new pullDownRefreshAsync().execute(fWeekIndex);  //获取第一页最新数据	
    }
	
	/**
	 * @ClassName pullDownRefreshAsync
	 * @Description 下拉异步刷新最新信息
	 * @author 21291
	 * @date 2014年10月23日 下午4:21:32
	 */
	private class pullDownRefreshAsync extends AsyncTask<Integer, Void, List<DHListInfo>>{

		@Override
		protected List<DHListInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHListInfo> result) {
			dArrayList.addAll(result);
			dAdapter = new DHListAdapter(fItemNumber,DHListActivity.this, dArrayList, R.layout.dh_list_item); //加载适配器数据
			mPullRefreshListView.setAdapter(dAdapter);
			mPullRefreshListView.onRefreshComplete();	
			super.onPostExecute(result);
		}
	}

	/** 
	* @Title: onPullUpListView 
	* @Description: 上拉加载更多,再加载上一周数据
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月23日 下午4:21:59
	*/
	public void onPullUpListView(){
		fWeekIndex--;   //分页
		if(fWeekIndex < 1){  //说明是跳到上一年的最后一周开始
			fCurrentYear=fCurrentYear-1;  //上一年
			fWeekIndex=DateHelper.getNumWeeksForYear(cal, fCurrentYear);//获取上一年周的总数 
		}
		new pullUpRefreshAsync().execute(fWeekIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description 上拉异步加载更多
	 * @author 21291
	 * @date 2014年10月23日 下午4:22:31
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<DHListInfo>>{

		@Override
		protected List<DHListInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHListInfo> result) {
			dArrayList.addAll(result);
			dAdapter.notifyDataSetChanged();	
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case AppContext.DEVELOP_HOURS_DETAIL:
				if(resultCode==RESULT_OK){
					dArrayList=new ArrayList<DHListInfo>();
					fWeekIndex= DateHelper.getWeekOfYear(cal, cal.getTime());	//获取当前日期是第几周
					new getListAsync().execute(fWeekIndex);	
				}		
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击工时列表时，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 下午6:46:58
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_develop_hour));
		logInfo.setFActionName("access");
		logInfo.setFNote("dhlist");
		UIHelper.sendLogs(DHListActivity.this,logInfo);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHListActivity.this);
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
