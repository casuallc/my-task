package com.qing.mytask.dao;

import java.util.List;

import com.qing.mytask.model.Task;

public interface TaskDaoI {

	public abstract Task query(Task task);

	public abstract List<Task> list(Task task);

	public abstract Task add(Task task);

	public abstract Task update(Task task);

}