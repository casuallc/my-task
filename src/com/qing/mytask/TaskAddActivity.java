package com.qing.mytask;

import java.util.Date;
import java.util.UUID;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qing.mytask.base.BaseActivity;
import com.qing.mytask.dao.TaskDao;
import com.qing.mytask.dao.TaskDaoI;
import com.qing.mytask.model.Task;
import com.qing.saq.anno.CView;
import com.qing.saq.anno.Event;
import com.qing.saq.jdbc.SQL;
import com.qing.saq.utils.DateUtils;
import com.qing.saq.utils.StringUtils;

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
	
	@Event(name="taskCompleteBt")
	private OnClickListener onTaskCompleteBtClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			save();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_add);
		getSaq().registe(this, getWindow().getDecorView()).init();
		
		taskStartdayEt.setText(DateUtils.formatDate(null, null));
	}
	
	/** 保存任务信息 */
	void save() {
		if(!check()) {
			return;
		}
		SQL.context = getApplicationContext();
		TaskDaoI dao = new TaskDao();
		Task task = new Task();
		task.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		task.setName(taskNameEt.getText().toString());
		task.setStartday(Long.valueOf(taskStartdayEt.getText().toString()));
		task.setContent(taskContentEt.getText().toString());
		task.setNeeds(taskNeedsEt.getText().toString());
		task.setEndday(Long.valueOf(taskEnddayEt.getText().toString()));
		dao.add(task);
		Toast.makeText(getApplicationContext(), "已保存", Toast.LENGTH_SHORT).show();
	}
	
	/** 校验输入 */
	boolean check() {
		if(taskNameEt.getText().toString().length() > 100) {
			Toast.makeText(this, "名称不超过100个字符！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(taskNeedsEt.getText().toString().length() > 100) {
			Toast.makeText(this, "所需物品不超过200个字符！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(StringUtils.isEmpty(taskStartdayEt.getText().toString())) {
			taskStartdayEt.setText(DateUtils.formatDate(null, null));
		}
		if(StringUtils.isEmpty(taskEnddayEt.getText().toString())) {
			taskEnddayEt.setText(DateUtils.formatDate(DateUtils.afterDate(new Date(), 10), null));
		}
		return true;
	}
}
