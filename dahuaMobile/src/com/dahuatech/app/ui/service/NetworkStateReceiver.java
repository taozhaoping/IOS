package com.dahuatech.app.ui.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.dahuatech.app.R;

/**
 * @ClassName NetworkStateReceiver
 * @Description 网络状态改变通知
 * @author 21291
 * @date 2014年6月10日 下午1:33:58
 */
public class NetworkStateReceiver extends BroadcastReceiver {
	
	@SuppressLint("ShowToast")
	@Override
	public void onReceive(Context context, Intent intent) {
		String ACTION_NAME = intent.getAction();
		if("com.dahuatech.app.action.APPWIFI_CHANGE".equals(ACTION_NAME)){
			if(!isNetworkConnected(context)){ //判断是否网络连接
				Toast.makeText(context, R.string.network_not_connected, 0).show();  
			}
		}
	}
	
	/** 
	* @Title: isNetworkConnected 
	* @Description: 检测网络是否可用
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午10:56:31
	*/
	private boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm!=null){
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if(ni != null && ni.isConnected()){
				// 当前网络是连接的
				if (ni.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}

}
