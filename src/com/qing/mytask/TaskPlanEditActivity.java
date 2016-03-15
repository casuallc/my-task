package com.qing.mytask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.qing.saq.jdbc.SQL;
import com.qing.saq.utils.DateUtils;
import com.qing.saq.utils.StringUtils;

public class TaskPlanEditActivity extends BaseActivity {
	@CView(id=R.id.task_plan_edit_task_name)
	private TextView taskNameTv;
	
	@CView(id=R.id.task_plan_edit_plan_after0)
	private EditText todayPlanEt;
	
	@CView(id=R.id.task_plan_edit_plan_after1)
	private EditText planAfter1Et;
	
	@CView(id=R.id.task_plan_edit_plan_after2)
	private EditText planAfter2Et;
	
	@CView(id=R.id.task_plan_edit_plan_after3)
	private EditText planAfter3Et;
	
	@CView(id=R.id.task_plan_edit_plan_after4)
	private EditText planAfter4Et;
	
	@CView(id=R.id.task_plan_edit_plan_after5)
	private EditText planAfter5Et;
	
	@CView(id=R.id.task_plan_edit_plan_after6)
	private EditText planAfter6Et;
	
	private EditText planArray[] = new EditText[7];
	
	private String taskId;
	
	private DayTaskDao dao;
	
	private List<DayTask> dayTaskList = new ArrayList<DayTask>(7);
	
	@CView(id=R.id.task_plan_edit_save)
	private Button savePlanBt;
	
	@Event(name="savePlanBt")
	private OnClickListener onSavePlanBtCL = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			for(int i=0; i<planArray.length; i++) {
				dayTaskList.get(i).setPlan(planArray[i].getText().toString());
				dao.addOrUpdatePlan(dayTaskList.get(i));
			}
			Toast.makeText(getApplicationContext(), "已保存", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_plan_edit);
		SQL.context = this;
		getSaq().registe(this, getWindow().getDecorView()).init();
		
		init();
	}
	
	private void init() {
		taskId = getIntent().getStringExtra("taskId");
		taskNameTv.setText(StringUtils.ifNull(getIntent().getStringExtra("taskName"), ""));
		dao = new DayTaskDao();
		List<DayTask> list = dao.listPlan(taskId, DateUtils.formatDate(new Date(), null));
		for(int i=0; i<7; i++) {
			dayTaskList.add(getTask(list, Long.valueOf(DateUtils.afterDate(new Date(), i, null))));
		}
		
		planArray[0] = todayPlanEt;
		planArray[1] = planAfter1Et;
		planArray[2] = planAfter2Et;
		planArray[3] = planAfter3Et;
		planArray[4] = planAfter4Et;
		planArray[5] = planAfter5Et;
		planArray[6] = planAfter6Et;
		
		for(int i=0; i<planArray.length; i++) {
			planArray[i].setText(StringUtils.ifNull(dayTaskList.get(i).getPlan(), ""));
		}
	}
	
	DayTask getTask(List<DayTask> list, long day) {
		DayTask task = null;
		for(DayTask d : list) {
			if(d.getDay() == day) {
				task = d;
				break;
			}
		}
		// 新增
		if(task == null) {
			task = new DayTask();
			task.setTaskId(taskId);
			task.setDay(day);
		}
		return task;
	}
}
