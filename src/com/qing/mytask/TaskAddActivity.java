package com.qing.mytask;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qing.mytask.R;
import com.qing.mytask.base.BaseActivity;
import com.qing.saq.anno.CView;
import com.qing.saq.anno.Event;

public class TaskAddActivity extends BaseActivity {
	@CView(id=R.id.task_add_quick)
	private Button taskQuickBt;
	
	@CView(id=R.id.task_add_complete)
	private Button taskCompleteBt;
	
	@CView(id=R.id.task_add_task_name)
	private EditText taskNameEt;
	
	@CView(id=R.id.task_add_task_startday)
	private EditText taskStartdayEt;
	
	@CView(id=R.id.task_add_task_content)
	private EditText taskContentEt;
	
	@CView(id=R.id.task_add_task_needs)
	private EditText taskNeedsEt;
	
	@CView(id=R.id.task_add_task_endday)
	private EditText taskEnddayEt;
	
	
	@Event(name="taskQuickBt")
	private OnClickListener onTaskQuickBtClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(TaskAddActivity.this, "hello", Toast.LENGTH_SHORT).show();
		}
	};
	
	@Event(name="taskCompleteBt")
	private OnClickListener onTaskCompleteBtClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			StringBuilder sb = new StringBuilder();
			sb.append(taskNameEt.getText().toString()).append("/n");
			sb.append(taskStartdayEt.getText().toString()).append("/n");
			sb.append(taskContentEt.getText().toString()).append("/n");
			sb.append(taskNeedsEt.getText().toString()).append("/n");
			sb.append(taskEnddayEt.getText().toString()).append("/n");
			Toast.makeText(TaskAddActivity.this, "完成保存"+sb.toString(), Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_add);
		getSaq().registe(this, getWindow().getDecorView()).init();
	}
	
	
}
