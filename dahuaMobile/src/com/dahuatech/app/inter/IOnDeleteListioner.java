package com.dahuatech.app.inter;

/**
 * @ClassName OnDeleteListioner
 * @Description 监听删除事件接口类
 * @author 21291
 * @date 2014年5月15日 上午11:23:41
 */
public interface IOnDeleteListioner {
	public abstract boolean isCandelete(int position);
	
	//删除
	public abstract void onDelete(int ID);
	
	//取消
	public abstract void onBack();
}
