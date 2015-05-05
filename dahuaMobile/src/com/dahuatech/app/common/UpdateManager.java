package com.dahuatech.app.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dahuatech.app.AppException;
import com.dahuatech.app.R;
import com.dahuatech.app.bean.UpdateInfo;
import com.dahuatech.app.business.FactoryBusiness;
import com.dahuatech.app.business.SettingBusiness;

@SuppressLint({ "HandlerLeak", "ShowToast" })
public class UpdateManager {

	private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
	
    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL   = 1;
    
	private String serviceUrl;  //更新服务地址
	
	private Context mContext;
	//通知对话框
	private Dialog noticeDialog;
	//下载对话框
	private Dialog downloadDialog;
	//'已经是最新' 或者 '无法获取最新版本' 的对话框
	private Dialog latestOrFailDialog;
    //进度条
    private ProgressBar mProgress;
    //显示下载数值
    private TextView mProgressText;
    //查询动画
    private ProgressDialog mProDialog;
    //进度值
    private int progress;
    //下载线程
    private Thread downLoadThread;
    //终止标记
    private boolean interceptFlag;
	//提示语
	private String updateMsg = "";
	//返回的安装包url
	private String apkUrl = "";
	//下载包保存路径
    private String savePath = "";
	//apk保存完整路径
	private String apkFilePath = "";
	//临时下载文件路径
	private String tmpFilePath = "";
	//下载文件大小
	private String apkFileSize;
	//已下载文件大小
	private String tmpFileSize;
	
