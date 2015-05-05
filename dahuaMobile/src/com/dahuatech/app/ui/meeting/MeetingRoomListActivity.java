package com.dahuatech.app.ui.meeting;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MeetingRoomAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.meeting.MeetingInitParamInfo;
import com.dahuatech.app.bean.meeting.MeetingRoomInfo;
import com.dahuatech.app.bean.meeting.MeetingSearchParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName MeetingRoomListActivity
 * @Description 我的会议 会议室搜索页面
 * @author 21291
 * @date 2014年9月11日 上午10:42:34
 */
public class MeetingRoomListActivity extends SherlockActivity implements SearchView.OnQueryTextListener,
SearchView.OnSuggestionListener, OnRefreshListener<ListView> {

	private PullToRefreshListView mPullRefreshListView; 	//列表
	private MeetingRoomAdapter mAdapter;					//适配器类
	private List<MeetingRoomInfo> mArrayList;				//数据源集合
	private List<String> mQueryList;			  			//查询会议室历史记录集合
	private DbManager mDbHelper;					//数据库管理类
	private MeetingBusiness mBusiness;						//业务逻辑类
	private ProgressDialog dialog;      					//弹出框
	
	private static final String[] COLUMNS = {  				//最近使用列格式
        BaseColumns._ID,
        SearchManager.SUGGEST_COLUMN_TEXT_1,
	};
	private MatrixCursor matrixCursor;						//历史记录数据源
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView;  						//搜索控件
	private AppContext appContext; 							//全局Context
		
	private String fQueryStr;								//查询字符串
	private String filterUrl,searchUrl;  					//服务地址
	private int fPageIndex=1;								//页数
	private final static int fPageSize=10;					//页大小
	private int fRecordCount;								//总的记录数
	
	private String fItemNumber; 							//员工号
	private String fSelectedDate="";						//已经选择会议日期
	private String FSelectedStart="";						//已经选择会议起始时间
	private String FSelectedEnd="";							//已经选择会议结束时间
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//打开数据库
		
		setContentView(R.layout.meeting_room_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		appContext=(AppContext)getApplication(); //初始化全局变量
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取服务地址
		filterUrl=AppUrl.URL_API_HOST_ANDROID_INITMEETINGROOMLISTACTIVITY;	
		searchUrl=AppUrl.URL_API_HOST_ANDROID_MEETINGROOMLISTACTIVITY;	
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fSelectedDate=extras.getString(AppContext.MEETING_DETAIL_SELECTEDDATE);
			FSelectedStart=extras.getString(AppContext.MEETING_DETAIL_SELECTEDSTART);
			FSelectedEnd=extras.getString(AppContext.MEETING_DETAIL_SELECTEDEND);
		}
		
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		initListData();
		new getRoomLocalAsync().execute();
	}
	
	/** 
	* @Title: initListData 
	* @Description: 初始化准备信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月11日 下午7:55:45
	*/
	private void initListData(){
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.meeting_room_list_pullToRefreshListView);
		mQueryList= new ArrayList<String>();
		mArrayList=new ArrayList<MeetingRoomInfo>();
		fQueryStr="";
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(MeetingRoomListActivity.this);
		mBusiness= (MeetingBusiness)factoryBusiness.getInstance("MeetingBusiness","");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_search, menu);	
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchItem.getActionView();
	    setupSearchView(searchItem);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				setResult(RESULT_OK, new Intent());
				finish();
				return true;		
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** 
	* @Title: setupSearchView 
	* @Description: 设置搜索控件
	* @param @param searchItem     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 上午10:34:28
	*/
	private void setupSearchView(MenuItem searchItem){
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    if(null!=searchManager){
	    	mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    mSearchView.setIconifiedByDefault(false);
	    mSearchView.setIconified(false);
	    mSearchView.setSubmitButtonEnabled(true);
	    mSearchView.setQueryHint(getResources().getString(R.string.meeting_search_room));
	    mSearchView.setOnQueryTextListener(this);
	    mSearchView.setOnSuggestionListener(this);
	}
	
	@Override
	public boolean onSuggestionSelect(int position) {
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
		fQueryStr = c.getString(c.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
		fPageIndex=1;
		mArrayList.clear();
		new getRoomSearchAsync().execute(fPageIndex);
	    return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr=query;
		fPageIndex=1;
		mArrayList.clear();
		new getRoomSearchAsync().execute(fPageIndex);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		showSuggestions(newText);
		return false;
	}
	
	/** 
	* @Title: showSuggestions 
	* @Description: 显示搜索历史记录
	* @param @param queryStr     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 下午12:05:11
	*/
	private void showSuggestions(String queryStr){
		if(!StringUtils.isEmpty(queryStr)){
			mQueryList=mDbHelper.queryRoomList(queryStr);
			if(mQueryList.size()>0){  //说明有客户搜索历史记录
				matrixCursor = new MatrixCursor(COLUMNS);
		        int i=1;
		        for (String item : mQueryList) {
		        	matrixCursor.addRow(new String[]{String.valueOf(i), item});
		            i++;
		        }
		        mSuggestionsAdapter = new SuggestionsAdapter(getSupportActionBar().getThemedContext(), matrixCursor);
			}
			else {
				mSuggestionsAdapter=null;
			}
			mSearchView.setSuggestionsAdapter(mSuggestionsAdapter);
		}	
	}
	
	/**
	 * @ClassName getRoomLocalAsync
	 * @Description 获取本地会议室历史记录集合
	 * @author 21291
	 * @date 2014年9月12日 上午10:36:22
	 */
	private class getRoomLocalAsync extends AsyncTask<Void,Void,List<MeetingRoomInfo>>{

		// 主要是完成耗时操作
		@Override
		protected List<MeetingRoomInfo> doInBackground(Void... params) {
			return getRoomLocalByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingRoomInfo> result) {
			super.onPostExecute(result);
			renderRoomLocalListView(result);
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
	private List<MeetingRoomInfo> getRoomLocalByPost(){
		List<MeetingRoomInfo> list=getFilterRoomLocal(mDbHelper.queryRoomAllList());
		int length=list.size();
		if(list.size() > 0){
			MeetingRoomInfo mRoomInfo=new MeetingRoomInfo();
			mRoomInfo.setFRoomId("-1");
			mRoomInfo.setFRoomName(getResources().getString(R.string.history_record_search_clear));
			mRoomInfo.setFType("1");
			list.add(length,mRoomInfo);
		}
		return list;
	}
	
	/** 
	* @Title: getFilterRoomLocal 
	* @Description: 本地历史记录过滤
	* @param @param localList 本地数据源
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 下午3:28:01
	*/
	private List<MeetingRoomInfo> getFilterRoomLocal(final List<MeetingRoomInfo> localList){
		List<MeetingRoomInfo> returnList;
		int length=localList.size();
		if(length > 0){  //说明有历史记录
			StringBuffer sbBuffer = new StringBuffer();	
			for (int i = 0; i < length; i++) {
				if(i==length-1){
					sbBuffer.append(localList.get(i).getFRoomId());
				}
				else{
					sbBuffer.append(localList.get(i).getFRoomId()).append(",");
				}
			}
			
			MeetingInitParamInfo mInitParamInfo=MeetingInitParamInfo.getMeetingInitParamInfo();
			mInitParamInfo.setFItemNumber(fItemNumber);
			mInitParamInfo.setFRoomIdS(sbBuffer.toString());	
			mInitParamInfo.setFSelectedDate(fSelectedDate);
			mInitParamInfo.setFSelectedStart(FSelectedStart);
			mInitParamInfo.setFSelectedEnd(FSelectedEnd);	
			
			mBusiness.setServiceUrl(filterUrl);
			returnList=mBusiness.getInitRoomList(mInitParamInfo);
		}
		else {
			returnList=new ArrayList<MeetingRoomInfo>();
		}
		return returnList;
	
	}
	
	/** 
	* @Title: renderRoomLocalListView 
	* @Description: 初始化本地会议室历史记录列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 上午10:43:15
	*/
	private void renderRoomLocalListView(final List<MeetingRoomInfo> listData){
		mArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter=new MeetingRoomAdapter(MeetingRoomListActivity.this,mArrayList,R.layout.meeting_room_list_item);
		mPullRefreshListView.setAdapter(mAdapter);
	}
	
	/**
	 * @ClassName getRoomSearchAsync
	 * @Description 异步获取会议室服务器实体集合
	 * @author 21291
	 * @date 2014年9月12日 上午11:04:24
	 */
	private class getRoomSearchAsync extends AsyncTask<Integer, Void, List<MeetingRoomInfo>>{

		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}
		
		// 主要是完成耗时操作
		@Override
		protected List<MeetingRoomInfo> doInBackground(Integer... params) {
			return getRoomListByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingRoomInfo> result) {
			super.onPostExecute(result);
			renderRoomListView(result);
			dialog.dismiss(); 	// 销毁等待框
		}	
	}
	
	/** 
	* @Title: getRoomListByPost 
	* @Description: 获取会议室服务器实体集合业务逻辑操作
	* @param @param pageIndex
	* @param @return     
	* @return List<MeetingRoomInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 上午11:08:07
	*/
	private  List<MeetingRoomInfo> getRoomListByPost(final int pageIndex){
		MeetingSearchParamInfo mSearchParamInfo=MeetingSearchParamInfo.getMeetingSearchParamInfo();
		mSearchParamInfo.setFItemNumber(fItemNumber);
		mSearchParamInfo.setFQueryText(fQueryStr);
		mSearchParamInfo.setFPageIndex(pageIndex);
		mSearchParamInfo.setFPageSize(fPageSize);
		mSearchParamInfo.setFSelectedDate(fSelectedDate);
		mSearchParamInfo.setFSelectedStart(FSelectedStart);
		mSearchParamInfo.setFSelectedEnd(FSelectedEnd);
		
		mBusiness.setServiceUrl(searchUrl);
		return mBusiness.getRoomList(mSearchParamInfo);
	}
	
	/** 
	* @Title: renderRoomListView 
	* @Description: 初始化会议室服务器列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 上午11:55:23
	*/
	private void renderRoomListView(final List<MeetingRoomInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MeetingRoomListActivity.this, getString(R.string.meeting_search_room_netparseerror),3000);
			return;
		}
		mArrayList.addAll(listData);
		fRecordCount=mArrayList.get(0).getFRecordCount();
		if(mArrayList.size() < fRecordCount){
			mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		}
		else {
			mPullRefreshListView.setMode(Mode.DISABLED);
		}
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		mAdapter=new MeetingRoomAdapter(MeetingRoomListActivity.this,mArrayList,R.layout.meeting_room_list_item);
		mPullRefreshListView.setAdapter(mAdapter);
	}
	
	/** 
	* @Title: onFTypeClick 
	* @Description: 列表项目单击事件
	* @param @param view     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 下午4:20:50
	*/
	public void onFTypeClick(View view){
		final MeetingRoomInfo mrRoomInfo = (MeetingRoomInfo) view.getTag();
		if("-1".equals(mrRoomInfo.getFRoomId())){  //说明点击的是清除历史记录
			mDbHelper.deleteRoomSearchAll();
			mArrayList.clear();
			mAdapter.refreshView();
			UIHelper.ToastMessage(MeetingRoomListActivity.this, getResources().getString(R.string.history_record_clear_success));
		}
		else{
			mDbHelper.insertRoomSearch(mrRoomInfo); 	//添加搜索记录到本地数据库中
			Intent intent = new Intent();
			intent.putExtra(AppContext.MEETING_DETAIL_ROOM_ID, mrRoomInfo.getFRoomId());
			intent.putExtra(AppContext.MEETING_DETAIL_ROOM_NAME, mrRoomInfo.getFRoomName());	
			intent.putExtra(AppContext.MEETING_DETAIL_ROOM_IP, mrRoomInfo.getFRoomIp());	
			setResult(RESULT_OK, intent);
			finish();
		}
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
	* @Description: 上拉加载更多 再重新加载10条数据
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 下午12:01:16
	*/
	public void onPullUpListView(){
		fPageIndex++;   //分页
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description 上拉异步加载更多
	 * @author 21291
	 * @date 2014年9月12日 下午12:01:44
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<MeetingRoomInfo>>{

		@Override
		protected List<MeetingRoomInfo> doInBackground(Integer... params) {
			return getRoomListByPost(params[0]); // 主要是完成耗时操作
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingRoomInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(MeetingRoomListActivity.this, getString(R.string.meeting_search_room_netparseerror),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			mArrayList.addAll(result);
			fRecordCount=mArrayList.get(0).getFRecordCount();
			if(mArrayList.size() < fRecordCount){
				mPullRefreshListView.setMode(Mode.PULL_FROM_END);
			}
			else {
				mPullRefreshListView.setMode(Mode.DISABLED);
			}
			mAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	protected void onDestroy() {
		if(mDbHelper != null){
			mDbHelper.closeSqlLite();
		}
		setResult(RESULT_OK, new Intent());
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
