package com.dahuatech.app.ui.expense.taxi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppUrl;
import com.dahuatech.app.R;
import com.dahuatech.app.adapter.ExpenseTaxiListAdapter;
import com.dahuatech.app.adapter.GPSDbAdapter;
import com.dahuatech.app.bean.ResultMessage;
import com.dahuatech.app.bean.expense.GpsRowIdInfo;
import com.dahuatech.app.business.ExpenseBusiness;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.common.DelSlideActionSheet;
import com.dahuatech.app.common.DelSlideActionSheet.OnActionSheetSelected;
import com.dahuatech.app.common.UIHelper;
import com.dahuatech.app.inter.IListViewonSingleTapUpListenner;
import com.dahuatech.app.inter.IOnDeleteListioner;
import com.dahuatech.app.ui.main.MenuActivity;
import com.dahuatech.app.widget.DelSlideListView;

/**
 * @ClassName ExpenseTaxiListActivity
 * @Description 上传信息列表页面
 * @author 21291
 * @date 2014年5月13日 下午2:08:10
 */
public class ExpenseTaxiListActivity extends MenuActivity implements IOnDeleteListioner, IListViewonSingleTapUpListenner,OnActionSheetSelected, OnCancelListener {

	private static final int ACTIVITY_ADD = 0;
	private static final int ACTIVITY_ITEMVIEW = 1;
	
	private Button btnUpload,btnList,btnNew;
	private Button btnCheck,btnCheckCancle,btnBatchUpload;  
	private DelSlideListView mDelSlideListView;
	private List<Map<String, Object>> mData;
	private GPSDbAdapter mDbHelper;
	private ExpenseTaxiListAdapter exAdapter;
	private Cursor mGpsCursor;
	
	private int delId = 0;			 //待删除下标
	private String globalUploadFlag; //是否上传至服务器标志   0：没有上传 1：已经上传
	private String fItemNumber;		 //员工号
	
	private String serviceUrl;  //服务地址
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//隐藏键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mDbHelper = new GPSDbAdapter(this);
		mDbHelper.openSqlLite();
		setContentView(R.layout.expense_taxilist);
		
		//获取对Actionbar的引用，这种方式兼容android2.1
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//获取服务地址
		serviceUrl=AppUrl.URL_API_HOST_ANDROID_EXPENSETAXILISTACTIVITY;	
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
		{
			fItemNumber=extras.getString(AppContext.FITEMNUMBER_KEY);
		}
		
		globalUploadFlag="0";  //默认是待上传记录
		renderListView();
		
