package com.dahuatech.app.api;

import android.content.Context;

import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.bean.ResponseMessage;;

/**
 * @ClassName InvokeApi
 * @Description 调用ApiClient
 * @author 21291
 * @date 2014年4月21日 上午10:24:57
 */
public class InvokeApi {
	private static ResponseMessage responseMessage;	
	private static ApiClient apiClient;
	static {
		 apiClient=null;
	}
	public InvokeApi() {}

	/** 
	* @Title: getApiClient 
	* @Description: 获取ApiClient对象
	* @param @param context 当前上下文环境
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 上午10:34:30
	*/
	public static ApiClient getApiClient(Context context) {
		// 实例化对象
		apiClient=ApiClient.getApiClient(context);
		return apiClient;
	}
	
	/** 
	* @Title: getApiClient 
	* @Description: 
	* @param @param serviceUrl
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014年4月21日 上午10:45:15
	*/
	/** 
	* @Title: getApiClient 
	* @Description: 获取ApiClient对象
	* @param @param context 当前上下文环境
	* @param @param serviceUrl 服务地址
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 上午10:34:59
	*/
	public static ApiClient getApiClient(Context context,String serviceUrl) {
		// 实例化对象
		apiClient=ApiClient.getApiClient(context,serviceUrl);
		apiClient.AddHeader("User-Agent", "Mozilla/5.0");
		apiClient.AddHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		apiClient.AddHeader("Accept", "application/json");
		apiClient.AddHeader("Content-type", "application/json;");
		return apiClient;
	}
	
	/** 
	* @Title: getApiClient 
	* @Description: 
	* @param @param serviceUrl
	* @param @param bare
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014年4月21日 上午10:45:30
	*/
	/** 
	* @Title: getApiClient 
	* @Description: 获取ApiClient对象
	* @param @param context 当前上下文环境
	* @param @param serviceUrl 服务地址
	* @param @param bare 是否空的参数
	* @param @return     
	* @return ApiClient    
	* @throws 
	* @author 21291
	* @date 2014年9月24日 上午10:38:11
	*/
	public static ApiClient getApiClient(Context context,String serviceUrl,boolean bare) {
		// 实例化对象
		apiClient=ApiClient.getApiClient(context,serviceUrl,bare);
		apiClient.AddHeader("User-Agent", "Mozilla/5.0");
		apiClient.AddHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		apiClient.AddHeader("Accept", "application/json");
		apiClient.AddHeader("Content-type", "application/json;");
		return apiClient;
	}
	
	/** 
	* @Title: closeHttpClient 
	* @Description: 关闭HttpClient连接
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年8月29日 下午1:45:36
	*/
	public static void closeHttpClient(){
		if(apiClient!=null){
			apiClient.closeHttpClient();
		}
	}
	
	/** 
	* @Title: getResponse 
	* @Description: 根据服务地址，获取响应信息
	* @param @param apiClient
	* @param @param requestMethod
	* @param @return     
	* @return ResponseMessage    
	* @throws 
	* @author 21291
	* @date 2014年4月21日 上午10:52:32
	*/
	public static ResponseMessage getResponse(ApiClient apiClient,RequestMethod requestMethod){
		try {
			responseMessage= new ResponseMessage();
			apiClient.Execute(requestMethod);
			responseMessage.setResponseCode(apiClient.getResponseCode());
			responseMessage.setResponseMessage(jsonTokener(apiClient.getResponseMessage()));
			responseMessage.setResponseErrorMessage(apiClient.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return responseMessage;		
	}
	
	/** 
	* @Title: jsonTokener 
	* @Description: 去掉UTF-8的BOM头
	* @param @param in
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月21日 上午10:40:26
	*/
	private static String jsonTokener(String in) {
		if (in != null && in.startsWith("\ufeff")) {
			in = in.substring(1);
		}
		return in;
	}

}
