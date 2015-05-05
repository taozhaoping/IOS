package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.mytask.ExpenseCostTypeInfo;
import com.dahuatech.app.bean.mytask.ExpensePrivateTHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ExpensePrivateTHeaderBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.common.ViewIdGenerator;
import com.dahuatech.app.inter.ITaskContext;
import com.dahuatech.app.ui.main.MenuActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName ExpenseActivity
 * @Description 对私报销表头单据Activity
 * @author 21291
 * @date 2014年5月20日 下午2:06:04
 */
public class ExpensePrivateTHeaderActivity extends MenuActivity implements ITaskContext{
	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fConSmName,fCommitDate,fConSmAmountAll;
	private TaskParamInfo taskParam;  //参数类
	private LinearLayout fCostTypeLinearLayout; //费用类型布局全局变量
	private Button appButton;				//审批按钮
	private TableLayout handleLayout;
	
	private float dip; //像素  相差1.5倍
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width; // margin in dips
	private	DisplayMetrics displaymetrics; 	//像素宽度信息
	
	private String serviceUrl;  //服务地址
	private AppContext appContext;// 全局Context
	private AsyncTaskContext aTaskContext;		//异步请求策略类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenseprivatetheader);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//初始化全局变量
		appContext = (AppContext)getApplication();
		//网络连接
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSEPRIVATETHEADERACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			fMenuID=extras.getString(AppContext.FMENUID_KEY);
			fSystemType=extras.getString(AppContext.FSYSTEMTYPE_KEY);
			fBillID=extras.getString(AppContext.FBILLID_KEY);
			fClassTypeId=extras.getString(AppContext.FCLASSTYPEID_KEY);
			fStatus=extras.getString(AppContext.FSTATUS_KEY);
		}
		
		displaymetrics = new DisplayMetrics();
		dip= this.getResources().getDisplayMetrics().density; 
		//初始化帧布局实例 
		fCostTypeLinearLayout=(LinearLayout)findViewById(R.id.expenseprivatetheader_LinearLayout);	
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ExpensePrivateTHeaderActivity.this, ExpensePrivateTHeaderActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ExpensePrivateTHeaderActivity.this, R.string.expenseprivatetheader_netparseerror);
			return;
		}
	}
	
	@Override
	public Base getDataByPost(String serviceUrl) {
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// 发送这个消息到消息队列中
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpensePrivateTHeaderActivity.this);
		ExpensePrivateTHeaderBusiness eBusiness= (ExpensePrivateTHeaderBusiness)factoryBusiness.getInstance("ExpensePrivateTHeaderBusiness",serviceUrl);
		return eBusiness.getExpensePrivateTHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		ExpensePrivateTHeaderInfo extHeaderInfo=(ExpensePrivateTHeaderInfo)base;
		if(!StringUtils.isEmpty(extHeaderInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.expenseprivatetheader_FBillNo);
			fBillNo.setText(extHeaderInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFConSmName())){
			fConSmName=(TextView)findViewById(R.id.expenseprivatetheader_FConSmName);
			fConSmName.setText(extHeaderInfo.getFConSmName());
		}
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFCommitDate())){
			fCommitDate=(TextView)findViewById(R.id.expenseprivatetheader_FCommitDate);
			fCommitDate.setText(extHeaderInfo.getFCommitDate());
		}
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFConSmAmountAll())){
			fConSmAmountAll=(TextView)findViewById(R.id.expenseprivatetheader_FConSmAmountAll);
			fConSmAmountAll.setText(extHeaderInfo.getFConSmAmountAll());
		}	
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFCostType())){
			showCostType(extHeaderInfo.getFCostType());
		}
		initApprove();
	}
	
	/** 
	* @Title: showCostType 
	* @Description: 构造子类集合
	* @param @param fCostType     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月23日 下午2:42:43
	*/
	private void showCostType(final String fCostType){
		try {
			Type listType = new TypeToken<ArrayList<ExpenseCostTypeInfo>>() {}.getType();
			Gson gson = new GsonBuilder().create();
			JSONArray jsonArray= new JSONArray(fCostType);
			List<ExpenseCostTypeInfo> costTypeList = gson.fromJson(jsonArray.toString(), listType);
			if(costTypeList.size() > 0){
				for (ExpenseCostTypeInfo expenseCostTypeInfo : costTypeList) {
					attachRelativeLayout(expenseCostTypeInfo);
				}
			}		
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* @Title: attachRelativeLayout 
	* @Description: 动态拼接 显示费用类型的视图类型，可能有多个
	* @param @param costTypeInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月23日 下午3:12:15
	*/
	@SuppressLint("InlinedApi")
	private void attachRelativeLayout(final ExpenseCostTypeInfo costTypeInfo){
		paddingLeft = 20; // margin in dips
		paddingRight = 20; 
		paddingTop = 10;
		paddingBottom = 5; 
	    width = getPixelsWidth(); 
        if(width>320){
	    	paddingLeft = (int)(paddingLeft * dip); // margin in pixels
	        paddingRight = (int)(paddingRight * dip); 
	        paddingTop=(int)(paddingTop * dip);
	        paddingBottom=(int)(paddingBottom * dip);
        }
		
		int costTypeName,costTypeAmount;
		switch (costTypeInfo.getFCostCode()) {
			case 2001:
				costTypeName=R.string.expenseprivatetheader_2001;
				costTypeAmount=R.drawable.amount_2001;
				break;
			case 2002:
				costTypeName=R.string.expenseprivatetheader_2002;	
				costTypeAmount=R.drawable.amount_2002;
				break;
			case 2003:
				costTypeName=R.string.expenseprivatetheader_2003;
				costTypeAmount=R.drawable.amount_2003;
				break;
			case 2004:
				costTypeName=R.string.expenseprivatetheader_2004;
				costTypeAmount=R.drawable.amount_2004;
				break;
			case 2005:
				costTypeName=R.string.expenseprivatetheader_2005;
				costTypeAmount=R.drawable.amount_2005;
				break;
			case 2006:
				costTypeName=R.string.expenseprivatetheader_2006;
				costTypeAmount=R.drawable.amount_2006;
				break;
			case 2007:
				costTypeName=R.string.expenseprivatetheader_2007;
				costTypeAmount=R.drawable.amount_2007;
				break;
			case 2008:
				costTypeName=R.string.expenseprivatetheader_2008;
				costTypeAmount=R.drawable.amount_2008;
				break;
			case 2009:
				costTypeName=R.string.expenseprivatetheader_2009;
				costTypeAmount=R.drawable.amount_2009;
				break;
			case 2010:
				costTypeName=R.string.expenseprivatetheader_2010;
				costTypeAmount=R.drawable.amount_2010;
				break;
			case 2011:
				costTypeName=R.string.expenseprivatetheader_2011;
				costTypeAmount=R.drawable.amount_2011;
				break;
			case 2012:
				costTypeName=R.string.expenseprivatetheader_2012;
				costTypeAmount=R.drawable.amount_2012;
				break;
			case 2013:
				costTypeName=R.string.expenseprivatetheader_2013;		
				costTypeAmount=R.drawable.amount_2013;
				break;
			case 2014:
				costTypeName=R.string.expenseprivatetheader_2014;			
				costTypeAmount=R.drawable.amount_2014;
				break;
			case 2016:
				costTypeName=R.string.expenseprivatetheader_2016;
				costTypeAmount=R.drawable.amount_2016;
				break;
			default:
				costTypeName=R.string.expenseprivatetheader_2001;		
				costTypeAmount=R.drawable.amount_2001;
				break;
		}
		
		int costTypeId=getNewId();
		RelativeLayout reLayout=new RelativeLayout(this);  //创建一个相对布局实例
		//配置相对布局实例的高度和宽度
		LayoutParams reLP=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayout.setLayoutParams(reLP);
		reLayout.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		
		//指向图标
		ImageButton imgButton=new ImageButton(this);
		LayoutParams imgParam = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		imgParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		imgButton.setLayoutParams(imgParam);
		imgButton.setPadding(5, -5, 0, 5);
		imgButton.setBackgroundResource(0);
		imgButton.setImageResource(R.drawable.imgbtn_arrow_list);
		imgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ExpensePrivateTHeaderActivity.this, ExpensePrivateTBodyActivity.class);
				intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemType);
				intent.putExtra(AppContext.FBILLID_KEY, fBillID);
				intent.putExtra(AppContext.FEXPENSEPRIVATE_COSTTYPE_KEY, String.valueOf(costTypeInfo.getFCostCode()));
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
		reLayout.addView(imgButton);
		
		//费用名称
		TextView tv1 = new TextView(this);
		LayoutParams tvParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tv1.setLayoutParams(tvParam1);
		tv1.setCompoundDrawablesWithIntrinsicBounds(costTypeAmount, 0, 0, 0);
		tv1.setText(costTypeName);
		tv1.setTextAppearance(ExpensePrivateTHeaderActivity.this, R.style.iconstext);
		reLayout.addView(tv1);
		
		//费用值符号
		TextView tv2 = new TextView(this);
		LayoutParams tvParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tv2.setLayoutParams(tvParam2);
		tvParam2.addRule(RelativeLayout.LEFT_OF, costTypeId);
		tv2.setText(R.string.engineering_165);
		tv2.setTextColor(Color.parseColor("#ae1f10"));
		tv2.setTextSize(14);
		tv2.setTypeface(Typeface.DEFAULT_BOLD);
		tv2.setPadding(0, -5, 10, 5);
		reLayout.addView(tv2);
		
		//费用值
		TextView tv3 = new TextView(this);
		LayoutParams tvParam3 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tvParam3.setMargins(0, -5, 40, 0);
		tv3.setId(costTypeId);
		tv3.setLayoutParams(tvParam3);
		tv3.setText(costTypeInfo.getFCostValue());
		tv3.setTextAppearance(ExpensePrivateTHeaderActivity.this, R.style.iconsamount);
		reLayout.addView(tv3);
		fCostTypeLinearLayout.addView(reLayout);	
	}
	
	/** 
	* @Title: getPixelsWidth 
	* @Description: 获取屏幕像素宽度
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年7月25日 下午2:00:31
	*/
	private int getPixelsWidth(){
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    return displaymetrics.widthPixels; 
	}
	
	/** 
	* @Title: initApprove 
	* @Description: 加载审批按钮视图
	* @param    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月13日 下午5:06:49
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		
		handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		handleLayout.setVisibility(View.GONE);
		appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
	
		if("1".equals(fStatus)){  //说明是已审批进来
			appButton.setText(R.string.approve_imgbtnApprove_record);
		}
		appButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("0".equals(fStatus)){ //显示待审批页面
					Intent intent = new Intent(ExpensePrivateTHeaderActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.expenseprivatetheader_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//显示已审批页面
					Intent intent = new Intent(ExpensePrivateTHeaderActivity.this, WorkFlowBeenActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOWBEEN);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				}
		 	}
		 });
		
		 fCostTypeLinearLayout.addView(buttonLayout);
	}
	
	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case AppContext.ACTIVITY_WORKFLOW:
				if(resultCode==RESULT_OK){
					fStatus="1";  //从待审批页面过来 说明是审批或驳回通过
					SharedPreferences sp= getSharedPreferences(AppContext.TASKLISTACTIVITY_CONFIG_FILE, MODE_PRIVATE);					
					sp.edit().putString(AppContext.TA_APPROVE_FSTATUS, fStatus).commit();
					appButton.setText(R.string.approve_imgbtnApprove_record);
				}			
				break;
			case AppContext.ACTIVITY_WORKFLOWBEEN:
				break;
			default:
				break;
		}
	}
	
	/** 
	* @Title: getNewId 
	* @Description: 获取一个新的ID值
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014年6月17日 下午2:45:08
	*/
	private int getNewId(){
		int fId;
		do {
			fId = ViewIdGenerator.generateViewId();
		} while (findViewById(fId) != null);
		return fId;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(ExpensePrivateTHeaderActivity.this);
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
