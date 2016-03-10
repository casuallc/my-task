package com.qing.mytask.model;

import com.qing.mytask.base.Model;

public class Task extends Model {

	private String id;
	private String name;
	private long startday;
	private long endday;
	private String content;
	private int status;
	private String needs;
	private long dayscost;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStartday() {
		return startday;
	}

	public void setStartday(long startday) {
		this.startday = startday;
	}

	public long getEndday() {
		return endday;
	}

	public void setEndday(long endday) {
		this.endday = endday;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNeeds() {
		return needs;
	}

	public void setNeeds(String needs) {
		this.needs = needs;
	}

	public long getDayscost() {
		return dayscost;
	}

	public void setDayscost(long dayscost) {
		this.dayscost = dayscost;
	}

}
