package com.qing.mytask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
			
			save();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_add);
		getSaq().registe(this, getWindow().getDecorView()).init();
		
	}
	
	void save() {
		SQLiteOpenHelper helper = new SQLiteOpenHelper(this, "mytask.db", null, 1) {
			
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				
			}
			
			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL("CREATE TABLE TASK (ID VARCHAR(32), NAME VARCHAR(100), STARTDAY BIGINT, CONTENT TEXT, NEEDS VARCHAR(200), ENDDAY BIGINT, CREATE_TIME BIGINT)");
			}
		};
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DROP TABLE TASK");
		db.execSQL("CREATE TABLE TASK (ID VARCHAR(32), NAME VARCHAR(100), STARTDAY BIGINT, CONTENT TEXT, NEEDS VARCHAR(200), ENDDAY BIGINT, CREATE_TIME BIGINT)");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		db.execSQL("INSERT INTO TASK (ID, NAME, STARTDAY, CONTENT, NEEDS, ENDDAY, CREATE_TIME) VALUES(?, ?, ?, ?, ?, ?, ?)", 
				new Object[]{UUID.randomUUID().toString().replaceAll("-", ""), 
				taskNameEt.getText().toString(), 
				Long.valueOf(taskStartdayEt.getText().toString()),
				taskNeedsEt.getText().toString(),
				Long.valueOf(taskEnddayEt.getText().toString()),
				format.format(new Date())
				});
		
		db.close();
	}
}
