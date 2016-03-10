package com.qing.mytask;

import java.util.UUID;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qing.mytask.base.BaseActivity;
import com.qing.mytask.dao.DayTaskDao;
import com.qing.mytask.model.DayTask;
import com.qing.saq.anno.CView;
import com.qing.saq.anno.Event;

public class DayTaskEditActivity extends BaseActivity {

	@CView(id=R.id.day_task_edit_task_name)
	private TextView taskNameTv;
	
	@CView(id=R.id.day_task_edit_task_content)
	private EditText taskContentEt;
	
	@CView(id=R.id.day_task_edit_complete)
	private Button taskCompleteBt;
	
	private DayTaskDao dao = new DayTaskDao();
	
	@Event(name="taskCompleteBt")
	private OnClickListener onTaskCompleteBtClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			DayTask task = dao.queryDayTask(taskId, null);
			if(task == null) {
				task = new DayTask();
				task.setTaskId(taskId);
				task.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				task.setContent(taskContentEt.getText().toString());
				dao.add(task);
				Toast.makeText(getApplicationContext(), "已保存", Toast.LENGTH_SHORT).show();
			} else {
				task.setId(task.getId());
				task.setContent(taskContentEt.getText().toString());
				dao.update(task);
				Toast.makeText(getApplicationContext(), "已更新", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private String taskId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_task_edit);
		
		getSaq().registe(this, getWindow().getDecorView()).init();
		taskId = getIntent().getStringExtra("taskId");
		taskNameTv.setText(getIntent().getStringExtra("taskName"));
		DayTask task = dao.queryDayTask(taskId, null);
		if(task != null) {
			taskContentEt.setText(task.getContent());
		}
	}
}
