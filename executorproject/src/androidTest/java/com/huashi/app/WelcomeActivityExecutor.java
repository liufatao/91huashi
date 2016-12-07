package com.huashi.app;

import android.app.Activity;
import com.robotium.recorder.executor.Executor;

@SuppressWarnings("rawtypes")
public class WelcomeActivityExecutor extends Executor {

	@SuppressWarnings("unchecked")
	public WelcomeActivityExecutor() throws Exception {
		super((Class<? extends Activity>) Class.forName("com.huashi.app.activity.WelcomeActivity"),  "com.huashi.app.R.id.", new android.R.id(), false, false, "1478598618641");
	}

	public void setUp() throws Exception { 
		super.setUp();
	}
}
