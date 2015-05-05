package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.RepositoryInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.mytask.PlusCopyInfo;
import com.dahuatech.app.bean.mytask.PlusCopyPersonInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.RejectNodeBusiness;
import com.dahuatech.app.common.GsonHelper;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.ParseXmlService;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName PlusCopyActivity
 * @Description 加签/抄送主页,只有新的单据平台有这两个功能 系统号为18的
 * @author 21291
 * @date 2014年9月22日 上午9:13:48
 */
public class PlusCopyActivity extends MenuActivity {
	private TextView fPlusTView,fItemNumbersTView,fItemNamesTView;
	private EditText fContentEText;
	private ImageView fInImageView,fCIView;
	private Button fConfirm,fCancle;
	private LinearLayout chkLayout;							//附加节点布局实体
	private ProgressDialog dialog,appDialog;    			//默认弹出框,审批弹出框
	
	private String fType,fSystemId,fBillId,fClassTypeId; 	//fType类型  "0"-代表加签  "1"-代表抄送
	private String fPlusCopyParam;
	private List<RejectNodeInfo> rArrayList;				//选中的附加节点
	private RejectNodeBusiness rBusiness;					//业务逻辑类
	private PlusCopyInfo plusCopyInfo;						//加签或抄送实体类
	private List<PlusCopyPersonInfo> personList;			//选中人员集合类
	
	private HashMap<String, String> plusCopyHashMap; 		//配置信息
	private AppContext appContext; 							//全局Context
	private String nodeUrl;  								//获取附加节点服务地址
	private String appUrl;  								//审批服务地址			
	private String fItemNumber; 							//员工号
	private String fBillName=""; 							//单据名称
	private String showResult; 								//显示操作结果
	
