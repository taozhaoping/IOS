package com.dahuatech.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * @ClassName MyBaseAdapter
 * @Description 自定义基础BaseAdapter
 * @author 21291
 * @date 2014年6月5日 下午2:10:25
 * @param <T>
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected Context context;				// 运行上下文
	protected List<T> listItems;			// 数据集合
	protected LayoutInflater listContainer;	// 视图容器
	protected int itemViewResource;			// 自定义项视图源
	
	public List<T> getListItems() {
		return listItems;
	}
	
	public void setListItems(List<T> listItems) {
		if(listItems!=null)
			this.listItems = listItems;
		else
			this.listItems = new ArrayList<T>();
	}
	
	public MyBaseAdapter(Context context, List<T> data,int resource) {
		this.context=context;
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.itemViewResource = resource;
		setListItems(data);
	}
	
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
