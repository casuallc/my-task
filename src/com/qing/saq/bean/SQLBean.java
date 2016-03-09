package com.qing.saq.bean;

import java.util.ArrayList;

public class SQLBean<T> {
	private Class<T> beanClazz;
	private StringBuilder sql;
	
	private ArrayList<String> args;
	
	public SQLBean(Class<T> clazz) {
		this.beanClazz = clazz;
	}
	
	public Class<T> getBeanClass() {
		return beanClazz;
	}
	
	public ArrayList<String> getArgs() {
		if(args == null) 
			args = new ArrayList<String>();
		return args;
	}
	
	public SQLBean<T> addArgs(Object obj) {
		if(args == null) 
			args = new ArrayList<String>();
		args.add(obj+"");
		return this;
	}
	
	public StringBuilder getSql() {
		if(sql == null)
			sql = new StringBuilder();
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}

}
