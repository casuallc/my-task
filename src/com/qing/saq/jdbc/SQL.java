package com.qing.saq.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qing.saq.bean.SQLBean;

public class SQL implements SQLI {
	public static Context context = null;
	private SQLiteDatabase db;
	
	private List<String> createSql = new ArrayList<String>();
	private List<String> upgradeSql = new ArrayList<String>();
	
	public boolean isOpen() {
		return db == null ? false : db.isOpen();
	}
	
	@Override
	public void open() {
		createSql.add("CREATE TABLE TASK (ID VARCHAR(32), NAME VARCHAR(100), STARTDAY BIGINT, CONTENT TEXT, NEEDS VARCHAR(200), ENDDAY BIGINT, CREATE_TIME BIGINT, STATUS INT)");
		
		upgradeSql.add("DROP TABLE IF EXISTS TASK");
		upgradeSql.add("CREATE TABLE TASK (ID VARCHAR(32), NAME VARCHAR(100), STARTDAY BIGINT, CONTENT TEXT, NEEDS VARCHAR(200), ENDDAY BIGINT, CREATE_TIME BIGINT, STATUS INT)");
		SQLiteOpenHelper helper = new SQLiteOpenHelper(context, "mytask.db", null, 2) {
			
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				for(String sql : upgradeSql) {
					db.execSQL(sql);
				}
			}
			
			@Override
			public void onCreate(SQLiteDatabase db) {
				for(String sql : createSql) {
					db.execSQL(sql);
				}
			}
		};
		db = helper.getWritableDatabase();
	}

	@Override
	public <T>T query(SQLBean<T> bean) {
		System.out.println(bean.getSql().toString());
		Cursor c = db.rawQuery(bean.getSql().toString(), bean.getArgs().toArray(new String[0]));
		List<T> list = toBean(c, bean.getBeanClass());
		c.close();
		if(list.size() > 0)
			return list.get(0);
		return null;
	}
	
	@Override
	public <T> List<T> list(SQLBean<T> bean) {
		System.out.println(bean.getSql().toString());
		Cursor c = db.rawQuery(bean.getSql().toString(), bean.getArgs().toArray(new String[0]));
		List<T> list = toBean(c, bean.getBeanClass());
		c.close();
		return list;
	}
	
	@Override
	public <T> void add(SQLBean<T> bean) {
		System.out.println(bean.getSql().toString());
		db.execSQL(bean.getSql().toString(), bean.getArgs().toArray(new String[0]));
	}
	
	@Override
	public <T> void update(SQLBean<T> bean) {
		System.out.println(bean.getSql().toString());
		db.execSQL(bean.getSql().toString(), bean.getArgs().toArray(new String[0]));
	}
	
	@Override
	public void close() {
		if(db != null)
			db.close();
	}
	
	/**
	 * cursor中的结果转化成指定的bean对象
	 * @param c
	 * @param clazz
	 * @return
	 */
	<T>List<T> toBean(Cursor c, Class<T> clazz) {
		List<T> result = new ArrayList<T>();
		try {
			Field fields[] = clazz.getDeclaredFields();
			Method setMethods[] = new Method[fields.length];
			for(int i=0; i<fields.length; i++) {
				Field f = fields[i];
				setMethods[i] = clazz.getDeclaredMethod("set"+f.getName().substring(0,1).toUpperCase(Locale.ENGLISH)+f.getName().substring(1), f.getType());
			}
			
			while(c.moveToNext()) {
				T obj = clazz.newInstance();
				for(int i=0; i<fields.length; i++) {
					Field f = fields[i];
					int index = c.getColumnIndex(f.getName().toUpperCase(Locale.ENGLISH));
					if(index != -1) {
						Object value = null;
						Class type = f.getType();
						if(type == String.class) {
							value = c.getString(index);
						} else if(type == long.class) {
							value = c.getLong(index);
						} else if(type == int.class) {
							value = c.getInt(index);
						}
						setMethods[i].invoke(obj, value);
					}
				}
				result.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
