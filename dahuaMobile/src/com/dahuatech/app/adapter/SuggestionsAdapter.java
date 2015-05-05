package com.dahuatech.app.adapter;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @ClassName SuggestionsAdapter
 * @Description 最近查询记录适配器
 * @author 21291
 * @date 2014年9月1日 下午6:12:51
 */
public class SuggestionsAdapter extends CursorAdapter {

	public SuggestionsAdapter(Context context, Cursor c) {
		super(context, c, 0);
	}

	public SuggestionsAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	public SuggestionsAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
	    LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		 TextView tv = (TextView) view;
         final int textIndex = cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1);
         tv.setText(cursor.getString(textIndex));
	}
}
