package com.dahuatech.app.ui.expense.flow;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpenseFlowItemAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.expense.ExpenseFlowItemInfo;
import com.dahuatech.app.bean.expense.FlowSearchParamInfo;
import com.dahuatech.app.business.ExpenseFlowItemBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName ExpenseClientSearchActivity
 * @Description 流水客户列表页面
 * @author 21291
 * @date 2014年9月1日 下午5:13:49
 */
public class ExpenseClientSearchListActivity extends SherlockActivity implements SearchView.OnQueryTextListener,
SearchView.OnSuggestionListener, OnRefreshListener<ListView>{
	
	private PullToRefreshListView mPullRefreshListView; 		//列表
	private ExpenseFlowItemAdapter mAdapter;   					//适配器类
	private List<ExpenseFlowItemInfo> eArrayList;				//数据源集合
	private ExpenseFlowItemInfo eInfo;							//单击实体		 
	private List<String> mQueryList;			  				//查询客户历史记录集合
	private DbManager mDbHelper;						        //数据库管理类
	private ExpenseFlowItemBusiness eBusiness;					//业务逻辑类
	private ProgressDialog dialog;      						//弹出框
	
	private static final String[] COLUMNS = {  					//最近使用列格式
          BaseColumns._ID,
          SearchManager.SUGGEST_COLUMN_TEXT_1,
	};
	private MatrixCursor matrixCursor;							//历史记录数据源
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView;  							//搜索控件
	private AppContext appContext; 								//全局Context
	
	private String fItemNumber,fQueryStr;						//员工号、查询字符串
	private String serviceUrl;  								//服务地址
	private int fPageIndex=1;									//页数
	private final static int fPageSize=15;						//页大小

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//打开数据库
		
		setContentView(R.layout.expense_flowclient_search);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		
		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEFLOWSEARCHACTIVITY;
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		initListData(); //初始化信息
		new getClientLocalAsync().execute();
	}
	
	/** 
	* @Title: initListData 
	* @Description: 初始化准备信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月2日 上午10:08:31
	*/
	private void initListData(){
		mPullRefreshListView=(PullToRefreshListView)findViewById(R.id.expense_flowclient_search_pullToRefreshListView);
		mQueryList= new ArrayList<String>();
		eArrayList=new ArrayList<ExpenseFlowItemInfo>();
		fQueryStr="";
		
		// 发送这个消息到消息队列中
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseClientSearchListActivity.this);
		eBusiness= (ExpenseFlowItemBusiness)factoryBusiness.getInstance("ExpenseFlowItemBusiness",serviceUrl);
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
	* @date 2014年9月1日 下午2:38:30
	*/
	private void setupSearchView(MenuItem searchItem){
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    if(null!=searchManager){
	    	mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    mSearchView.setIconifiedByDefault(false);
	    mSearchView.setIconified(false);
	    mSearchView.setSubmitButtonEnabled(true);
	    mSearchView.setQueryHint(getResources().getString(R.string.expense_flow_client_list_search));
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
		eArrayList.clear();
		new getClientSearchAsync().execute(fPageIndex);
	    return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr=query;
		fPageIndex=1;
		eArrayList.clear();
		new getClientSearchAsync().execute(fPageIndex);
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
	* @date 2014年9月2日 下午3:02:41
	*/
	private void showSuggestions(String queryStr){
		if(!StringUtils.isEmpty(queryStr)){
			mQueryList=mDbHelper.queryClientList(queryStr);
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
	 * @ClassName getClientLocalAsync
	 * @Description 获取本地客户历史记录集合
	 * @author 21291
	 * @date 2014年9月2日 下午5:18:58
	 */
	private class getClientLocalAsync extends AsyncTask<Void,Void,List<ExpenseFlowItemInfo>>{

		// 主要是完成耗时操作
		@Override
		protected List<ExpenseFlowItemInfo> doInBackground(Void... params) {
			return getClientLocalByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpenseFlowItemInfo> result) {
			super.onPostExecute(result);
			renderClientLocalListView(result);
		}	
	}
	
	/** 
	* @Title: getLocalByPost 
	* @Description: 获取本地客户历史记录实体集合
	* @param @return     
	* @return List<ExpenseFlowItemInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月2日 下午5:21:21
	*/
	private  List<ExpenseFlowItemInfo> getClientLocalByPost(){
		List<ExpenseFlowItemInfo> list=mDbHelper.queryLocalList("client");
		int length=list.size();
		if(length > 0){
			ExpenseFlowItemInfo eInfo=new ExpenseFlowItemInfo();
			eInfo.setFId("");
			eInfo.setFItemName(getResources().getString(R.string.history_record_search_clear));
			list.add(length,eInfo);
		}
		return list;
	}
	
	/** 
	* @Title: renderClientLocalListView 
	* @Description: 初始化本地客户历史记录列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月2日 下午5:22:27
	*/
	private void renderClientLocalListView(final List<ExpenseFlowItemInfo> listData){
		eArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter=new ExpenseFlowItemAdapter(ExpenseClientSearchListActivity.this,eArrayList,R.layout.expense_flowsearch_layout);
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
				
				eInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					eInfo=(ExpenseFlowItemInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.expense_flowsearch_layout);
					eInfo=(ExpenseFlowItemInfo)reLayout.getTag();
				}
				if(eInfo==null){
					return;	
				}
				if(StringUtils.isEmpty(eInfo.getFId())){	//清除搜索记录
					mDbHelper.deleteClientSearchAll();
					eArrayList.clear();
					mAdapter.refreshView();
					UIHelper.ToastMessage(ExpenseClientSearchListActivity.this, getResources().getString(R.string.history_record_clear_success));
				}
				else {
					mDbHelper.closeSqlLite();
					Intent intent = new Intent();
					intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID, eInfo.getFId());
					intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME, eInfo.getFItemName());	
					setResult(RESULT_OK, intent);
					finish();
				}	
			}
		});
	}
	
	/**
	 * @ClassName getClientSearchAsync
	 * @Description 异步获取客户服务器实体集合信息
	 * @author 21291
	 * @date 2014年9月1日 下午7:53:46
	 */
	private class getClientSearchAsync extends AsyncTask<Integer, Void, List<ExpenseFlowItemInfo>>{

		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}
		
		// 主要是完成耗时操作
		@Override
		protected List<ExpenseFlowItemInfo> doInBackground(Integer... params) {
			return getClientListByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpenseFlowItemInfo> result) {
			super.onPostExecute(result);
			renderClientListView(result);
			dialog.dismiss(); 	// 销毁等待框
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description: 获取客户服务器实体集合业务逻辑操作
	* @param @param pageIndex
	* @param @param fQueryStr
	* @param @return     
	* @return List<ExpenseFlowItemInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月1日 下午7:58:50
	*/
	private  List<ExpenseFlowItemInfo> getClientListByPost(final int pageIndex){
		FlowSearchParamInfo fSearchParamInfo=FlowSearchParamInfo.getFlowSearchParamInfo();
		fSearchParamInfo.setFItemNumber(fItemNumber);
		fSearchParamInfo.setFQueryItem(fQueryStr);
		fSearchParamInfo.setfQueryType("client");
		fSearchParamInfo.setFPageIndex(pageIndex);
		fSearchParamInfo.setFPageSize(fPageSize);
		eBusiness.setServiceUrl(serviceUrl);
		return eBusiness.getExpenseFlowItemList(fSearchParamInfo);
	}
	
	/** 
	* @Title: renderListView 
	* @Description: 初始化客户服务器列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月1日 下午8:03:50
	*/
	private void renderClientListView(final List<ExpenseFlowItemInfo> listData){
		if(listData.size()==0){
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(ExpenseClientSearchListActivity.this, getString(R.string.expense_flow_client_list_netparseerror),3000);
			return;
		}
		mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);
		
		eArrayList.addAll(listData);
		mAdapter=new ExpenseFlowItemAdapter(ExpenseClientSearchListActivity.this,eArrayList,R.layout.expense_flowsearch_layout);
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
				
				eInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在LinearLayout布局下
					eInfo=(ExpenseFlowItemInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.expense_flowsearch_layout);
					eInfo=(ExpenseFlowItemInfo)reLayout.getTag();
				}
				if(eInfo==null){
					return;	
				}
				mDbHelper.insertClientSearch(eInfo);  //添加搜索记录到本地数据库中
				Intent intent = new Intent();
				intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID, eInfo.getFId());
				intent.putExtra(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME, eInfo.getFItemName());	
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
	* @Description:  上拉加载更多 再重新加载10条数据
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月2日 上午9:55:33
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
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<ExpenseFlowItemInfo>>{

		@Override
		protected List<ExpenseFlowItemInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getClientListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpenseFlowItemInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(ExpenseClientSearchListActivity.this, getString(R.string.expense_flow_client_list_netparseerror),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			eArrayList.addAll(result);
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
