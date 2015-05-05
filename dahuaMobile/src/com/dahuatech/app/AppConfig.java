package com.dahuatech.app;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

/**
 * @ClassName AppConfig
 * @Description 应用程序配置类：用于保存用户相关信息及设置
 * @author 21291
 * @date 2014年4月16日 下午2:05:17
 */
public class AppConfig {
	private final static String APP_CONFIG = "AppConfig";
	
	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	public final static String CONF_COOKIE = "cookie";
	public final static String CONF_LOAD_IMAGE = "perf_loadimage";
	public final static String CONF_SCROLL = "perf_scroll";
	public final static String CONF_HTTPS_LOGIN = "perf_httpslogin";
	public final static String CONF_VOICE = "perf_voice";
	public final static String CONF_CHECKUP = "perf_checkup";
	
	public final static String SAVE_IMAGE_PATH = "save_image_path";
	@SuppressLint("NewApi")
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory()+ File.separator+ "OSChina"+ File.separator;
	
	private Context mContext;
	private static AppConfig appConfig;
	static{
		appConfig=null;
	}
	
	public AppConfig(){}  //默认构造函数
	
	/** 
	* @Title: getAppConfig 
	* @Description: 获取AppConfig实例 (单例模式-线程安全写法)
	* @param @param context
	* @param @return     
	* @return AppConfig    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午9:23:05
	*/
	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}
	
	/** 
	* @Title: getSharedPreferences 
	* @Description: 获取Preference设置
	* @param @param context
	* @param @return     
	* @return SharedPreferences    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午2:08:09
	*/
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	/** 
	* @Title: isLoadImage 
	* @Description: 是否加载显示文章图片
	* @param @param 
	* @param @return     
	* @return boolean    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午2:13:50
	*/
	public static boolean isLoadImage(Context context) {
		return getSharedPreferences(context).getBoolean(CONF_LOAD_IMAGE, true);
	}
	
	public String getCookie() {
		return get(CONF_COOKIE);
	}

	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}
	
	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}
	
	/** 
	* @Title: get 
	* @Description: 获取Properties对象
	* @param @return     
	* @return Properties    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午9:19:31
	*/
	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			// 读取app_config目录下的config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);
			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}
	
	/** 
	* @Title: setProps 
	* @Description: 设置Properties对象
	* @param @param p     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 上午9:19:23
	*/
	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			// 把config建在(自定义)app_config的目录下
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);
			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}
}
