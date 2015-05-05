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
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.DHComfirmListPersonAdapter;
import com.dahuatech.app.bean.develophour.DHConfirmListChildInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListPersonJsonInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListPersonParamInfo;
import com.dahuatech.app.bean.develophour.DHConfirmListRootInfo;
import com.dahuatech.app.business.DevelopHourBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName DHConfirmListPersonActivity
 * @Description 研发工时确认列表人员信息
 * @author 21291
 * @date 2014年11月5日 下午3:03:38
 */
public class DHConfirmListPersonActivity extends MenuActivity {

	private ExpandableListView exlistView;  						//列表控件
	private ProgressDialog dialog;      							//弹出框
	
	private DevelopHourBusiness dBusiness;							//业务逻辑类
	private DHComfirmListPersonAdapter dAdapter;					//数据适配器类
	private List<DHConfirmListRootInfo> dArrayList;					//数据源
	
	private String serviceUrl;  									//服务地址
	private AppContext appContext; 									//全局Context
	
	private Type listType;											//子项子级类型
	private Gson gson;												//gson实例
	
	private String fProjectNumber;									//项目经理员工号
	private int fWeekIndex;											//第几周
	private int fCurrentYear;										//当前年份
	private String fProjectCode;  									//项目编号
	private String fConfrimNumber;  								//确认人员员工号     
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dh_confirm_list_person);
		
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
			fProjectNumber=extras.getString(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTNUMBER);
			fWeekIndex=extras.getInt(AppContext.DEVELOP_HOURS_CONFIRM_PASS_WEEKINDEX,0);
			fCurrentYear=extras.getInt(AppContext.DEVELOP_HOURS_CONFIRM_PASS_YEAR,0);
			fProjectCode=extras.getString(AppContext.DEVELOP_HOURS_CONFIRM_PASS_PROJECTCODE);
			fConfrimNumber=extras.getString(AppContext.DEVELOP_HOURS_CONFIRM_PASS_CONFIRMNUMBER);
		}
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_DHCONFIRMLISTPERSONACTIVITY;	
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		initListView();
		new getListAsync().execute();
	}
	
	/** 
	* @Title: initListView 
	* @Description: 初始化列表页面控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月5日 下午3:27:04
	*/
	private void initListView(){
		exlistView=(ExpandableListView)findViewById(R.id.dh_confirm_list_person_ExpandableListView);
	
		//初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(DHConfirmListPersonActivity.this);
		dBusiness= (DevelopHourBusiness)factoryBusiness.getInstance("DevelopHourBusiness","");
		dArrayList=new ArrayList<DHConfirmListRootInfo>();
		
		listType= new TypeToken<ArrayList<DHConfirmListChildInfo>>(){}.getType();
		gson=GsonHelper.getInstance();
	}
	
	/**
	 * @ClassName getListAsync
	 * @Description 异步获取实体集合信息
	 * @author 21291
	 * @date 2014年11月5日 下午3:31:11
	 */
	private class getListAsync extends AsyncTask<Void, Void, List<DHConfirmListPersonJsonInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<DHConfirmListPersonJsonInfo> doInBackground(Void... params) {
			return getListByPost();
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<DHConfirmListPersonJsonInfo> result) {
			super.onPostExecute(result);
			renderListView(result);	
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getListByPost 
	* @Description:  获取实体集合信息
	* @param @return     
	* @return List<DHConfirmListPersonJsonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年11月5日 下午3:54:23
	*/
	private List<DHConfirmListPersonJsonInfo> getListByPost(){
		DHConfirmListPersonParamInfo dPersonParamInfo=DHConfirmListPersonParamInfo.getDHConfirmListPersonParamInfo();
		dPersonParamInfo.setFProjectNumber(fProjectNumber);
		dPersonParamInfo.setFWeekIndex(fWeekIndex);
		dPersonParamInfo.setFYear(fCurrentYear);
		dPersonParamInfo.setFProjectCode(fProjectCode);
		dPersonParamInfo.setFConfrimNumber(fConfrimNumber);
		
		dBusiness.setServiceUrl(serviceUrl);
		return dBusiness.getDHConfirmListPerson(dPersonParamInfo);
	}

	/** 
	* @Title: renderListView 
	* @Description: 初始化列表集合
	* @param @param listData     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月5日 下午3:55:47
	*/
	private void renderListView(final List<DHConfirmListPersonJsonInfo> listData){
		if(listData.size()==0){ //没有数据
			UIHelper.ToastMessage(DHConfirmListPersonActivity.this, getString(R.string.dh_confrim_list_person_netparseerror),3000);
			return;
		}
		getPersonList(listData);
		dAdapter = new DHComfirmListPersonAdapter(DHConfirmListPersonActivity.this,dArrayList,R.layout.dh_confirm_list_person_root,R.layout.dh_confirm_list_person_child); //加载适配器数据
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
	* @Title: getPersonList 
	* @Description: 获取每人具体工时信息
	* @param @param list     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月5日 下午3:59:57
	*/
	private void getPersonList(List<DHConfirmListPersonJsonInfo> list){	
		for (DHConfirmListPersonJsonInfo dPersonJsonInfo : list) {
			DHConfirmListRootInfo dConfirmListRootInfo=new DHConfirmListRootInfo();
			dConfirmListRootInfo.setFTypeId(dPersonJsonInfo.getFTypeId());
			dConfirmListRootInfo.setFTypeName(dPersonJsonInfo.getFTypeName());
			dConfirmListRootInfo.setFHours(dPersonJsonInfo.getFHours());
			if(!StringUtils.isEmpty(dPersonJsonInfo.getFSubChilds())){
				getFSubChilds(dPersonJsonInfo.getFSubChilds(),dConfirmListRootInfo);
			}
			dArrayList.add(dConfirmListRootInfo);
		}
	}
	
	/** 
	* @Title: getFSubChilds 
	* @Description: 获取子类集合
	* @param @param fSubEntrys 子类集合字符串
	* @param @param dRootInfo  父类实体       
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月5日 下午4:03:35
	*/
	private void getFSubChilds(String fSubEntrys,DHConfirmListRootInfo dRootInfo){
		try {
			JSONArray jsonArray= new JSONArray(fSubEntrys);
			List<DHConfirmListChildInfo> dList=gson.fromJson(jsonArray.toString(), listType);
			dRootInfo.setFChildren(dList);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(DHConfirmListPersonActivity.this);
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
