package com.dahuatech.app.inter;

import com.dahuatech.app.bean.attendance.AdAmapInfo;
import com.dahuatech.app.bean.develophour.DHWeekInfo;
import com.dahuatech.app.bean.mytask.RejectNodeInfo;

/**
 * @ClassName SpinnerListener
 * @Description 自定义下拉框点击事件
 * @author 21291
 * @date 2014年9月12日 下午3:05:55
 */
public interface ISpinnerListener {
	public void rejectOk(int n,RejectNodeInfo reNodeInfo);
	public void dHWeekOk(int n,String itemText,DHWeekInfo dhWeekInfo);
	public void adAmapOk(int n,AdAmapInfo adAmapInfo);
    public void cancelled();
}
