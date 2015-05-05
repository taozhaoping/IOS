package com.dahuatech.app.ui.develop.hour;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHListProjectAdapter;
import com.dahuatech.app.bean.develophour.DHListProjectInfo;
import com.dahuatech.app.bean.develophour.DHListProjectJsonInfo;
import com.dahuatech.app.bean.develophour.DHListProjectParamInfo;
import com.dahuatech.app.bean.develophour.DHListTypeInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName DHListProjectActivity
 * @Description 研发工时列表项目信息
 * @author 21291
 * @date 2014年10月22日 下午1:50:18
 */
public class DHListProjectActivity extends MenuActivity {
	private static DHListProjectActivity mInstance;

	private ExpandableListView exlistView;  						//列表控件
	private ImageView fAddProject;									//添加项目按钮
	private ProgressDialog dialog;      							//弹出框
	
	private DevelopHourBusiness dBusiness;							//业务逻辑类
	private List<DHListProjectInfo> dArrayList;						//数据源
	private DHListProjectAdapter dAdapter;							//适配器类
	private DHListTypeInfo dTypeInfo;								//子项点击实体
	
	private int fBillId;											//周单据ID
	private String fItemNumber,fWeekValue,fWeekDate;  				//员工号,第几周,每周时间值
	private String fType;											//显示类型
	private String serviceUrl;  									//服务地址
	private AppContext appContext; 									//全局Context
	
	private Type dHListType;										//类型
	private Gson gson;												//gson实例
	
