package com.dahuatech.app.ui.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.TaskListAdapter;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.StorageParameterInfo;
import com.dahuatech.app.bean.mytask.TaskInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.TaskListBusiness;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.XListView;
import com.dahuatech.app.widget.XListView.IXListViewListener;

/**
 * @ClassName TaskListActivity
 * @Description 任务Activity类
 * @author 21291
 * @date 2014年4月23日 上午10:16:54
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class TaskListActivity extends MenuActivity implements IXListViewListener {
	private static TaskListActivity mInstance;

	private ImageButton searchImageButton;
	private Button btnList,btnHistory;
	private EditText searchEditText;
	private XListView mListView; 	// 列表控件对象
	private ProgressDialog dialog;	// 进程框
	
	private Handler mHandler; // 在主线程中
	// 初始化一个线程池，固定10个线程
	private ExecutorService executorService; 
	private static int pendAppStart = 1; //  待审批记录起始页数
	private static int passAppStart=1;	 // 已经审批起始页数
	private List<TaskInfo> tArrayList; // 数据源集合
	private TaskListAdapter mAdapter; // 适配器
	
	private String serviceUrl; // 服务地址
	private HashMap<String, String> hashMap; // 配置信息
	
	private TaskInfo task;   		//传递的参数类
	private String fSearchText;		// 搜索文本
	private String fItemNumber;     // 员工号
	private String fStatus="0";		// 默认待审批记录，0-待审批，1-已审批
	private int fTotalCount=0;		// 分页总记录数 包括待审批和已经审批记录
	
	private AppContext appContext;// 全局Context
	private SharedPreferences sp;  //首选项配置文件
	
	public static TaskListActivity getInstance(){
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.tasklist);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//隐藏键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
		//初始化数据集合
		tArrayList = new ArrayList<TaskInfo>();
		//初始化全局变量
		appContext = (AppContext)getApplication();
		//网络连接
		if(!appContext.isNetworkConnected()){
			tArrayList.clear();
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		sp=getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);
		executorService = Executors.newFixedThreadPool(10);

		// 获取我的任务配置信息
		hashMap = ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "TaskListActivity");
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_TASKLISTACTIVITY;	
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);			
			//设置配置信息 为了搜素框用
			sp.edit().putString(AppContext.FSTATUS_KEY, fStatus).commit();
		}
		
		// 声明一个等待框以提示用户等待
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		new TaskListAsync().execute(serviceUrl, String.valueOf(pendAppStart), AppContext.PAGE_SIZE);	
		
		//待审批事件
		btnList = (Button) findViewById(R.id.items_imgbtnList);
		btnList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnList.setBackgroundResource (R.drawable.tabs_active);
				btnList.setTextAppearance(TaskListActivity.this,R.style.tabs_active);
				btnHistory.setBackgroundResource (R.drawable.tabs_default_right);
				btnHistory.setTextAppearance(TaskListActivity.this,R.style.tabs_default_right);
				tArrayList.clear();		
				fStatus="0";
				searchEditText.setText("");
				fSearchText="";
				pendAppStart=1;
				new TaskListAsync().execute(serviceUrl, String.valueOf(pendAppStart), AppContext.PAGE_SIZE);	
			}
		});
		
		//已审批事件
		btnHistory = (Button) findViewById(R.id.items_imgbtnHistory);
		btnHistory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				btnHistory.setBackgroundResource (R.drawable.tabs_active);
				btnHistory.setTextAppearance(TaskListActivity.this,R.style.tabs_active);
				btnList.setBackgroundResource (R.drawable.tabs_default_left);
				btnList.setTextAppearance(TaskListActivity.this,R.style.tabs_default_left);
				tArrayList.clear();
				fStatus="1";
				searchEditText.setText("");
				fSearchText="";
				passAppStart=1;
				new TaskListAsync().execute(serviceUrl, String.valueOf(passAppStart), AppContext.PAGE_SIZE);	
			}
		});		
		
		searchEditText=(EditText) findViewById(R.id.tasklist_searchEditText);
		searchEditText.setFocusableInTouchMode(true);
		searchEditText.requestFocus();
		
		//搜索事件
		searchImageButton=(ImageButton)findViewById(R.id.tasklist_searchImageButton);
		searchImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {			
				InputMethodManager imm = (InputMethodManager) getSystemService(TaskListActivity.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);				
				fSearchText=searchEditText.getText().toString();
				if(!StringUtils.isEmpty(fSearchText)){  //如果不为空
					imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
					tArrayList.clear();
					if("0".equals(fStatus)){  //说明是待审批记录搜索
						pendAppStart=1;
						new TaskListAsync().execute(serviceUrl, String.valueOf(pendAppStart), AppContext.PAGE_SIZE);	
					}
					else{ //说明是已审批记录
						passAppStart=1;
						new TaskListAsync().execute(serviceUrl, String.valueOf(passAppStart), AppContext.PAGE_SIZE);	
					}
				}
			}
		});
		
		//临时的解决方案  建议thread/asynctask
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		sendLogs();	//发送日志信息进行统计
	}
	
	/**
	 * @ClassName TaskListAsync
	 * @Description 异步执行任务,获取数据结果集
	 * @author 21291
	 * @date 2014年4月23日 下午3:15:54
	 */
	private class TaskListAsync extends AsyncTask<String, Void, List<TaskInfo>> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 显示等待框
			dialog.show();
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<TaskInfo> result) {
			super.onPostExecute(result);
			renderListView(result);
			// 销毁等待框
			dialog.dismiss();
		}

		// 主要是完成耗时操作
		@Override
		protected List<TaskInfo> doInBackground(String... params) {
			return getListByPost(params[0], params[1], params[2]);
		}
	}
	
	/**
	 * @Title: renderListView
	 * @Description: 加载界面数据并初始化
	 * @param @param arrayList 程序集
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年4月8日 下午2:59:58
	 */
	private void renderListView(List<TaskInfo> arrayList) {
		// 实例化XListView
		mListView = (XListView) findViewById(R.id.tasklist_xListView);
		if(arrayList.size()==0){ //如果获取不到数据
			if("0".equals(fStatus)){
				UIHelper.ToastMessage(TaskListActivity.this, getString(R.string.tasklist_list_netparseerror),3000);
			}
			else {
				UIHelper.ToastMessage(TaskListActivity.this, getString(R.string.tasklist_history_netparseerror),3000);
			}
			fTotalCount=0;
			mListView.setAdapter(null);
			mListView.setXListViewListener(TaskListActivity.this);
			if(!mListView.ismPullLoading()){
				mListView.setmPullLoading(true);
			}
			mListView.stopLoadFinish();	
			return;
		}
		fTotalCount=arrayList.get(0).getFTotalCount();
		mListView.setPullLoadEnable(true);
		tArrayList.addAll(arrayList);
		
		// 使用数据集构造adapter对象
		mAdapter = new TaskListAdapter(TaskListActivity.this, tArrayList,R.layout.tasklistlayout,fStatus);
		// 设置XListView的adapter
		mListView.setAdapter(mAdapter);
		mListView.setXListViewListener(TaskListActivity.this);
		// 实例化 handler
		mHandler = new Handler();
	
		//每个项目点击事件
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id){
				if(position==0)  //点击其他无效
					return;				
				task=null;
				//判断视图类型是否TextView
				if(view instanceof RelativeLayout){ //是
					task=(TaskInfo) parent.getItemAtPosition(position);
				}
				else { //否
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.tasklistlayout_tablelayout);
					task=(TaskInfo) reLayout.getTag();
				}
				if(task==null)
					return;					
				UIHelper.showTaskDetail(TaskListActivity.this,task,fStatus); // 跳转
			}
		});
	}
	
	/**
	 * @Title: getListByPost
	 * @Description: 通过POST方式获取任务数据源
	 * @param @param serviceUrl 服务地址
	 * @param @param pageIndex 页码
	 * @param @param pageSize 页面显示大小
	 * @param @return
	 * @return ArrayList<TaskInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年4月9日 下午2:25:39
	 */
	private List<TaskInfo> getListByPost(String serviceUrl,String pageIndex, String pageSize) {
		// 参数实体-仓储类
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		// 参数实体-存储过程参数类
		List<StorageParameterInfo> storageParameters = new ArrayList<StorageParameterInfo>();
		// 我的任务列表参数配置
		taskListParam(repository, storageParameters, pageIndex, pageSize);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(TaskListActivity.this);
		TaskListBusiness taskListBusiness= (TaskListBusiness)factoryBusiness.getInstance("TaskListBusiness",serviceUrl);
		return taskListBusiness.getTaskList(repository, storageParameters);
	}

	/** 
	* @Title: taskListParam 
	* @Description: 我的任务列表参数配置
	* @param @param repository 仓储实体
	* @param @param StorageParameter 存储过程实体
	* @param @param pageIndex 页码
	* @param @param pageSize 页数
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月25日 上午9:18:52
	*/
	private void taskListParam(RepositoryInfo repository,List<StorageParameterInfo> StorageParameter, String pageIndex,String pageSize) {
		// 参数值
		String commTblName = "(SELECT a.FMobileOffice,a.FID AS FMenuID,b.* FROM T_Menu AS a LEFT JOIN t_task AS b ON a.FSystemType=b.FSystemType AND a.FClassTypeID=b.FClassTypeID) AS t";
		String commSelectFieldName = "FID,FMenuID,FBillID,FTitle,FSender,FSendTime,FClassTypeID,FClassTypeName,FSystemType,null as FTotalCount";
		String commStrWhere = "t.FMobileOffice=1 AND t.FStatus="+fStatus+" AND t.FReciever='"+fItemNumber+"'"; //正式字符串
//		String commStrWhere = "t.FStatus="+fStatus+" AND t.FReciever='"+fItemNumber+"'"; //测试字符串
		if(!StringUtils.isEmpty(fSearchText)){
			/*2015-04-07  陶照平  搜索添加单据类型搜索*/
			commStrWhere = commStrWhere + " and ((FTitle is not null  and t.FTitle like '%"+fSearchText+"%') ";
			commStrWhere = commStrWhere + " or ( FClassTypeName is not null and FClassTypeName like '%"+fSearchText+"%'))";
		}	
		String commOrderFieldName = "FID";

		repository.setClassType(hashMap.get("ClassType"));
		repository.setIsTest(hashMap.get("IsTest"));
		repository.setServiceName(hashMap.get("ServiceName"));
		repository.setServiceType(hashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(hashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(hashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);

		StorageParameter.add(new StorageParameterInfo("tblName", commTblName,"varchar", 1000, false));
		StorageParameter.add(new StorageParameterInfo("SelectFieldName",commSelectFieldName, "varchar", 4000, false));
		StorageParameter.add(new StorageParameterInfo("strWhere", commStrWhere,"varchar", 4000, false));
		StorageParameter.add(new StorageParameterInfo("OrderFieldName",commOrderFieldName, "varchar", 255, false));
		StorageParameter.add(new StorageParameterInfo("PageSize", pageSize,"int", 4, false));
		StorageParameter.add(new StorageParameterInfo("PageIndex", pageIndex,"int", 4, false));
		StorageParameter.add(new StorageParameterInfo("ReturnRowCount", "0","int", 4, true));
		StorageParameter.add(new StorageParameterInfo("OrderType", "true","bit", 2, false));
	}
	
	/**
	* <p>Title: onRefresh</p> 
	* <p>Description: 重新上拉刷新方法</p>  
	* @see com.dahuatech.app.widget.XListView.IXListViewListener#onRefresh() 
	*/
	@Override
	public void onRefresh() {
		executorService.submit(new Runnable() {
			public void run() {
				try {
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							tArrayList.clear();
							geneItems(1);
							mAdapter = new TaskListAdapter(TaskListActivity.this,tArrayList,R.layout.tasklistlayout,fStatus);
							mListView.setAdapter(mAdapter);
							if(!mListView.ismPullLoading()){
								mListView.setmPullLoading(true);
							}
							onLoad();
						}
					}, 1000);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	/** 
	* <p>Title: onLoadMore</p> 
	* <p>Description: 重新加载更多方法</p>  
	* @see com.dahuatech.app.widget.XListView.IXListViewListener#onLoadMore() 
	*/
	@Override
	public void onLoadMore() {
		executorService.submit(new Runnable() {
			public void run() {
				try {
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if("0".equals(fStatus)){
								geneItems(++pendAppStart);
							}
							else {
								geneItems(++passAppStart);
							}						
							mAdapter.notifyDataSetChanged();
							onLoad();
						}
					}, 1000);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	private void geneItems(int pageIndex) {
		if(tArrayList.size() < fTotalCount){
			tArrayList.addAll(getListByPost(serviceUrl, String.valueOf(pageIndex), AppContext.PAGE_SIZE));
		}else {
			if("0".equals(fStatus))
				pendAppStart=1;
			else
				passAppStart=1;	
		}			
	}

	/** 
	* @Title: onLoad 
	* @Description: 显示字符串
	* @param @param showStr     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月25日 下午2:45:40
	*/
	private void onLoad() {
		mListView.stopRefresh();
		if(tArrayList.size() < fTotalCount)
			mListView.stopLoadMore();
		else
			mListView.stopLoadFinish();	
		mListView.setRefreshTime("刚刚");
	}
	
	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		SharedPreferences sp= getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);	
		String fAppStatus=sp.getString(AppContext.TA_APPROVE_FSTATUS, " ");
		if(!StringUtils.isEmpty(fAppStatus)){
			sp.edit().remove(AppContext.TA_APPROVE_FSTATUS).commit();
			tArrayList.clear();
			searchEditText.setText("");
			fSearchText="";
			new TaskListAsync().execute(serviceUrl, "1", AppContext.PAGE_SIZE);	
		}
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击我的任务时，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月31日 下午1:56:47
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_mytasks));
		logInfo.setFActionName("access");
		logInfo.setFNote("note");
		UIHelper.sendLogs(TaskListActivity.this,logInfo);
	}

	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(TaskListActivity.this);
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
