package com.dahuatech.app.api;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * @ClassName HttpConnectionManager
 * @Description 连接池管理类
 * @author 21291
 * @date 2014年4月15日 下午5:03:36
 */
public class HttpConnectionManager {

	private DefaultHttpClient defaultHttpClient=null;
	private ClientConnectionManager  connectionManager;
	private static HttpParams httpParams;
	private static SchemeRegistry schemeRegistry;

	// 最大连接数
	public static final int MAX_TOTAL_CONNECTIONS = 2000;

	// 最大等待时间
	public static final  int WAIT_TIMEOUT = 60000;

	// 每个路由最大连接数
	public static final int MAX_ROUTE_CONNECTIONS = 2000;

	// 连接超时时间
	public static final  int CONNECT_TIMEOUT = 50000;

	// 读取超时时间
	public static final  int READ_TIMEOUT = 20000;

	static {
		httpParams = new BasicHttpParams();
		// 设置最大连接数
		ConnManagerParams.setMaxTotalConnections(httpParams,MAX_TOTAL_CONNECTIONS);
		// 设置获取连接的最大等待时间
		ConnManagerParams.setTimeout(httpParams, WAIT_TIMEOUT);	
		// 设置每个路由最大连接数  
		ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);  
		ConnManagerParams.setMaxConnectionsPerRoute(httpParams,connPerRoute);
		// 设置连接超时时间 
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECT_TIMEOUT); 
		// 设置读取超时时间  
		HttpConnectionParams.setSoTimeout(httpParams, READ_TIMEOUT);
		// 版本协议
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);  
		// 设置字符集
		HttpProtocolParams.setContentCharset(httpParams, "UTF_8");  
		
		//连接策略 http-默认 8010端口  https-默认 443端口
		schemeRegistry = new SchemeRegistry();  
		schemeRegistry.register(new Scheme("http",PlainSocketFactory.getSocketFactory(),8010));  
		schemeRegistry.register(new Scheme("https",SSLSocketFactory.getSocketFactory(),443));
	}
	
	/** 
	* @Name: HttpConnectionManager 
	* @Description:  默认构造函数
	*/
	public HttpConnectionManager(){
		connectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry); 
	}
	
	//获取客户端请求实例
	public HttpClient getHttpClient() {
		if (defaultHttpClient == null){
			defaultHttpClient=new DefaultHttpClient(connectionManager,httpParams);
		}
		return defaultHttpClient;
	}
}