	@SuppressWarnings("unused")
	private String curVersionName;
	private int curVersionCode;
	private UpdateInfo mUpdate;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
    		switch (msg.what) {
				case DOWN_UPDATE:
					mProgress.setProgress(progress);
					mProgressText.setText(tmpFileSize + "/" + apkFileSize);
					break;
				case DOWN_OVER:
					downloadDialog.dismiss();
					installApk();
					break;
				case DOWN_NOSDCARD:
					downloadDialog.dismiss();
					Toast.makeText(mContext, R.string.soft_update_sdcard, 3000).show();
					break;
			}
    	};
	 };
		
	/** 
	* @Name: CommonMenu 
	* @Description: 内部类单例模式 
	*/
	private UpdateManager(){}
	private static class SingletonHolder {  
        private static UpdateManager instance = new UpdateManager();  
    }  
	public static UpdateManager getUpdateManager(Context context,String url) {
		SingletonHolder.instance.interceptFlag=false;
		SingletonHolder.instance.mContext=context;
		SingletonHolder.instance.serviceUrl=url;
		return SingletonHolder.instance;
	}
	
	/** 
	* @Title: checkAppUpdate 
	* @Description: 检查App更新
	* @param @param context 数据上下文
	* @param @param isShowMsg  是否显示提示消息   
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年5月23日 上午10:53:32
	*/
	public void checkAppUpdate(final boolean isShowMsg){
		getCurrentVersion();
		if(isShowMsg){
			if(mProDialog == null)
				mProDialog = ProgressDialog.show(mContext, null, "正在检测，请稍后...", true, true);
			else if(mProDialog.isShowing() || (latestOrFailDialog!=null && latestOrFailDialog.isShowing()))
				return;
		}
		final Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				if(mProDialog != null && !mProDialog.isShowing()){ 	//进度条对话框不显示 - 检测结果也不显示
					return;
				}
				//关闭并释放释放进度条对话框
				if(isShowMsg && mProDialog != null){
					mProDialog.dismiss();
					mProDialog = null;
				}
				//显示检测结果
				if(msg.what == 1){
					mUpdate = (UpdateInfo)msg.obj;
					if(mUpdate != null){
						if(curVersionCode < mUpdate.getVersionCode()){
							apkUrl = mUpdate.getDownloadUrl();
							updateMsg = mUpdate.getUpdateLog();
							showNoticeDialog();
						}else if(isShowMsg){
							showLatestOrFailDialog(DIALOG_TYPE_LATEST);
						}
					}
				}else if(isShowMsg){
					showLatestOrFailDialog(DIALOG_TYPE_FAIL);
				}
			}
		};
		new Thread(){
			public void run() {
				Message msg = new Message();
				try {	
					FactoryBusiness<?> factoryBusiness=FactoryBusiness.getFactoryBusiness(mContext);
					SettingBusiness settingBusiness= (SettingBusiness)factoryBusiness.getInstance("SettingBusiness",serviceUrl);
					UpdateInfo update = settingBusiness.checkVersion();
					msg.what = 1;
					msg.obj = update;
				} catch (AppException e) {
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}			
		}.start();		
	}	
	
	/**
	 * 显示'已经是最新'或者'无法获取版本信息'对话框
	 */
	private void showLatestOrFailDialog(int dialogType) {
		if (latestOrFailDialog != null) {
			//关闭并释放之前的对话框
			latestOrFailDialog.dismiss();
			latestOrFailDialog = null;
		}
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		if (dialogType == DIALOG_TYPE_LATEST) {
			builder.setMessage(R.string.soft_update_no);
		} else if (dialogType == DIALOG_TYPE_FAIL) {
			builder.setMessage(R.string.soft_update_cannotget);
		}
		builder.setPositiveButton(R.string.soft_update_sure, null);
		latestOrFailDialog = builder.create();
		latestOrFailDialog.show();
	}

	/**
	 * 获取当前客户端版本信息
	 */
	private void getCurrentVersion(){
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	curVersionName = info.versionName;
        	curVersionCode = info.versionCode;
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
	}
	
	/**
	 * 显示版本更新通知对话框
	 */
	private void showNoticeDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(updateMsg);
		builder.setPositiveButton(R.string.soft_update_updatebtn, new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();			
			}
		});
		builder.setNegativeButton(R.string.soft_update_later, new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}
	
	/**
	 * 显示下载对话框
	 */
	@SuppressLint("InflateParams")
	private void showDownloadDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);
		
		builder.setView(v);
		builder.setNegativeButton(R.string.soft_update_cancel, new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();
		
		downloadApk();
	}
	
	private Runnable mdownApkRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				String apkName = "dahuaMobile_"+mUpdate.getVersionName()+".apk";
				String tmpApk = "dahuaMobile_"+mUpdate.getVersionName()+".tmp";
				
				//判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();		
				if(storageState.equals(Environment.MEDIA_MOUNTED)){
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dahuaMobile/Update/";
					File file = new File(savePath);
					if(!file.exists()){
						file.mkdirs();
					}
					apkFilePath = savePath + apkName;
					tmpFilePath = savePath + tmpApk;
				}
				
				//没有挂载SD卡，无法下载文件
				if(StringUtils.isEmpty(apkFilePath)){
					mHandler.sendEmptyMessage(DOWN_NOSDCARD);
					return;
				}
				
				File ApkFile = new File(apkFilePath);
				
				//是否已下载更新文件
				if(ApkFile.exists()){
					downloadDialog.dismiss();
					installApk();
					return;
				}
				
				//输出临时下载文件
				File tmpFile = new File(tmpFilePath);
				FileOutputStream fos = new FileOutputStream(tmpFile);
				
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				
				//显示文件大小格式：2个小数点显示
		    	DecimalFormat df = new DecimalFormat("0.00");
		    	//进度条下面显示的总文件大小
		    	apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
				
				int count = 0;
				byte buf[] = new byte[1024];
				
				do{   		   		
		    		int numread = is.read(buf);
		    		count += numread;
		    		//进度条下面显示的当前下载文件大小
		    		tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
		    		//当前进度值
		    	    progress =(int)(((float)count / length) * 100);
		    	    //更新进度
		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    		if(numread <= 0){	
		    			//下载完成 - 将临时下载文件转成APK文件
						if(tmpFile.renameTo(ApkFile)){
							//通知安装
							mHandler.sendEmptyMessage(DOWN_OVER);
						}
		    			break;
		    		}
		    		fos.write(buf,0,numread);
		    	}while(!interceptFlag);//点击取消就停止下载
				
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}	
		}
	};
		
	/** 
	* @Title: downloadApk 
	* @Description: 下载apk
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月24日 下午3:05:22
	*/
	private void downloadApk(){
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/** 
	* @Title: installApk 
	* @Description: 安装apk
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年11月24日 下午3:05:31
	*/
	private void installApk(){
		File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }    
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mContext.startActivity(i);
	}
}
