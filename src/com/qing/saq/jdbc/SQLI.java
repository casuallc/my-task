package com.qing.saq.jdbc;

import java.util.List;

import com.qing.saq.bean.SQLBean;

public interface SQLI {

	public abstract void open();

	public abstract <T> T query(SQLBean<T> bean);

	public abstract <T> List<T> list(SQLBean<T> bean);

	public abstract <T> void add(SQLBean<T> bean);

	public abstract <T> void update(SQLBean<T> bean);

	public abstract void close();

}