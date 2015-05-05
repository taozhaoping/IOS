package com.dahuatech.app.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dahuatech.app.AppContext;
import com.dahuatech.app.AppException;
import com.dahuatech.app.bean.RequestMethod;
import com.dahuatech.app.common.LogUtil;
import com.dahuatech.app.common.StringUtils;

public class ApiClient {
	
	private static final String TAG = "APIClient";	
	
	private static String appCookie;
	private static String appUserAgent;
	
	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	
	private AppException appException;			//全局异常类
	private Context context;					//全局上下文环境
	private String serviceUrl; 					//服务地址
	private int responseCode;					//响应状态码
	private String errorMessage;				//错误信息
	private String responseMessage;				//响应信息
	private boolean bare; 						//是否空的参数
	private HttpClient httpClient = null;  		//客户端请求实例
	private HttpResponse  httpResponse=null; 	//客户端响应实例
	
	public AppException getAppException() {
		return appException;
	}

	public void setAppException(AppException appException) {
		this.appException = appException;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}
	
	public boolean isBare() {
		return bare;
	}
	
	public ArrayList<NameValuePair> getParams() {
		return params;
	}

	public void setParams(ArrayList<NameValuePair> params) {
		this.params = params;
	}

	public ArrayList<NameValuePair> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<NameValuePair> headers) {
		this.headers = headers;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}
	
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setBare(boolean bare) {
		this.bare = bare;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	//默认初始化信息
	private ApiClient(Context context) {
		this.context=context;
		this.appException=null;
		this.serviceUrl="";
		this.responseCode = 0;
		this.responseMessage = "";
		this.errorMessage = "";
		this.bare = false;
	}
	
	//单例模式(线程安全写法)
	private static ApiClient apiClient; 
	public static ApiClient getApiClient(Context context) {
		if (apiClient == null) {
			apiClient = new ApiClient(context);
		}
		return apiClient;
	}
	
	//获取实例
	public static ApiClient getApiClient(Context context,String serviceUrl) {
		if (apiClient == null) {
			apiClient = new ApiClient(context);	
		}
		apiClient.setAppException(null);
		apiClient.setResponseCode(0);
		apiClient.setResponseMessage("");
		apiClient.setErrorMessage("");
		apiClient.setBare(false);
		apiClient.setServiceUrl(serviceUrl);
		apiClient.setHeaders(new ArrayList<NameValuePair>());
		apiClient.setParams(new ArrayList<NameValuePair>());
		return apiClient;
	}
	
	//获取实例
	public static ApiClient getApiClient(Context context,String serviceUrl,boolean bare) {
		if (apiClient == null) {
			apiClient = new ApiClient(context);
		}
		apiClient.setAppException(null);
		apiClient.setResponseCode(0);
		apiClient.setResponseMessage("");
		apiClient.setErrorMessage("");
		apiClient.setServiceUrl(serviceUrl);
		apiClient.setBare(bare);
		apiClient.setHeaders(new ArrayList<NameValuePair>());
		apiClient.setParams(new ArrayList<NameValuePair>());
		return apiClient;
	}
	
	/** 
	* @Title: AddParam 
	* @Description: 添加参数
	* @param @param name
	* @param @param value     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午5:25:01
	*/
	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	/** 
	* @Title: AddHeader 
	* @Description: 添加报文头
	* @param @param name
	* @param @param value     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午5:25:16
	*/
	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}
	
	/** 
	* @Title: getHttpClient 
	* @Description: 获取HttpClient实例
	* @param @return     
	* @return HttpClient    
	* @throws 
	* @author 21291
	* @date 2014年4月15日 下午3:59:35
	*/
	private HttpClient getHttpClient() {	
		if(httpClient==null)
		{
			httpClient= new HttpConnectionManager().getHttpClient();
		}
		return httpClient;
	}
	
	/** 
	* @Title: closeHttpClient 
	* @Description: 关闭httpClient连接
	* @param @param httpClient     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月15日 下午4:07:11
	*/
	public void closeHttpClient(){
		if(httpClient!=null){
			httpClient.getConnectionManager().shutdown();
			httpClient=null;
		}
	}
	
	/** 
	* @Title: Execute 
	* @Description: 执行get/put请求封装
	* @param @param method
	* @param @throws Exception     
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午5:27:24
	*/
	public void Execute(RequestMethod method) {
		// 添加参数信息
		String combinedParams = "";
		StringEntity entity = null;
		switch (method) {
			case GET: {
				if (!params.isEmpty()) {
					combinedParams += "/";
					for (NameValuePair p : params) {
						String paramString="";
						try {
							paramString = URLEncoder.encode(p.getValue(),HTTP.UTF_8);
						} catch (UnsupportedEncodingException e) {
							appException= AppException.encode(e);
							appException.makeToast(context);
						}
						if (combinedParams.length() > 1)
							combinedParams += "/" + paramString;
						else
							combinedParams += paramString;
					}
				}
				serviceUrl = serviceUrl + combinedParams;	
				HttpGet requestGet = new HttpGet(serviceUrl);
				// 添加报文头信息
				for (NameValuePair h : headers) {
					requestGet.setHeader(h.getName(), h.getValue());
				}
				executeRequest(requestGet);
				break;
			}
			case POST: {
				HttpPost requestPost = new HttpPost(serviceUrl);
				// 添加报文头信息
				for (NameValuePair h : headers) {
					requestPost.setHeader(h.getName(), h.getValue());
				}
				if (!params.isEmpty()) {
					if (bare) {
						try {
							entity = new StringEntity(params.get(0).getValue(),HTTP.UTF_8);
						} catch (UnsupportedEncodingException e) {
							appException= AppException.encode(e);
							appException.makeToast(context);
						}
					} else {
						JSONObject jsonObj = new JSONObject();
						combinedParams += "";
						for (NameValuePair p : params) {
							try {
								jsonObj.put(p.getName(), p.getValue());
							} catch (JSONException e) {
								appException= AppException.json(e);
								appException.makeToast(context);
							}
						}
						try {
							entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
						} catch (UnsupportedEncodingException e) {
							appException= AppException.encode(e);
							appException.makeToast(context);
						}
					}
					requestPost.setEntity(entity);
				}
				executeRequest(requestPost);
				break;
			}
		}
	}
	
	/** 
	* @Title: executeRequest 
	* @Description: 执行相关动作请求
	* @param @param request     
	* @return void    
	 * @throws AppException 
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午5:30:17
	*/
	private void executeRequest(HttpUriRequest request){
		try {
			httpClient = getHttpClient();
			httpResponse = httpClient.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			if (responseCode != HttpStatus.SC_OK) {
				errorMessage = httpResponse.getStatusLine().getReasonPhrase();
				LogUtil.d(TAG, "错误状态码:" + errorMessage + ""+" 具体错误信息:"+errorMessage);
				appException= AppException.http(responseCode);
				appException.makeToast(context);
			}
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				int length = (int) entity.getContentLength();
				responseMessage = retrieveInputStream(length, new InputStreamReader(entity.getContent(), HTTP.UTF_8));	
				entity.consumeContent();  //释放内容
				LogUtil.d(TAG, "responseMessage响应信息:" + responseMessage + "");
			}	
		}catch (IOException e) {
			if(request!=null){
				request.abort();
			}
			closeHttpClient();
			LogUtil.e(TAG, "网络错误异常："+e.getMessage());
			appException= AppException.network(e);
			appException.makeToast(context);
		}
		finally{
			if(request!=null){
				request.abort();
			}
			closeHttpClient();
		}
	}

	/** 
	* @Title: retrieveInputStream 
	* @Description: 接受从WCF端传回的数据，转换程String类型返回
	* @param @param length 长度 
	* @param @param isr 输入流
	* @param @return
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午5:30:43
	*/
	private String retrieveInputStream(int length,InputStreamReader isr) {
		if (length < 0)
			length = 10000;
		StringBuffer stringBuffer = new StringBuffer(length);
		try {
			char buffer[] = new char[length];
			int count;
			while ((count = isr.read(buffer, 0, length - 1)) > 0) {
				stringBuffer.append(buffer, 0, count);
			}
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(TAG, e.getMessage());
		} catch (IllegalStateException e) {
			LogUtil.e(TAG, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			LogUtil.e(TAG, e.getMessage());
		} finally {
			if(isr!=null)
			{
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuffer.toString();
	}
	
	/** 
	* @Title: getNetBitmap 
	* @Description: 获取网络图片
	* @param @param url 服务地址
	* @param @return
	* @param @throws AppException     
	* @return Bitmap    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午5:26:59
	*/
	public Bitmap getNetBitmap(String url) throws AppException {
		HttpUriRequest httpGet = null;
		Bitmap bitmap = null;
		try 
		{
			httpClient = getHttpClient();
			httpGet = new HttpGet(url);
			httpResponse = httpClient.execute(httpGet);
			int statusCode=httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				httpGet.abort();
				closeHttpClient();
				throw AppException.http(statusCode);
			}
			HttpEntity entity = httpResponse.getEntity();
			if(entity!=null)
			{
			   InputStream inStream = entity.getContent();
		       bitmap = BitmapFactory.decodeStream(inStream);
		       inStream.close();
			}     
		} catch (IOException e) { // 发生网络异常
			if(httpGet!=null){
				httpGet.abort();
			}		
			closeHttpClient();
			e.printStackTrace();
			LogUtil.e(TAG, "异常信息："+e.getMessage());
			throw AppException.network(e);
		}
		return bitmap;
	}
	
	/** 
	* @Title: cleanCookie 
	* @Description: 清空Cookie信息
	* @param      
	* @return void    
	* @throws 
	* @author 21291
	* @date 2014年4月17日 下午4:10:24
	*/
	public static void cleanCookie() {
		appCookie = "";
	}
	
	/** 
	* @Title: getCookie 
	* @Description: 获取Cookie信息
	* @param @param appContext
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午5:24:08
	*/
	public static String getCookie(AppContext appContext) {
		if(StringUtils.isEmpty(appCookie)) {
			appCookie = appContext.getProperty("cookie");
		}
		return appCookie;
	}
	
	/** 
	* @Title: getUserAgent 
	* @Description: 获取代理客户端信息
	* @param @param appContext
	* @param @return     
	* @return String    
	* @throws 
	* @author 21291
	* @date 2014年4月16日 下午5:24:21
	*/
	public static String getUserAgent(AppContext appContext) {
		if(StringUtils.isEmpty(appUserAgent)) {
			StringBuilder ua = new StringBuilder("OSChina.NET");
			ua.append('/'+appContext.getPackageInfo().versionName+'_'+appContext.getPackageInfo().versionCode);//App版本
			ua.append("/Android");//手机系统平台
			ua.append("/"+android.os.Build.VERSION.RELEASE);//手机系统版本
			ua.append("/"+android.os.Build.MODEL); //手机型号
			ua.append("/"+appContext.getAppId());//客户端唯一标识
			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}
}
