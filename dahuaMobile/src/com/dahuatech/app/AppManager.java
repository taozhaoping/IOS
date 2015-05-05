package com.dahuatech.app;

import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @ClassName AppManager
 * @Description 应用程序Activity管理类，用于Activity管理和应用程序退出
 * @author 21291
 * @date 2014年4月16日 上午10:37:33
 */
public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	public AppManager() {}

	/** 
	* @Title: getAppManager 
	* @Description: 单一实例
	* @param @return     
	* @return AppManager    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:38:47
	*/
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	
	
	/** 
	* @Title: addActivity 
	* @Description: 添加Activity到堆栈中
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:39:55
	*/
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	
	/** 
	* @Title: currentActivity 
	* @Description: 获取当前Activity(堆栈中最后一个压入的)
	* @param @return     
	* @return Activity    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:40:48
	*/
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	
	/** 
	* @Title: finishActivity 
	* @Description: 结束当前Activity（堆栈中最后一个压入的）
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:44:06
	*/
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	
	/** 
	* @Title: finishActivity 
	* @Description: 结束指定的Activity
	* @param @param activity     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:44:18
	*/
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	
	/** 
	* @Title: finishActivity 
	* @Description: 结束指定类名的Activity
	* @param @param cls     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:45:36
	*/
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	
	/** 
	* @Title: finishAllActivity 
	* @Description: 结束所有Activity
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:46:08
	*/
	public void finishAllActivity(){
		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();
	}
	
	/** 
	* @Title: AppExit 
	* @Description: 退出应用程序
	* @param @param context     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 上午10:47:57
	*/
	@SuppressLint("NewApi")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
}
