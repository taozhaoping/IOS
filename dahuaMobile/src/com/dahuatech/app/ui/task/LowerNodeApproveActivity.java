package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.LowerNodeAppBackInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppConfigInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppItemInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppResultInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppRoleInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppRoleJsonInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppSpinnerInfo;
import com.dahuatech.app.bean.mytask.LowerNodeAppUserInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.LowerNodeAppBusiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.MultiSelectionSpinner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName LowerNodeApproveActivity
 * @Description 下级节点审批人,只有新的单据平台有这两个功能 系统号为18的
 * @author 21291
 * @date 2014年11月6日 上午11:45:15
 */
@SuppressLint("InflateParams")
public class LowerNodeApproveActivity extends MenuActivity {
	
	private LinearLayout checkBoxLayout,structLayout;					//流转节点布局,组织架构布局
	private CheckBox[] checkBoxArray;									//定义复选框数组
	
	private TextView fResultTView;										//选取结果
	private Button fConfirm,fCancle;
	private ProgressDialog dialog,appDialog;    						//默认弹出框,审批弹出框				
	private String fSystemId,fBillId,fClassTypeId,fBillName; 			//单据名称
	private LowerNodeAppBusiness lBusiness;								//业务逻辑类
	private HashMap<String, String> configHashMap; 						//配置信息
	private AppContext appContext; 										//全局Context

	private String fLowerNodeAppParam;  								//拼接的字符串参数
	private String nodeStatusUrl,passWorkFlowUrl;  						//获取下级节点审批服务地址,审批服务地址			
	private String fItemNumber; 										//员工号									
	private Gson gson;													//gson帮助类
	
	private Map<String, LowerNodeAppResultInfo> nodeValueMap;			//节点选取结果实体集合
	public Map<String, LowerNodeAppResultInfo> getNodeValueMap() {
		return nodeValueMap;
	}
	private List<LowerNodeAppBackInfo> lBackList;						//选择的值返回列表
	private LowerNodeAppConfigInfo lConfig;								//下级节点审批配置实体
	
	//通过附带出来的审批人信息
	private List<LowerNodeAppUserInfo> lowerNodeAppUserList;			//审批人基本信息实体集合
	private List<LowerNodeAppRoleInfo> lowerNodeAppRoleList;			//审批人角色信息实体集合
	
	private static final String spinnerUserType="User";					//下拉框节点类型
	private static final String spinnerRoleType="Role";					//下拉框角色类型
	private String  spinnerTypeIndex;									//下拉框索引
	private Map<String,LowerNodeAppSpinnerInfo> spinnerMap;				//下级节点审批人下拉框实体集合
	public Map<String, LowerNodeAppSpinnerInfo> getSpinnerMap() {
		return spinnerMap;
	}

