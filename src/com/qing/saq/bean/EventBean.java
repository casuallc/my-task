package com.qing.saq.bean;

import java.lang.reflect.Field;

import com.qing.saq.anno.Event;

public class EventBean {

	private String name;
	private Event event;
	
	private Field field;
	
	public void setField(Field field) {
		this.field = field;
	}
	
	public Field getField() {
		field.setAccessible(true);
		return field;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
