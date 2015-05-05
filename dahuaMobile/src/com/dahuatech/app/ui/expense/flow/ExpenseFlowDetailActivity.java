package com.dahuatech.app.ui.expense.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.ExpenseFlowDetailInfo;
import com.dahuatech.app.business.ExpenseBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

/**
 * @ClassName ExpenseFlowDetailActivity
 * @Description 流水详细页面
 * @author 21291
 * @date 2014年8月22日 上午11:28:38
 */
@SuppressLint("InflateParams")
public class ExpenseFlowDetailActivity extends MenuActivity {
	private ArrayAdapter<String> childAdapter;  	 		//子类适配器
	
	private String[] fTravelLabelArray;						//差旅费标签值数组
	private String[] fSocializeLabelArray;					//交际应酬费标签值数组
	private String[] fExpendTypeValueArray;					//消费类型实际值数组
	private String[] fTravelValueArray;						//差旅费实际值数组
	private String[] fSocializeValueArray;					//交际应酬费实际值数组
	private String[] fBusinesslevelValueArray;				//出差地级别实际值数组
	private String[] fNocardValueArray;						//未刷卡原因实际值数组
	
	private int fExpenseType;								//消费类型 0-差旅费,1-交际应酬费
	private int fBindParentPosition = 0;					//父级绑定位置
	private int fBindChildPosition = 0;						//子类绑定位置
	
	private LinearLayout fSubLinearLayout; 					//子类布局全局变量
	private AppContext appContext; 							//全局Context
	private String fItemNumber;  							//员工号
	private String fPayType;  								//支付类型  值为“信用卡”或“携程”，不允许修改消费时间，地点，金额
	private String serviceUrl;  							//上传服务器地址
	private String fDefaultExpendTime;  					//默认消费时间
	private String fSkipSource;  							//跳转来源，有直接打车跳转过来或新增过来
	
	private Calendar cal;									//日期类
	
	private ExpenseBusiness eBusiness;						//业务逻辑类
	private ExpenseFlowDetailInfo exDetailInfo;				//详情实体类
	private DbManager mDbHelper;					//数据库管理类
	private Boolean btnResult;								//按钮操作结果
	private String showLocalResult;							//显示暂存操作结果
	private String showUploadResult;						//显示上传操作结果
	private ProgressDialog uploadDialog;      				//上传服务弹出框
	
	private EditText fExpendTime,fExpendAmount,fExpendAddress,fCause,fAccompanyReason; //父类控件
	private Spinner parentSpinner,childSpinner;				//父类和子类
	private TextView fClientId,fProjectId,fClient,fProject;	//客户和项目ID和名称
	private TextView fAccompanylabel;						//陪同人员标签
	private RadioGroup fAccompany;							//有无陪同人员
	private RadioButton fRadioYes,fRadioNo;					//有或无
	private Button btnTemp,btnUpload;						//暂存、上传按钮
	private ImageView fExpendAddressIV,fCauseIV,fClientIV,fProjectIV,fAccompanyReasonIV;
	
	private EditText fStartAddress,fStartTime,fEndAddress,fEndTime,fDescription;  //子类控件
	private Spinner fBusinessLevelSpinner,fReasonSpinner;	
	private ImageView fStartAddressIV,fStartTimeIV,fEndAddressIV,fEndTimeIV,fDescriptionIV;
	
	private int fLocalId=0; 								//本地主键内码 流水详情状态 0-新增,1-修改
	private int fServerId=0; 								//服务器主键内码 流水详情状态 0-新增,1-修改
	private String fExpendTimeValue,fExpendAmountValue,fExpendAddressValue,fCauseValue,fAccompanyReasonValue; //父类控件值
	private String fSpinnerParent,fSpinnerChild;			//消费类型  父类值 子类值
	private String fClientIdValue,fProjectIdValue;			//客户和项目ID值
	private String fClientName,fProjectName;				//客户和项目名称值
	private String fAccompanyValue;                    	 	//有无陪同人员值
	
