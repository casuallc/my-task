package com.qing.mytask.base;

import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MyAdapter<T> extends BaseAdapter {
	protected List<T> dataList;
	
	protected Context context;
	
	public MyAdapter() {
	}
	
	public MyAdapter(List<T> dataList, Context context) {
		this.dataList = dataList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
