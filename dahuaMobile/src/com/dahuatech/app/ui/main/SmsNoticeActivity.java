package com.dahuatech.app.ui.main;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.R;
import com.dahuatech.app.common.RegexpUtils;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName SmsNoticeActivity
 * @Description 邀请同事短信页面
 * @author 21291
 * @date 2014年10月15日 下午1:39:32
 */
public class SmsNoticeActivity extends MenuActivity {

	private EditText searchTel; 		//短号
	private ImageButton searchImage; 	//搜索按钮
	private TextView content; 			//短信内容
	private Button sms_button; 			//发送按钮
	private ProgressDialog dialog;    	//弹出框

	private String fItemNumber; // 员工号
	private SharedPreferences sp; // 获取登陆信息
	
	private final String SENT = "SMS_SENT";
	private final String DELIVERED = "SMS_DELIVERED";
	
	private static SmsNoticeActivity mInstance;
	public static SmsNoticeActivity getInstance() {
		return mInstance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance=this;
		setContentView(R.layout.sms_notice);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// 隐藏软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		sp = getSharedPreferences(AppContext.LOGINACTIVITY_CONFIG_FILE,MODE_PRIVATE);
		fItemNumber = sp.getString(AppContext.USER_NAME_KEY, ""); // 获取员工号

		searchTel = (EditText) findViewById(R.id.sms_searchEditText);
		searchImage = (ImageButton) findViewById(R.id.sms_searchImageButton);
		content = (TextView) findViewById(R.id.sms_TextView);
		sms_button = (Button) findViewById(R.id.sms_Button);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.sms_sent_success));
		dialog.setCancelable(false);		

		// 按钮搜索
		searchImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UIHelper.showContacts(SmsNoticeActivity.this, fItemNumber,"sms_search");
			}
		});

		// 短信发送
		sms_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendSms();
			}
		});
	}

	/**
	 * @Title: sendSms
	 * @Description: 发送短信
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年10月15日 下午4:41:15
	 */
	private void sendSms() {
		if (StringUtils.isEmpty(searchTel.getText().toString())) {
			UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_invite_error));
			return;
		}
		
		if(!RegexpUtils.isMobileNo(searchTel.getText().toString())){
			UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_invite_illegal_mobile));
			return;
		}
		dialog.show();
		sendSMS(searchTel.getText().toString(), content.getText().toString());
	}

    /**
	 * @Title: sendSMS
	 * @Description: 发送短信内容
	 * @param @param phoneNumber 对方号码
	 * @param @param message 短信内容
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年10月15日 下午4:49:32
	 */
	private void sendSMS(String phoneNumber, String message)
	{	
		SmsManager sms = SmsManager.getDefault();
		ArrayList<String> msgStringArray = sms.divideMessage(message);  
		ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
		ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
		
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);

		for (int j = 0; j < msgStringArray.size(); j++) {
			sentIntents.add(sentPI);
			deliveryIntents.add(deliveredPI);
		}
		
		// ---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				if(dialog.isShowing()){
					dialog.dismiss();
				}
				switch (getResultCode()) {
					case Activity.RESULT_OK:
						UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_invite_success));
						// 延迟2秒关闭
				        new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
				        		finish();  
				            }
				        }, 2000);
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_sent_failure_generic));
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_sent_failure_no_service));
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_sent_failure_null_pdu));
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_sent_failure_radio_off));
						break;
				}	
			}
		}, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		/*registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if(dialog.isShowing()){
					dialog.dismiss();
				}
				switch (getResultCode()) {
					case Activity.RESULT_OK:
						UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_invite_success));
						// 延迟2秒关闭
				        new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
				        		finish();  
				            }
				        }, 2000);	
						break;
					case Activity.RESULT_CANCELED:
						UIHelper.ToastMessage(SmsNoticeActivity.this, getResources().getString(R.string.sms_invite_failure));
						break;
				}
			}
		}, new IntentFilter(DELIVERED));*/
		
	    sms.sendMultipartTextMessage(phoneNumber, null, msgStringArray, sentIntents, deliveryIntents);
	}

	// 从客户/项目页面,回调方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case AppContext.CONTACTS_SMS_SEARCH:
			if (resultCode == RESULT_OK) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					searchTel.setText(extras.getString(AppContext.CONTACTS_RETURN_VALUE));
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		if(mInstance!=null){
			mInstance=null;
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