	private String fStartAddressValue,fStartTimeValue,fEndAddressValue,fEndTimeValue,fDescriptionValue; //子类控件值
	private String fBusinessLevelValue,fReasonValue;		//子类控件Spinner具体值	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper=new DbManager(this);
		mDbHelper.openSqlLite();			//打开数据库
		setContentView(R.layout.expense_flowdetail);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);		
		
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
			//默认和必要传递值
			fPayType=extras.getString(DbManager.KEY_DETAIL_FPAYTYPE);
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
			fDefaultExpendTime=extras.getString(AppContext.EXPENSE_FLOW_DETAIL_EXPENDTIME);
			fSkipSource=extras.getString(AppContext.EXPENSE_FLOW_DETAIL_SKIP_SOURCE);
			
			fLocalId=extras.getInt(DbManager.KEY_ROWID,0);
			fServerId=extras.getInt(DbManager.KEY_DETAIL_FSERVERID,0);
			//获取流水父类详情信息
			fExpendTimeValue=extras.getString(DbManager.KEY_DETAIL_FEXPENDTIME);
			fSpinnerParent=extras.getString(DbManager.KEY_DETAIL_FEXPENDTYPEPARENT);
			fSpinnerChild=extras.getString(DbManager.KEY_DETAIL_FEXPENDTYCHILD);
			fExpendAddressValue=extras.getString(DbManager.KEY_DETAIL_FEXPENDADDRESS);
			fExpendAmountValue=extras.getString(DbManager.KEY_DETAIL_FEXPENDAMOUNT);
			fCauseValue=extras.getString(DbManager.KEY_DETAIL_FCAUSE);
			fClientIdValue=extras.getString(DbManager.KEY_DETAIL_FCLIENTID);
			fProjectIdValue=extras.getString(DbManager.KEY_DETAIL_FPROJECTID);
			fClientName=extras.getString(DbManager.KEY_DETAIL_FCLIENT);
			fProjectName=extras.getString(DbManager.KEY_DETAIL_FPROJECT);
			fAccompanyValue=extras.getString(DbManager.KEY_DETAIL_FACCOMPANY);
			fAccompanyReasonValue=extras.getString(DbManager.KEY_DETAIL_FACCOMPANYREASON);
			//获取流水子类详情信息
			fStartAddressValue=extras.getString(DbManager.KEY_DETAIL_FSTART);
			fEndAddressValue=extras.getString(DbManager.KEY_DETAIL_FDESTINATION);
			fStartTimeValue=extras.getString(DbManager.KEY_DETAIL_FSTARTTIME);
			fEndTimeValue=extras.getString(DbManager.KEY_DETAIL_FENDTIME);
			fBusinessLevelValue=extras.getString(DbManager.KEY_DETAIL_FBUSINESSLEVEL);
			fReasonValue=extras.getString(DbManager.KEY_DETAIL_FREASON);
			fDescriptionValue=extras.getString(DbManager.KEY_DETAIL_FDESCRIPTION);
		}
		fSubLinearLayout=(LinearLayout)findViewById(R.id.expense_flowdetail_LinearLayout);
		addChildView();	 //添加子类视图
		
		initView();  	//初始化视图
		if(fLocalId > 0 || fServerId > 0){  //修改页面
			bindView();
		}
		else {   //新增页面
			setView();
		}
		setParentListener();		//设置父类控件监听事件
		setChildListener();			//设置子类控件监听事件		
		setChildView();	            //显示子类视图
	}
	
	/** 
	* @Title: initView 
	* @Description: 初始化视图控件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午2:19:58
	*/
	private void initView(){
		//数据源
		fTravelLabelArray=this.getResources().getStringArray(R.array.travel_labels_array);
		fSocializeLabelArray=this.getResources().getStringArray(R.array.socialize_labels_array);
		fExpendTypeValueArray=this.getResources().getStringArray(R.array.expendtype_value_array);
		fTravelValueArray=this.getResources().getStringArray(R.array.travel_value_array);
		fSocializeValueArray=this.getResources().getStringArray(R.array.socialize_value_array);
		fBusinesslevelValueArray=this.getResources().getStringArray(R.array.businesslevel_value_array);
		fNocardValueArray=this.getResources().getStringArray(R.array.nocard_value_array);
		
		//业务逻辑类实例化
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseFlowDetailActivity.this);
		eBusiness= (ExpenseBusiness)factoryBusiness.getInstance("ExpenseBusiness",serviceUrl);
		
		//实体类
		exDetailInfo=ExpenseFlowDetailInfo.getExpenseFlowDetailInfo();
		
		//父类控件
		fExpendTime=(EditText)findViewById(R.id.expense_flowdetail_FExpendTime);
		fExpendTime.setInputType(InputType.TYPE_NULL);
		fExpendAmount=(EditText)findViewById(R.id.expense_flowdetail_FExpendAmount);
		
		parentSpinner=(Spinner)findViewById(R.id.expense_flowdetail_Spinner_Parent);
		childSpinner=(Spinner)findViewById(R.id.expense_flowdetail_Spinner_Child);
		
		fExpendAddress=(EditText)findViewById(R.id.expense_flowdetail_FExpendAddress);
		fClientId=(TextView)findViewById(R.id.expense_flowdetail_FClient_FId);
	    fProjectId=(TextView)findViewById(R.id.expense_flowdetail_FProject_FId);
		fClient=(TextView)findViewById(R.id.expense_flowdetail_FClient);
		fProject=(TextView)findViewById(R.id.expense_flowdetail_FProject);
		fCause=(EditText)findViewById(R.id.expense_flowdetail_FCause);
		
		fAccompany = (RadioGroup) findViewById(R.id.expense_flow_detail_FAccompany);
		fRadioYes = (RadioButton) findViewById(R.id.expense_flow_detail_FAccompany_yes);
		fRadioNo = (RadioButton) findViewById(R.id.expense_flow_detail_FAccompany_no); 
		fAccompanylabel=(TextView)findViewById(R.id.expense_flow_detail_FAccompanylabel);
		fAccompanyReason=(EditText)findViewById(R.id.expense_flow_detail_FAccompanyReason);
		
		//父类图标控件
		fExpendAddressIV=(ImageView)findViewById(R.id.expense_flowdetail_FExpendAddress_ImageView);
		fCauseIV=(ImageView)findViewById(R.id.expense_flowdetail_FCause_ImageView);
		fClientIV=(ImageView)findViewById(R.id.expense_flowdetail_FClient_ImageView);
		fProjectIV=(ImageView)findViewById(R.id.expense_flowdetail_FProject_ImageView);
		fAccompanyReasonIV=(ImageView)findViewById(R.id.expense_flow_detail_FAccompanyReason_ImageView);
		
		//差旅费子类控件
		fStartAddress=(EditText)findViewById(R.id.expense_flow_detail_travel_FStartAddress);
		fStartTime=(EditText)findViewById(R.id.expense_flow_detail_travel_FStartTime);
		fStartTime.setInputType(InputType.TYPE_NULL);
		fEndAddress=(EditText)findViewById(R.id.expense_flow_detail_travel_FEndAddress);
		fEndTime=(EditText)findViewById(R.id.expense_flow_detail_travel_FEndTime);
		fEndTime.setInputType(InputType.TYPE_NULL);
		fBusinessLevelSpinner=(Spinner)findViewById(R.id.expense_flow_detail_travel_FBusinessLevel);
		//差旅费子类图标控件
		fStartAddressIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FStartAddress_ImageView);
		fStartTimeIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FStartTime_ImageView);
		fEndAddressIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FEndAddress_ImageView);
		fEndTimeIV=(ImageView)findViewById(R.id.expense_flow_detail_travel_FEndTime_ImageView);
		
		//交际应酬费子类控件
		fReasonSpinner=(Spinner)findViewById(R.id.expense_flow_detail_socialize_FReason);
		fDescription=(EditText)findViewById(R.id.expense_flowdetail_socialize_FDescription);
		//交际应酬费子类图标控件
		fDescriptionIV=(ImageView)findViewById(R.id.expense_flowdetail_socialize_FDescription_ImageView);
		
		//暂存和上传控件
		btnTemp=(Button)findViewById(R.id.expense_flow_detail_button_temp);
		btnUpload=(Button)findViewById(R.id.expense_flow_detail_button_upload);
		
		//日期
		cal = Calendar.getInstance();
		
		//初始化返回结果
		btnResult=false;
		showLocalResult=getResources().getString(R.string.expense_flow_detail_show_local_failure);
		showUploadResult=getResources().getString(R.string.expense_flow_detail_show_upload_failure);
	
		uploadDialog = new ProgressDialog(this);
		uploadDialog.setMessage(getResources().getString(R.string.dialog_uploading));
		uploadDialog.setCancelable(false);
	}
	
	/** 
	* @Title: setView 
	* @Description: 新增进来-设置视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月5日 下午1:48:59
	*/
	private void setView(){
		//初始化默认值
		if(StringUtils.isEmpty(fDefaultExpendTime)){
			fDefaultExpendTime=StringUtils.toShortDateString(cal.getTime());
		}
		fExpendTime.setText(fDefaultExpendTime);	
		
		parentSpinner.setSelection(fBindParentPosition,true);	//默认选中差旅费
		childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fTravelLabelArray);		
		childSpinner.setAdapter(childAdapter); 
		childSpinner.setSelection(fBindChildPosition,true); 	//设置子类选中项
		
		//默认消费类型
		fSpinnerParent = getResources().getStringArray(R.array.expendtype_value_array)[parentSpinner.getSelectedItemPosition()];
		fSpinnerChild = getResources().getStringArray(R.array.travel_value_array)[childSpinner.getSelectedItemPosition()];
		
		//默认选中有陪同人员
		fAccompany.check(fRadioYes.getId()); 
		fAccompanyValue="有";
		
		//默认子类值
		fBusinessLevelValue = getResources().getStringArray(R.array.businesslevel_value_array)[fBusinessLevelSpinner.getSelectedItemPosition()];
		fReasonValue = getResources().getStringArray(R.array.nocard_value_array)[fReasonSpinner.getSelectedItemPosition()];
	
		//打车报销跳到流水
		if(!StringUtils.isEmpty(fExpendAmountValue)){
			fExpendAmount.setText(fExpendAmountValue);
		}
		if(!StringUtils.isEmpty(fStartAddressValue)){
			fStartAddress.setText(fStartAddressValue);
		}
		if(!StringUtils.isEmpty(fStartTimeValue)){
			fStartTime.setText(fStartTimeValue);
		}
		if(!StringUtils.isEmpty(fEndAddressValue)){
			fEndAddress.setText(fEndAddressValue);
		}
		if(!StringUtils.isEmpty(fEndTimeValue)){
			fEndTime.setText(fEndTimeValue);
		}
	}
	
	/** 
	* @Title: bindView 
	* @Description:  修改进来-绑定视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月5日 下午1:49:18
	*/
	private void bindView(){
		//状态控制  信用卡/携程不能修改消费时间、地点、金额
		if("信用卡".equals(fPayType) || "携程".equals(fPayType)){
			fExpendTime.setEnabled(false);
			fExpendAmount.setEnabled(false);
			fExpendAddress.setEnabled(false);
			fExpendAddressIV.setVisibility(View.GONE);
		}
		
		//父类控件赋值
		if(!StringUtils.isEmpty(fExpendTimeValue)){
			fExpendTime.setText(fExpendTimeValue);
		}		
		if(!StringUtils.isEmpty(fExpendAmountValue)){
			fExpendAmount.setText(fExpendAmountValue);
		}	
		if("2006".equals(fSpinnerParent)){  //差旅费
			childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fTravelLabelArray);		
			fBindChildPosition=Arrays.asList(fTravelValueArray).indexOf(fSpinnerChild);
		}
		else{  //交际应酬费
			childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fSocializeLabelArray);
			fBindChildPosition=Arrays.asList(fSocializeValueArray).indexOf(fSpinnerChild);
		}
		fBindParentPosition=Arrays.asList(fExpendTypeValueArray).indexOf(fSpinnerParent);	
		childSpinner.setAdapter(childAdapter);  //绑定适配器和值
		
		parentSpinner.setSelection(fBindParentPosition,true);	 //设置父类选中项
		childSpinner.setSelection(fBindChildPosition,true);  	 //设置子类选中项
			
		if(!StringUtils.isEmpty(fExpendAddressValue)){
			fExpendAddress.setText(fExpendAddressValue);
		}		
		if(!StringUtils.isEmpty(fExpendAddressValue)){
			fExpendAddress.setText(fExpendAddressValue);
		}	
		if(!StringUtils.isEmpty(fCauseValue)){
			fCause.setText(fCauseValue);
		}	
		if(!StringUtils.isEmpty(fClientIdValue)){
			fClientId.setText(fClientIdValue);
		}	
		if(!StringUtils.isEmpty(fProjectIdValue)){
			fProjectId.setText(fProjectIdValue);
		}
		if(!StringUtils.isEmpty(fClientName)){
			fClient.setText(fClientName);
		}	
		if(!StringUtils.isEmpty(fProjectName)){
			fProject.setText(fProjectName);
		}
		
		if("1".equals(fAccompanyValue)){
			fAccompany.check(fRadioYes.getId()); 
		}
		else {
			fAccompany.check(fRadioNo.getId()); 
		}	
		
		if(!StringUtils.isEmpty(fAccompanyReasonValue)){
			fAccompanyReason.setText(fAccompanyReasonValue);
		}
		
		//子类控件赋值
		if(!StringUtils.isEmpty(fStartAddressValue)){
			fStartAddress.setText(fStartAddressValue);
		}
		if(!StringUtils.isEmpty(fStartTimeValue)){
			fStartTime.setText(fStartTimeValue);
		}
		if(!StringUtils.isEmpty(fEndAddressValue)){
			fEndAddress.setText(fEndAddressValue);
		}
		if(!StringUtils.isEmpty(fEndTimeValue)){
			fEndTime.setText(fEndTimeValue);
		}
		int fBindBusinessLevelPosition=Arrays.asList(fBusinesslevelValueArray).indexOf(fBusinessLevelValue);
		fBusinessLevelSpinner.setSelection(fBindBusinessLevelPosition,true);	 //设置出差地级别选中项
		
		int fBindReasonPosition=Arrays.asList(fNocardValueArray).indexOf(fReasonValue);
		fReasonSpinner.setSelection(fBindReasonPosition,true);	 				//设置未刷卡原因选中项
		if(!StringUtils.isEmpty(fDescriptionValue)){
			fDescription.setText(fDescriptionValue);
		}	
	}
	
	/** 
	* @Title: setControlListener 
	* @Description: 设置父类控件监听事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午2:21:46
	*/
	private void setParentListener(){
		
		//消费时间点击监听
		fExpendTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {	
				new DatePickerDialog(ExpenseFlowDetailActivity.this ,expendTimeDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();   
				}	
		});
		
		//父级下拉框监听
		parentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			// 动态改变地级适配器的绑定值
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				if(position==0){ //差旅费
					fExpenseType=0;
					childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fTravelLabelArray);	
					hiddenSocializeView();
					showTravelView();
				}
				else{  //交际应酬费
					fExpenseType=1;
					childAdapter = new ArrayAdapter<String>(ExpenseFlowDetailActivity.this,android.R.layout.simple_spinner_item, fSocializeLabelArray);
					hiddenTravelView();
					showSocializeView();
				}
				childSpinner.setAdapter(childAdapter);
				childSpinner.setSelection(0,true);  
				fSpinnerParent = getResources().getStringArray(R.array.expendtype_value_array)[parentSpinner.getSelectedItemPosition()];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
				
		//子级下拉框监听
		childSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				switch (fExpenseType) {
					case 0:  //差旅费
						fSpinnerChild = getResources().getStringArray(R.array.travel_value_array)[childSpinner.getSelectedItemPosition()];
						break;
					case 1:  //交际应酬费
						fSpinnerChild = getResources().getStringArray(R.array.socialize_value_array)[childSpinner.getSelectedItemPosition()];
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
		
		//有无陪同人员点击事件
		fAccompany.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==fRadioYes.getId())
			    	{ 
				     	fAccompanylabel.setText(getResources().getString(R.string.expense_flow_detail_FAccompanyPerson));
				     	fAccompanyValue=fRadioYes.getText().toString();
			    	} 
			      	else
			      	{ 
				     	fAccompanylabel.setText(getResources().getString(R.string.expense_flow_detail_FAccompanyReason));
			      		fAccompanyValue=fRadioNo.getText().toString();
			      	}       
			}
		});
		
		//暂存事件
		btnTemp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verifyControlValue()){  //验证通过
					getControlValue();
					setModel();
					tempSave();
				}
			}
		});
		
		//上传事件
		btnUpload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(verifyControlValue()){  //验证通过
					getControlValue();
					setModel();
					uploadSave();
				}
			}
		});
		
		//消费地点
		fExpendAddressIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fExpendAddress);
			}
		});
		
		//消费原因
		fCauseIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fCause);
			}
		});
		
		//消费原因
		fCauseIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fCause);
			}
		});
		
		//客户名称
		fClientIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseFlowClientList(ExpenseFlowDetailActivity.this,fItemNumber);
			}
		});
		
		//项目名称
		fProjectIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UIHelper.showExpenseFlowProjectList(ExpenseFlowDetailActivity.this,fItemNumber);
			}
		});
		
		//有无陪同原因/人员
		fAccompanyReasonIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fAccompanyReason);
			}
		});	
	}
	
	/** 
	* @Title: setChildListener 
	* @Description: 设置子类控件监听事件
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午2:24:05
	*/
	private void setChildListener(){		
		//出差地级别下拉框监听
		fBusinessLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				fBusinessLevelValue = getResources().getStringArray(R.array.businesslevel_value_array)[fBusinessLevelSpinner.getSelectedItemPosition()];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
		
		//交际应酬费子类控件监听	 
		//未刷卡原因下拉框监听
		fReasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				fReasonValue = getResources().getStringArray(R.array.nocard_value_array)[fReasonSpinner.getSelectedItemPosition()];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {	}	
		});
		
		//出发地点
		fStartAddressIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fStartAddress);
			}
		});	
		
		//目的地
		fEndAddressIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fEndAddress);
			}
		});	
		
		//出发时间
		fStartTimeIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(ExpenseFlowDetailActivity.this ,startTimeDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();   
			}
		});	
		
		//结束时间
		fEndTimeIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(ExpenseFlowDetailActivity.this ,endTimeDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();   
			}
		});	
		
		//说明
		fDescriptionIV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showInputBox(fDescription);
			}
		});	
	}
	
	/** 
	* @Title: showInputBox 
	* @Description: 显示设置输入文本框
	* @param @param editText     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月22日 上午11:49:12
	*/
	private void showInputBox(final EditText editText){
		String content=editText.getText().toString();
		editText.setCursorVisible(true);	 //显示光标
		editText.requestFocus();  //获取焦点
		editText.setSelection(content.length());
		//显示软件键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
	}
	
	/** 
	* @Title: addChildView 
	* @Description: 添加子类视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午5:05:22
	*/
	private void addChildView(){
		View travelLinearLayout = LayoutInflater.from(this).inflate(R.layout.expense_flow_travel, null);
		View socializeLinearLayout = LayoutInflater.from(this).inflate(R.layout.expense_flow_socialize, null);
		View buttonLinearLayout = LayoutInflater.from(this).inflate(R.layout.expense_flowdetail_button, null);
		fSubLinearLayout.addView(travelLinearLayout);
		fSubLinearLayout.addView(socializeLinearLayout);
		fSubLinearLayout.addView(buttonLinearLayout);
	}
	
	/** 
	* @Title: setChildView 
	* @Description: 设置子类视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月3日 下午7:03:44
	*/
	private void setChildView(){
		if(fBindParentPosition==0){  //隐藏交际应酬费子类视图
			hiddenSocializeView();
		}
		else{  //隐藏差旅费子类视图
			hiddenTravelView();
		}
	}
	
	/** 
	* @Title: showTravelView 
	* @Description: 显示差旅费子类视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月3日 下午7:41:45
	*/
	private void showTravelView(){
		LinearLayout showLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_travel);
		showLayout.setVisibility(View.VISIBLE);
	}
	
	/** 
	* @Title: showSocializeView 
	* @Description: 显示交际应酬费子类视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月3日 下午7:41:47
	*/
	private void showSocializeView(){
		LinearLayout showLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_socialize);
		showLayout.setVisibility(View.VISIBLE);
	}
	
	/** 
	* @Title: hiddenTravelView 
	* @Description: 隐藏差旅费子类视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月3日 下午5:30:52
	*/
	private void hiddenTravelView(){
		LinearLayout hiddenLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_travel);
		hiddenLayout.setVisibility(View.GONE);
	}
	
	/** 
	* @Title: hiddenSocializeView 
	* @Description: 隐藏交际应酬费子类视图
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月3日 下午7:26:03
	*/
	private void hiddenSocializeView(){
		LinearLayout hiddenLayout=(LinearLayout)findViewById(R.id.expense_flow_detail_socialize);
		hiddenLayout.setVisibility(View.GONE);
	}
	
	//消费时间监听器
	private DatePickerDialog.OnDateSetListener expendTimeDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);
			  setDate(fExpendTime);
		}
	};
	
	//出发时间监听器
	private DatePickerDialog.OnDateSetListener startTimeDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);  
			  setDate(fStartTime);
		}
	};
		
	//到达时间监听器
	private DatePickerDialog.OnDateSetListener endTimeDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);   
			  setDate(fEndTime);
		}
	};

	/** 
	* @Title: setDate 
	* @Description: 设置日期
	* @param @param editText     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午1:50:35
	*/
	private void setDate(EditText editText){
		editText.setText(StringUtils.toShortDateString(cal.getTime()));
	}
	
	// 从客户/项目页面,回调方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.EXPENSE_FLOW_DETAIL_CLIENT:
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						fClientId.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID));
						fClient.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME));
					}
				}			
				break;
			case AppContext.EXPENSE_FLOW_DETAIL_PROJECT:
				if(resultCode==RESULT_OK){
					Bundle extras = data.getExtras();
					if(extras!=null){
						fProjectId.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FID));
						fProject.setText(extras.getString(AppContext.EXPENSE_FLOW_DETAIL_BACK_FNAME));
					}
				}	
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: getControlValue 
	* @Description: 获取控件值
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午3:02:53
	*/
	private void getControlValue(){
		//父类值
		fExpendTimeValue=fExpendTime.getText().toString();
		fExpendAmountValue=fExpendAmount.getText().toString();
		fExpendAddressValue=fExpendAddress.getText().toString();
		fCauseValue=fCause.getText().toString();
		fAccompanyReasonValue=fAccompanyReason.getText().toString();	
		fClientIdValue=fClientId.getText().toString();
		fProjectIdValue=fProjectId.getText().toString();
		fClientName=fClient.getText().toString();
		fProjectName=fProject.getText().toString();
		
		//子类值
		fStartAddressValue=fStartAddress.getText().toString();
		fStartTimeValue=fStartTime.getText().toString();
		fEndAddressValue=fEndAddress.getText().toString();		
		fEndTimeValue=fEndTime.getText().toString();
		fDescriptionValue=fDescription.getText().toString();
	}
	
	/** 
	* @Title: verifyControlValue 
	* @Description: 验证必填字段
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午7:58:33
	*/
	private boolean  verifyControlValue(){
		boolean result=true;
		if(StringUtils.isEmpty(fExpendAmount.getText().toString())){
			UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, getResources().getString(R.string.expense_flow_detail_notInput_FExpendAmount));
			result= false;
		}else if (StringUtils.isEmpty(fExpendAddress.getText().toString())) {
			UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, getResources().getString(R.string.expense_flow_detail_notInput_FExpendAddress));
			result= false; 	
		}else if(StringUtils.isEmpty(fCause.getText().toString())) {
			UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, getResources().getString(R.string.expense_flow_detail_notInput_FCause));
			result= false;
		}
		return result;
	}
	
	/** 
	* @Title: setModel 
	* @Description: 设置实体类信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月5日 上午10:52:09
	*/
	private void setModel(){
		exDetailInfo.setFLocalId(fLocalId);
		exDetailInfo.setFServerId(fServerId);
		//父类
		exDetailInfo.setFPayType(fPayType);
		exDetailInfo.setFItemNumber(fItemNumber);
		exDetailInfo.setFExpendTime(fExpendTimeValue);
		exDetailInfo.setFExpendTypeParent(fSpinnerParent);
		exDetailInfo.setFExpendTypeChild(fSpinnerChild);
		exDetailInfo.setFExpendAddress(fExpendAddressValue);
		exDetailInfo.setFExpendAmount(fExpendAmountValue);
		exDetailInfo.setFCause(fCauseValue);
		exDetailInfo.setFClientId(fClientIdValue);
		exDetailInfo.setFProjectId(fProjectIdValue);
		exDetailInfo.setFClient(fClientName);
		exDetailInfo.setFProject(fProjectName);
		if("有".equals(fAccompanyValue)){
			exDetailInfo.setFAccompany("1");
		}
		else {
			exDetailInfo.setFAccompany("2");
		}	
		exDetailInfo.setFAccompanyReason(fAccompanyReasonValue);
		//差旅费子类
		exDetailInfo.setFStart(fStartAddressValue);
		exDetailInfo.setFDestination(fEndAddressValue);
		exDetailInfo.setFStartTime(fStartTimeValue);
		exDetailInfo.setFEndTime(fEndTimeValue);	
		exDetailInfo.setFBusinessLevel(fBusinessLevelValue);
		//交际应酬费子类
		exDetailInfo.setFReason(fReasonValue);
		exDetailInfo.setFDescription(fDescriptionValue);
	}

	
	/** 
	* @Title: tempSave 
	* @Description: 暂存保存
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午8:05:38
	*/
	private void tempSave(){	
		exDetailInfo.setFUploadFlag("0");
		if(fLocalId > 0){ //修改操作
			btnResult=mDbHelper.updateFlowDetail(exDetailInfo);
		}
		else{ //新增操作
			btnResult=mDbHelper.insertFlowDetail(exDetailInfo);
		}
		if(btnResult){
			showLocalResult=getResources().getString(R.string.expense_flow_detail_show_local_success);	
		}
		UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, showLocalResult);
		tempBack();
	}
	
	/** 
	* @Title: tempBack 
	* @Description: 暂存返回
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 下午5:13:09
	*/
	private void tempBack(){
		// 延迟2秒跳回列表页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	mDbHelper.closeSqlLite();
        		if(fLocalId > 0){  //修改
        			Intent intent = new Intent();
        			setResult(RESULT_OK, intent);
        		}
        		else{//新增
        			UIHelper.showExpenseFlowLocalList(ExpenseFlowDetailActivity.this, fItemNumber);   		
        		}
        		finish();     
            }
        }, 2000);	
	}
	
	/** 
	* @Title: uploadSave 
	* @Description: 上传保存
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月4日 下午8:05:36
	*/
	@SuppressWarnings("unchecked")
	private void uploadSave(){
		exDetailInfo.setFUploadFlag("1");
		List<ExpenseFlowDetailInfo> intendList=new ArrayList<ExpenseFlowDetailInfo>();
		intendList.add(exDetailInfo);
		new detailUploadAsync().execute(intendList);
	}
	
	/**
	 * @ClassName detailUploadAsync
	 * @Description 上传数据到服务器
	 * @author 21291
	 * @date 2014年9月10日 下午3:57:00
	 */
	private class detailUploadAsync extends AsyncTask<List<ExpenseFlowDetailInfo>,Void,ResultMessage>{
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
	* @date 2014年9月10日 下午4:00:38
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
	* @date 2014年9月10日 下午4:02:23
	*/
	private void showUploadResult(ResultMessage resultMessage){
		if(resultMessage.isIsSuccess()){  //说明上传成功
			sendLogs(); //发送日志信息进行统计
			showUploadResult=getResources().getString(R.string.expense_flow_detail_show_upload_success);
		}
		UIHelper.ToastMessage(ExpenseFlowDetailActivity.this, showUploadResult);
		uploadBack();
	}
	
	/** 
	* @Title: uploadBack 
	* @Description: 上传返回
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月9日 下午5:12:42
	*/
	private void uploadBack(){
		
		// 延迟2秒跳回列表页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	mDbHelper.closeSqlLite();
        		if(StringUtils.isEmpty(fSkipSource)){
        			Intent intent = new Intent();
        			setResult(RESULT_OK, intent);      		
        		}
        		else{
        			UIHelper.showExpenseFlowList(ExpenseFlowDetailActivity.this,fItemNumber);
        		}
        		finish();  
            }
        }, 2000);	
	
	}
	
	/** 
	* @Title: sendLogs 
	* @Description: 点击保存时，发送日志记录到服务器
	* @param @param fOrderId     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年9月28日 下午6:56:25
	*/
	private void sendLogs(){
		LogsRecordInfo logInfo=LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_expense_flow));
		logInfo.setFActionName("upload");
		logInfo.setFNote("note");
		UIHelper.sendLogs(ExpenseFlowDetailActivity.this,logInfo);
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
	}
}
