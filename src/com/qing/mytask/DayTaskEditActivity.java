package com.qing.mytask;

import java.util.UUID;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qing.mytask.base.BaseActivity;
import com.qing.mytask.dao.DayTaskDao;
import com.qing.mytask.dao.TaskDao;
import com.qing.mytask.model.DayTask;
import com.qing.mytask.model.Task;
import com.qing.mytask.model.TaskDelay;
import com.qing.saq.anno.CView;
import com.qing.saq.anno.Event;
import com.qing.saq.utils.DateUtils;
import com.qing.saq.utils.StringUtils;

public class DayTaskEditActivity extends BaseActivity {

	@CView(id=R.id.day_task_edit_task_name)
	private TextView taskNameTv;
	
	@CView(id=R.id.day_task_edit_task_daycontent)
	private EditText taskDayContentEt;
	
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
				task.setContent(taskDayContentEt.getText().toString());
				dao.add(task);
				Toast.makeText(getApplicationContext(), "已保存", Toast.LENGTH_SHORT).show();
			} else {
				task.setId(task.getId());
				task.setContent(taskDayContentEt.getText().toString());
				dao.update(task);
				Toast.makeText(getApplicationContext(), "已更新", Toast.LENGTH_SHORT).show();
			}
		}
	};

	private String taskId;
	
	@CView(id=R.id.day_task_edit_task_startday)
	private TextView taskStartdayTv;
	
	@CView(id=R.id.day_task_edit_task_endday)
	private TextView taskEnddayTv;
	
	@CView(id=R.id.day_task_edit_task_content)
	private TextView taskContentTv;
	
	@CView(id=R.id.day_task_edit_task_needs)
	private TextView taskNeedsTv;
	
	@CView(id=R.id.day_task_edit_per_task_hist)
	private Button perTaskHistBt;
	
	@Event(name="perTaskHistBt")
	private OnClickListener onPerTaskHistBtCL = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("taskId", taskId);
			intent.putExtra("taskName", getIntent().getStringExtra("taskName"));
			intent.setClass(getApplicationContext(), PerTaskHistActivity.class);
			startActivity(intent);
		}
	};
	
	@CView(id=R.id.day_task_edit_task_plan)
	private TextView taskPlanTv;
	
	@CView(id=R.id.day_task_edit_task_plan)
	private Button taskPlanBt;
	
	@Event(name="taskPlanBt")
	private OnClickListener onTaskPlanBtCL = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("taskId", taskId);
			intent.putExtra("taskName", getIntent().getStringExtra("taskName"));
			intent.setClass(getApplicationContext(), TaskPlanEditActivity.class);
			startActivity(intent);
		}
	};
	
	@CView(id=R.id.day_task_edit_delay_reason)
	private EditText delayReasonEt;
	
	@CView(id=R.id.day_task_edit_delay_oneday)
	private Button delayOneDayBt;
	
	@Event(name="delayOneDayBt")
	private OnClickListener onDelayOneDayBtCL = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String reason = StringUtils.ifNull(delayReasonEt.getText().toString(), "");
			if(taskDelay == null)
				taskDelay = new TaskDelay();
			taskDelay.setTaskId(taskId);
			taskDelay.setDay(Long.valueOf(DateUtils.formatDate(null, null)));
			taskDelay.setReason(reason);
			taskDao.delayTask(taskDelay);
			Toast.makeText(getApplicationContext(), "延迟到明天", Toast.LENGTH_SHORT).show();
		}
	};
	
	private Task task;
	
	private TaskDelay taskDelay;
	
	private TaskDao taskDao = new TaskDao();
	
	@CView(id=R.id.day_task_edit_task_done)
	private Button taskDoneBt;
	private AlertDialog.Builder doneBuilder;
	
	@Event(name="taskDoneBt")
	private OnClickListener onTaskDoneBtCL = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			doneBuilder.show();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_task_edit);
		getSaq().registe(this, getWindow().getDecorView()).init();
		
		init();
	}
	
	void init() {
		taskId = getIntent().getStringExtra("taskId");
		taskNameTv.setText(StringUtils.ifNull(getIntent().getStringExtra("taskName"), ""));
		DayTask dayTask = dao.queryDayTask(taskId, null);
		if(dayTask != null) {
			taskDayContentEt.setText(StringUtils.ifNull(dayTask.getContent(), ""));
			taskPlanTv.setText(StringUtils.ifNull(dayTask.getPlan(), null));
		}
		
		// 初始化任务信息
		task = taskDao.queryById(taskId);
		taskStartdayTv.setText(StringUtils.ifNull(DateUtils.formatDate(task.getStartday(), null), ""));
		taskEnddayTv.setText(StringUtils.ifNull(DateUtils.formatDate(task.getEndday(), null), ""));
		taskContentTv.setText(StringUtils.ifNull(task.getContent(), ""));
		taskNeedsTv.setText(StringUtils.ifNull(task.getNeeds(), ""));
		
		// 延迟
		if(task.getEndday() <= Long.valueOf(DateUtils.formatDate(null, null))) {
			delayReasonEt.setVisibility(View.VISIBLE);
			delayOneDayBt.setVisibility(View.VISIBLE);
			taskDelay = taskDao.queryTaskDelay(taskId, DateUtils.formatDate(null, null));
			if(taskDelay != null) {
				delayReasonEt.setText(taskDelay.getReason());
			}
		}
		
		
		doneBuilder = new AlertDialog.Builder(DayTaskEditActivity.this);
		doneBuilder.setTitle("提示").setMessage("已经完成了？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				taskDao.taskDone(task);
				Toast.makeText(DayTaskEditActivity.this, "完成了", Toast.LENGTH_SHORT).show();
				
				finish();
			}
		}).create();
	}
}
