package com.qing.saq.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SQLProxy implements InvocationHandler {
	
	private SQL sql = new SQL();

	private void before() {
		if(!sql.isOpen())
			sql.open();
	}
	
	private void after() {
		sql.close();
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		method.invoke(sql, args);
		after();
		return null;
	}

}
