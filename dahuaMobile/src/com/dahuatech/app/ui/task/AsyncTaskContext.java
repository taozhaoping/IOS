package com.dahuatech.app.ui.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dahuatech.app.R;
import com.dahuatech.app.bean.Base;
import com.dahuatech.app.inter.ITaskContext;

/**
 * @ClassName AsyncTaskContext
 * @Description 异步请求策略类
 * @author 21291
 * @date 2014年10月28日 下午1:54:10
 */
public class AsyncTaskContext {						
	private Context context;				 		//上下文环境
	private ITaskContext iTaskContext;				//共同接口实例
	private ProgressDialog dialog;   				//弹出框
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public ITaskContext getiTaskContext() {
		return iTaskContext;
	}
	public void setiTaskContext(ITaskContext iTaskContext) {
		this.iTaskContext = iTaskContext;
	}

	//内部类单例模式
	private static class singletonHolder {  
        private static AsyncTaskContext instance = new AsyncTaskContext();  
    }  
	private AsyncTaskContext() {}
	private static AsyncTaskContext asyncTaskContext=null;
	public static AsyncTaskContext getInstance(Context context,ITaskContext iTaskContext) {
		asyncTaskContext=singletonHolder.instance;
		asyncTaskContext.setContext(context);	
		asyncTaskContext.setiTaskContext(iTaskContext);
		return asyncTaskContext;
	}
	
	/** 
	* @Title: initDiglog 
	* @Description: 初始化弹出框
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月28日 下午2:27:05
	*/
	private void initDiglog(){
		dialog = new ProgressDialog(context);
		dialog.setMessage(context.getResources().getString(R.string.dialog_loading));
		dialog.setCancelable(false);
	}
	
	/** 
	* @Title: callAsyncTask 
	* @Description: 调用异步请求类
	* @param @param serviceUrl     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年10月28日 下午2:41:10
	*/
	public void callAsyncTask(String serviceUrl){
		initDiglog();
		new AsyncTaskRequest().execute(serviceUrl);
	}

	/**
	 * @ClassName AsyncTaskRequest
	 * @Description  异步执行任务,获取数据实体
	 * @author 21291
	 * @date 2014年10月28日 下午2:40:30
	 */
	private class AsyncTaskRequest extends AsyncTask<String, Void, Base> {
		// 表示任务执行之前的操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show(); // 显示等待框
		}

		// 完成更新UI操作
		@Override
		protected void onPostExecute(Base result) {
			super.onPostExecute(result);
			iTaskContext.initBase(result);
			dialog.dismiss(); 	// 销毁等待框
		}

		// 主要是完成耗时操作
		@Override
		protected Base doInBackground(String... params) {
			return iTaskContext.getDataByPost(params[0]);
		}
	}	
}
