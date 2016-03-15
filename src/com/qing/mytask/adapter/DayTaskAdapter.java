package com.qing.mytask.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qing.mytask.R;
import com.qing.mytask.base.MyAdapter;
import com.qing.mytask.model.DayTask;
import com.qing.saq.utils.StringUtils;

public class DayTaskAdapter extends MyAdapter<DayTask> {
	
	public DayTaskAdapter(List<DayTask> dataList, Context context) {
		this.dataList = dataList;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.per_task_hist_item_daytasklist, null);
			holder.content = (TextView) convertView.findViewById(R.id.per_task_hist_daytaskcontent);
			holder.plan = (TextView) convertView.findViewById(R.id.per_task_hist_daytaskplan);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.content.setText(StringUtils.ifNull(dataList.get(position).getContent(), ""));
		holder.plan.setText(StringUtils.ifNull(dataList.get(position).getPlan(), ""));
		return convertView;
	}
	
	class ViewHolder {
		TextView content;
		TextView plan;
	}
}