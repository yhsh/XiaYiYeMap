/*
 * @Title DemoMainActivity.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Huagx
 * @date 2015-10-15 ����3:52:24
 * @version 1.0
 */
package cn.xiayiye.xiayiyemap.sdkdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cld.mapapi.VersionInfo;
import com.cld.mapapi.map.CldMap.NaviInitListener;
import com.cld.navisdk.CldNaviManager;
import com.cld.sdk.demo.R;

/**
 * 类注释
 *
 * @author Huagx
 * @date 2015-10-15 下午3:52:24
 */
public class DemoMainActivity extends ListActivity {

	// 隐式意图过滤的类型
	private static final String CATEGORY_SDK_DEMO = "android.intent.category.CARELANDSDK_DEMO";

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化Demo视图
		initViews();
		// 初始化sdk
		CldNaviManager.getInstance().init(this, mInitListener);

		setTitle(getTitle() + "_" + getVersion());
	}

	private String getVersion() {
		try {
			if(VersionInfo.isTimeLimit()){
				return VersionInfo.getApiVersion() + "_限时";
			}else{
				return VersionInfo.getApiVersion();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 初始化视图
	private void initViews() {
		// 设置listView的适配器
		setListAdapter(new SimpleAdapter(this, getListData(),
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 }));
		// listview获得当前焦点的时候，相应用户输入的匹配符，筛选出匹配的Items
		getListView().setTextFilterEnabled(true);
	}

	/**
	 * 通过隐式意图筛选出匹配的所有对象，并获得对象信息的集合
	 *
	 * @return ArrayList<Map<String,Object>>
	 * @author Wangxy
	 * @date 2016-3-21 上午11:51:00
	 */
	private ArrayList<Map<String, Object>> getListData() {
		ArrayList<Map<String, Object>> maps = new ArrayList<Map<String, Object>>(
				0);
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(CATEGORY_SDK_DEMO);
		PackageManager pm = getPackageManager();
		// 传入隐式意图进行筛选
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
		int size = resolveInfos.size();
		for (int i = 0; i < size; i++) {
			ResolveInfo info = resolveInfos.get(i);
			// 创建一个Map集合存储title和intent
			Map<String, Object> item = new HashMap<String, Object>(2);
			CharSequence labelSeq = info.loadLabel(pm);
			// 得到对应Item的标题title
			item.put("title", labelSeq != null ? labelSeq.toString()
					: info.activityInfo.name);
			// 通过包名,类名得到相应的Intent对象
			item.put(
					"intent",
					activityIntent(
							info.activityInfo.applicationInfo.packageName,
							info.activityInfo.name));
			maps.add(item);
		}
		return maps;
	}

	/**
	 * 通过包名,类名来得到一个Intent对象
	 *
	 * @return Intent
	 * @author Wangxy
	 * @date 2016-3-21 上午11:51:13
	 */
	private Intent activityIntent(String pkg, String componentName) {
		Intent result = new Intent();
		// 设置要跳转的类名
		result.setClassName(pkg, componentName);
		return result;
	}

	@SuppressLint("ShowToast")
	@SuppressWarnings("unchecked")
	@Override
	// 子item的点击事件,点击后跳转到相应activity
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// 获得listView相应item对象
		Map<String, Object> map = (Map<String, Object>) l
				.getItemAtPosition(position);
		// 得到相应的Intent对象
		Intent intent = (Intent) map.get("intent");
		// 跳转到Intent指向的界面
		startActivity(intent);
	}

	// 监听key是否校验成功,并给出提示.
	private NaviInitListener mInitListener = new NaviInitListener() {

		@Override
		public void initFailed(String msg) {// 初始化失败方法

		}

		@Override
		public void onAuthResult(int status, String msg) {// 初始化结果
			String str;
			if (0 == status) {// 初始化结果状态判断
				str = "key校验成功!";
			} else {
				str = "key校验失败!";
			}
			// 初始化结果返回的信息提示
			Toast.makeText(DemoMainActivity.this, str, Toast.LENGTH_LONG)
					.show();
		}

		@Override
		public void initStart() {// 初始化开始方法

		}

		@Override
		public void initSuccess() {// 初始化成功方法

		}
	};

	@Override
	// 点击返回键的事件处理
	public void onBackPressed() {
		// 退出程序
		System.exit(0);
		// 杀死进程
		android.os.Process.killProcess(android.os.Process.myUid());
		// sdk反初始化
		CldNaviManager.getInstance().unInit();
	}
}
