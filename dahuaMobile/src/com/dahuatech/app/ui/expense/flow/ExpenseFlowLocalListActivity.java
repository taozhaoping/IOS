package com.dahuatech.app.ui.expense.flow;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.dahuatech.app.adapter.ExpenseFlowLocalAdapter;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.business.ExpenseBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ExpenseFlowLocalListActivity
 * @Description 我的流水待上传列表页面
 * @author 21291
 * @date 2014年8月27日 下午12:00:56
 */
public class ExpenseFlowLocalListActivity extends MenuActivity {	
	private ListView mListView; 	// 列表控件对象
	private List<ExpenseFlowDetailInfo> eArrayList;		//数据源
	private List<ExpenseFlowDetailInfo> sArrayList;		//被选中的item项
	private List<Integer> localRowIdList;				//被选中的item项的主键内码集合
	
	private ExpenseFlowLocalAdapter mAdapter;   		//适配器集合
	private	ExpenseFlowDetailInfo eDetailInfo;			//单击实体
	private DbManager mDbHelper;				//数据库管理类
	private ExpenseBusiness eBusiness;					//业务逻辑类
	private ProgressDialog dialog;      				//弹出框
	private ProgressDialog uploadDialog;      			//上传服务弹出框
	private Button btnBatchUpload,btnBatchDelete;       //操作按钮
	
	private String fItemNumber;  	//员工号
	private String serviceUrl;   	//上传服务器地址
	private AppContext appContext; 	//全局Context
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//打开数据库
		setContentView(R.layout.expense_flowlocal_list);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化全局变量
		appContext=(AppContext)getApplication();
		//判断是否有网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}	
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEFLOWUPLOADACTIVITY;	
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		
		//数据加载弹出框
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//数据上传弹出框
		uploadDialog = new ProgressDialog(this);
		uploadDialog.setMessage(getResources().getString(R.string.dialog_uploading));
		uploadDialog.setCancelable(false);
		
