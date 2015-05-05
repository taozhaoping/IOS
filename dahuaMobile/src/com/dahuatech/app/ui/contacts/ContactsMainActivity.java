package com.dahuatech.app.ui.contacts;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ContactsAdapter;
import com.dahuatech.app.bean.ContactInfo;
import com.dahuatech.app.bean.ContactInfo.ContactResultInfo;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.LogsRecordInfo;
import com.dahuatech.app.business.ContactsBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.ui.meeting.MeetingPersonListActivity;

/**
 * @ClassName ContactsMainActivity
 * @Description 通讯录主页的Activity
 * @author 21291
 * @date 2014年6月26日 下午1:48:52
 */
public class ContactsMainActivity extends MenuActivity {
	private static ContactsMainActivity mInstance;

	private ProgressDialog dialog; // 进程框
	private EditText searchText; // 搜索文本框
	private ImageButton btnSearch; // 按钮
	private ListView mListView; // 列表控件对象

	private ContactsAdapter mAdapter; // 适配器
	private ContactsBusiness contactsBusiness; // 业务逻辑类
	private String fItemNumber; // 员工号
	private String fSourceType; // 来源类型

	private String serviceUrl; // 服务地址
	private AppContext appContext; // 全局Context

	private DbManager mDbHelper; // 数据库管理类

	public static ContactsMainActivity getInstance() {
		return mInstance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInstance = this;
		setContentView(R.layout.contacts_main);

		mDbHelper = new DbManager(this);
		mDbHelper.openSqlLite();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// 初始化全局变量
		appContext = (AppContext) getApplication();
		// 网络连接
		if (!appContext.isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}

		// 获取服务地址
		serviceUrl = AppUrl.URL_API_HOST_ANDROID_CONTACTSMAINACTIVITY;
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			fItemNumber = extras.getString(AppContext.FITEMNUMBER_KEY);
			fSourceType = extras.getString(AppContext.CONTACTS_SOURCE_TYPE);
		}

		// 初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness = FactoryBusiness
				.getFactoryBusiness(ContactsMainActivity.this);
		contactsBusiness = (ContactsBusiness) factoryBusiness.getInstance(
				"ContactsBusiness", serviceUrl);

		mListView = (ListView) findViewById(R.id.contacts_listView); // 实例化ListView
		searchText = (EditText) findViewById(R.id.contacts_searchEditText); // 搜索文本
		btnSearch = (ImageButton) findViewById(R.id.contacts_searchImageButton);

