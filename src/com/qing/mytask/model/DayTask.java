package com.qing.mytask.model;

import com.qing.mytask.base.Model;

public class DayTask extends Model {

	private String id;
	private String taskId;
	private String content;
	
	private long day;
	
	public void setDay(long day) {
		this.day = day;
	}
	
	public long getDay() {
		return day;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
