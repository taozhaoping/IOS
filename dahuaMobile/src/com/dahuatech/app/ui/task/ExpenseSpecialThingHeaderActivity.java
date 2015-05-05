package com.dahuatech.app.ui.task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.bean.mytask.ExpenseCostTypeInfo;
import com.dahuatech.app.bean.mytask.ExpenseSpecialThingHeaderInfo;
import com.dahuatech.app.bean.mytask.TaskParamInfo;
import com.dahuatech.app.business.ExpenseSpecialThingHeaderBusiness;
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
* @Title: ExpenseSpecialThingActivity.java 
* @Package com.dahuatech.app.ui.task 
* @Description: �������ﴦ��
* @date 2015��4��7�� ����2:21:54 
* @author taozhaoping 26078
* @author mail taozhaoping@gmail.com
* @version V1.0
 */
public class ExpenseSpecialThingHeaderActivity extends MenuActivity implements ITaskContext{

	private String fMenuID,fSystemType,fBillID,fClassTypeId,fStatus;
	private TextView fBillNo,fConSmName,fCommitDate,fAmountAll,fGeneralName;
	private TaskParamInfo taskParam;  //������
	private LinearLayout fCostTypeLinearLayout; //�������Ͳ���ȫ�ֱ���
	private Button appButton;	//����ť
	private TableLayout handleLayout;
	
	private float dip; //����  ���1.5��
	private int paddingLeft,paddingRight,paddingTop,paddingBottom,width; // margin in dips
	private	DisplayMetrics displaymetrics; 	//���ؿ����Ϣ
	
	private String serviceUrl;  //�����ַ
	private AppContext appContext;// ȫ��Context
	private AsyncTaskContext aTaskContext;		//�첽���������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expensespecialthingheader);
		
