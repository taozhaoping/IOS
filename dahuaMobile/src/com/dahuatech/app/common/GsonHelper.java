package com.dahuatech.app.common;

import java.text.DateFormat;
import java.util.Date;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @ClassName GsonHelper
 * @Description Gson帮助类
 * @author 21291
 * @date 2014年10月30日 下午2:05:41
 */
public class GsonHelper {

	//内部类单例模式,延迟加载,线程安全(java中class加载时互斥的),也减少了内存消耗
	private static class SingletonHolder {  
        private static Gson instance = new  GsonBuilder()
		.registerTypeAdapter(Date.class, new DateHelper())
		.serializeNulls().setDateFormat(DateFormat.LONG)
		.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
		.setPrettyPrinting().create();
    }
	public static Gson getInstance() {
		return SingletonHolder.instance;
	}
}