	//通过搜索获取审批人信息
	private List<PlusCopyPersonInfo> personList;						//选中人员集合类
	private TextView fSearchNodeName,fSearchItem;						//搜索结果控件
	private ImageView fSearchImageView;									//搜索按钮
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lower_node_approve);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
				
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
			fSystemId=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fBillId=extras.getString(AppContext.FBILLID_KEY);	
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fBillName=extras.getString(AppContext.WORKFLOW_FBILLNAME_KEY);
		}	
			
		//获取新版加签/抄送节点配置信息
		configHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "LowerNodeAppRepository");
		nodeStatusUrl=AppUrl.URL_API_HOST_ANDROID_LOWERNODEAPPROVEURL;			//获取下级节点审批服务地址
		passWorkFlowUrl=AppUrl.URL_API_HOST_ANDROID_PASSLOWERNODEHANDLEURL;	    //审批操作服务地址 	
		
		initView();
		setListener();
		new getLowerNodeAppAsync().execute(fLowerNodeAppParam);
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化视图控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 下午2:31:55
	*/
	private void initView(){
		checkBoxLayout=(LinearLayout)findViewById(R.id.lower_node_approve_checkBox_layout);
		structLayout=(LinearLayout)findViewById(R.id.lower_node_approve_struct_layout);
		fResultTView=(TextView)findViewById(R.id.lower_node_approve_FResult);
		
		fConfirm=(Button)findViewById(R.id.lower_node_approve_FConfirm);
		fCancle=(Button)findViewById(R.id.lower_node_approve_FCancle);
		
		//数据加载
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//审批加载
		appDialog = new ProgressDialog(this);
		appDialog.setMessage(getResources().getString(R.string.dialog_approveing));
		appDialog.setCancelable(false);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(LowerNodeApproveActivity.this);
		lBusiness= (LowerNodeAppBusiness)factoryBusiness.getInstance("LowerNodeAppBusiness",nodeStatusUrl);	
		
		fLowerNodeAppParam=fSystemId+","+fClassTypeId+","+fBillId+","+fItemNumber;
		gson=GsonHelper.getInstance();
		
		nodeValueMap=new LinkedHashMap<String, LowerNodeAppResultInfo>();   //初始化
	}
	
	/** 
	* @Title: setListener 
	* @Description: 设置视图控件事件处理方法
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 下午2:45:02
	*/
	private void setListener(){
		//确定审批
		fConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!StringUtils.isEmpty(fResultTView.getText().toString())){
					setLowerNodeAppBack();
					if(verify()){
						new passWorkflowAsync().execute(passWorkFlowUrl);
					}
				}
				else {
					UIHelper.ToastMessage(LowerNodeApproveActivity.this, getResources().getString(R.string.lower_node_approve_result_error));
				}
			}
		});
		
		//取消
		fCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();		
			}
		});
	}
	
	/**
	 * @ClassName getLowerNodeAppAsync
	 * @Description 异步获取下级节点审批实体集合
	 * @author 21291
	 * @date 2014年11月11日 上午9:19:39
	 */
	private class getLowerNodeAppAsync extends AsyncTask<String, Void, List<LowerNodeAppInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<LowerNodeAppInfo> doInBackground(String... params) {
			return getLowerNodeAppByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<LowerNodeAppInfo> result) {
			super.onPostExecute(result);
			renderLowerNodeAppView(result);	
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getLowerNodeAppByPost 
	* @Description: 获取下级节点审批人集合
	* @param @param paramStr
	* @param @return     
	* @return List<LowerNodeAppInfo>    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 上午9:25:47
	*/
	private List<LowerNodeAppInfo> getLowerNodeAppByPost(final String paramStr){ 
		// 参数实体-仓储类 工作流
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		repository.setClassType(configHashMap.get("ClassType"));
		repository.setIsTest(configHashMap.get("IsTest"));
		repository.setServiceName(configHashMap.get("ServiceName"));
		repository.setServiceType(configHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(configHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(configHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);		
		
		return lBusiness.getLowerNodeAppInfo(repository,paramStr);
	}
	
	/** 
	* @Title: renderLowerNodeAppView 
	* @Description: 添加下级节点
	* @param @param lowerNodeAppInfos     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 上午9:30:13
	*/
	@SuppressLint("InflateParams")
	private void renderLowerNodeAppView(final List<LowerNodeAppInfo> lowerNodeAppInfos){
		if(lowerNodeAppInfos.size() > 0){  //说明有下级节点
			int lnLength=lowerNodeAppInfos.size();
			checkBoxArray=new CheckBox[lnLength];
			for (int i = 0; i < lnLength; i++) {
				parseLowerNodeAppInfo(lowerNodeAppInfos.get(i),i,lnLength);
			}
		}
	}

	/** 
	* @Title: parseLowerNodeAppInfo 
	* @Description: 解析下级节点审批状态实体
	* @param @param lowerNodeApp 下级节点实例
	* @param @param i 索引   
	* @param @param lnLength 总的长度    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 下午3:07:27
	*/
	private void parseLowerNodeAppInfo(final LowerNodeAppInfo lowerNodeApp, final int i, final int lnLength){
		View linearLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_checkbox, null);
	
		final CheckBox fCheckBoxName=(CheckBox)linearLayout.findViewById(R.id.lower_node_approve_checkbox_name);
		fCheckBoxName.setId(i);
		fCheckBoxName.setText(lowerNodeApp.getFNodeName());
		checkBoxArray[i]=fCheckBoxName;
		
		final TextView fCheckBoxValue=(TextView)linearLayout.findViewById(R.id.lower_node_approve_checkbox_value);
		fCheckBoxValue.setText(lowerNodeApp.getFNodeId());
		
		LowerNodeAppResultInfo lResultInfo=new LowerNodeAppResultInfo();
		lResultInfo.setFNodeId(lowerNodeApp.getFNodeId());
		lResultInfo.setFNodeName(lowerNodeApp.getFNodeName());
		lResultInfo.setFIsMust(lowerNodeApp.getFIsMust());
		nodeValueMap.put(lowerNodeApp.getFNodeName(), lResultInfo);  //每个节点选取的值保存起来
		
		if(i==0){  //首个节点默认选中
			fCheckBoxName.setChecked(true);
			setSubClassView(lowerNodeApp);
		}
		else{
			fCheckBoxName.setChecked(false);
		}
		
		//复选框选中事件
		fCheckBoxName.setOnCheckedChangeListener(new CheckedListener(fCheckBoxName, lowerNodeApp, lnLength));
		checkBoxLayout.addView(linearLayout);
	}
	
	/**
	 * @ClassName CheckedListener
	 * @Description 复选框点击类
	 * @author 21291
	 * @date 2014年11月11日 下午5:00:50
	 */
	private class CheckedListener implements OnCheckedChangeListener{
		private CheckBox chBox;
		private LowerNodeAppInfo lNodeAppInfo;
		private int lnLength;
		
		public CheckedListener(CheckBox checkBox, LowerNodeAppInfo lowerNodeAppInfo,int lnLength){
			this.chBox=checkBox;
			this.lNodeAppInfo=lowerNodeAppInfo;
			this.lnLength=lnLength;
		}
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			structLayout.removeAllViews();
			if(isChecked){  //说明是选中的状态
				for (int j = 0; j < lnLength; j++) {
					if(buttonView.getId()==j){
						chBox.setChecked(true);
					}
					else {
						checkBoxArray[j].setChecked(false);
					}
				}
				setSubClassView(lNodeAppInfo);
			}
		}	
	}
	
	/** 
	* @Title: setSubClassView 
	* @Description: 根据节点状态，设置不同控件显示类型
	* @param @param lowerNodeApp     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 下午1:53:34
	*/
	private void setSubClassView(final LowerNodeAppInfo lowerNodeApp){
		if(lowerNodeApp.getFNodeStatus() ==1){  //说明审批人信息是通过搜索框来获取
			personList=new ArrayList<PlusCopyPersonInfo>();
			initLowerNodeAppSearch(lowerNodeApp.getFNodeName());
		}
		
		if(lowerNodeApp.getFNodeStatus()==2){  //说明审批人信息是从附带出的流程节点配置的信息来获取
			spinnerMap=new LinkedHashMap<String, LowerNodeAppSpinnerInfo>();  //初始化
			getLowerNodeAppItem(lowerNodeApp.getFSubEntrys());
			
			List<String> lUserStringList=getUserStringList(lowerNodeAppUserList);
			if(lUserStringList.size() > 0){  //说明有审批人基本信息
				initLoweNodeAppSpinnerUser(lUserStringList,lowerNodeApp.getFNodeName());
			}
			
			int roleLength=lowerNodeAppRoleList.size();	
			if(roleLength > 0){ //说明有审批人角色信息
				for (int i = 0; i < roleLength; i++) {
					initLoweNodeAppSpinnerRole(i,roleLength,lowerNodeAppRoleList.get(i).getFRoleName(),getUserStringList(lowerNodeAppRoleList.get(i).getFUserList()),lowerNodeApp.getFNodeName());
				}
			}
		}
		
		if(lowerNodeApp.getFNodeStatus()==3){  //说明审批人信息,既不通过搜索来获取，也不通过附带出的人员来获取，自动生成审批人
			LowerNodeAppResultInfo nodeResult=nodeValueMap.get(lowerNodeApp.getFNodeName());
			String selectResult="系统自动生成";
			nodeResult.setFSelectResult(selectResult);
			nodeResult.setFShowResult(lowerNodeApp.getFNodeName()+":"+selectResult);
			fResultTView.setText(MultiSelectionSpinner.getNodeValueMap(nodeValueMap));
		}
	}
	
	/** 
	* @Title: getLowerNodeAppItem 
	* @Description: 获取审批人基本信息集合包装类
	* @param @param fSubEntrys     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 上午11:10:49
	*/
	private void getLowerNodeAppItem(final String fSubEntrys){
		try {
			JSONObject jsonObject=new JSONObject(fSubEntrys);
			LowerNodeAppItemInfo lowerNodeAppItem= gson.fromJson(jsonObject.toString(),LowerNodeAppItemInfo.class);
			if(lowerNodeAppItem!=null){
				lowerNodeAppUserList=parseLowerNodeAppUser(lowerNodeAppItem.getFApproveUser());
				lowerNodeAppRoleList=parseLowerNodeAppRole(lowerNodeAppItem.getFApproveRole());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	/** 
	* @Title: getUserStringList 
	* @Description: 获取审批人基本信息实体员工名称
	* @param @param userList 审批人基本信息
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 上午11:31:15
	*/
	private List<String> getUserStringList(final List<LowerNodeAppUserInfo> userList){
		List<String> lUserString=new ArrayList<String>();
		if(userList.size()>0){
			for (LowerNodeAppUserInfo item : userList) {
				lUserString.add(item.getFItemName()+"("+item.getFItemNumber()+")");
			}
		}
		return lUserString;
	}
	
	/** 
	* @Title: parseLowerNodeAppUser 
	* @Description: 解析审批人基本信息实体
	* @param @param fApproveUser
	* @param @return     
	* @return List<LowerNodeAppUserInfo>    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 上午11:33:46
	*/
	private List<LowerNodeAppUserInfo> parseLowerNodeAppUser(final String fApproveUser){
		List<LowerNodeAppUserInfo> resultList=new ArrayList<LowerNodeAppUserInfo>();
		try {
			Type userListType = new TypeToken<ArrayList<LowerNodeAppUserInfo>>(){}.getType();	
			JSONArray jsonArray= new JSONArray(fApproveUser);
			resultList=gson.fromJson(jsonArray.toString(), userListType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	/** 
	* @Title: parseLowerNodeAppRole 
	* @Description: 解析审批人角色信息实体
	* @param @param fApproveRole
	* @param @return     
	* @return List<LowerNodeAppRoleInfo>    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 上午11:53:33
	*/
	private List<LowerNodeAppRoleInfo> parseLowerNodeAppRole(final String fApproveRole){
		List<LowerNodeAppRoleJsonInfo> parseList=new ArrayList<LowerNodeAppRoleJsonInfo>();
		try {
			Type roleJsonListType = new TypeToken<ArrayList<LowerNodeAppRoleJsonInfo>>(){}.getType();	
			JSONArray jsonArray= new JSONArray(fApproveRole);
			parseList=gson.fromJson(jsonArray.toString(), roleJsonListType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getLowerNodeAppRole(parseList);
	}
	
	/** 
	* @Title: getLowerNodeAppRole 
	* @Description: 获取审批人角色信息实体
	* @param @param roleJsonList
	* @param @return     
	* @return List<LowerNodeAppRoleInfo>    
	* @throws 
	* @author 21291
	* @date 2014年11月11日 下午1:17:16
	*/
	private List<LowerNodeAppRoleInfo> getLowerNodeAppRole(final List<LowerNodeAppRoleJsonInfo> roleJsonList){
		List<LowerNodeAppRoleInfo> resultList=new ArrayList<LowerNodeAppRoleInfo>();
		if(roleJsonList.size() > 0){  //说明有字符串类
			for (LowerNodeAppRoleJsonInfo item : roleJsonList) {
				LowerNodeAppRoleInfo roleInfo=new LowerNodeAppRoleInfo();
				roleInfo.setFRoleName(item.getFRoleName());
				roleInfo.setFUserList(parseLowerNodeAppUser(item.getFRoleApproveUser()));
				resultList.add(roleInfo);
			}
		}
		return resultList;
	}
	
	/** 
	* @Title: initLowerNodeAppSearch 
	* @Description: 初始化搜索框视图
	* @param @param fNodeName 节点名称    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午2:17:24
	*/
	private void initLowerNodeAppSearch(final String fNodeName){
		View searchLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_search, null);
		
		fSearchNodeName=(TextView)searchLayout.findViewById(R.id.lower_node_approve_search_FNodeName);
		fSearchNodeName.setText(fNodeName);
		fSearchItem=(TextView)searchLayout.findViewById(R.id.lower_node_approve_search_FItem);
		fSearchImageView=(ImageView)searchLayout.findViewById(R.id.lower_node_approve_search_FImageView);

		fSearchImageView.setOnClickListener(new OnClickListener() {	//弹出人员搜索界面 	
			@Override
			public void onClick(View v) {
				UIHelper.showPlusCopyPersonSearch(LowerNodeApproveActivity.this,PlusCopyPersonInfo.ConvertToJson(personList));			
			}
		});
		
		structLayout.addView(searchLayout);
	}
	
	/** 
	* @Title: initLoweNodeAppSpinnerUser 
	* @Description: 初始化节点人员下拉框视图
	* @param @param fUserNames 员工名称+员工号
	* @param @param fNodeName  节点名称   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 上午11:42:07
	*/
	private void initLoweNodeAppSpinnerUser(final List<String> fUserNames,final String fNodeName){
		View spinnerUserLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_spinner_user, null);
		
		final MultiSelectionSpinner mSpinnerUser=(MultiSelectionSpinner)spinnerUserLayout.findViewById(R.id.lower_node_approve_spinner_user);
		mSpinnerUser.setItems(fUserNames);
		mSpinnerUser.setlNodeApprove(this);
		mSpinnerUser.setSpinnerType(spinnerUserType);
		mSpinnerUser.setSpinnerIndex(0);
		mSpinnerUser.setRoleSpinnerCount(1);
		mSpinnerUser.setFNodeName(fNodeName);
		mSpinnerUser.setResultView(fResultTView);
		
		spinnerTypeIndex=spinnerUserType+"_"+"0";
		LowerNodeAppSpinnerInfo lNodeAppSpinner=new LowerNodeAppSpinnerInfo();
		lNodeAppSpinner.setFSpinnerType(spinnerUserType);
		lNodeAppSpinner.setFSpinnerIndex(0);
		lNodeAppSpinner.setFRoleSpinnerCount(1);
		spinnerMap.put(spinnerTypeIndex, lNodeAppSpinner);
		
		structLayout.addView(spinnerUserLayout);
	}

	/** 
	* @Title: initLoweNodeAppSpinnerRole 
	* @Description: 初始化节点角色下拉框视图
	* @param @param fRoleIndex 角色下拉框索引
	* @param @param fRoleCount 角色下拉框总数
	* @param @param fRoleTitle 角色下拉框标题
	* @param @param fRoleNames 角色下拉框显示字符串值     
	* @param @param fNodeName  节点名称    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 上午11:51:58
	*/
	private void initLoweNodeAppSpinnerRole(final int fRoleIndex,final int fRoleCount, final String fRoleTitle, final List<String> fRoleNames,final String fNodeName){
		View spinnerRoleLayout = LayoutInflater.from(this).inflate(R.layout.lower_node_approve_spinner_role, null);
		
		final TextView fRoleTitleTView=(TextView)spinnerRoleLayout.findViewById(R.id.lower_node_approve_spinner_role_title);
		fRoleTitleTView.setText(fRoleTitle);
		
		final MultiSelectionSpinner mSpinnerRole=(MultiSelectionSpinner)spinnerRoleLayout.findViewById(R.id.lower_node_approve_spinner_role);
		mSpinnerRole.setItems(fRoleNames);
		mSpinnerRole.setlNodeApprove(this);
		mSpinnerRole.setSpinnerType(spinnerRoleType);
		mSpinnerRole.setSpinnerIndex(fRoleIndex);
		mSpinnerRole.setRoleSpinnerCount(fRoleCount);
		mSpinnerRole.setFNodeName(fNodeName);
		mSpinnerRole.setResultView(fResultTView);
		
		spinnerTypeIndex=spinnerRoleType+"_"+String.valueOf(fRoleIndex);
		LowerNodeAppSpinnerInfo lNodeAppSpinner=new LowerNodeAppSpinnerInfo();
		lNodeAppSpinner.setFSpinnerType(spinnerRoleType);
		lNodeAppSpinner.setFSpinnerIndex(fRoleIndex);
		lNodeAppSpinner.setFRoleSpinnerCount(fRoleCount);
		spinnerMap.put(spinnerTypeIndex, lNodeAppSpinner);
		
		structLayout.addView(spinnerRoleLayout);
	}
	
	// 从搜索人员页面,回调方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.FPLUSCOPY_PERSON_KEY: //人员列表
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						//获取传递信息
						String transferStr=extras.getString(AppContext.PLUSCOPY_PERSON_LIST);
						personList.clear();
						personList.addAll(getPersonList(transferStr));
						personList=ListHelper.rDPlusCopyPerson(personList); //去重
						getAttendPerson(personList);
					}
				}	
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: getPersonList 
	* @Description: 获取人员列表选择的人员
	* @param @param fTempList
	* @param @return     
	* @return List<PlusCopyPersonInfo>    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午2:35:21
	*/
	private List<PlusCopyPersonInfo> getPersonList(final String fTempList){
		List<PlusCopyPersonInfo> resultList=new ArrayList<PlusCopyPersonInfo>();
		if(!StringUtils.isEmpty(fTempList)){
			try {
				Type listType = new TypeToken<ArrayList<PlusCopyPersonInfo>>(){}.getType();
				JSONArray jsonArray= new JSONArray(fTempList);
				resultList=gson.fromJson(jsonArray.toString(), listType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	/** 
	* @Title: getAttendPerson 
	* @Description: 获取参与人员名称
	* @param @param personList     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午2:35:31
	*/
	private void getAttendPerson(final List<PlusCopyPersonInfo> personList){
		String selectResult="";
		StringBuffer sb = new StringBuffer();
		boolean foundOne = false;  
		for (PlusCopyPersonInfo item : personList) {
			if(foundOne){
        		sb.append(", ");  
        	}
        	foundOne=true;
        	sb.append(item.getFItemName()+"("+item.getFItemNumber()+")");
		}
		selectResult=sb.toString();
		fSearchItem.setText(selectResult);
		setSearchSelectResult(selectResult);
	}
	
	/** 
	* @Title: setSearchSelectResult 
	* @Description: 设置搜索文本框选取的文本值
	* @param @param selectResult     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午2:56:50
	*/
	private void setSearchSelectResult(final String selectResult){
		String fNodeName=fSearchNodeName.getText().toString();
		LowerNodeAppResultInfo nodeResult=nodeValueMap.get(fNodeName);
		nodeResult.setFSelectResult(selectResult);
		nodeResult.setFShowResult(fNodeName+":"+selectResult);
		fResultTView.setText(MultiSelectionSpinner.getNodeValueMap(nodeValueMap));
	}
	
	/** 
	* @Title: setLowerNodeAppBack 
	* @Description: 设置选取的下级节点返回值
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午4:24:54
	*/
	@SuppressWarnings("rawtypes")
	private void setLowerNodeAppBack(){
		lBackList=new ArrayList<LowerNodeAppBackInfo>();
		//遍历HashMap
        Iterator it = nodeValueMap.entrySet().iterator(); 
        Map.Entry entry;
        while(it.hasNext()) {
        	entry = (Map.Entry) it.next(); 
        	Object key = entry.getKey();  
        	LowerNodeAppResultInfo lResultInfo =nodeValueMap.get(key);
        	LowerNodeAppBackInfo lBackInfo=new LowerNodeAppBackInfo();
        	lBackInfo.setFIsMust(lResultInfo.getFIsMust());
        	lBackInfo.setFNodeId(lResultInfo.getFNodeId());
        	lBackInfo.setFNodeName(lResultInfo.getFNodeName());
        	lBackInfo.setFSelectItem(parseSelectResult(lResultInfo.getFSelectResult()));
        	lBackList.add(lBackInfo);
        }  
	}
	
	/** 
	* @Title: parseSelectResult 
	* @Description: 解析选中的结果字符串值
	* @param @param selectResult
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年11月13日 上午10:16:09
	*/
	private String parseSelectResult(final String selectResult){
		String result="";
		if(!StringUtils.isEmpty(selectResult)){
			List<String> parseList=StringUtils.convertToList(selectResult, ",");
			if(parseList.size() > 0){
				StringBuilder sb = new StringBuilder();  
				boolean foundOne = false;    
				for (String item : parseList) {
					if (foundOne) {  
						sb.append(",");  
					}  
					foundOne = true;  
					int firstIndex=item.indexOf("(");  //开始括号的索引值
					int endIndex=item.indexOf(")");    //结束括号的索引值
					if(firstIndex > 0 && endIndex > 0){
						sb.append(item.substring(firstIndex+1, endIndex)); 
					}		
				}
				result=sb.toString();
			}
		}
		return result;
	}
	
	/** 
	* @Title: verify 
	* @Description: 确定之前验证
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午4:32:49
	*/
	private boolean verify(){
		boolean flag=false;	
		if(lBackList.size() > 0){
			List<Boolean> returnList=new ArrayList<Boolean>();
			for (LowerNodeAppBackInfo item : lBackList) {
				if(item.getFIsMust()==1 && StringUtils.isEmpty(item.getFSelectItem())){  //说明是必选
					UIHelper.ToastMessage(LowerNodeApproveActivity.this, item.getFNodeName()+"为必走节点,请点击选择值");
					returnList.add(true);
					break;
				}
			}
			if(returnList.size() == 0){
				flag=true;
			}
		}
		return flag;
	}
	
	/**
	 * @ClassName passWorkflowAsync
	 * @Description 异步进行审批操作
	 * @author 21291
	 * @date 2014年11月12日 下午5:03:29
	 */
	private class passWorkflowAsync extends AsyncTask<String, Void, ResultMessage>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			appDialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected ResultMessage doInBackground(String... params) {
			return passWorkflowByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			renderPassWorkView(result);	
			appDialog.dismiss(); 	// 销毁等待框
		}	
	}
	
	/** 
	* @Title: passWorkflowByPost 
	* @Description: 异步请求操作数据
	* @param @param serviceUrl
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午5:05:32
	*/
	private ResultMessage passWorkflowByPost(final String serviceUrl){
		lConfig=LowerNodeAppConfigInfo.getLowerNodeAppConfigInfo();
		lConfig.setFAppKey(AppContext.WORKFLOW_NEWOFFICE_KEY);
		lConfig.setFSystemId(Integer.valueOf(fSystemId));
		lConfig.setFClassTypeId(fClassTypeId);
		lConfig.setFBillId(fBillId);
		lConfig.setFItemNumber(fItemNumber);
		
		lBusiness.setServiceUrl(serviceUrl);
		return lBusiness.passWorkFlowHandle(lConfig, lBackList);
	}
	
	/** 
	* @Title: renderPassWorkView 
	* @Description: 对于结果进行UI更新
	* @param @param resultMessage     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月12日 下午5:06:25
	*/
	private void renderPassWorkView(final ResultMessage resultMessage){
		UIHelper.ToastMessage(LowerNodeApproveActivity.this, resultMessage.getResult());
		sendLogs(); //发送日志信息进行统计
		
		// 延迟2秒进入关闭
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	finish();
            }
        }, 2000);	
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 审批操作时，发送日志记录到服务器
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年7月31日 上午11:05:52
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_mytasks));
		logInfo.setFActionName("nextnode");
		logInfo.setFNote(fBillName);
		UIHelper.sendLogs(LowerNodeApproveActivity.this,logInfo);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;				
		}
		return super.onOptionsItemSelected(item);
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
