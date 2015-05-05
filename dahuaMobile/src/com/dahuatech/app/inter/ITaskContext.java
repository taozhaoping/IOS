package com.dahuatech.app.inter;

import com.dahuatech.app.bean.Base;

/**
 * @ClassName ITaskContext
 * @Description 单据共同方法接口
 * @author 21291
 * @date 2014年10月28日 下午2:25:33
 */
public interface ITaskContext {	
	//异步获取实体信息
	public abstract Base getDataByPost(String serviceUrl);
	
	//初始化信息
	public abstract void initBase(Base base);
}