		//待上传
		btnUpload=(Button) findViewById(R.id.expense_taxilist_upload);
		btnUpload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnUpload.setBackgroundResource (R.drawable.tabs_active);
				btnUpload.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_active);
				
				btnList.setBackgroundResource (R.drawable.tabs_default);
				btnList.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_default);
				
				globalUploadFlag="0";  //待上传记录
				renderListView();
			}
		});
		
		//已上传
		btnList=(Button) findViewById(R.id.expense_taxilist_list);
		btnList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnList.setBackgroundResource (R.drawable.tabs_active);
				btnList.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_active);
				
				btnUpload.setBackgroundResource (R.drawable.tabs_default);
				btnUpload.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_default_left);
				
				globalUploadFlag="1";  //已经上传记录
				renderListView();
			}
		});
		
		//新增补偿
		btnNew=(Button) findViewById(R.id.expense_taxilist_new);
		btnNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//参数设置
				Intent intent = new Intent();
				intent.setClass(ExpenseTaxiListActivity.this, ExpenseTaxiInputActivity.class);
				startActivityForResult(intent, ACTIVITY_ADD);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});	
	}
	
	/** 
	* @Title: renderListView 
	* @Description: 初始化加载列表信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月13日 下午2:15:08
	*/
	private void renderListView(){
		mDelSlideListView=(DelSlideListView)this.findViewById(R.id.tasklist_ListView);
		mGpsCursor = mDbHelper.getGpsdbByUploadFlag(globalUploadFlag);
        mData = getData();
        exAdapter = new ExpenseTaxiListAdapter(this,mData,R.layout.expense_taxilistlayout);
        mDelSlideListView.setAdapter(exAdapter);
        mDelSlideListView.setmOnDeleteListioner(this);
		mDelSlideListView.setThisonSingleTapUpListenner(this);
		exAdapter.setOnDeleteListioner(this);
		
		//全选
		btnCheck=(Button) findViewById(R.id.expense_taxilist_All);
		btnCheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//遍历list的长度，将exAdapter中的map值全部设为true  
                for (int i = 0; i < mData.size(); i++) {  
                	exAdapter.getIsSelected().put(i, true);  	
                }  
            	exAdapter.swapItems(mData);
			}
		});
		
		//全选取消
		btnCheckCancle=(Button) findViewById(R.id.expense_taxilist_Cancle);
		btnCheckCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//遍历list的长度，将已选的按钮设为未选  
                for (int i = 0; i < mData.size(); i++) {  
                    if (exAdapter.getIsSelected().get(i)) {  
                    	exAdapter.getIsSelected().put(i, false);  
                    }  
                }  
                exAdapter.swapItems(mData);
			}
		});
		
		//批量上传
		btnBatchUpload=(Button) findViewById(R.id.expense_taxilist_BatchUpload);
		if("0".equals(globalUploadFlag)){  //批量上传
			btnBatchUpload.setText(R.string.expense_itemview_upload);
		}
		else {
			btnBatchUpload.setText(R.string.expense_taxilist_delete);
		}
		btnBatchUpload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("0".equals(globalUploadFlag)){  //批量上传操作
					//初始化上传结果集
					List<GpsRowIdInfo> gpsRowIdInfos=new ArrayList<GpsRowIdInfo>();
					//遍历list的长度，将已选的按钮设为未选  
	                for (int i = 0; i < mData.size(); i++) {  
	                    if (exAdapter.getIsSelected().get(i)) {  
	                    	GpsRowIdInfo gpsRowIdInfo=new GpsRowIdInfo();
	                    	gpsRowIdInfo.setRowId((String)mData.get(i).get("id"));
	                    	gpsRowIdInfo.setUserId(fItemNumber);
	                    	gpsRowIdInfo.setStartTime((String)mData.get(i).get("startTime"));
	                    	gpsRowIdInfo.setEndTime((String)mData.get(i).get("endTime"));
	                    	gpsRowIdInfo.setStartLocation((String)mData.get(i).get("startLocation"));
	                    	gpsRowIdInfo.setEndLocation((String)mData.get(i).get("endLocation"));   	
	                    	gpsRowIdInfo.setStartPlace((String)mData.get(i).get("startAddress"));
	                    	gpsRowIdInfo.setEndPlace((String)mData.get(i).get("endAddress"));                    	
	                    	gpsRowIdInfo.setCause("");
	                    	gpsRowIdInfo.setAutoFlag((String)mData.get(i).get("autoFlag"));
	                    	gpsRowIdInfo.setAmount((String)mData.get(i).get("amount"));
	                    	
	                    	gpsRowIdInfos.add(gpsRowIdInfo);
	                    } 
	                } 
	                if(gpsRowIdInfos.size() > 0){
	                	batchUpload(gpsRowIdInfos);
	                }
	                else {
	                	UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_batch_fail);
					}
            	}
				else {  //批量删除操作
					List<Map<String, Object>> deleteList=new ArrayList<Map<String, Object>>();//初始化待删除结果集
	                for (int i = 0; i < mData.size(); i++) {  
	                    if (exAdapter.getIsSelected().get(i)) {  
	                      	deleteList.add(mData.get(i));
	                    } 
	                } 
	                if(deleteList.size() > 0){	
	                	for (int i = 0; i < deleteList.size(); i++) {
	                		//删除数据
	                		Map<String, Object> delObj= deleteList.get(i);               		
	            			mDbHelper.deleteGpsdb(Integer.parseInt(delObj.get("id").toString()));
	            			mData.remove(delObj);
						}
	                	exAdapter.initDate();
	                	exAdapter.swapItems(mData);
	                	UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_delete_success);
	                }
	                else {
	                	UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_delete_fail);
					} 
				}	
			}
		});
		
		OnItemClickListener listener =  new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				exAdapter.setSelectItem(position);
				exAdapter.notifyDataSetInvalidated(); //更新UI界面
				
				String id = (String) mData.get(position).get("id");
				String startTime = (String) mData.get(position).get("startTime");
				String endTime = (String) mData.get(position).get("endTime");
				String uploadFlag = (String) mData.get(position).get("uploadFlag");
				String startAddress = (String) mData.get(position).get("startAddress");
				String endAddress = (String) mData.get(position).get("endAddress");
				String cause = (String) mData.get(position).get("cause");
				String autoFlag = (String)mData.get(position).get("autoFlag");
				String startLocation = (String)mData.get(position).get("startLocation");
				String endLocation = (String)mData.get(position).get("endLocation");
				String amount=(String)mData.get(position).get("amount");
				globalUploadFlag=uploadFlag;
				
				Intent intent = new Intent();
				intent.setClass(ExpenseTaxiListActivity.this, ExpenseTaxiItemViewActivity.class);
				
				intent.putExtra(AppContext.FITEMNUMBER_KEY, fItemNumber);
				intent.putExtra(GPSDbAdapter.KEY_ROWID, id);
				intent.putExtra(GPSDbAdapter.KEY_STARTTIME, startTime);
				intent.putExtra(GPSDbAdapter.KEY_ENDTIME, endTime);
				intent.putExtra(GPSDbAdapter.KEY_UPLOADFLAG, uploadFlag);
				intent.putExtra(GPSDbAdapter.KEY_STARTADDRESS, startAddress);
				intent.putExtra(GPSDbAdapter.KEY_ENDADDRESS, endAddress);	
				intent.putExtra(GPSDbAdapter.KEY_CAUSE, cause);
				intent.putExtra(GPSDbAdapter.KEY_AUTOFLAG, autoFlag);
				intent.putExtra(GPSDbAdapter.KEY_STARTLOCATION, startLocation);
				intent.putExtra(GPSDbAdapter.KEY_ENDLOCATION, endLocation);
				intent.putExtra(GPSDbAdapter.KEY_AMOUNT, amount);
				startActivityForResult(intent, ACTIVITY_ITEMVIEW); 
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
        };
        mDelSlideListView.setOnItemClickListener(listener);
	}
	
	/** 
	* @Title: getData 
	* @Description: 获取数据集合
	* @param @return     
	* @return List<Map<String,Object>>    
	* @throws 
	* @author 21291
	* @date 2014年5月13日 下午2:15:39
	*/
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(mGpsCursor.moveToFirst();!mGpsCursor.isAfterLast();mGpsCursor.moveToNext()){
			
			int startTimeIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_STARTTIME);  
			int endTimeIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ENDTIME);
			int uploadFlagIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_UPLOADFLAG);
			int autoFlagIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_AUTOFLAG);
			int idIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ROWID);
			int startAddressIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_STARTADDRESS);
			int endAddressIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ENDADDRESS);
			int causeIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_CAUSE);
			int startLocationIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_STARTLOCATION); 
			int endLocationIndex = mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_ENDLOCATION);
			int amountIndex=mGpsCursor.getColumnIndex(GPSDbAdapter.KEY_AMOUNT);
			
			Map<String, Object> map = new HashMap<String, Object>();
			String startTime = mGpsCursor.getString(startTimeIndex);  
			String endTime = mGpsCursor.getString(endTimeIndex);
			String uploadFlag = mGpsCursor.getString(uploadFlagIndex);
			String autoFlag = mGpsCursor.getString(autoFlagIndex);
			String id = mGpsCursor.getString(idIndex);
			String startAddress = mGpsCursor.getString(startAddressIndex);
			String endAddress = mGpsCursor.getString(endAddressIndex);
			String cause = mGpsCursor.getString(causeIndex);
			String startLocation = mGpsCursor.getString(startLocationIndex);
			String endLocation = mGpsCursor.getString(endLocationIndex);
			String amount=mGpsCursor.getString(amountIndex);
			
			map.put("startTime", startTime);//开始时间
			map.put("endTime", endTime);    //结束时间
			map.put("uploadFlag", uploadFlag);//是否成功上传到服务器
			map.put("autoFlag",autoFlag); //手动补偿  manual，还是自动收集数据 automatic
			map.put("id", id); //id
			map.put("startAddress", startAddress);//开始地点
			map.put("endAddress", endAddress); //结束地点
			map.put("cause", cause); //补偿原因
			map.put("startLocation", startLocation);//开始坐标
			map.put("endLocation", endLocation);//结束坐标
			map.put("amount", amount);			//金额
			
			list.add(map);	
		}	
		mGpsCursor.close();
		return list;
	}
	
	/** 
	* @Title: batchUpload 
	* @Description: 批量上传乘车记录报销系统
	* @param @param gpsRowIdInfos     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月21日 上午11:32:55
	*/
	@SuppressLint("HandlerLeak")
	private void batchUpload(final List<GpsRowIdInfo> gpsRowIdInfos){
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if(msg.what==1 && msg.obj!=null){
					ResultMessage res=(ResultMessage)msg.obj;
					if(res.isIsSuccess()){
						List<String> hadUpload = new ArrayList<String>(Arrays.asList(res.getResult().split(",")));
						if(hadUpload.size() > 0){
							for (String item : hadUpload) {
								int rowId = Integer.parseInt(item);
								//更改上传标志为已经上传
								mDbHelper.updateGpsdb(rowId, "1");
							}
						}
						UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_success);		
						
						// 延迟2秒刷新页面
				        new Handler().postDelayed(new Runnable() {
				            @Override
				            public void run() {
				            	renderListView();
				            }
				        }, 2000);
					}
					else {
						UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_fail_reason+res.getResult());
					}		
				}
				else {
					UIHelper.ToastMessage(ExpenseTaxiListActivity.this, R.string.expense_upload_fail);
				}
			}
		};
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(ExpenseTaxiListActivity.this);
					ExpenseBusiness exBusiness  = (ExpenseBusiness)factoryBusiness.getInstance("ExpenseBusiness",serviceUrl);
					ResultMessage res=exBusiness.batchUpload(gpsRowIdInfos);
					msg.obj = res;
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case ACTIVITY_ADD:
				globalUploadFlag="0";  //新增过来是待上传的
				btnUpload.setBackgroundResource (R.drawable.tabs_active);
				btnUpload.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_active);
				
				btnList.setBackgroundResource (R.drawable.tabs_default);
				btnList.setTextAppearance(ExpenseTaxiListActivity.this,R.style.tabs_default);
				break;
			case ACTIVITY_ITEMVIEW:
				break;
			default:
				break;
		}
		renderListView();
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
	public void onClick(int whichButton) {
		switch (whichButton) {
			case 0:
				if(delId==mData.size()-1){
					exAdapter.getIsSelected().put(delId, false); 
				}
				else{
					if(exAdapter.getIsSelected().get(delId+1)){
						exAdapter.getIsSelected().put(delId+1, false);  
					}
					else {
						exAdapter.getIsSelected().put(delId, false);  
					}
				}				
				//删除数据
				String dataId=mData.get(delId).get("id").toString();		
				int rowId = Integer.parseInt(dataId);
				mDbHelper.deleteGpsdb(rowId);
				mData.remove(delId);
				mDelSlideListView.deleteItem();
				exAdapter.swapItems(mData);				
				break;
			case 1:
				break;
			default:
				break;
		}	
	}
	@Override
	public boolean isCandelete(int position) {
		return true;
	}

	@Override
	public void onDelete(int ID) {
		delId = ID;
		DelSlideActionSheet.showSheet(this, this, this);		
	}
	
	@Override
	public void onBack() {}

	@Override
	public void onCancel(DialogInterface dialog) {}

	@Override
	public void onSingleTapUp() {}
}
