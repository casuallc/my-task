package com.qing.saq.bean;

import java.lang.reflect.Field;

import com.qing.saq.anno.CView;

/**
 * view 组件
 * @author liuchangqing
 */
public class CViewBean {

	private String name;
	private CView cView;
	
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

	public CView getCView() {
		return cView;
	}

	public void setCView(CView cView) {
		this.cView = cView;
	}
}
