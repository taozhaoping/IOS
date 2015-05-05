package com.dahuatech.app.inter;

import com.dahuatech.app.bean.ResultMessage;

/**
 * @ClassName ICheckNextNode
 * @Description 检查该单据是否启用下级节点
 * @author 21291
 * @date 2014年11月14日 上午10:19:59
 */
public interface ICheckNextNode {	

	//返回结果值
	public abstract void setCheckResult(ResultMessage resultMessage);
}
