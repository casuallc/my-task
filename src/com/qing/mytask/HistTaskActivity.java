package com.qing.mytask;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.qing.mytask.adapter.HistTaskAdapter;
import com.qing.mytask.base.BaseActivity;
import com.qing.mytask.dao.TaskDao;
import com.qing.mytask.model.Task;
import com.qing.saq.anno.CView;
import com.qing.saq.jdbc.SQL;

public class HistTaskActivity extends BaseActivity {
	
	@CView(id=R.id.hist_task_tasklist)
	private ListView taskListLv;

	private TaskDao dao;
	private List<Task> taskList;
	
	private OnItemClickListener onTaskListLvCL = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent();
			intent.setClass(HistTaskActivity.this, PerTaskHistActivity.class);
			intent.putExtra("taskId", taskList.get(position).getId());
			intent.putExtra("taskName", taskList.get(position).getName());
			startActivity(intent);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hist_task);
		
		SQL.context = getApplicationContext();
		getSaq().registe(this, getWindow().getDecorView()).init();
		init();
	}
	
	void init() {
		dao = new TaskDao();
		taskList = dao.listHistTask();
		
		taskListLv.setAdapter(new HistTaskAdapter(taskList, this));
		taskListLv.setOnItemClickListener(onTaskListLvCL);
	}
	
}