		initListData();
		new getLocalAsync().execute();
		setButtonListener();
	}
	
	/** 
	* @Title: initListData 
	* @Description: 初始化数据源集合信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 上午10:48:12
	*/
	private void initListData(){
		//业务逻辑类实例化
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseFlowLocalListActivity.this);
		eBusiness= (ExpenseBusiness)factoryBusiness.getInstance("ExpenseBusiness",serviceUrl);
		
		btnBatchUpload=(Button)findViewById(R.id.expense_flowlocal_list_batchUpload);
		btnBatchDelete=(Button)findViewById(R.id.expense_flowlocal_list_batchDelete);
		mListView=(ListView)findViewById(R.id.expense_flowlocal_list);
		eArrayList=new ArrayList<ExpenseFlowDetailInfo>();
	}
	
	/**
	 * @ClassName getLocalAsync
	 * @Description 异步获取本地待上传列表集合
	 * @author 21291
	 * @date 2014年9月9日 上午10:55:08
	 */
	private class getLocalAsync extends AsyncTask<Void,Void,List<ExpenseFlowDetailInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); 	// 显示等待框
		}
		
		// 主要是完成耗时操作
		@Override
		protected List<ExpenseFlowDetailInfo> doInBackground(Void... params) {
			return getLocalByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<ExpenseFlowDetailInfo> result) {
			super.onPostExecute(result);
			renderLocalListView(result);	
			dialog.dismiss(); 	// 销毁等待框
		}	
	}
		
	/** 
	* @Title: getLocalByPost 
	* @Description: 获取本地待上传列表集合
	* @param @return     
	* @return List<ExpenseFlowDetailInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 上午10:56:38
	*/
	private List<ExpenseFlowDetailInfo> getLocalByPost(){
		return mDbHelper.queryFlowDetailList();
	}
	
	/** 
	* @Title: renderLocalListView 
	* @Description: 加载本地待上传列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 上午11:04:23
	*/
	private void renderLocalListView(final List<ExpenseFlowDetailInfo> listData){
		if(listData.size()==0){
			UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getString(R.string.expense_flow_local_list_netparseerror),3000);
		}
		eArrayList.addAll(listData);
		mAdapter=new ExpenseFlowLocalAdapter(ExpenseFlowLocalListActivity.this,eArrayList,R.layout.expense_flowlocal_layout);
		mListView.setAdapter(mAdapter);
		mListView.setItemsCanFocus(true);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {		
				mAdapter.setSelectItem(position);
				mAdapter.notifyDataSetInvalidated();
				
				eDetailInfo=null;		
				if(view instanceof RelativeLayout){  //说明是在RelativeLayout布局下
					eDetailInfo=(ExpenseFlowDetailInfo)parent.getItemAtPosition(position);
				}
				else {
					RelativeLayout reLayout= (RelativeLayout) view.findViewById(R.id.expense_flowlocal_list_item);
					eDetailInfo=(ExpenseFlowDetailInfo)reLayout.getTag();
				}
				if(eDetailInfo==null){
					return;	
				}
				
				UIHelper.showExpenseFlowDetail(ExpenseFlowLocalListActivity.this,fItemNumber,"local",eDetailInfo);
			}
		});
	}
	
	/** 
	* @Title: setButtonListener 
	* @Description: 设置按钮点击事件处理
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月10日 上午11:25:26
	*/
	private void setButtonListener(){
		btnBatchUpload.setOnClickListener(new OnClickListener() {  //批量上传
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				uploadSelected();
				sArrayList=getSelectedList();
				if(sArrayList.size() == 0){
					UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchUpload_noselect));
				}
				else{
					localRowIdList=new ArrayList<Integer>();
					for (ExpenseFlowDetailInfo item : sArrayList) {
						localRowIdList.add(item.getFLocalId());
					}
					new localUploadAsync().execute(sArrayList);
				}
			}
		});
		
		btnBatchDelete.setOnClickListener(new OnClickListener() {  //批量删除
			
			@Override
			public void onClick(View v) {
				deleteSelected();
				sArrayList=getSelectedList();
				if(sArrayList.size() == 0){
					UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchDelete_noselect));
				}
				else{
					localRowIdList=new ArrayList<Integer>();
					for (ExpenseFlowDetailInfo item : sArrayList) {
						localRowIdList.add(item.getFLocalId());
					}
					if(mDbHelper.batchDeleteFlowDetail(localRowIdList)){
						UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchDelete_success));
					}
					else{
						UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchDelete_failure));
					}		
					// 延迟2秒刷新页面
			        new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	uploadSelected();
			            	eArrayList.clear();
							new getLocalAsync().execute();   
			            }
			        }, 2000);	
				}	
			}
		});
	}
	
	/** 
	* @Title: uploadSelected 
	* @Description: 批量上传列表选择
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 下午1:53:07
	*/
	private void uploadSelected(){
		btnBatchUpload.setBackgroundResource (R.drawable.tabs_default_left_active_white);
		btnBatchUpload.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_left_active_white);
		
		btnBatchDelete.setBackgroundResource (R.drawable.tabs_default_right_white);
		btnBatchDelete.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_right_white);
	}
	
	/** 
	* @Title: deleteSelected 
	* @Description: 批量删除选择
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 下午1:54:22
	*/
	private void deleteSelected(){
		btnBatchUpload.setBackgroundResource (R.drawable.tabs_default_left_white);
		btnBatchUpload.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_left_white);
		
		btnBatchDelete.setBackgroundResource (R.drawable.tabs_default_right_active_white);
		btnBatchDelete.setTextAppearance(ExpenseFlowLocalListActivity.this,R.style.tabs_default_right_active_white);
	}
	
	/** 
	* @Title: getSelectedList 
	* @Description: 返回被选中的item项
	* @param @return     
	* @return List<ExpenseFlowDetailInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月10日 上午11:29:48
	*/
	private List<ExpenseFlowDetailInfo> getSelectedList(){
		List<ExpenseFlowDetailInfo> selectList=new ArrayList<ExpenseFlowDetailInfo>();
		for (int i = 0; i < eArrayList.size(); i++) {
			if(mAdapter.getIsSelected().get(i)){  //说明选中了
				selectList.add(eArrayList.get(i));
			}
		}
		return selectList;
	}
	
	/**
	 * @ClassName localUploadAsync
	 * @Description 上传数据到服务器
	 * @author 21291
	 * @date 2014年9月10日 下午4:07:38
	 */
	private class localUploadAsync extends AsyncTask<List<ExpenseFlowDetailInfo>,Void,ResultMessage>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			uploadDialog.show(); 	// 显示等待框
		}
		
		// 主要是完成耗时操作
		@Override
		protected ResultMessage doInBackground(List<ExpenseFlowDetailInfo>... params) {
			return uploadServer(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showUploadResult(result);	
			uploadDialog.dismiss(); 	// 销毁等待框
		}	
	}
	
	/** 
	* @Title: uploadServer 
	* @Description: 上传流水详情记录到服务器
	* @param @param uploadList
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年9月10日 下午4:11:45
	*/
	private ResultMessage uploadServer(List<ExpenseFlowDetailInfo> uploadList){
		return eBusiness.flowBatchUpload(uploadList);
	}
	
	/** 
	* @Title: showUploadResult 
	* @Description: 更新上传流水记录UI后操作
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月10日 下午4:11:54
	*/
	private void showUploadResult(ResultMessage resultMessage){
		if(resultMessage.isIsSuccess()){  //说明上传成功	
			mDbHelper.batchUpdateFlag(localRowIdList);
			UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchUpload_success));
		}
		else{
			UIHelper.ToastMessage(ExpenseFlowLocalListActivity.this, getResources().getString(R.string.expense_flow_local_list_batchUpload_failure));
		}	
		// 延迟2秒刷新页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	uploadSelected();
            	eArrayList.clear();
				new getLocalAsync().execute();   
            }
        }, 2000);	
	}
	
	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case UIHelper.ACTIVITY_EXPENSEDETAIL_LOCAL:
				if(resultCode==RESULT_OK){
					uploadSelected();
					eArrayList.clear();
					new getLocalAsync().execute();
				}			
				break;
			default:
				break;
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
		if(mAdapter!=null && eArrayList.size() > 0){
			mAdapter.refreshView(eArrayList);
		}
	}
}
