package com.qing.mytask;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.qing.mytask.base.BaseActivity;
import com.qing.mytask.base.MyAdapter;
import com.qing.mytask.dao.DayTaskDao;
import com.qing.mytask.dao.TaskDao;
import com.qing.mytask.model.DayTask;
import com.qing.saq.anno.CView;

public class PerTaskHistActivity extends BaseActivity {
	
	@CView(id=R.id.per_task_hist_daytasklist)
	private ListView daytaskListLv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.per_task_hist);
		getSaq().registe(this, getWindow().getDecorView()).init();
		
		initData();
	}
	
	private DayTaskDao dao = new DayTaskDao();
	
	private List<DayTask> dayTaskList;
	
	@CView(id=R.id.per_task_hist_task_name)
	private TextView taskNameTv;
	
	void initData() {
		String taskId = getIntent().getStringExtra("taskId");
		taskNameTv.setText(getIntent().getStringExtra("taskName"));
		dayTaskList = dao.listDayTaskByTaskid(taskId);
		daytaskListLv.setAdapter(new DayTaskAdapter());
	}
	
	class DayTaskAdapter extends MyAdapter {
		
		@Override
		public int getCount() {
			return dayTaskList.size();
		}
		
		@Override
		public Object getItem(int position) {
			return dayTaskList.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getApplicationContext(), R.layout.per_task_hist_item_daytasklist, null);
				holder.content = (TextView) convertView.findViewById(R.id.per_task_hist_daytaskcontent);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.content.setText(dayTaskList.get(position).getContent());
			return convertView;
		}
		
		class ViewHolder {
			TextView content;
		}
	}
}