		// 点击搜索
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 隐藏软件键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
				showSearch(searchText.getText().toString());
				searchText.setText("");
				searchText.setHint(getResources().getString(
						R.string.contacts_search));
				sendLogs("query"); // 发送日志信息进行统计
			}
		});
		sendLogs("access"); // 发送日志信息进行统计

		// 加载本地数据
		initContactLocalByPost();

		// 选择子项事件
		/*
		 * mListView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { if (position == 0) { // 点击其他无效 return; }
		 * 
		 * mAdapter.notifyDataSetInvalidated(); ContactInfo contactInfo = null;
		 * if (view instanceof RelativeLayout) { // 说明是在RelativeLayout布局下
		 * contactInfo = (ContactInfo) parent .getItemAtPosition(position); }
		 * else { RelativeLayout reLayout = (RelativeLayout) view
		 * .findViewById(R.id.contactslist_tablelayout); contactInfo =
		 * (ContactInfo) reLayout.getTag(); } if (contactInfo == null) { return;
		 * } mDbHelper.deleteContactSearchAll(); } });
		 */

	}

	/**
	 * @Title: getPersonLocalByPost
	 * @Description: 获取本地人员历史记录实体集合
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 下午4:40:32
	 */
	private void initContactLocalByPost() {
		List<ContactInfo> list = mDbHelper.queryContactAllList();
		int length = list.size();
		if (length > 0) {
			list.add(
					length,
					new ContactInfo("0", getResources().getString(
							R.string.history_record_search_clear), "", "", ""));
		}
		// 通讯录
		int Resource = R.layout.contacts_listlayout;
		if ("sms_search".equals(fSourceType)) {
			// 邀请同事
			Resource = R.layout.contacts_item_invitation;
		}
		mAdapter = new ContactsAdapter(this, list, Resource, mDbHelper);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * @Title: showSearch
	 * @Description: 显示搜索内容
	 * @param @param fSearchText
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年6月26日 下午2:05:44
	 */
	private void showSearch(String fSearchText) {
		// 声明一个等待框以提示用户等待
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在查询中，请稍后...");
		dialog.setCancelable(false);
		// 发送请求
		new ContactsAsyncClient().execute(fSearchText);
	}

	/**
	 * @ClassName ContactsAsyncClient
	 * @Description 异步获取内容
	 * @author 21291
	 * @date 2014年6月26日 下午6:22:51
	 */
	private class ContactsAsyncClient extends
			AsyncTask<String, Integer, ContactResultInfo> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // 显示等待框
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(ContactResultInfo result) {
			super.onPostExecute(result);
			renderListView(result);
			dialog.dismiss(); // 销毁等待框
		}

		// 主要是完成耗时操作
		@Override
		protected ContactResultInfo doInBackground(String... params) {
			return getListByPost(params[0]);
		}
	}

	/**
	 * @Title: getListByPost
	 * @Description: 通过POST方式获取数据源
	 * @param @param fSearchText 搜索文本框值
	 * @param @return
	 * @return ContactResultInfo
	 * @throws
	 * @author 21291
	 * @date 2014年6月26日 下午6:22:38
	 */
	private ContactResultInfo getListByPost(String fSearchText) {
		return contactsBusiness.getContactsList(fSearchText);
	}

	/**
	 * 
	 * @Title: dataClear
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param view 参数
	 * @return void 返回类型
	 * @throws
	 * @author taozhaoping 26078
	 * @author mail taozhaoping@gmail.com
	 */
	public void dataClear(View view) {
		System.out.println("ContactsMainActivity.dataClear()");
		mDbHelper.deleteContactSearchAll();
		mListView.setAdapter(null);
		// adpter刷新
		// mAdapter.notifyDataSetChanged();
	}

	/**
	 * @Title: renderListView
	 * @Description: 初始化界面数据
	 * @param @param contactResultInfo
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年6月26日 下午6:22:25
	 */
	private void renderListView(ContactResultInfo contactResultInfo) {
		if (!StringUtils.isEmpty(contactResultInfo.resultStr)) { // 如果获取不到数据
			if ("1".equals(contactResultInfo.resultStr)) {
				UIHelper.ToastMessage(ContactsMainActivity.this,
						R.string.contacts_twoword);
			}
			if ("2".equals(contactResultInfo.resultStr)) {
				UIHelper.ToastMessage(ContactsMainActivity.this,
						R.string.contacts_notfind);
			}
		}
		// 设置ListView的adapter
		if (contactResultInfo.contactList.size() > 0) {
			// 使用数据集构造adapter对象
			// 通讯录
			int Resource = R.layout.contacts_listlayout;
			if ("sms_search".equals(fSourceType)) {
				// 邀请同事
				Resource = R.layout.contacts_item_invitation;
			}
			mAdapter = new ContactsAdapter(this, contactResultInfo.contactList,
					Resource, mDbHelper);
			mListView.setAdapter(mAdapter);
		} else {
			mListView.setAdapter(null);
		}
	}

	/**
	 * @Title: sendLogs
	 * @Description: 点击通讯录时，发送日志记录到服务器
	 * @param @param typeName 操作类型
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年7月31日 下午3:06:44
	 */
	private void sendLogs(final String typeName) {
		LogsRecordInfo logInfo = LogsRecordInfo.getLogsRecordInfo();
		logInfo.setFItemNumber(fItemNumber);
		logInfo.setFAccessTime("");
		logInfo.setFModuleName(getResources().getString(R.string.log_contacts));
		logInfo.setFActionName(typeName);
		logInfo.setFNote("note");
		UIHelper.sendLogs(ContactsMainActivity.this, logInfo);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDbHelper.closeSqlLite();
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
