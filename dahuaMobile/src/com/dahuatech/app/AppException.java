package com.dahuatech.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.http.HttpException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.dahuatech.app.common.StringUtils;
import com.dahuatech.app.common.UIHelper;

/**
 * @ClassName AppException
 * @Description 应用程序异常类：用于捕获异常和提示错误信息
 * @author 21291
 * @date 2014年4月16日 上午11:25:33
 */
public class AppException extends Exception implements UncaughtExceptionHandler {
	
	private static final long serialVersionUID = 1L;

	private final static boolean Debug = true; //是否保存错误日志
	
	//定义异常类型 
	public final static byte TYPE_NETWORK 	= 0x01;
	public final static byte TYPE_SOCKET	= 0x02;
	public final static byte TYPE_HTTP_CODE	= 0x03;
	public final static byte TYPE_HTTP_ERROR= 0x04;
	public final static byte TYPE_XML	 	= 0x05;
	public final static byte TYPE_IO	 	= 0x06;
	public final static byte TYPE_RUN	 	= 0x07;
	public final static byte TYPE_JSON	 	= 0x08;
	public final static byte TYPE_ENCODING	= 0x09;
	
	private byte type;
	private int code;
	
	public int getCode() {
		return this.code;
	}
	public int getType() {
		return this.type;
	}
	
	//系统默认的UncaughtException处理类 
	@SuppressWarnings("unused")
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	
	private AppException(){
		//当程序发生Uncaught异常的时候,有该类 来接管程序,并记录 发送错误报告.. 註冊方式
		this.mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
	}
	
	public AppException(byte type, int code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;		
		if(Debug){
			this.saveErrorLog(excp);
		}
	}
	
	/**
	 * 保存异常日志
	 * @param excp
	 */
	public void saveErrorLog(Exception excp) {
		String errorlog = "errorlog.txt";
		String savePath = "";
		String logFilePath = "";
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			//判断是否挂载了SD卡
			String storageState = Environment.getExternalStorageState();		
			if(storageState.equals(Environment.MEDIA_MOUNTED)){
				savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/dahuaMobile/Log/";
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				logFilePath = savePath + errorlog;
			}
			//没有挂载SD卡，无法写文件
			if(StringUtils.isEmpty(logFilePath)){
				return;
			}
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			fw = new FileWriter(logFile,true);
			pw = new PrintWriter(fw);
			pw.println("--------------------"+(StringUtils.toDateString(new Date()))+"---------------------");	
			excp.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}finally{ 
			if(pw != null){ pw.close(); } 
			if(fw != null){ try { fw.close(); } catch (IOException e) { }}
		}
	}
	
	/** 
	* @Title: makeToast 
	* @Description: 友好提示
	* @param @param ctx     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月24日 下午8:05:23
	*/
	public void makeToast(Context ctx){
		switch(this.getType()){
		case TYPE_HTTP_CODE:
			String err = ctx.getString(R.string.http_status_code_error, this.getCode());
			Toast.makeText(ctx, err, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_HTTP_ERROR:
			Toast.makeText(ctx, R.string.http_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_SOCKET:
			Toast.makeText(ctx, R.string.socket_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_NETWORK:
			Toast.makeText(ctx, R.string.network_not_connected, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_XML:
			Toast.makeText(ctx, R.string.xml_parser_failed, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_JSON:
			Toast.makeText(ctx, R.string.xml_parser_failed, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_IO:
			Toast.makeText(ctx, R.string.io_exception_error, Toast.LENGTH_SHORT).show();
			break;
		case TYPE_RUN:
			Toast.makeText(ctx, R.string.app_run_code_error, Toast.LENGTH_SHORT).show();
			break;	
		case TYPE_ENCODING:
			Toast.makeText(ctx, R.string.error_encode_error, Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	public static AppException http(int code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}
	
	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, 0 ,e);
	}

	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, 0 ,e);
	}
	
	public static AppException io(Exception e) {
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			return new AppException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof IOException){
			return new AppException(TYPE_IO, 0 ,e);
		}
		return run(e);
	}
	
	public static AppException xml(Exception e) {
		return new AppException(TYPE_XML, 0, e);
	}
	
	public static AppException json(Exception e) {
		return new AppException(TYPE_JSON, 0, e);
	}
	
	public static AppException encode(Exception e) {
		return new AppException(TYPE_ENCODING, 0, e);
	}
	
	/** 
	* @Title: network 
	* @Description: 返回网络错误实例
	* @param @param e
	* @param @return     
	* @return AppException    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 上午9:45:28
	*/
	public static AppException network(Exception e) {
		AppException appException=null;
		if(e instanceof UnknownHostException || e instanceof ConnectException){
			appException=new AppException(TYPE_NETWORK, 0, e);
		}
		else if(e instanceof HttpException){
			appException= http(e);
		}
		else if(e instanceof SocketException || e instanceof SocketTimeoutException){
			appException= socket(e);
		}
		return appException;
	}
	
	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, 0, e);
	}

	/** 
	* @Title: getAppExceptionHandler 
	* @Description: 获取APP异常崩溃处理对象
	* @param @return     
	* @return AppException    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午1:19:19
	*/
	public static AppException getAppExceptionHandler(){
		return new AppException();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(ex == null) {
			return;
		}	
		final Context context = AppManager.getAppManager().currentActivity();
		if(context == null) {
			return;
		}		
		final String crashReport = getCrashReport(context, ex);
		//显示异常信息&发送报告
		new Thread() {
			public void run() {
				Looper.prepare();
				UIHelper.sendAppCrashReport(context, crashReport);
				Looper.loop();
			}

		}.start();
	}
	
	/** 
	* @Title: handleException 
	* @Description: 自定义异常处理:收集错误信息&发送错误报告
	* @param @param ex
	* @param @return     
	* @return true:处理了该异常信息;否则返回false    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午1:20:34
	*/
	@SuppressWarnings("unused")
	private boolean handleException(Throwable ex) {
		if(ex == null) {
			return false;
		}	
		final Context context = AppManager.getAppManager().currentActivity();
		if(context == null) {
			return false;
		}		
		final String crashReport = getCrashReport(context, ex);
		//显示异常信息&发送报告
		new Thread() {
			public void run() {
				Looper.prepare();
				UIHelper.sendAppCrashReport(context, crashReport);
				Looper.loop();
			}

		}.start();
		return true;
	}
	
	/** 
	* @Title: getCrashReport 
	* @Description: 获取APP崩溃异常报告
	* @param @param context
	* @param @param ex
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午1:21:45
	*/
	private String getCrashReport(Context context, Throwable ex) {
		PackageInfo pinfo = ((AppContext)context.getApplicationContext()).getPackageInfo();
		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Version: "+pinfo.versionName+"("+pinfo.versionCode+")\n");
		exceptionStr.append("Android: "+android.os.Build.VERSION.RELEASE+"("+android.os.Build.MODEL+")\n");
		exceptionStr.append("Exception: "+ex.getMessage()+"\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString()+"\n");
		}
		return exceptionStr.toString();
	}


}
