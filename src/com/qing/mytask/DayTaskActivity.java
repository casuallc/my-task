package com.qing.mytask;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qing.mytask.base.BaseActivity;
import com.qing.mytask.base.MyAdapter;
import com.qing.mytask.dao.TaskDao;
import com.qing.mytask.model.Task;
import com.qing.saq.anno.CView;
import com.qing.saq.anno.Event;
import com.qing.saq.anno.EventType;
import com.qing.saq.jdbc.SQL;
import com.qing.saq.utils.DateUtils;
import com.qing.saq.utils.StringUtils;

public class DayTaskActivity extends BaseActivity {
	
	@CView(id=R.id.day_task_add)
	private Button addTaskBt;
	
	@CView(id=R.id.day_task_tasklist)
	private ListView taskListLv;
	
	@CView(id=R.id.day_task_task_name)
	private TextView taskNameTv;
	
	@CView(id=R.id.day_task_task_startday)
	private TextView taskStartdayTv;
	
	@CView(id=R.id.day_task_task_endday)
	private TextView taskEnddayTv;
	
	@CView(id=R.id.day_task_days_task_cost)
	private TextView daysTaskCostTv;
	
	private TaskListAdapter tasklistAdapter;
	
	private List<Task> taskList;
	
	@Event(name="taskListLv", type=EventType.LV_ITEM_CLICK)
	private OnItemClickListener onTaskListLvItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent();
			intent.putExtra("taskId", taskList.get(position).getId());
			intent.putExtra("taskName", taskList.get(position).getName());
			intent.setClass(DayTaskActivity.this, DayTaskEditActivity.class);
			startActivity(intent);
		}
	};
	
	@Event(name="addTaskBt")
	private OnClickListener onAddTaskBtCL = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), TaskAddActivity.class);
			startActivity(intent);
		}
	};
	
	@CView(id=R.id.day_task_hist_task)
	private Button histTaskBt;
	@Event(name="histTaskBt")
	private OnClickListener onHistTaskBtCL = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(DayTaskActivity.this, HistTaskActivity.class);
			startActivity(intent);
		}
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_task);
		
		SQL.context = getApplicationContext();
		getSaq().registe(this, getWindow().getDecorView()).init();
		init();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		taskList = dao.list(new Task());
		tasklistAdapter.setData(taskList);
		tasklistAdapter.notifyDataSetChanged();
	}
	
	private TaskDao dao = new TaskDao();
	
	void init() {
		tasklistAdapter = new TaskListAdapter();
		taskList = dao.list(new Task());
		tasklistAdapter.setData(taskList);
		taskListLv.setAdapter(tasklistAdapter);
	}
	
	class TaskListAdapter extends MyAdapter {
		private List<Task> list;
		
		public TaskListAdapter() {
		}
		
		public void setData(List<Task> list) {
			this.list = list;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}
		
		@Override
		public Object getItem(int position) {
			return list.get(position);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null) {
				convertView = View.inflate(DayTaskActivity.this, R.layout.day_task_item_tasklist, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.day_task_task_name);
				holder.startday = (TextView) convertView.findViewById(R.id.day_task_task_startday);
				holder.dayscost = (TextView) convertView.findViewById(R.id.day_task_days_task_cost);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(StringUtils.ifNull(list.get(position).getName(), ""));
			holder.startday.setText(StringUtils.ifNull(DateUtils.formatDate(list.get(position).getStartday(), null), ""));
			holder.dayscost.setText(list.get(position).getDayscost()+"");
			
			return convertView;
		}
		
		class ViewHolder {
			TextView name;
			TextView dayscost;
			TextView startday;
		}
	}
}
