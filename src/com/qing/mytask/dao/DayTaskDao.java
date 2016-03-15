package com.qing.mytask.dao;

import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.qing.mytask.model.DayTask;
import com.qing.saq.bean.SQLBean;
import com.qing.saq.jdbc.SQLI;
import com.qing.saq.jdbc.SQLProxy;
import com.qing.saq.utils.StringUtils;

public class DayTaskDao {
	private SQLI sql;
	
	public DayTaskDao() {
		sql = (SQLI)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{SQLI.class}, new SQLProxy());
	}
	
	/**
	 * 查询每个task某一天的任务，day默认为当天
	 * @param task
	 * @return
	 */
	public DayTask query(DayTask task) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("SELECT ID, CONTENT, TASK_ID, DAY FROM DAY_TASK");
		bean.getSql().append(" WHERE TASK_ID = ? AND DAY = ?");
		bean.addArgs(task.getTaskId()).addArgs(task.getDay() == 0 ? format.format(new Date()) : task.getDay());;
		return sql.query(bean);
	}
	
	/**
	 * 添加dayTask，day默认为当天
	 * @param task
	 * @return
	 */
	public DayTask add(DayTask task) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("INSERT INTO DAY_TASK(ID, TASK_ID, CONTENT, DAY) VALUES (?, ?, ?, ?)");
		bean.addArgs(task.getId()).addArgs(task.getTaskId()).addArgs(task.getContent()).addArgs(task.getDay() == 0 ? format.format(new Date()) : task.getDay());
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
	
	/**
	 * 查询当天的日计划情况
	 * @param taskId
	 * @param day
	 * @return
	 */
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
	
	/**
	 * 查询每个task 每天完成的情况
	 * @param taskId
	 * @param type
	 * @return
	 */
	public List<DayTask> listDayTaskList(String taskId) {
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("SELECT ID, CONTENT, TASK_ID TASKID, DAY, PLAN FROM DAY_TASK");
		bean.getSql().append(" WHERE TASK_ID = ? ORDER BY DAY");
		bean.addArgs(taskId);
		return sql.list(bean);
	}
	
	/**
	 * 查询startDay之后的所有计划
	 * @param taskId
	 * @param startDay
	 * @return
	 */
	public List<DayTask> listPlan(String taskId, String startDay) {
		SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
		bean.getSql().append("SELECT ID, CONTENT, TASK_ID TASKID, DAY, PLAN FROM DAY_TASK");
		bean.getSql().append(" WHERE TASK_ID = ? AND DAY >= ?");
		bean.addArgs(taskId).addArgs(startDay);
		return sql.list(bean);
	}
	
	public DayTask addOrUpdatePlan(DayTask task) {
		if(StringUtils.isEmpty(task.getId())) {
			task.setId(StringUtils.getUUID());
			SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
			bean.getSql().append("INSERT INTO DAY_TASK(ID, TASK_ID, PLAN, DAY) VALUES (?, ?, ?, ?)");
			bean.addArgs(task.getId()).addArgs(task.getTaskId()).addArgs(task.getPlan()).addArgs(task.getDay());
			sql.add(bean);
		} else {
			SQLBean<DayTask> bean = new SQLBean<DayTask>(DayTask.class);
			bean.getSql().append("UPDATE DAY_TASK SET PLAN=? WHERE ID = ?");
			bean.addArgs(task.getPlan()).addArgs(task.getId());
			sql.update(bean);
		}
		return task;
	}
}
