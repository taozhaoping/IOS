package com.dahuatech.app.bean.attendance;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName AdAmapListInfo
 * @Description 中心地址缓存实体
 * @author 21291
 * @date 2015年1月5日 上午10:13:13
 */
public class AdAmapListInfo extends Base {
	private static final long serialVersionUID = 1L;
	
	private String FCacheKey;	//缓存版本号
	private String FAmapList;	//中心地址列表
	public String getFCacheKey() {
		return FCacheKey;
	}
	public void setFCacheKey(String fCacheKey) {
		FCacheKey = fCacheKey;
	}
	public String getFAmapList() {
		return FAmapList;
	}
	public void setFAmapList(String fAmapList) {
		FAmapList = fAmapList;
	}
	
	//内部类单例模式
	private static class SingletonHolder {  
        private static AdAmapListInfo instance = new AdAmapListInfo();  
    }
	private AdAmapListInfo() {}
	public static AdAmapListInfo getAdAmapListInfo() {
		return SingletonHolder.instance;
	}
}