		//��ȡ��Actionbar�����ã����ַ�ʽ����android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//��ʼ��ȫ�ֱ���
		appContext = (AppContext)getApplication();
		//��������
		if(!appContext.isNetworkConnected()){
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		
		//��ȡ�����ַ
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSESPECIALTHINGHEADERACTIVITY;	
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
		//��ʼ��֡����ʵ�� 
		fCostTypeLinearLayout=(LinearLayout)findViewById(R.id.expensespecialthingheader_LinearLayout);	
		if(!StringUtils.isEmpty(fMenuID) && !StringUtils.isEmpty(fSystemType) && !StringUtils.isEmpty(fBillID) && !StringUtils.isEmpty(fClassTypeId)&&!StringUtils.isEmpty(fStatus)){
			aTaskContext=AsyncTaskContext.getInstance(ExpenseSpecialThingHeaderActivity.this, ExpenseSpecialThingHeaderActivity.this);
			aTaskContext.callAsyncTask(serviceUrl);
		}
		else {
			UIHelper.ToastMessage(ExpenseSpecialThingHeaderActivity.this, R.string.expensespecialtheader_netparseerror);
			return;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		commonMenu.setContext(ExpenseSpecialThingHeaderActivity.this);
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

	@Override
	public Base getDataByPost(String serviceUrl) {
		taskParam=TaskParamInfo.getTaskParamInfo();
		taskParam.setFBillID(fBillID);
		taskParam.setFMenuID(fMenuID);
		taskParam.setFSystemType(fSystemType);
		
		// ���������Ϣ����Ϣ������
		FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(this);
		ExpenseSpecialThingHeaderBusiness eBusiness= (ExpenseSpecialThingHeaderBusiness)factoryBusiness.getInstance("ExpenseSpecialThingHeaderBusiness",serviceUrl);
		return eBusiness.getExpenseSpecialThingHeaderInfo(taskParam);
	}

	@Override
	public void initBase(Base base) {
		// TODO Auto-generated method stub
		ExpenseSpecialThingHeaderInfo extHeaderInfo=(ExpenseSpecialThingHeaderInfo)base;
		if(!StringUtils.isEmpty(extHeaderInfo.getFBillNo())){
			fBillNo=(TextView)findViewById(R.id.expensespecialthingheader_FBillNo);
			fBillNo.setText(extHeaderInfo.getFBillNo());
		}
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFConSmName())){
			fConSmName=(TextView)findViewById(R.id.expensespecialthingheader_FConSmName);
			fConSmName.setText(extHeaderInfo.getFConSmName());
		}
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFCommitDate())){
			fCommitDate=(TextView)findViewById(R.id.expensespecialthingheader_FCommitDate);
			fCommitDate.setText(extHeaderInfo.getFCommitDate());
		}
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFAmountAll())){
			fAmountAll=(TextView)findViewById(R.id.expensespecialthingheader_FAmountAll);
			fAmountAll.setText(extHeaderInfo.getFAmountAll());
		}	
		
		if(!StringUtils.isEmpty(extHeaderInfo.getFCostType())){
			showCostType(extHeaderInfo.getFCostType());
		}
		initApprove();
	}
	
	/** 
	* @Title: initApprove 
	* @Description: ��������ť��ͼ
	* @param    
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��8��13�� ����5:06:49
	*/
	@SuppressLint("InflateParams")
	private void initApprove(){
		View buttonLayout = LayoutInflater.from(this).inflate(R.layout.approve_button, null);
		
		handleLayout=(TableLayout)buttonLayout.findViewById(R.id.approve_button_tableLayout); 
		handleLayout.setVisibility(View.GONE); 
		appButton=(Button)buttonLayout.findViewById(R.id.approve_button_imgbtnApprove);
	
		if("1".equals(fStatus)){  //˵�������������
			appButton.setText(R.string.approve_imgbtnApprove_record);
		}
		appButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("0".equals(fStatus)){ //��ʾ������ҳ��
					Intent intent = new Intent(ExpenseSpecialThingHeaderActivity.this, WorkFlowActivity.class);
					intent.putExtra(AppContext.WORKFLOW_FSYSTEMTYPE_KEY, fSystemType);
					intent.putExtra(AppContext.WORKFLOW_FCLASSTYPEID_KEY, fClassTypeId);
					intent.putExtra(AppContext.WORKFLOW_FBILLID_KEY, fBillID);
					intent.putExtra(AppContext.WORKFLOW_FBILLNAME_KEY, getResources().getString(R.string.expensespecialtheader_title));
					startActivityForResult(intent,AppContext.ACTIVITY_WORKFLOW);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
				} 
				else 
				{//��ʾ������ҳ��
					Intent intent = new Intent(ExpenseSpecialThingHeaderActivity.this, WorkFlowBeenActivity.class);
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
	
	/** 
	* @Title: showCostType 
	* @Description: �������༯��
	* @param @param fCostType     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��20�� ����11:14:26
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
	* @Title: getPixelsWidth 
	* @Description: ��ȡ��Ļ���ؿ��
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��7��25�� ����2:00:31
	*/
	private int getPixelsWidth(){
	    getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
	    return displaymetrics.widthPixels; 
	}
	
	
	/** 
	* @Title: attachRelativeLayout 
	* @Description: ��̬ƴ�� ��ʾ�������͵���ͼ���ͣ������ж��
	* @param @param costTypeInfo     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014��6��20�� ����11:25:27
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
		RelativeLayout reLayout=new RelativeLayout(this);  //����һ����Բ���ʵ��
		//������Բ���ʵ��ĸ߶ȺͿ��
		LayoutParams reLP=new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);	
		reLayout.setLayoutParams(reLP);
		reLayout.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
		
		//ָ��ͼ��
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
				intent.setClass(ExpenseSpecialThingHeaderActivity.this, ExpenseSpecialThingBodyActivity.class);
				intent.putExtra(AppContext.FSYSTEMTYPE_KEY, fSystemType);
				intent.putExtra(AppContext.FBILLID_KEY, fBillID);
				intent.putExtra(AppContext.FEXPENSEPRIVATE_COSTTYPE_KEY, String.valueOf(costTypeInfo.getFCostCode()));
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
		reLayout.addView(imgButton);
		
		//�������
		TextView tv1 = new TextView(this);
		LayoutParams tvParam1 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tv1.setLayoutParams(tvParam1);
		tv1.setCompoundDrawablesWithIntrinsicBounds(costTypeAmount, 0, 0, 0);
		tv1.setText(costTypeName);
		tv1.setTextAppearance(ExpenseSpecialThingHeaderActivity.this, R.style.iconstext);
		reLayout.addView(tv1);
		
		//����ֵ���
		TextView tv2 = new TextView(this);
		LayoutParams tvParam2 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tv2.setLayoutParams(tvParam2);
		tvParam2.addRule(RelativeLayout.LEFT_OF, costTypeId);
		tv2.setText(R.string.engineering_165);
		tv2.setTextColor(Color.parseColor("#ae1f10"));
		tv2.setTextSize(14);
		tv2.setTypeface(Typeface.DEFAULT_BOLD);
		tv2.setPadding(5, -5, 10, 5);
		reLayout.addView(tv2);
		
		//����ֵ
		TextView tv3 = new TextView(this);
		LayoutParams tvParam3 = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		tvParam3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tvParam3.setMargins(0, -5, 40, 0);
		tv3.setId(costTypeId);
		tv3.setLayoutParams(tvParam3);
		tv3.setText(costTypeInfo.getFCostValue());
		tv3.setTextAppearance(ExpenseSpecialThingHeaderActivity.this, R.style.iconsamount);
		reLayout.addView(tv3);
		fCostTypeLinearLayout.addView(reLayout);	
	}
	
	/** 
	* @Title: getNewId 
	* @Description: ��ȡһ���µ�IDֵ
	* @param @return     
	* @return int    
	* @throws 
	* @author 21291
	* @date 2014��6��17�� ����2:45:08
	*/
	private int getNewId(){
		int fId;
		do {
			fId = ViewIdGenerator.generateViewId();
		} while (findViewById(fId) != null);
		return fId;
	}
}