	public static DHListProjectActivity getInstance() {
		return mInstance;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.dh_list_project);
		
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
			fBillId=extras.getInt(AppContext.DEVELOP_HOURS_WEEK_BILLID,0);
			fWeekValue=extras.getString(AppContext.DEVELOP_HOURS_WEEK_VALUE);
			fWeekDate=extras.getString(AppContext.DEVELOP_HOURS_WEEK_DATE);
			fType=extras.getString(AppContext.DEVELOP_HOURS_LIST_PROJECT_TYPE);
		}
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHLISTPROJECTACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		initListView();
		setOnlistener();
		new getListAsync().execute();
	}
	
	/** 
	* @Title: initListView 
	* @Description: 初始化列表页面控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月27日 上午9:55:46
	*/
	private void initListView(){
		fAddProject=(ImageView)findViewById(R.id.dh_list_project_Add_ImageView);
		exlistView=(ExpandableListView)findViewById(R.id.dh_list_project_ExpandableListView);
	
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHListProjectActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness",serviceUrl);
		dArrayList=new ArrayList<DHListProjectInfo>();
		
		dHListType= new TypeToken<ArrayList<DHListTypeInfo>>(){}.getType();
		gson=GsonHelper.getInstance();
	}
	
	/** 
	* @Title: setOnlistener 
	* @Description: 设置控件监听事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月29日 下午4:43:26
	*/
	private void setOnlistener(){
		fAddProject.setOnClickListener(new OnClickListener() { //项目中添加工时
			
			@Override
			public void onClick(View v) {
				UIHelper.showDHListProjectDetail(DHListProjectActivity.this,fBillId,fWeekValue,fItemNumber,"Add","DhListProject",fWeekDate,"","","");
			}
		});
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description 异步获取实体集合信息
	 * @author 21291
	 * @date 2014年10月27日 上午10:21:20
	 */
	private class getListAsync extends AsyncTask<Void, Void, List<DHListProjectJsonInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<DHListProjectJsonInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHListProjectJsonInfo> result) {
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
	private List<DHListProjectJsonInfo> getListByPost(){
		DHListProjectParamInfo dhListProjectParamInfo=DHListProjectParamInfo.getDHListProjectParamInfo();
		dhListProjectParamInfo.setFBillId(fBillId);
		dhListProjectParamInfo.setFWeekValue(fWeekValue);
		return dBusiness.getDHListProject(dhListProjectParamInfo);
	}

	/** 
	* @Title: renderListView 
	* @Description:  初始化列表集合
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月27日 上午10:32:42
	*/
	private void renderListView(final List<DHListProjectJsonInfo> listData){
		if(listData.size()==0){ //没有数据
			UIHelper.ToastMessage(DHListProjectActivity.this, getString(R.string.dh_list_project_netparseerror),3000);
			return;
		}
		getProjectList(listData);
		dAdapter = new DHListProjectAdapter(DHListProjectActivity.this,fBillId,fWeekValue,fItemNumber,fWeekDate,dArrayList,R.layout.dh_list_project_item,R.layout.dh_list_type_item); //加载适配器数据
		exlistView.setAdapter(dAdapter);
		
		for(int i=0; i < dAdapter.getGroupCount(); i++){
			exlistView.expandGroup(i);
		}
		exlistView.setOnGroupClickListener(new OnGroupClickListener() {  //父项点击

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {	
				return false;
			}
	    });
		
		exlistView.setOnChildClickListener(new OnChildClickListener() {  //子项点击

	        @Override
	        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {  	
	        	   
	        	dAdapter.setParentItem(groupPosition);
	        	dAdapter.setChildItem(childPosition);
	        	dAdapter.notifyDataSetChanged();
	        	
	        	dTypeInfo=null;
	        	dTypeInfo=(DHListTypeInfo)dAdapter.getChild(groupPosition,childPosition);
				if(dTypeInfo!=null){
					final DHListProjectInfo dhProjectInfo = (DHListProjectInfo)dAdapter.getGroup(groupPosition);
					UIHelper.showDHListProjectDetail(DHListProjectActivity.this,fBillId,fWeekValue,fItemNumber,"Edit","",fWeekDate,dhProjectInfo.getFProjectCode(),dhProjectInfo.getFProjectName(),dTypeInfo.getFTypeId());
				}
				
	        	return true;
	        }
	    });   
		
		exlistView.setOnGroupExpandListener(new OnGroupExpandListener() {	
			int previousItem = -1;
            public void onGroupExpand(int groupPosition) {
            	 if(groupPosition != previousItem )
            		 exlistView.collapseGroup(previousItem);
                 previousItem = groupPosition;
            }
        });
 
		exlistView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {
            }
        });
	}
	
	/** 
	* @Title: getProjectList 
	* @Description: 获取每周项具体项目列表
	* @param @param list     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月27日 上午10:34:19
	*/
	private void getProjectList(List<DHListProjectJsonInfo> list){	
		for (DHListProjectJsonInfo dhListProjectJsonInfo : list) {
			DHListProjectInfo dhListProjectInfo=new DHListProjectInfo();
			dhListProjectInfo.setFProjectCode(dhListProjectJsonInfo.getFProjectCode());
			dhListProjectInfo.setFProjectName(dhListProjectJsonInfo.getFProjectName());
			dhListProjectInfo.setFHours(dhListProjectJsonInfo.getFHours());
			if(!StringUtils.isEmpty(dhListProjectJsonInfo.getFSubEntrys())){
				getSubEntrys(dhListProjectJsonInfo.getFSubEntrys(),dhListProjectInfo);
			}
			dArrayList.add(dhListProjectInfo);
		}
	}
	
	/** 
	* @Title: getSubEntrys 
	* @Description: 获取子类集合
	* @param @param fSubEntrys 子类集合字符串
	* @param @param dhListProjectInfo 父类实体    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月27日 上午10:40:37
	*/
	private void getSubEntrys(String fSubEntrys,DHListProjectInfo dhListProjectInfo){
		try {
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DHListTypeInfo> dhListType=gson.fromJson(jsonArray.toString(), dHListType);
			dhListProjectInfo.setdListTypeInfo(dhListType);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHListProjectActivity.this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK) { //返回键
    		if("edit".equals(fType)){  //如果是编辑过的返回
    			if(DHListActivity.getInstance()!=null){
    				DHListActivity.getInstance().finish();
    			}
    			UIHelper.showDHList(DHListProjectActivity.this,fItemNumber);
    			finish();
    		}
    	} 	
    	return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if(dAdapter!=null && dArrayList.size() > 0){
			dAdapter.refreshView(dArrayList);
		}
	}
}
