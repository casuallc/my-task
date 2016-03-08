package com.qing.mytask.base;

import android.app.Activity;

import com.qing.saq.SAQ;

public class BaseActivity extends Activity {

	private SAQ saq = new SAQ();
	
	public SAQ getSaq() {
		return saq;
	}
}
