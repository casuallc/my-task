package com.qing.mytask.dao;

import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.qing.mytask.model.Task;
import com.qing.saq.bean.SQLBean;
import com.qing.saq.jdbc.SQLI;
import com.qing.saq.jdbc.SQLProxy;

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
		bean.getSql().append("SELECT ID, NAME, STARTDAY, CONTENT, NEEDS, ENDDAY, CREATE_TIME FROM TASK");
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
		bean.getSql().append("UPDATE TASK SET NAME=?, CONTENT=?, NEEDS=?, ENDDAY=?, STATUS) WHERE ID = ?");
		bean.addArgs(task.getName()).addArgs(task.getContent());
		bean.addArgs(task.getNeeds()).addArgs(1).addArgs(task.getId());
		sql.update(bean);
		return task;
	}

}
