package com.qing.mytask.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qing.mytask.R;
import com.qing.mytask.base.MyAdapter;
import com.qing.mytask.model.Task;
import com.qing.saq.utils.DateUtils;
import com.qing.saq.utils.StringUtils;

public class HistTaskAdapter extends MyAdapter<Task> {
	
	public HistTaskAdapter(List<Task> dataList, Context context) {
		super(dataList, context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.hist_task_item_tasklist, null);
			holder.name = (TextView) convertView.findViewById(R.id.hist_task_task_name);
			holder.startday = (TextView) convertView.findViewById(R.id.hist_task_task_startday);
			holder.endday = (TextView) convertView.findViewById(R.id.hist_task_task_endday);
			holder.doneday = (TextView) convertView.findViewById(R.id.hist_task_task_doneday);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Task task = dataList.get(position);
		holder.name.setText(StringUtils.ifNull(task.getName(), ""));
		holder.startday.setText(DateUtils.formatDate(task.getStartday(), null));
		holder.endday.setText(DateUtils.formatDate(task.getEndday(), null));
		holder.doneday.setText(DateUtils.formatDate(task.getDoneday(), null));
		
		return convertView;
	}
	
	class ViewHolder {
		TextView name;
		TextView startday;
		TextView endday;
		TextView doneday;
	}

}
