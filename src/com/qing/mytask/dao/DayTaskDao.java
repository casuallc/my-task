package com.qing.mytask.dao;

import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.qing.mytask.model.DayTask;
import com.qing.saq.bean.SQLBean;
import com.qing.saq.jdbc.SQLI;
import com.qing.saq.jdbc.SQLProxy;

public class DayTaskDao {
	private SQLI sql;
	
	public DayTaskDao() {
		sql = (SQLI)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{SQLI.class}, new SQLProxy());
	}
	
	public DayTask query(DayTask task) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("SELECT ID, CONTENT, TASK_ID, DAY FROM DAY_TASK");
		bean.getSql().append(" WHERE TASK_ID = ? AND DAY = ?");
		bean.addArgs(task.getTaskId()).addArgs(format.format(new Date()));;
		return sql.query(bean);
	}
	
	public DayTask add(DayTask task) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("INSERT INTO DAY_TASK(ID, TASK_ID, CONTENT, DAY) VALUES (?, ?, ?, ?)");
		bean.addArgs(task.getId()).addArgs(task.getTaskId()).addArgs(task.getContent()).addArgs(format.format(new Date()));
		sql.add(bean);
		return task;
	}
	
	public DayTask update(DayTask task) {
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("UPDATE DAY_TASK SET CONTENT=? WHERE ID = ?");
		bean.addArgs(task.getContent()).addArgs(task.getId());
		sql.update(bean);
		return task;
	}
	
	public DayTask queryDayTask(String taskId, String day) {
		if(day == null) {
			day = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(new Date());
		}
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("SELECT ID, CONTENT, TASK_ID TASKID, DAY FROM DAY_TASK");
		bean.getSql().append(" WHERE TASK_ID = ? AND DAY = ?");
		bean.addArgs(taskId).addArgs(day);
		return sql.query(bean);
	}
	
}