	private Gson gson;										//gson帮助类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pluscopy_main);
		
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
			fType=extras.getString(AppContext.FPLUSCOPY_TYPE_KEY);
			fSystemId=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fBillId=extras.getString(AppContext.FBILLID_KEY);	
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fBillName=extras.getString(AppContext.WORKFLOW_FBILLNAME_KEY);
		}	
			
		//获取新版加签/抄送节点配置信息
		plusCopyHashMap=ParseXmlService.xmlPullParser(getResources().getXml(R.xml.configfile), "PlusCopyNodeRepository");
		nodeUrl=AppUrl.URL_API_HOST_ANDROID_REJECTNODEREPOSITORY;	//获取附加节点服务地址
		appUrl=AppUrl.URL_API_HOST_ANDROID_PLUSCOPYAPPURL;	    	//审批服务地址 	
		
		initView();
		setListener();
		new getNodeAsync().execute(fPlusCopyParam);
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
		fPlusTView=(TextView)findViewById(R.id.pluscopy_main_FPlus);
		if("1".equals(fType)){  //说明是抄送进来
			setTitle(getResources().getString(R.string.copy_main_title));
			fPlusTView.setText(getResources().getString(R.string.pluscopy_main_copy));
			showResult=getResources().getString(R.string.pluscopy_main_copy_app_result);
		}
		else{
			showResult=getResources().getString(R.string.pluscopy_main_plus_app_result);
		}
		fItemNumbersTView=(TextView)findViewById(R.id.pluscopy_main_FItemNumbers);
		fItemNamesTView=(TextView)findViewById(R.id.pluscopy_main_FItemNames);
		fContentEText=(EditText)findViewById(R.id.pluscopy_main_FContent);
		fInImageView=(ImageView)findViewById(R.id.pluscopy_main_FItemNumbers_ImageView);
		fCIView=(ImageView)findViewById(R.id.pluscopy_main_FContent_ImageView);
		chkLayout=(LinearLayout)findViewById(R.id.pluscopy_main_checkBox);
		fConfirm=(Button)findViewById(R.id.pluscopy_main_FConfirm);
		fCancle=(Button)findViewById(R.id.pluscopy_main_FCancle);
		
		//数据加载
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
		
		//审批加载
		appDialog = new ProgressDialog(this);
		appDialog.setMessage(getResources().getString(R.string.dialog_approveing));
		appDialog.setCancelable(false);
		
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(PlusCopyActivity.this);
		rBusiness= (RejectNodeBusiness)factoryBusiness.getInstance("RejectNodeBusiness",nodeUrl);
		
		rArrayList=new ArrayList<RejectNodeInfo>();
		fPlusCopyParam=fSystemId+","+fClassTypeId+","+fBillId;
		personList=new ArrayList<PlusCopyPersonInfo>();
		plusCopyInfo=PlusCopyInfo.getPlusCopyInfo();
		
		gson=GsonHelper.getInstance();
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
		//弹出人员搜索界面
		fInImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showPlusCopyPersonSearch(PlusCopyActivity.this,PlusCopyPersonInfo.ConvertToJson(personList));			
			}
		});
		
		//显示光标可编辑状态
		fCIView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content=fContentEText.getText().toString();
				fContentEText.setCursorVisible(true);	 //显示光标
				fContentEText.requestFocus();  			 //获取焦点
				fContentEText.setSelection(content.length());
				
				//显示软件键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(fContentEText,InputMethodManager.SHOW_IMPLICIT);		
			}
		});
		
		//确定审批
		fConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verify()){
					setModel();
					new plusCopyAppAsync().execute(plusCopyInfo);
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
	 * @ClassName getNodeAsync
	 * @Description  异步获取附加节点实体集合信息
	 * @author 21291
	 * @date 2014年9月24日 下午2:54:16
	 */
	private class getNodeAsync extends AsyncTask<String, Void, List<RejectNodeInfo>>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<RejectNodeInfo> doInBackground(String... params) {
			return getNodeByPost(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<RejectNodeInfo> result) {
			super.onPostExecute(result);
			renderNodeView(result);	
			dialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: getNodeByPost 
	* @Description: 获取附加节点集合
	* @param @param paramStr 传递的参数值
	* @param @return     
	* @return List<RejectNodeInfo>    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 下午4:55:43
	*/
	private List<RejectNodeInfo> getNodeByPost(String paramStr){ 
		// 参数实体-仓储类 工作流
		RepositoryInfo repository = RepositoryInfo.getRepositoryInfo();
		repository.setClassType(plusCopyHashMap.get("ClassType"));
		repository.setIsTest(plusCopyHashMap.get("IsTest"));
		repository.setServiceName(plusCopyHashMap.get("ServiceName"));
		repository.setServiceType(plusCopyHashMap.get("ServiceType"));
		repository.setSqlType(Boolean.valueOf(plusCopyHashMap.get("SqlType")));
		repository.setIsCahce(Boolean.valueOf(plusCopyHashMap.get("IsCahce")));
		repository.setFItemNumber(fItemNumber);		
		return rBusiness.getRejectNodeInfo(repository,paramStr);
	}
	
	/** 
	* @Title: renderNodeView 
	* @Description: 添加附加节点控件
	* @param @param rNodeInfos     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 下午4:42:06
	*/
	@SuppressLint("InflateParams")
	private void renderNodeView(final List<RejectNodeInfo> rNodeInfos){
		if(rNodeInfos.size() > 0){  //说明有附加节点
			int i=1;
			for (RejectNodeInfo item : rNodeInfos) {
				 View linearLayout = LayoutInflater.from(this).inflate(R.layout.pluscopy_main_checkbox, null);
				 
				 final TextView fChkValue=(TextView)linearLayout.findViewById(R.id.pluscopy_main_checkBox_value);
				 fChkValue.setText(item.getFNodeKey());
				 
				 final CheckBox fChkName=(CheckBox)linearLayout.findViewById(R.id.pluscopy_main_checkBox_name);
				 fChkName.setText(item.getFNodeValue());
				 
				 if(i==1){
					 fChkName.setChecked(true); 
					 rArrayList.add(item);
				 }	
				 //复选框选中事件
				 fChkName.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						RejectNodeInfo rNodeInfo=new RejectNodeInfo();
						rNodeInfo.setFNodeKey(fChkValue.getText().toString());
						rNodeInfo.setFNodeValue(fChkName.getText().toString());
						if(isChecked){  //说明是选中的状态
							rArrayList.add(rNodeInfo);
						}
						else{  //移除选中的节点
							for (RejectNodeInfo item : rArrayList) {
								//说明这个节点已经被选中
								if(item.getFNodeKey().equals(rNodeInfo.getFNodeKey()))  
								{
									rArrayList.remove(item);
								}
							}	
						}
					}
				 });
				 
				 chkLayout.addView(linearLayout);
				 i++;
			}
		}
	}
	
	
	/** 
	* @Title: verify 
	* @Description: 确定之前验证
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年9月26日 上午9:37:14
	*/
	private boolean verify(){
		if(rArrayList.size()<=0){  //附加节点
			UIHelper.ToastMessage(PlusCopyActivity.this, getResources().getString(R.string.pluscopy_main_nodes_error));
			return false;
		}
		if(StringUtils.isEmpty(fItemNumbersTView.getText().toString()) || StringUtils.isEmpty(fItemNamesTView.getText().toString())){
			if("0".equals(fType)){  //加签
				UIHelper.ToastMessage(PlusCopyActivity.this, getResources().getString(R.string.pluscopy_main_plus_error));
			}
			else{  //抄送
				UIHelper.ToastMessage(PlusCopyActivity.this, getResources().getString(R.string.pluscopy_main_copy_error));
			}
			return false;
		}	
		return true;
	}
	
	/** 
	* @Title: setModel 
	* @Description: 设置实体类信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月26日 上午9:50:27
	*/
	private void setModel(){
		int i=0;
		int rCount=rArrayList.size();
		StringBuffer sb = new StringBuffer();	
		for (RejectNodeInfo item : rArrayList) {
			if(i==rCount-1){
				sb.append(item.getFNodeKey());
			}
			else{
				sb.append(item.getFNodeKey()).append(",");
			}
			i++;
		}
		
		plusCopyInfo.setFAppKey(AppContext.WORKFLOW_NEWOFFICE_KEY);
		plusCopyInfo.setFSystemId(fSystemId);
		plusCopyInfo.setFClassTypeId(fClassTypeId);
		plusCopyInfo.setFBillId(fBillId);
		plusCopyInfo.setFItemNumber(fItemNumber);
		plusCopyInfo.setFType(fType);
		plusCopyInfo.setFNodeIds(sb.toString());
		plusCopyInfo.setFPersonNumbers(fItemNumbersTView.getText().toString());
		plusCopyInfo.setFContent(fContentEText.getText().toString());
	}
	
	/**
	 * @ClassName plusCopyAppAsync
	 * @Description 加签/抄送审批操作
	 * @author 21291
	 * @date 2014年9月26日 上午10:03:24
	 */
	private class plusCopyAppAsync extends AsyncTask<PlusCopyInfo,Void,ResultMessage>{
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			appDialog.show(); // 显示等待框
		}
		
		// 主要是完成耗时操作
		@Override
		protected ResultMessage doInBackground(PlusCopyInfo... params) {
			return plusCopyServer(params[0]);
		}
		
		// 完成更新UI操作
		@Override
		protected void onPostExecute(ResultMessage result) {
			super.onPostExecute(result);
			showAppResult(result);	
			appDialog.dismiss(); // 销毁等待框
		}	
	}
	
	/** 
	* @Title: plusCopyServer 
	* @Description: 异步操作处理业务逻辑方法
	* @param @param pCopyInfo
	* @param @return     
	* @return ResultMessage    
	* @throws 
	* @author 21291
	* @date 2014年9月26日 上午10:08:38
	*/
	private ResultMessage plusCopyServer(PlusCopyInfo pCopyInfo){
		rBusiness.setServiceUrl(appUrl);
		return rBusiness.plusCopyApp(pCopyInfo);
	}
	
	/** 
	* @Title: showAppResult 
	* @Description: 显示加签/抄送操作结果
	* @param @param result     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月26日 上午10:15:12
	*/
	private void showAppResult(ResultMessage result){
		if(!result.isIsSuccess()){  //说明操作失败
			showResult=result.getResult();
		}
		UIHelper.ToastMessage(PlusCopyActivity.this, showResult);
		//sendLogs(fType); 	//发送日志信息进行统计
		
		// 延迟2秒跳回
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        		finish();     
            }
        }, 2000);
	}
	
	
	// 从人员页面,回调方法
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
	* @date 2014年9月25日 上午11:46:25
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
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年9月19日 上午11:01:28
	*/
	private void getAttendPerson(final List<PlusCopyPersonInfo> personList){
		int i=0;
		String strItemNumbers="";
		String strNames="";
		int personCount=personList.size();
		if(personCount > 0){
			StringBuffer sb1 = new StringBuffer();	
			StringBuffer sb2 = new StringBuffer();	
			for (PlusCopyPersonInfo item : personList) {
				if(i==personCount-1){
					sb1.append(item.getFItemNumber());
					sb2.append(item.getFItemName());
				}
				else{
					sb1.append(item.getFItemNumber()).append(",");
					sb2.append(item.getFItemName()).append(",");
				}
				i++;
			}
			strItemNumbers=sb1.toString();
			strNames=sb2.toString();
		}
		fItemNumbersTView.setText(strItemNumbers);
		fItemNamesTView.setText(strNames);
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 加签/抄送操作时，发送日志记录到服务器
	* @param @param fType 操作类型     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 下午5:20:22
	*//*
	private void sendLogs(String fType){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_mytasks));
		if("0".equals(fType)){  //加签
			logInfo.setFActionName("plus");
		}
		else{//抄送
			logInfo.setFActionName("copy");
		}
	
		logInfo.setFNote(fBillName);
		UIHelper.sendLogs(PlusCopyActivity.this,logInfo);
	}*/
	
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
