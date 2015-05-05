package com.dahuatech.app.bean;

/**
 * @ClassName Entity
 * @Description 实体类
 * @author 21291
 * @date 2014年4月17日 下午3:46:35
 */
public abstract class Entity extends Base {
	
	private static final long serialVersionUID = 1L;
	
	protected int FID;
	
	public int getFID() {
		return FID;
	}
	
	public Entity(){}
}
