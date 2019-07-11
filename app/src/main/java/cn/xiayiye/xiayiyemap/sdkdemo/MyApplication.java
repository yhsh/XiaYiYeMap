/*
 * @Title MyApplication.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Huagx
 * @date 2015-12-18 ����12:21:01
 * @version 1.0
 */
package cn.xiayiye.xiayiyemap.sdkdemo;

import android.app.Application;

import com.cld.base.CldBase;
import com.cld.base.CldBaseParam;
import com.cld.log.CldCrashHandler;
import com.cld.mapapi.frame.CldSdkCxt;

/**
 * @author Huagx
 * @date 2015-12-18 下午12:21:01
 */
public class MyApplication extends Application {

	/**
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		// 设置程序Context
		CldSdkCxt.setAppContext(this.getApplicationContext());
		CldSdkCxt.setApplication(this);

		// 初始化基础库
		CldBaseParam param = new CldBaseParam();
		param.ctx = this.getApplicationContext();
		CldBase.init(param);

		// 开启异常捕获机制
		CldCrashHandler crashHandler = CldCrashHandler.getInstance();
		crashHandler.init(this, CldSdkCxt.getAppLogFilePath() + "/logtrace.cr");
		Thread.setDefaultUncaughtExceptionHandler(crashHandler);
		crashHandler.start();

	}
}

