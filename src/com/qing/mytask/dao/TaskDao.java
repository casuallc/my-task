package com.qing.mytask.dao;

import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.qing.mytask.model.Task;
import com.qing.mytask.model.TaskDelay;
import com.qing.saq.bean.SQLBean;
import com.qing.saq.jdbc.SQLI;
import com.qing.saq.jdbc.SQLProxy;
import com.qing.saq.utils.DateUtils;
import com.qing.saq.utils.StringUtils;

public class TaskDao implements TaskDaoI {
	private SQLI sql;
	
	public TaskDao() {
		sql = (SQLI)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{SQLI.class}, new SQLProxy());
	}

	@Override
	public Task query(Task task) {
		SQLBean<Task> bean = new SQLBean<Task>(Task.class);
		bean.getSql().append("SELECT ID, NAME, STARTDAY, CONTENT, NEEDS, ENDDAY, CREATE_TIME FROM TASK");
		bean.getSql().append(" WHERE ID = ?");
		bean.addArgs(task.getId());
		return sql.query(bean);
	}
	
	@Override
	public Task queryById(String id) {
		Task task = new Task();
		task.setId(id);
		return query(task);
	}
	
	@Override
	public List<Task> list(Task task) {
		SQLBean<Task> bean = new SQLBean<Task>(Task.class);
		bean.getSql().append("SELECT ID, NAME, STARTDAY, CONTENT, NEEDS, ENDDAY, CREATE_TIME FROM TASK WHERE STATUS != '10'");
		return sql.list(bean);
	}
	
	@Override
	public Task add(Task task) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
		SQLBean<Task> bean = new SQLBean<Task>(Task.class);
		bean.getSql().append("INSERT INTO TASK(ID, NAME, STARTDAY, CONTENT, NEEDS, ENDDAY, CREATE_TIME, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		bean.addArgs(task.getId()).addArgs(task.getName()).addArgs(task.getStartday()).addArgs(task.getContent());
		bean.addArgs(task.getNeeds()).addArgs(task.getEndday()).addArgs(format.format(new Date())).addArgs(0);
		sql.add(bean);
		return task;
	}
	
	@Override
	public Task update(Task task) {
		SQLBean<Task> bean = new SQLBean<Task>(Task.class);
		bean.getSql().append("UPDATE TASK SET NAME=?, CONTENT=?, NEEDS=?, ENDDAY=? WHERE ID = ?");
		bean.addArgs(task.getName()).addArgs(task.getContent());
		bean.addArgs(task.getNeeds()).addArgs(1).addArgs(task.getId());
		sql.update(bean);
		return task;
	}
	
	/**
	 * 延迟任务
	 * @param task
	 */
	public void delayTask(TaskDelay task) {
		SQLBean<TaskDelay> bean = new SQLBean<TaskDelay>(TaskDelay.class);
		if(StringUtils.isEmpty(task.getId())) {
			task.setId(StringUtils.getUUID());
			task.setDay(Long.valueOf(DateUtils.formatDate(new Date(), null)));
			bean.getSql().append("INSERT INTO TASK_DELAY (ID, TASK_ID, REASON, DAY) VALUES(?, ?, ?, ?)");
			bean.addArgs(task.getId()).addArgs(task.getTaskId()).addArgs(task.getReason()).addArgs(task.getDay());
			sql.update(bean);
		} else {
			bean.getSql().append("UPDATE TASK_DELAY SET REASON = ? WHERE ID = ?");
			bean.addArgs(task.getReason()).addArgs(task.getId());
			sql.update(bean);
		}
	}
	
	/**
	 * 查询某天任务延迟的原因
	 * @param taskId
	 * @param day
	 * @return
	 */
	public TaskDelay queryTaskDelay(String taskId, String day) {
		SQLBean<TaskDelay> bean = new SQLBean<TaskDelay>(TaskDelay.class);
		bean.getSql().append("SELECT ID, TASK_ID, REASON, DAY FROM TASK_DELAY WHERE TASK_ID = ? AND DAY = ?");
		bean.addArgs(taskId).addArgs(day);
		return sql.query(bean);
	}
	
	/**
	 * 任务完成
	 * @param task
	 */
	public void taskDone(Task task) {
		SQLBean<Task> bean = new SQLBean<Task>(Task.class);
		bean.getSql().append("UPDATE TASK SET STATUS=?, DONEDAY=? WHERE ID = ?");
		bean.addArgs(10).addArgs(task.getDoneday()).addArgs(task.getId());
		sql.update(bean);
	}

	/**
	 * @return tasks that have done
	 */
	public List<Task> listHistTask() {
		SQLBean<Task> bean = new SQLBean<Task>(Task.class);
		bean.getSql().append("SELECT ID, NAME, STARTDAY, CONTENT, NEEDS, ENDDAY, CREATE_TIME, DONEDAY FROM TASK WHERE STATUS = '10'");
		return sql.list(bean);
	}
}
