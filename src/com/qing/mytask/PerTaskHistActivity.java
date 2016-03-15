package com.qing.mytask;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.qing.mytask.base.BaseActivity;
import com.qing.mytask.dao.DayTaskDao;
import com.qing.mytask.model.DayTask;
import com.qing.saq.anno.CView;
import com.qing.saq.utils.StringUtils;
import com.qing.mytask.adapter.DayTaskAdapter;

public class PerTaskHistActivity extends BaseActivity {
	
	@CView(id=R.id.per_task_hist_planlist)
	private ListView daytaskListLv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.per_task_hist);
		getSaq().registe(this, getWindow().getDecorView()).init();
		
		initData();
	}
	
	private DayTaskDao dao = new DayTaskDao();
	
	private List<DayTask> daytaskList;
	
	@CView(id=R.id.per_task_hist_task_name)
	private TextView taskNameTv;
	
	void initData() {
		String taskId = getIntent().getStringExtra("taskId");
		taskNameTv.setText(StringUtils.ifNull(getIntent().getStringExtra("taskName"), ""));
		daytaskList = dao.listDayTaskList(taskId);
		daytaskListLv.setAdapter(new DayTaskAdapter(daytaskList, this));
	}
	
}
