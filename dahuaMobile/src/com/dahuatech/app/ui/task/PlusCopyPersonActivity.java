package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.PlusCopyPersonAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.RejectNodeBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName PlusCopyPersonActivity
 * @Description 加签/抄送人员列表Activity
 * @author 21291
 * @date 2014年9月22日 上午9:14:24
 */
public class PlusCopyPersonActivity extends SherlockActivity implements SearchView.OnQueryTextListener,
SearchView.OnSuggestionListener {
	private Button btnYetSelect,btnNotSelect,btnCheckAll,btnConfirm;
	private ListView pListView;							//列表
	private ProgressDialog dialog;      				//弹出框
	
	private PlusCopyPersonAdapter mAdapter;				//适配器类
	private List<PlusCopyPersonInfo> pArrayList;		//查询数据源
	private List<PlusCopyPersonInfo> personList;		//已选择的参与人员列表
	private List<PlusCopyPersonInfo> sArrayList;		//被选中的item项
	
	private String tempList ;							//已经选择人员集合字符串
	private List<String> mQueryList;			  		//查询人员姓名历史记录集合
	private DbManager mDbHelper;						//数据库管理类
	private RejectNodeBusiness rBusiness;				//业务逻辑类
	
	private static final String[] COLUMNS = {  			//最近使用列格式
        BaseColumns._ID,
        SearchManager.SUGGEST_COLUMN_TEXT_1,
	};
	private MatrixCursor matrixCursor;					//历史记录数据源
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView;  					//搜索控件
	private AppContext appContext; 						//全局Context
	
	private int fStatus=0;								//默认进入选择人员列表，0-选择人员列表 1-已选人员列表
	private String fQueryStr;							//查询字符串
	private String serviceUrl;  						//服务地址

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//打开数据库
		
		setContentView(R.layout.pluscopy_person);
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
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_PLUSCOPYPERSONURL;
		//获取传递信息
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			tempList=extras.getString(AppContext.PLUSCOPY_PERSON_LIST);
		}
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initListData();
		setListener();
		new getPersonLocalAsync().execute();
	}
	
	/** 
	* @Title: initListData 
	* @Description: 初始化准备信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月12日 下午4:32:45
	*/
	private void initListData(){
		btnYetSelect=(Button)findViewById(R.id.pluscopy_person_yet_select);
		btnNotSelect=(Button)findViewById(R.id.pluscopy_person_not_select);
		btnCheckAll=(Button)findViewById(R.id.pluscopy_person_checkAll);
		btnConfirm=(Button)findViewById(R.id.pluscopy_person_confirm);
		
		pListView=(ListView)findViewById(R.id.pluscopy_person_list);
		pArrayList = new ArrayList<PlusCopyPersonInfo>();
		mQueryList= new ArrayList<String>();
		fQueryStr="";
		
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(PlusCopyPersonActivity.this);
		rBusiness= (RejectNodeBusiness)factoryBusiness.getInstance("RejectNodeBusiness",serviceUrl);
		
		//已经选中的参与人员列表
		showPersonList(tempList);
	}
	
	/** 
	* @Title: showPersonList 
	* @Description: 构造已经选择人员集合
	* @param @param fTempList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午10:28:08
	*/
	private void showPersonList(final String fTempList){
		personList= new ArrayList<PlusCopyPersonInfo>();
		if(!StringUtils.isEmpty(fTempList)){
			try {
				Type listType = new TypeToken<ArrayList<PlusCopyPersonInfo>>(){}.getType();
				Gson gson = new GsonBuilder().create();
				JSONArray jsonArray= new JSONArray(fTempList);
				personList=gson.fromJson(jsonArray.toString(), listType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
	* @date 2014年9月25日 下午2:05:27
	*/
	private void setupSearchView(MenuItem searchItem){
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    if(null!=searchManager){
	    	mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    }
	    mSearchView.setIconifiedByDefault(false);
	    mSearchView.setIconified(false);
	    mSearchView.setSubmitButtonEnabled(true);
	    mSearchView.setQueryHint(getResources().getString(R.string.pluscopy_search_person));
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
		pArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr=query;
		pArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		showSuggestions(newText);
		return false;
	}
	
	/** 
	* @Title: showSuggestions 
	* @Description: 显示人员搜索历史记录
	* @param @param queryStr     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午10:54:22
	*/
	private void showSuggestions(String queryStr){
		if(!StringUtils.isEmpty(queryStr)){
			mQueryList=mDbHelper.queryPlusCopyPersonList(queryStr);
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
	* @Title: setListener 
	* @Description: 设置控件事件处理方法
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月18日 下午12:16:01
	*/
	private void setListener(){
		
		//选择人员 进行搜索
		btnNotSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				skipSelected();	
				pArrayList.clear();
				new getPersonLocalAsync().execute();
			}
		});	
		
		//已经选择人员
		btnYetSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sureSelected();
				renderPersonSelectedListView(personList);
			}
		});

		//全选记录
		btnCheckAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String checkAllText=btnCheckAll.getText().toString();
				List<PlusCopyPersonInfo> soureList=pArrayList;
				if(fStatus==1){ //已经选择人员
					soureList=personList;
				}
				if(soureList.size() > 0){	
					if("全选".equals(checkAllText)){
						btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_resetAll));
						setCheckAll(soureList);
					}
					else {
						btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_checkAll));
						setCancleAll(soureList);
					}
				}
				else{
					UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.pluscopy_person_not_checkAll));
				}
			}
		});
		
		//确定
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(fStatus==0){  //选择人员
					sArrayList=getSelectedList(pArrayList);
				}
				else { //已经选择人员
					sArrayList=getSelectedList(personList);
				}
				if(sArrayList.size() == 0){
					UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.pluscopy_person_notselected));
				}
				else{
					if(fStatus==0){  //把选择的人员标记为已选择
						personList.addAll(sArrayList);
						personList=ListHelper.rDPlusCopyPerson(personList); //去重
						for (PlusCopyPersonInfo item : personList) {
							mDbHelper.insertPlusCopyPersonSearch(item); 	//添加搜索记录到本地数据库中
						}
						UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.pluscopy_person_selected));
						// 延迟2秒跳到已经选择页面
				        new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
				            	sureSelected();  
				            	renderPersonSelectedListView(personList);
				            }
				        }, 1000);	
					}
					else{  //返回已选择人员列表
						for (PlusCopyPersonInfo item : personList) {
							mDbHelper.insertPlusCopyPersonSearch(item); 	//添加搜索记录到本地数据库中
						}
						Intent intent = new Intent();
						intent.putExtra(AppContext.PLUSCOPY_PERSON_LIST, PlusCopyPersonInfo.ConvertToJson(personList));
						setResult(RESULT_OK, intent);
						finish();
					}
				}
			}
		});
	}
	
	/** 
	* @Title: skipSelected 
	* @Description:跳转到选择人员页面中 
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午10:58:35
	*/
	private void skipSelected(){
		fStatus=0;
		btnNotSelect.setBackgroundResource (R.drawable.tabs_active);
		btnNotSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_active);
		
		btnYetSelect.setBackgroundResource (R.drawable.tabs_default_right);
		btnYetSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_default_right);
		
		btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_checkAll));
		btnConfirm.setText(getResources().getString(R.string.pluscopy_person_btnSelect));
	}
	
	/** 
	* @Title: sureSelected 
	* @Description: 跳转到已选择人员页面中
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午10:58:49
	*/
	private void sureSelected(){
		fStatus=1;
		btnYetSelect.setBackgroundResource (R.drawable.tabs_active);
		btnYetSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_active);
		
		btnNotSelect.setBackgroundResource (R.drawable.tabs_default_left);
		btnNotSelect.setTextAppearance(PlusCopyPersonActivity.this,R.style.tabs_default_left);
	
		btnCheckAll.setText(getResources().getString(R.string.pluscopy_person_checkAll));
		btnConfirm.setText(getResources().getString(R.string.pluscopy_person_btnSure));
	}
	
	/** 
	* @Title: getSelectedList 
	* @Description: 获取选择的用户集合
	* @param @param sourceList 选中的数据源集合
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:00:45
	*/
	private List<PlusCopyPersonInfo> getSelectedList(final List<PlusCopyPersonInfo> sourceList){
		List<PlusCopyPersonInfo> selectList=new ArrayList<PlusCopyPersonInfo>();
		for (int i = 0; i < sourceList.size(); i++) {
			if(mAdapter.getIsSelected().get(i)){  //说明选中了
				selectList.add(sourceList.get(i));
			}
		}
		return selectList;
	}
	
	/** 
	* @Title: setCheckAll 
	* @Description: 全选按钮事件
	* @param @param sourceList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:01:36
	*/
	private void setCheckAll(final List<PlusCopyPersonInfo> sourceList){
		//遍历list的长度，将mAdapter中的map值全部设为true  
        for (int i = 0; i < sourceList.size(); i++) { 
        	if("-1".equals(sourceList.get(i).getFItemNumber())){
        		mAdapter.getIsSelected().put(i, false);  
        	}
        	else {
        		mAdapter.getIsSelected().put(i, true);  
			}
        }  
        mAdapter.refreshView(sourceList);
	}

	/** 
	* @Title: setCancleAll 
	* @Description: 取消按钮事件
	* @param @param sourceList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:01:42
	*/
	private void setCancleAll(final List<PlusCopyPersonInfo> sourceList){  
		//遍历list的长度，将已选的按钮设为未选  
        for (int i = 0; i < sourceList.size(); i++) {  
            if (mAdapter.getIsSelected().get(i)) {  
            	mAdapter.getIsSelected().put(i, false);  
            }  
        }  
        mAdapter.refreshView(sourceList);
	}
	
	/**
	 * @ClassName getPersonLocalAsync
	 * @Description 加签/抄送模块获取本地人员历史记录集合
	 * @author 21291
	 * @date 2014年9月25日 上午11:06:54
	 */
	private class getPersonLocalAsync extends AsyncTask<Void,Void,List<PlusCopyPersonInfo>>{

		// 主要是完成耗时操作
		@Override
		protected List<PlusCopyPersonInfo> doInBackground(Void... params) {
			return getPersonLocalByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<PlusCopyPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonLocalListView(result);
		}	
	}
	
	/** 
	* @Title: getPersonLocalByPost 
	* @Description: 加签/抄送模块获取本地人员历史记录实体集合
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:08:34
	*/
	private  List<PlusCopyPersonInfo> getPersonLocalByPost(){
		List<PlusCopyPersonInfo> list=mDbHelper.queryPlusCopyPersonAllList();
		int length=list.size();
		if(length > 0){
			list.add(length, new PlusCopyPersonInfo("-1",getResources().getString(R.string.history_record_search_clear)));
		}
		return list;
	}
	
	/** 
	* @Title: renderPersonLocalListView 
	* @Description: 加签/抄送模块初始化本地人员历史记录列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:09:43
	*/
	private void renderPersonLocalListView(final List<PlusCopyPersonInfo> listData){
		pArrayList.addAll(listData);
		mAdapter=new PlusCopyPersonAdapter(PlusCopyPersonActivity.this,pArrayList,R.layout.pluscopy_person_item,false);
		pListView.setAdapter(mAdapter);
	}
	
	/** 
	* @Title: onFItemNameClick 
	* @Description: 人员名称点击事件
	* @param @param view     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月29日 下午12:11:46
	*/
	public void onFItemNameClick(View view){
		final PlusCopyPersonInfo personInfo = (PlusCopyPersonInfo) view.getTag();
		if("-1".equals(personInfo.getFItemNumber())){	//清除搜索记录
			mDbHelper.deletePlusCopyPersonSearchAll();
			pArrayList.clear();
			mAdapter.refreshView();
			UIHelper.ToastMessage(PlusCopyPersonActivity.this, getResources().getString(R.string.history_record_clear_success));
		}
	}
	
	/** 
	* @Title: renderPersonSelectedListView 
	* @Description: 加签/抄送模块已经选择人员列表加载
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:15:46
	*/
	private void renderPersonSelectedListView(final List<PlusCopyPersonInfo> listData){
		mAdapter=new PlusCopyPersonAdapter(PlusCopyPersonActivity.this,listData,R.layout.pluscopy_person_item,true);
		pListView.setAdapter(mAdapter);
	}
	
	/**
	 * @ClassName getPersonSearchAsync
	 * @Description 加签/抄送模块异步获取人员服务器实体集合
	 * @author 21291
	 * @date 2014年9月25日 上午11:16:35
	 */
	private class getPersonSearchAsync extends AsyncTask<Void, Void, List<PlusCopyPersonInfo>>{

		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}
		
		// 主要是完成耗时操作
		@Override
		protected List<PlusCopyPersonInfo> doInBackground(Void... params) {
			return getPersonListByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<PlusCopyPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonListView(result);
			dialog.dismiss(); 	// 销毁等待框
		}	
	}
	
	/** 
	* @Title: getPersonListByPost 
	* @Description: 加签/抄送模块获取人员服务器实体集合业务逻辑操作
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:17:19
	*/
	private  List<PlusCopyPersonInfo> getPersonListByPost(){
		return rBusiness.getPlusCopyPersonList(fQueryStr);
	}
	
	/** 
	* @Title: renderPersonListView 
	* @Description: 加签/抄送模块初始化人员服务器列表
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月25日 上午11:18:32
	*/
	private void renderPersonListView(final List<PlusCopyPersonInfo> listData){
		if(listData.size()==0){
			UIHelper.ToastMessage(PlusCopyPersonActivity.this, getString(R.string.pluscopy_search_person_netparseerror),3000);
			return;
		}
		pArrayList.addAll(listData);
		mAdapter=new PlusCopyPersonAdapter(PlusCopyPersonActivity.this,pArrayList,R.layout.pluscopy_person_item,true);
		pListView.setAdapter(mAdapter);
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
