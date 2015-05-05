package com.dahuatech.app.ui.expense.flow;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpandableListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.expense.ExpandableInfo;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.bean.expense.FlowParamInfo;
import com.dahuatech.app.business.ExpandableBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuExapandableListActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

/**
 * @ClassName ExpenseFlowListActivity
 * @Description 我的流水服务器列表主页
 * @author 21291
 * @date 2014年8月21日 下午3:14:04
 */
public class ExpenseFlowListActivity extends MenuExapandableListActivity implements OnRefreshListener2<ExpandableListView> {
 
	private Button btnSubmit,btnUpload,btnAdd;  //操作按钮
	private EditText searchEdit;				//搜索文本
	private ImageButton searchImageBtn;			//搜索按钮 
	
	private PullToRefreshExpandableListView mPullRefreshListView;
	private ExpandableListAdapter listAdapter;  //适配器类
	private List<String> listDataHeader;        //头部集合
	private HashMap<String, List<ExpenseFlowDetailInfo>> listDataChild;  //子集结合
	private ExpenseFlowDetailInfo efDetailInfo;	//流水详情实体类
	private ExpandableBusiness eBusiness;		//业务逻辑类
	private ProgressDialog dialog;      		//弹出框
	
	private String fItemNumber,fQueryText;  	//员工号、查询文本值
	private String serviceUrl;  				//服务地址
	private AppContext appContext; 				//全局Context
	
	private int fPageIndex=1;					//页数
	private final static int fPageSize=5;		//页大小
	
