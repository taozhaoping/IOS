package com.dahuatech.app.ui.meeting;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MeetingListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.meeting.MeetingListInfo;
import com.dahuatech.app.bean.meeting.MeetingListParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName MeetingListActivity
 * @Description 我的会议列表页面
 * @author 21291
 * @date 2014年9月10日 下午5:04:20
 */
public class MeetingListActivity extends MenuActivity implements OnRefreshListener2<ListView> {
	private static MeetingListActivity mInstance;
	public static MeetingListActivity getInstance() {
		return mInstance;
	}
	private Button btnMeetingList,btnMeetingLaunch;
	
	private PullToRefreshListView mPullRefreshListView; 	//列表
	private MeetingListAdapter mAdapter;					//适配器类
	private List<MeetingListInfo> mArrayList;				//数据源集合
	private MeetingListInfo mListInfo;						//单击实体对象
	private MeetingBusiness mBusiness;						//业务逻辑类
	private ProgressDialog dialog;      					//弹出框
	
	private String fItemNumber; 							//员工号
	private String serviceUrl;  							//服务地址
	private AppContext appContext; 							//全局Context
	
	private int fPageIndex=1;								//页数
	private final static int fPageSize=5;					//页大小
	private int fRecordCount;								//总的记录数
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.meeting_list);	
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		appContext=(AppContext)getApplication(); //初始化全局变量
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_MEETINGLISTACTIVITY;	//获取服务地址
		
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		prepareListData();
		setListener();
		new getListAsync().execute(fPageIndex);	
		sendLogs();	//发送日志信息进行统计
	}
	
	/** 
	* @Title: prepareListData 
	* @Description: 集合实例化准备
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 下午3:11:03
	*/
	private void prepareListData(){
		mArrayList=new ArrayList<MeetingListInfo>();
		btnMeetingList=(Button)findViewById(R.id.meeting_list_mine);
		btnMeetingLaunch=(Button)findViewById(R.id.meeting_list_launch);
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.meeting_list_PullToRefreshListView);
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MeetingListActivity.this);
		mBusiness= (MeetingBusiness)factoryBusiness.getInstance("MeetingBusiness",serviceUrl);
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
		//会议列表刷新
		btnMeetingList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showListMeeting();
				mArrayList.clear();
				fPageIndex=1;
				new getListAsync().execute(fPageIndex);
			}
		});
		
		//发起会议
		btnMeetingLaunch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showLaunchMeeting();
				UIHelper.showMeetingDetail(MeetingListActivity.this,fItemNumber,"","1");
			}
		});
	}
	
	/** 
	* @Title: showListMeeting 
	* @Description: 显示会议列表
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月19日 上午11:46:05
	*/
	private void showListMeeting(){
		btnMeetingList.setBackgroundResource (R.drawable.meeting_tabs_left_active);
		btnMeetingList.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_left_active);
		
		btnMeetingLaunch.setBackgroundResource (R.drawable.meeting_tabs_right);
		btnMeetingLaunch.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_right);	
	}
	
	/** 
	* @Title: showLaunchMeeting 
	* @Description: 显示发起会议
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月19日 上午11:46:26
	*/
	private void showLaunchMeeting(){
		btnMeetingList.setBackgroundResource (R.drawable.meeting_tabs_left);
		btnMeetingList.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_left);
		
		btnMeetingLaunch.setBackgroundResource (R.drawable.meeting_tabs_right_active);
		btnMeetingLaunch.setTextAppearance(MeetingListActivity.this,R.style.meeting_tabs_right_active);	
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description 异步获取实体集合信息
	 * @author 21291
	 * @date 2014年9月11日 下午4:30:33
	 */
	private class getListAsync extends AsyncTask<Integer, Void, List<MeetingListInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<MeetingListInfo> doInBackground(Integer... params) {
			return getListByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingListInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 获取实体集合信息
	* @param @param pageIndex
	* @param @return     
	* @return List<MeetingListInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 下午4:31:02
	*/
	private  List<MeetingListInfo> getListByPost(final Integer pageIndex){
		MeetingListParamInfo mListParamInfo=MeetingListParamInfo.getMeetingListParamInfo();
		mListParamInfo.setFItemNumber(fItemNumber);
		mListParamInfo.setFPageIndex(pageIndex);
		mListParamInfo.setFPageSize(fPageSize);
		mBusiness.setServiceUrl(serviceUrl);
		
		return mBusiness.getMeetingList(mListParamInfo);
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
	private void renderListView(final List<MeetingListInfo> listData){
		if(listData.size()==0){ //没有数据
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MeetingListActivity.this, getString(R.string.meeting_list_netparseerror),3000);
			return;
		}
		mArrayList.addAll(listData);
		fRecordCount=mArrayList.get(0).getFRecordCount();
		if(mArrayList.size() < fRecordCount){
			mPullRefreshListView.setMode(Mode.BOTH);
		}
		else {
			mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		}
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);  
		
		mAdapter = new MeetingListAdapter(MeetingListActivity.this, mArrayList, R.layout.meeting_list_item); //加载适配器数据
		mPullRefreshListView.setAdapter(mAdapter);
		
		//子项点击事件
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(position==0) { //点击其他无效
					return;	
				}
				mAdapter.setSelectItem(position-1);
				mAdapter.notifyDataSetInvalidated(); //更新UI界面
				
				mListInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					mListInfo=(MeetingListInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.meeting_list_item);
					mListInfo=(MeetingListInfo)reLayout.getTag();
				}
				if(mListInfo==null){
					return;	
				}
				UIHelper.showMeetingDetail(MeetingListActivity.this,fItemNumber,mListInfo.getFId(),mListInfo.getFStatus());
			}
		});
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
	* @date 2014年9月11日 下午4:42:34
	*/
	public void onPullDownListView(){
		mArrayList.clear();
		fPageIndex=1;
		new pullDownRefreshAsync().execute(fPageIndex);  //获取第一页最新数据	
    }

	/**
	 * @ClassName pullDownRefreshAsync
	 * @Description 下拉异步刷新最新信息
	 * @author 21291
	 * @date 2014年9月11日 下午4:43:10
	 */
	private class pullDownRefreshAsync extends AsyncTask<Integer, Void, List<MeetingListInfo>>{

		@Override
		protected List<MeetingListInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingListInfo> result) {
			mPullRefreshListView.setMode(Mode.BOTH);
			if(result.size()==0){
				UIHelper.ToastMessage(MeetingListActivity.this, getString(R.string.meeting_list_no_refresh_records),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			mArrayList.addAll(result);
			mAdapter = new MeetingListAdapter(MeetingListActivity.this, mArrayList, R.layout.meeting_list_item); //加载适配器数据
			mPullRefreshListView.setAdapter(mAdapter);
			mPullRefreshListView.onRefreshComplete();	
			super.onPostExecute(result);
		}
	}
	
	/** 
	* @Title: onPullUpListView 
	* @Description: 上拉加载更多 再重新加载5条数据
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 下午4:51:18
	*/
	public void onPullUpListView(){
		fPageIndex++;   //分页
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description 上拉异步加载更多
	 * @author 21291
	 * @date 2014年9月11日 下午4:51:08
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<MeetingListInfo>>{

		@Override
		protected List<MeetingListInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingListInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(MeetingListActivity.this, getString(R.string.meeting_list_no_load_records),3000);
				mPullRefreshListView.onRefreshComplete();
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				return;
			}
			mArrayList.addAll(result);
			fRecordCount=mArrayList.get(0).getFRecordCount();
			if(mArrayList.size() < fRecordCount){
				mPullRefreshListView.setMode(Mode.BOTH);
			}
			else {
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
			}
			mAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	// 从会议详情页面,回调方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.MEETING_DETAIL_INFO:  //会议详情
				if(resultCode==RESULT_OK){
					showListMeeting();
					fPageIndex=1;
					mArrayList.clear();
					new getListAsync().execute(fPageIndex);
				}			
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击我的会议时，发送日志记录到服务器
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
		logInfo.setFModuleName(getResources().getString(R.string.log_mymeeting));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(MeetingListActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(MeetingListActivity.this);
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
		if(mAdapter!=null && mArrayList.size() > 0){
			mAdapter.refreshView(mArrayList);
		}
	}

}
