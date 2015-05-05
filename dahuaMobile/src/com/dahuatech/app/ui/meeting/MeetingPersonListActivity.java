package com.dahuatech.app.ui.meeting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.MeetingPersonAdapter;
import com.dahuatech.app.adapter.SuggestionsAdapter;
import com.dahuatech.app.bean.meeting.MeetingPersonInfo;
import com.dahuatech.app.bean.meeting.MeetingSearchParamInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.MeetingBusiness;
import com.dahuatech.app.common.DbManager;
import com.dahuatech.app.common.ListHelper;
import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @ClassName MeetingPersonListActivity
 * @Description 我的会议人员搜索列表页面
 * @author 21291
 * @date 2014年9月11日 上午10:42:12
 */
public class MeetingPersonListActivity extends SherlockActivity implements
		SearchView.OnQueryTextListener, SearchView.OnSuggestionListener,
		OnRefreshListener<ListView> {
	private Button btnYetSelect, btnNotSelect, btnCheckAll, btnConfirm;
	private PullToRefreshListView mPullRefreshListView; // 列表
	private ProgressDialog dialog; // 弹出框

	private MeetingPersonAdapter mAdapter; // 适配器类
	private List<MeetingPersonInfo> mArrayList; // 本地数据源集合
	private List<MeetingPersonInfo> personList; // 已选择的参与人员列表
	private List<MeetingPersonInfo> sArrayList; // 被选中的item项

	private String tempList; // 已经选择人员集合字符串
	private MeetingPersonInfo mPersonInfo; // 单击实体
	private List<String> mQueryList; // 查询人员姓名历史记录集合
	private DbManager mDbHelper; // 数据库管理类
	private MeetingBusiness mBusiness; // 业务逻辑类

	private static final String[] COLUMNS = { // 最近使用列格式
	BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, };
	private MatrixCursor matrixCursor; // 历史记录数据源
	private SuggestionsAdapter mSuggestionsAdapter;
	private SearchView mSearchView; // 搜索控件
	private AppContext appContext; // 全局Context

	private String fQueryStr; // 查询字符串
	private String fFlag = "All"; // 传递人员时类型标志 ALL-表示可以传递全部，Single表示传递单个 默认传递全部
	private String serviceUrl; // 服务地址
	private int fPageIndex = 1; // 页数
	private final static int fPageSize = 10; // 页大小
	private int fStatus = 0; // 默认进入选择人员列表，0-选择人员列表 1-已选人员列表
	private int fRecordCount; // 总的记录数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDbHelper = new DbManager(this);
		mDbHelper.openSqlLite(); // 打开数据库

		setContentView(R.layout.meeting_person_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		appContext = (AppContext) getApplication(); // 初始化全局变量
		// 判断是否有网络连接
		if (!appContext.isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}

		// 获取服务地址
		serviceUrl = AppUrl.URL_API_HOST_ANDROID_MEETINGPERSONLISTACTIVITY;
		// 获取传递信息
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			tempList = extras.getString(AppContext.MEETING_DETAIL_PERSON_LIST);
			fFlag = extras.getString(AppContext.MEETING_DETAIL_PERSON_FLAG);
		}

		dialog = new ProgressDialog(this);
		dialog.setMessage(getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);

		initListData();
		setListener();
		new getPersonLocalAsync().execute();
	}

	/**
	 * @Title: initListData
	 * @Description: 初始化准备信息
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 下午4:32:45
	 */
	private void initListData() {
		btnYetSelect = (Button) findViewById(R.id.meeting_person_list_yet_select);
		btnNotSelect = (Button) findViewById(R.id.meeting_person_list_not_select);
		btnCheckAll = (Button) findViewById(R.id.meeting_person_list_checkAll);
		btnConfirm = (Button) findViewById(R.id.meeting_person_list_confirm);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.meeting_person_list_pullToRefreshListView);
		mArrayList = new ArrayList<MeetingPersonInfo>();
		mQueryList = new ArrayList<String>();
		fQueryStr = "";

		// 初始化业务逻辑类
		FactoryBusiness<?> factoryBusiness = FactoryBusiness
				.getFactoryBusiness(MeetingPersonListActivity.this);
		mBusiness = (MeetingBusiness) factoryBusiness.getInstance(
				"MeetingBusiness", serviceUrl);

		// 已经选中的参与人员列表
		showPersonList(tempList);
	}

	/**
	 * @Title: showPersonList
	 * @Description: 构造已经选择参与人员集合
	 * @param @param fTempList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月18日 下午5:50:43
	 */
	private void showPersonList(final String fTempList) {
		personList = new ArrayList<MeetingPersonInfo>();
		if (!StringUtils.isEmpty(fTempList)) {
			try {
				Type listType = new TypeToken<ArrayList<MeetingPersonInfo>>() {
				}.getType();
				Gson gson = new GsonBuilder().create();
				JSONArray jsonArray = new JSONArray(fTempList);
				personList = gson.fromJson(jsonArray.toString(), listType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_search, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_OK, new Intent());
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * @Title: setupSearchView
	 * @Description: 设置搜索控件
	 * @param @param searchItem
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 上午10:34:28
	 */
	private void setupSearchView(MenuItem searchItem) {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (null != searchManager) {
			mSearchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
		}
		mSearchView.setIconifiedByDefault(false);
		mSearchView.setIconified(false);
		mSearchView.setSubmitButtonEnabled(true);
		mSearchView.setQueryHint(getResources().getString(
				R.string.meeting_search_person));
		mSearchView.setOnQueryTextListener(this);
		mSearchView.setOnSuggestionListener(this);
	}

	@Override
	public boolean onSuggestionSelect(int position) {
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		Cursor c = (Cursor) mSuggestionsAdapter.getItem(position);
		fQueryStr = c.getString(c
				.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
		fPageIndex = 1;
		mArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute(fPageIndex);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		fQueryStr = query;
		fPageIndex = 1;
		mArrayList.clear();
		skipSelected();
		new getPersonSearchAsync().execute(fPageIndex);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		showSuggestions(newText);
		return false;
	}

	/**
	 * @Title: showSuggestions
	 * @Description: 显示搜索历史记录
	 * @param @param queryStr
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 下午4:54:39
	 */
	private void showSuggestions(String queryStr) {
		if (!StringUtils.isEmpty(queryStr)) {
			mQueryList = mDbHelper.queryPersonList(queryStr);
			if (mQueryList.size() > 0) { // 说明有客户搜索历史记录
				matrixCursor = new MatrixCursor(COLUMNS);
				int i = 1;
				for (String item : mQueryList) {
					matrixCursor
							.addRow(new String[] { String.valueOf(i), item });
					i++;
				}
				mSuggestionsAdapter = new SuggestionsAdapter(
						getSupportActionBar().getThemedContext(), matrixCursor);
			} else {
				mSuggestionsAdapter = null;
			}
			mSearchView.setSuggestionsAdapter(mSuggestionsAdapter);
		}
	}

	/**
	 * @Title: setListener
	 * @Description: 设置控件事件处理方法
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月18日 下午12:16:01
	 */
	private void setListener() {

		// 选择人员 进行搜索
		btnNotSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				skipSelected();
				mArrayList.clear();
				new getPersonLocalAsync().execute();
			}
		});

		// 已经选择人员
		btnYetSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sureSelected();
				renderPersonSelectedListView(personList);
			}
		});

		// 全选记录
		btnCheckAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String checkAllText = btnCheckAll.getText().toString();
				List<MeetingPersonInfo> soureList = mArrayList;
				if (fStatus == 1) { // 已经选择人员
					soureList = personList;
				}
				if (soureList.size() > 0) {
					if ("全选".equals(checkAllText)) {
						btnCheckAll.setText(getResources().getString(
								R.string.meeting_person_list_btnResetAll));
						setCheckAll(soureList);
					} else {
						btnCheckAll.setText(getResources().getString(
								R.string.meeting_person_list_btnCheckAll));
						setCancleAll(soureList);
					}
				} else {
					UIHelper.ToastMessage(
							MeetingPersonListActivity.this,
							getResources().getString(
									R.string.meeting_person_list_not_checkAll));
				}
			}
		});

		// 确定
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fStatus == 0) { // 选择人员
					sArrayList = getSelectedList(mArrayList);
				} else { // 已经选择人员
					sArrayList = getSelectedList(personList);
				}
				if (sArrayList.size() == 0) {
					UIHelper.ToastMessage(
							MeetingPersonListActivity.this,
							getResources().getString(
									R.string.meeting_person_list_notselected));
				} else {
					if (fStatus == 0) { // 把选择的人员标记为已选择
						if ("Single".equals(fFlag)) { // 说明是单个传输
							personList.clear();
						}
						
						personList.addAll(sArrayList);
						personList = ListHelper.rDMeetingPerson(personList); // 去重
						for (MeetingPersonInfo item : personList) {
							mDbHelper.insertPersonSearch(item); // 添加搜索记录到本地数据库中
						}

						UIHelper.ToastMessage(
								MeetingPersonListActivity.this,
								getResources().getString(
										R.string.meeting_person_list_selected));
						// 延迟2秒跳到已经选择页面
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								sureSelected();
								renderPersonSelectedListView(personList);
							}
						}, 1000);
					} else { // 返回已选择人员列表
						List<MeetingPersonInfo> selectPersonList = getSelectedList(personList);
						for (MeetingPersonInfo item : personList) {
							mDbHelper.insertPersonSearch(item); // 添加搜索记录到本地数据库中
						}
						Intent intent = new Intent();
						intent.putExtra(AppContext.MEETING_DETAIL_PERSON_LIST,
								MeetingPersonInfo.ConvertToJson(selectPersonList));
						intent.putExtra(AppContext.MEETING_DETAIL_PERSON_FLAG,
								fFlag);
						setResult(RESULT_OK, intent);
						finish();
					}
				}
			}
		});
	}

	/**
	 * @Title: skipSelected
	 * @Description: 跳转到选择人员页面中
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月19日 上午9:09:35
	 */
	private void skipSelected() {
		fStatus = 0;
		btnNotSelect.setBackgroundResource(R.drawable.meeting_tabs_left_active);
		btnNotSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_left_active);

		btnYetSelect.setBackgroundResource(R.drawable.meeting_tabs_right);
		btnYetSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_right);

		btnCheckAll.setText(getResources().getString(
				R.string.meeting_person_list_btnCheckAll));
		btnConfirm.setText(getResources().getString(
				R.string.meeting_person_list_btnSelect));
	}

	/**
	 * @Title: sureSelected
	 * @Description: 跳转到已选择人员页面中
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月19日 上午9:09:50
	 */
	private void sureSelected() {
		fStatus = 1;
		btnYetSelect
				.setBackgroundResource(R.drawable.meeting_tabs_right_active);
		btnYetSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_right_active);

		btnNotSelect.setBackgroundResource(R.drawable.meeting_tabs_left);
		btnNotSelect.setTextAppearance(MeetingPersonListActivity.this,
				R.style.meeting_tabs_left);

		btnCheckAll.setText(getResources().getString(
				R.string.meeting_person_list_btnCheckAll));
		btnConfirm.setText(getResources().getString(
				R.string.meeting_person_list_btnSure));
	}

	/**
	 * @Title: getSelectedList
	 * @Description: 获取选择的用户集合
	 * @param @param sourceList 数据源集合
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月18日 下午2:21:31
	 */
	private List<MeetingPersonInfo> getSelectedList(
			final List<MeetingPersonInfo> sourceList) {
		List<MeetingPersonInfo> selectList = new ArrayList<MeetingPersonInfo>();
		for (int i = 0; i < sourceList.size(); i++) {
			if (mAdapter.getIsSelected().get(i)) { // 说明选中了
				selectList.add(sourceList.get(i));
			}
		}
		return selectList;
	}

	/**
	 * @Title: setCheckAll
	 * @Description: 全选
	 * @param @param sourceList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月18日 下午2:36:10
	 */
	private void setCheckAll(final List<MeetingPersonInfo> sourceList) {
		// 遍历list的长度，将mAdapter中的map值全部设为true
		for (int i = 0; i < sourceList.size(); i++) {
			if ("-1".equals(sourceList.get(i).getFItemNumber())) {
				mAdapter.getIsSelected().put(i, false);
			} else {
				mAdapter.getIsSelected().put(i, true);
			}
		}
		mAdapter.refreshView(sourceList);
	}

	/**
	 * @Title: setCancleAll
	 * @Description: 取消
	 * @param @param sourceList
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月18日 下午2:41:41
	 */
	private void setCancleAll(final List<MeetingPersonInfo> sourceList) {
		// 遍历list的长度，将已选的按钮设为未选
		for (int i = 0; i < sourceList.size(); i++) {
			if (mAdapter.getIsSelected().get(i)) {
				mAdapter.getIsSelected().put(i, false);
			}
		}
		mAdapter.refreshView(sourceList);
	}

	/**
	 * @ClassName getPersonLocalAsync
	 * @Description 获取本地人员历史记录集合
	 * @author 21291
	 * @date 2014年9月12日 下午4:39:43
	 */
	private class getPersonLocalAsync extends
			AsyncTask<Void, Void, List<MeetingPersonInfo>> {

		// 主要是完成耗时操作
		@Override
		protected List<MeetingPersonInfo> doInBackground(Void... params) {
			return getPersonLocalByPost();
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonLocalListView(result);
		}
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
	private List<MeetingPersonInfo> getPersonLocalByPost() {
		List<MeetingPersonInfo> list = mDbHelper.queryPersonAllList();
		int length = list.size();
		if (length > 0) {
			list.add(length, new MeetingPersonInfo("-1", getResources()
					.getString(R.string.history_record_search_clear), "", 0));
		}
		return list;
	}

	/**
	 * @Title: renderPersonLocalListView
	 * @Description: 初始化本地人员历史记录列表
	 * @param @param listData
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 下午4:43:23
	 */
	private void renderPersonLocalListView(
			final List<MeetingPersonInfo> listData) {
		mArrayList.addAll(listData);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter = new MeetingPersonAdapter(MeetingPersonListActivity.this,
				mArrayList, R.layout.meeting_person_list_item, false);
		mPullRefreshListView.setAdapter(mAdapter);

		// 子项点击事件
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) { // 点击其他无效
					return;
				}

				mAdapter.setSelectItem(position - 1);
				mAdapter.notifyDataSetInvalidated();

				mPersonInfo = null;
				if (view instanceof RelativeLayout) { // 说明是在RelativeLayout布局下
					mPersonInfo = (MeetingPersonInfo) parent
							.getItemAtPosition(position);
				} else {
					RelativeLayout reLayout = (RelativeLayout) view
							.findViewById(R.id.meeting_person_list_item);
					mPersonInfo = (MeetingPersonInfo) reLayout.getTag();
				}
				if (mPersonInfo == null) {
					return;
				}

				// 判断是否是清除历史记录
				if ("-1".equals(mPersonInfo.getFItemNumber())) { // 清除搜索记录
					RelativeLayout reLayout = (RelativeLayout) view
							.findViewById(R.id.meeting_person_list_item);
					MeetingPersonListActivity.this.onFItemNameClick(reLayout);

					return;
				}

				mDbHelper.closeSqlLite();
				List<MeetingPersonInfo> transferList = new ArrayList<MeetingPersonInfo>();
				transferList.add(mPersonInfo);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(AppContext.MEETING_DETAIL_PERSON_LIST,
						MeetingPersonInfo.ConvertToJson(transferList));
				bundle.putString(AppContext.MEETING_DETAIL_PERSON_FLAG, fFlag);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	public void clearPersonSearchAll(View view) {

	}

	/**
	 * @Title: onFItemNameClick
	 * @Description: 人员名称点击事件
	 * @param @param view
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月29日 下午12:11:46
	 */
	public void onFItemNameClick(View view) {
		final MeetingPersonInfo mePersonInfo = (MeetingPersonInfo) view
				.getTag();
		if ("-1".equals(mePersonInfo.getFItemNumber())) { // 清除搜索记录
			mDbHelper.deletePersonSearchAll();
			mArrayList.clear();
			mAdapter.refreshView();
			UIHelper.ToastMessage(
					MeetingPersonListActivity.this,
					getResources().getString(
							R.string.history_record_clear_success));
		}
	}

	/**
	 * @Title: renderPersonSelectedListView
	 * @Description: 已经选择人员列表加载
	 * @param @param listData
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月18日 下午1:52:46
	 */
	private void renderPersonSelectedListView(
			final List<MeetingPersonInfo> listData) {
		mPullRefreshListView.setMode(Mode.DISABLED);
		mAdapter = new MeetingPersonAdapter(MeetingPersonListActivity.this,
				listData, R.layout.meeting_person_list_item, true);
		mPullRefreshListView.setAdapter(mAdapter);
	}

	/**
	 * @ClassName getPersonSearchAsync
	 * @Description 异步获取人员服务器实体集合
	 * @author 21291
	 * @date 2014年9月12日 下午4:47:34
	 */
	private class getPersonSearchAsync extends
			AsyncTask<Integer, Void, List<MeetingPersonInfo>> {

		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // 显示等待框
		}

		// 主要是完成耗时操作
		@Override
		protected List<MeetingPersonInfo> doInBackground(Integer... params) {
			return getPersonListByPost(params[0]);
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingPersonInfo> result) {
			super.onPostExecute(result);
			renderPersonListView(result);
			dialog.dismiss(); // 销毁等待框
		}
	}

	/**
	 * @Title: getPersonListByPost
	 * @Description: 获取人员服务器实体集合业务逻辑操作
	 * @param @param pageIndex
	 * @param @return
	 * @return List<MeetingPersonInfo>
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 下午4:48:00
	 */
	private List<MeetingPersonInfo> getPersonListByPost(final int pageIndex) {
		MeetingSearchParamInfo mSearchParamInfo = MeetingSearchParamInfo
				.getMeetingSearchParamInfo();
		mSearchParamInfo.setFItemNumber("");
		mSearchParamInfo.setFQueryText(fQueryStr);
		mSearchParamInfo.setFPageIndex(pageIndex);
		mSearchParamInfo.setFPageSize(fPageSize);
		mSearchParamInfo.setFSelectedDate("");
		mSearchParamInfo.setFSelectedStart("");
		mSearchParamInfo.setFSelectedEnd("");

		return mBusiness.getPersonList(mSearchParamInfo);
	}

	/**
	 * @Title: renderPersonListView
	 * @Description: 初始化人员服务器列表
	 * @param @param listData
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 下午4:48:15
	 */
	private void renderPersonListView(final List<MeetingPersonInfo> listData) {
		if (listData.size() == 0) {
			mPullRefreshListView.setMode(Mode.DISABLED);
			UIHelper.ToastMessage(MeetingPersonListActivity.this,
					getString(R.string.meeting_search_person_netparseerror),
					3000);
			return;
		}
		mArrayList.addAll(listData);
		fRecordCount = mArrayList.get(0).getFRecordCount();
		if (mArrayList.size() < fRecordCount) {
			mPullRefreshListView.setMode(Mode.PULL_FROM_END);
		} else {
			mPullRefreshListView.setMode(Mode.DISABLED);
		}
		mPullRefreshListView.getRefreshableView().setSelector(
				android.R.color.transparent);
		mPullRefreshListView.setOnRefreshListener(this);

		mAdapter = new MeetingPersonAdapter(MeetingPersonListActivity.this,
				mArrayList, R.layout.meeting_person_list_item, true);
		mPullRefreshListView.setAdapter(mAdapter);

		// 子项点击事件
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) { // 点击其他无效
					return;
				}
				mAdapter.setSelectItem(position - 1);
				mAdapter.notifyDataSetInvalidated();

				mPersonInfo = null;
				if (view instanceof RelativeLayout) { // 说明是在RelativeLayout布局下
					mPersonInfo = (MeetingPersonInfo) parent
							.getItemAtPosition(position);
				} else {
					RelativeLayout reLayout = (RelativeLayout) view
							.findViewById(R.id.meeting_person_list_item);
					mPersonInfo = (MeetingPersonInfo) reLayout.getTag();
				}
				if (mPersonInfo == null) {
					return;
				}
				mDbHelper.insertPersonSearch(mPersonInfo); // 添加搜索记录到本地数据库中
				List<MeetingPersonInfo> transferList = new ArrayList<MeetingPersonInfo>();
				transferList.add(mPersonInfo);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(AppContext.MEETING_DETAIL_PERSON_LIST,
						MeetingPersonInfo.ConvertToJson(transferList));
				bundle.putString(AppContext.MEETING_DETAIL_PERSON_FLAG, fFlag);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// 上拉加载更多
		mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(
				getResources().getString(
						R.string.pull_to_refresh_refreshing_label));
		mPullRefreshListView.getLoadingLayoutProxy().setPullLabel(
				getResources().getString(R.string.pull_to_refresh_pull_label));
		mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel(
				getResources()
						.getString(R.string.pull_to_refresh_release_label));
		onPullUpListView();
	}

	/**
	 * @Title: onPullUpListView
	 * @Description: 上拉加载更多 再重新加载10条数据
	 * @param
	 * @return void
	 * @throws
	 * @author 21291
	 * @date 2014年9月12日 下午4:52:43
	 */
	public void onPullUpListView() {
		fPageIndex++; // 分页
		new pullUpRefreshAsync().execute(fPageIndex);
	}

	/**
	 * @ClassName pullUpRefreshAsync
	 * @Description 上拉异步加载更多
	 * @author 21291
	 * @date 2014年9月12日 下午4:52:33
	 */
	private class pullUpRefreshAsync extends
			AsyncTask<Integer, Void, List<MeetingPersonInfo>> {

		@Override
		protected List<MeetingPersonInfo> doInBackground(Integer... params) {
			return getPersonListByPost(params[0]); // 主要是完成耗时操作
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(List<MeetingPersonInfo> result) {
			if (result.size() == 0) {
				UIHelper.ToastMessage(
						MeetingPersonListActivity.this,
						getString(R.string.meeting_search_person_netparseerror),
						3000);
				mPullRefreshListView.onRefreshComplete();
				return;
			}
			mArrayList.addAll(result);
			fRecordCount = mArrayList.get(0).getFRecordCount();
			if (mArrayList.size() < fRecordCount) {
				mPullRefreshListView.setMode(Mode.PULL_FROM_END);
			} else {
				mPullRefreshListView.setMode(Mode.DISABLED);
			}
			mAdapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}

	@Override
	protected void onDestroy() {
		if (mDbHelper != null) {
			mDbHelper.closeSqlLite();
		}
		setResult(RESULT_OK, new Intent());
		finish();
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