	private String fCurrentDate;				//当前日期，同时为分页取最近5条的最早一个消费日期
	private Calendar cal;						//日期类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_flowlist);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		appContext=(AppContext)getApplication(); //初始化全局变量
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEFLOWLISTACTIVITY;	
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
			
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);	
		
		//日期
		cal = Calendar.getInstance();	
		fCurrentDate=StringUtils.toShortDateString(cal.getTime());  //设置当前日期
		
		prepareListData();  //实例化集合变量	
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
	* @date 2014年8月27日 下午1:54:27
	*/
	private void prepareListData(){
		listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ExpenseFlowDetailInfo>>();
    	//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseFlowListActivity.this);
		eBusiness= (ExpandableBusiness)factoryBusiness.getInstance("ExpandableBusiness",serviceUrl);
		
		//初始化控件
		btnSubmit=(Button)findViewById(R.id.expense_flowlist_btnSubmit);
		btnUpload=(Button)findViewById(R.id.expense_flowlist_btnUpload);
		btnAdd=(Button)findViewById(R.id.expense_flowlist_btnAdd);
		searchEdit=(EditText)findViewById(R.id.expense_flowlist_searchEditText);
		searchImageBtn=(ImageButton)findViewById(R.id.expense_flowlist_searchImageButton);	
        mPullRefreshListView = (PullToRefreshExpandableListView) findViewById(R.id.expense_flowlist_expand);	
        fQueryText="";
	}
	
	/** 
	* @Title: setListener 
	* @Description: 设置监听事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 下午1:48:27
	*/
	private void setListener(){
		btnSubmit.setOnClickListener(new OnClickListener() {  //已上传事件
			
			@Override
			public void onClick(View v) {
				serverSelected();
				fPageIndex=1;
				listDataHeader.clear(); 
				listDataChild.clear();
				new getListAsync().execute(fPageIndex);
			}
		});
		
		btnUpload.setOnClickListener(new OnClickListener() {  //待上传事件
			
			@Override
			public void onClick(View v) {
				localSelected();
				UIHelper.showExpenseFlowLocalList(ExpenseFlowListActivity.this, fItemNumber);
			}
		});

		btnAdd.setOnClickListener(new OnClickListener() {  //新增事件
	
			@Override
			public void onClick(View v) {
				addSelected();
				UIHelper.showExpenseFlowDetail(ExpenseFlowListActivity.this,fItemNumber,"");
			}
		});
		
		searchEdit.setOnClickListener(new OnClickListener() {  //文本框点击事件
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(ExpenseFlowListActivity.this ,searchDateListener,
						cal.get(Calendar.YEAR ),   
			            cal.get(Calendar.MONTH ),   
			            cal.get(Calendar.DAY_OF_MONTH )).show();   
			}
		});
		
		searchImageBtn.setOnClickListener(new OnClickListener() {  //搜索按钮点击事件
			
			@Override
			public void onClick(View v) {
				if(!"消费时间".equals(searchEdit.getText().toString())){
					fQueryText=searchEdit.getText().toString();
				}
				else {
					fQueryText="";
				}
				fPageIndex=1;
				listDataHeader.clear(); 
				listDataChild.clear();
				new getListAsync().execute(fPageIndex);
			}
		});
	}

	//消费时间搜索监听器
	private DatePickerDialog.OnDateSetListener searchDateListener = new DatePickerDialog.OnDateSetListener(){		
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);
			  searchEdit.setText(StringUtils.toShortDateString(cal.getTime()));
		}
	};
	
	/** 
	* @Title: serverSelected 
	* @Description: 已上传的列表
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 上午10:41:43
	*/
	private void serverSelected(){
		btnSubmit.setBackgroundResource (R.drawable.tabs_default_left_active_white);
		btnSubmit.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_left_active_white);
		
		btnUpload.setBackgroundResource(R.drawable.tabs_default_expense_flow_white);
		btnUpload.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_white);
		
		btnAdd.setBackgroundResource (R.drawable.tabs_default_right_white);
		btnAdd.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_right_white);
	}
	
	/** 
	* @Title: localSelected 
	* @Description: 本地列表
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 上午10:42:09
	*/
	private void localSelected(){
		btnUpload.setBackgroundResource(R.drawable.tabs_default_expense_flow_active_white);
		btnUpload.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_active_white);
		
		btnSubmit.setBackgroundResource(R.drawable.tabs_default_left_white);
		btnSubmit.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_left_white);
	
		btnAdd.setBackgroundResource (R.drawable.tabs_default_right_white);
		btnAdd.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_right_white);
	}
	
	
	/** 
	* @Title: addSelected 
	* @Description: 新增页面
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 上午10:42:45
	*/
	private void addSelected(){
		btnAdd.setBackgroundResource (R.drawable.tabs_default_right_active_white);
		btnAdd.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_right_active_white);
		
		btnSubmit.setBackgroundResource (R.drawable.tabs_default_left_white);
		btnSubmit.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_left_white);
		
		btnUpload.setBackgroundResource(R.drawable.tabs_default_expense_flow_white);
		btnUpload.setTextAppearance(ExpenseFlowListActivity.this,R.style.tabs_default_white);
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description 异步获取实体集合信息
	 * @author 21291
	 * @date 2014年8月28日 上午10:23:58
	 */
	private class getListAsync extends AsyncTask<Integer, Void, List<ExpandableInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<ExpandableInfo> doInBackground(Integer... params) {
			return getListByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpandableInfo> result) {
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
	* @return List<ExpandableInfo>    
	* @throws 
	* @author 21291
	* @date 2014年8月28日 上午10:19:21
	*/
	private List<ExpandableInfo> getListByPost(final Integer pageIndex){
		FlowParamInfo fParamInfo=FlowParamInfo.getFlowParamInfo();
		fParamInfo.setFItemNumber(fItemNumber);
		fParamInfo.setFQueryText(fQueryText);
		fParamInfo.setFCurrentDate(fCurrentDate);
		fParamInfo.setFPageIndex(pageIndex);
		fParamInfo.setFPageSize(fPageSize);
		eBusiness.setServiceUrl(serviceUrl);
		return eBusiness.getExpandableList(fParamInfo);
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
	private void renderListView(final List<ExpandableInfo> listData){
		if(listData.size()==0){ //没有数据
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(ExpenseFlowListActivity.this, getString(R.string.expense_flow_list_netparseerror),3000);
			listDataHeader.clear();
			listDataChild.clear();
		}
		else {
			mPullRefreshListView.setMode(Mode.BOTH);
			mPullRefreshListView.getRefreshableView().setSelector(android.R.color.transparent);
			mPullRefreshListView.setOnRefreshListener(this);  
			initListData(listData); //初始化数据
		}
		listAdapter = new ExpandableListAdapter(ExpenseFlowListActivity.this, listDataHeader, listDataChild,fItemNumber); //加载适配器数据
		setListAdapter(listAdapter);
		
		ExpandableListView exlistView = mPullRefreshListView.getRefreshableView();  
		exlistView.setGroupIndicator(null); //去掉图标
		exlistView.setOnChildClickListener(new OnChildClickListener() {

	        @Override
	        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {  	
	        	listAdapter.setParentItem(groupPosition);
	        	listAdapter.setChildItem(childPosition);
	        	listAdapter.notifyDataSetChanged();
				
	        	efDetailInfo=null;		
	        	efDetailInfo=(ExpenseFlowDetailInfo)listAdapter.getChild(groupPosition,childPosition);
				if(efDetailInfo==null){
					return false;	
				}
	        	
				UIHelper.showExpenseFlowDetail(ExpenseFlowListActivity.this,fItemNumber,"server",efDetailInfo);
	            return true;
	        }
	    });
		
		exlistView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {	
				return false;
			}
	    });
		
		exlistView.setOnGroupExpandListener(new OnGroupExpandListener() {	
            public void onGroupExpand(int groupPosition) {}
        });
 
		exlistView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {}
        });
		searchEdit.setText(getResources().getString(R.string.expense_flow_list_search));
	}
	
	/** 
	* @Title: initListData 
	* @Description: 初始化数据集合
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月28日 上午10:41:52
	*/
	private void initListData(final List<ExpandableInfo> listData){
		try {
			Type listType = new TypeToken<ArrayList<ExpenseFlowDetailInfo>>(){}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray=null;
			List<ExpenseFlowDetailInfo> eFlowInfos=null;
			for (ExpandableInfo item : listData) {
				// 添加子项标题集合
				listDataHeader.add(item.getFGroupTitle());
				jsonArray= new JSONArray(item.getFSubEntrys());
				eFlowInfos=gson.fromJson(jsonArray.toString(), listType);
				listDataChild.put(item.getFGroupTitle(), eFlowInfos);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
		 //下拉刷新
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getResources().getString(R.string.pull_to_refresh_refreshing_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(getResources().getString(R.string.pull_to_refresh_pull_label));
		 mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel(getResources().getString(R.string.pull_to_refresh_release_label));
	     onPullDownListView();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
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
	* @date 2014年8月27日 下午4:09:48
	*/
	public void onPullDownListView(){
		fPageIndex=1;
		fQueryText="";
		fCurrentDate=StringUtils.toShortDateString(cal.getTime());  		//设置当前日期
		searchEdit.setText(fQueryText);
		new pullDownRefreshAsync().execute(fPageIndex);  //获取第一页最新数据	
    }
	
	/**
	 * @ClassName pullDownRefreshAsync
	 * @Description 下拉异步刷新最新信息
	 * @author 21291
	 * @date 2014年8月28日 下午3:12:44
	 */
	private class pullDownRefreshAsync extends AsyncTask<Integer, Void, List<ExpandableInfo>>{

		@Override
		protected List<ExpandableInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpandableInfo> result) {
			mPullRefreshListView.setMode(Mode.BOTH);
			if(result.size()==0){
				UIHelper.ToastMessage(ExpenseFlowListActivity.this, getString(R.string.expense_flow_list_no_refresh_records),3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			listDataHeader.clear();
	        listDataChild.clear();
			initListData(result);
			listAdapter = new ExpandableListAdapter(ExpenseFlowListActivity.this, listDataHeader, listDataChild,fItemNumber); //加载适配器数据
			setListAdapter(listAdapter);
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
	* @date 2014年8月27日 下午4:10:43
	*/
	public void onPullUpListView(){
		fPageIndex++;   //分页
		int titleSize= listDataHeader.size();
		if(titleSize > 0){
			Date date = StringUtils.toShortDate(listDataHeader.get(titleSize-1));
			fCurrentDate=StringUtils.getSpecifiedDayBefore(date);  //获取集合最后一项日期的前一天			
		}else {
			fCurrentDate=StringUtils.toShortDateString(cal.getTime());
		}
		new pullUpRefreshAsync().execute(fPageIndex); 
    }
	
	/**
	 * @ClassName pullRefreshAsync
	 * @Description 上拉异步加载更多
	 * @author 21291
	 * @date 2014年8月28日 上午11:31:13
	 */
	private class pullUpRefreshAsync extends AsyncTask<Integer, Void, List<ExpandableInfo>>{

		@Override
		protected List<ExpandableInfo> doInBackground(Integer... params) {
			// 主要是完成耗时操作
			return getListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpandableInfo> result) {
			if(result.size()==0){
				UIHelper.ToastMessage(ExpenseFlowListActivity.this, getString(R.string.expense_flow_list_no_load_records),3000);
				mPullRefreshListView.onRefreshComplete();
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
				return;
			}
			initListData(result);
			listAdapter.notifyDataSetChanged();		
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
	
	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case UIHelper.ACTIVITY_EXPENSEDETAIL_SERVER:
				if(resultCode==RESULT_OK){
					fPageIndex=1;
					listDataHeader.clear(); 
					listDataChild.clear();
					new getListAsync().execute(fPageIndex);
				}			
				break;
			default:
				break;
		}
		serverSelected();
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击流水列表时，发送日志记录到服务器
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
		logInfo.setFModuleName(getResources().getString(R.string.log_expense_flow));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(ExpenseFlowListActivity.this,logInfo);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(ExpenseFlowListActivity.this);
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
		if(listAdapter!=null && listDataChild.size() > 0){
			listAdapter.refreshView(listDataChild);
		}
	}
}
