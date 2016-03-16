package com.qing.mytask.model;

public class TaskDelay {

	private String id;

	private String taskId;
	private String reason;

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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
