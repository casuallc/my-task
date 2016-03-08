package com.qing.mytask.add;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.qing.mytask.R;
import com.qing.mytask.base.BaseActivity;
import com.qing.saq.SAQ;
import com.qing.saq.anno.CView;
import com.qing.saq.anno.Event;

public class AddActivity extends BaseActivity {
	@CView(id=R.id.task_add_quick)
	private Button taskQuickBt;
	@CView(id=R.id.task_add_complete)
	private Button taskQCompleteBt;
	
	private SAQ saq = new SAQ();
	
	@Event(name="taskQuickBt")
	private OnClickListener onTaskQuickBtClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(AddActivity.this, "hello", Toast.LENGTH_SHORT).show();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_add);
		
		saq.registe(this, getWindow().getDecorView());
		
		saq.init();
	}
	
	
}
