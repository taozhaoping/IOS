package com.dahuatech.app.ui.expense.taxi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dahuatech.app.R;
import com.dahuatech.app.adapter.GPSDbAdapter;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;

public class ExpenseTaxiInputActivity extends MenuActivity {
	private Button btnCommit,btnCancel;
	private EditText editStartTime,editStartPos,editEndPos,editEndTime,editAmount;
	private GPSDbAdapter mDbHelper;
	private String ime,startTime,endTime,startAddress,endAddress,amount;
	private int mRowId;
	
	private Calendar cal;
	private SimpleDateFormat df;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new GPSDbAdapter(this);
		mDbHelper.openSqlLite();
		
		setContentView(R.layout.expense_taxiinput);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化全局变量
		cal = Calendar.getInstance();
		df = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		
		editStartTime=(EditText)findViewById(R.id.expense_taxiinput_edtStartTime);
		editStartTime.setText(df.format(cal.getTime()));
		editStartTime.setInputType(InputType.TYPE_NULL);
		editEndTime=(EditText)findViewById(R.id.expense_taxiinput_edtEndTime);
		editEndTime.setText(df.format(cal.getTime()));
		editEndTime.setInputType(InputType.TYPE_NULL);
		editStartPos=(EditText)findViewById(R.id.expense_taxiinput_edtStartPos);
		editEndPos=(EditText)findViewById(R.id.expense_taxiinput_edtEndPos);
		editAmount=(EditText)findViewById(R.id.expense_taxiinput_edtAmount);
		
		//手机ime
		TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		ime = tm.getSimSerialNumber();
		
		mRowId = -1;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {  //修改操作
			mRowId = Integer.parseInt(extras.getString(GPSDbAdapter.KEY_ROWID));
			startTime = extras.getString(GPSDbAdapter.KEY_STARTTIME);
			endTime = extras.getString(GPSDbAdapter.KEY_ENDTIME);
			startAddress = extras.getString(GPSDbAdapter.KEY_STARTADDRESS);
			endAddress = extras.getString(GPSDbAdapter.KEY_ENDADDRESS);
			amount=extras.getString(GPSDbAdapter.KEY_AMOUNT);
			init();
		}
		
	    //开始时间点击事件
	    editStartTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {	
				new DatePickerDialog(ExpenseTaxiInputActivity.this ,startDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();   
				}	
		});
	    
	    //结束时间点击事件
	    editEndTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				new DatePickerDialog(ExpenseTaxiInputActivity.this ,endDateListener,
						cal.get(Calendar. YEAR ),   
			            cal.get(Calendar. MONTH ),   
			            cal.get(Calendar. DAY_OF_MONTH )).show();  
				}
		}); 
  		
  		//取消
  		btnCancel=(Button) findViewById(R.id.expense_taxiinput_imgbtnReject);
  		btnCancel.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				finish();
  			}
  		});
  		
  		//提交
  	    btnCommit=(Button) findViewById(R.id.expense_taxiinput_imgbtnSave);
  	    btnCommit.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				save();
  			}
  		});
	}
	
	/** 
	* @Title: init 
	* @Description: 初始化文本信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月14日 下午6:23:30
	*/
	private void init(){
		if(startTime != null){	
			editStartTime.setText(startTime);
		}	
		if(startAddress != null){
			editStartPos.setText(startAddress);
		}
		if(endTime != null){
			editEndTime.setText(endTime);
		}
		if(endAddress != null){
			editEndPos.setText(endAddress);
		}
		if(amount!=null){
			editAmount.setText(amount);
		}
	}
	
	/** 
	* @Title: save 
	* @Description: 保存提交
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月14日 下午6:35:23
	*/
	private void save(){ 
		startTime=editStartTime.getText().toString();
		startAddress=editStartPos.getText().toString();
		endTime=editEndTime.getText().toString();
		endAddress=editEndPos.getText().toString();
		amount=editAmount.getText().toString();
		
		if(StringUtils.isEmpty(startTime) || startTime.equals(getString(R.string.expense_taxiinput_editstartDate))){
			UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_notInput_startDate);
			return;
		}else if(StringUtils.isEmpty(startAddress) || startAddress.equals(getString(R.string.expense_taxiinput_editstartadress))){
			UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_notInput_startadress);
			return;
		}else if(StringUtils.isEmpty(endTime) || endTime.equals(getString(R.string.expense_taxiinput_editendDate))){
			UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_notInput_endDate);
			return;
		}else if(StringUtils.isEmpty(endAddress) || endAddress.equals(getString(R.string.expense_taxiinput_editendadress))){
			UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_notInput_endadress);
			return;
		}
		else if(StringUtils.isEmpty(amount) || amount.equals(getString(R.string.expense_taxiinput_editamount))){
			UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_notInput_amount);
			return;
		}
		else {
			try {
				if(df.parse(startTime).after(Calendar.getInstance().getTime())){
					UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_notInput_beginTimeAfterCurTime);
					return ;
				}else if(df.parse(startTime).after(df.parse(endTime))){
					UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_notInput_beginTimeAfterEndTime);
					return ;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(mRowId == -1){ //新增一条记录
			mDbHelper.manualEntry(startTime, endTime, startAddress, endAddress, "", "manual", ime,amount);
			UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_taxiinput_saveSuccess);
		}else{ //更新一条记录
			mDbHelper.updateGpsdb(mRowId, startAddress, endAddress, startTime, endTime, "",amount);
			UIHelper.ToastMessage(ExpenseTaxiInputActivity.this, R.string.expense_taxiinput_updateSuccess);
		}
		
		// 延迟2秒跳回列表页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	mDbHelper.closeSqlLite();
            	Intent mIntent = new Intent();
            	setResult(RESULT_OK, mIntent);
        		finish();
            }
        }, 2000);	
	}
	
	/**
	 * 开始时间监听
	 */
	private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) { 
			  cal.set(Calendar.YEAR , year);   
			  cal.set(Calendar.MONTH , monthOfYear);   
			  cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);   
			  updateDate(editStartTime); 
		}
	};

			
	/**
	 * 结束时间监听 
	 */
	private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
			cal.set(Calendar.YEAR , year);   
			cal.set(Calendar.MONTH , monthOfYear);   
			cal.set(Calendar.DAY_OF_MONTH , dayOfMonth);   
			updateDate(editEndTime); 
		}
	};
	
	/**
	 * 时间更新设置
	 */
	private void updateDate(EditText edtTime){
        edtTime.setText(df.format(cal.getTime()));
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
}
