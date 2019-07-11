/*
 * @Title LocationDemo.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author Huagx
 * @date 2016-1-28 ����8:46:40
 * @version 1.0
 */
package cn.xiayiye.xiayiyemap.sdkdemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cld.location.CldLocation;
import com.cld.location.CldLocationClient;
import com.cld.location.CldLocationOption;
import com.cld.location.ICldLocationListener;
import com.cld.location.LocationMode;
import com.cld.mapapi.map.MapView;
import com.cld.mapapi.model.LatLng;
import com.cld.sdk.demo.R;

/**
 * 定位模块演示界面
 *
 * @author Huagx
 * @date 2016-1-28 上午8:46:40
 */
public class LocationDemo extends Activity implements OnClickListener {

	// 仅GPS
	private Button mBtn_Gps;
	// 网络定位
	private Button mBtn_Net;
	// 混合定位
	private Button mBtn_Gps_Net;
	// 清除位置
	private Button mBtn_Clear;
	// 显示结果
	private TextView mTv_Result;
	// 地图
	private MapView mMapView;
	// 定位终端
	private CldLocationClient locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locationdemo);
		init();
	}

	// 初始化
	private void init() {
		// 找到相应控件
		mBtn_Gps = (Button) findViewById(R.id.btn_gps);
		mBtn_Gps_Net = (Button) findViewById(R.id.btn_gps_net);
		mBtn_Net = (Button) findViewById(R.id.btn_net);
		mBtn_Clear = (Button) findViewById(R.id.btn_clear);
		mTv_Result = (TextView) findViewById(R.id.result);
		mTv_Result.setTextColor(Color.RED);
		mMapView = (MapView) findViewById(R.id.mapView);

		// 设置点击事件
		mBtn_Gps.setOnClickListener(this);
		mBtn_Gps_Net.setOnClickListener(this);
		mBtn_Net.setOnClickListener(this);
		mBtn_Clear.setOnClickListener(this);

		// 开启gps混合定位
		location(LocationMode.MIXED, 0);
	}

	// 点击不同的按钮,使用对应的方法实现定位
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_gps:// 仅GPS
				location(LocationMode.GPS, 5000);
				break;
			case R.id.btn_gps_net:// 混合定位
				location(LocationMode.MIXED, 5000);
				break;
			case R.id.btn_net:// 仅网络
				location(LocationMode.NETWORK, 5000);
				break;
			case R.id.btn_clear:// 清除位置
				mTv_Result.setText("");
				break;
		}
	}

	/**
	 * 定位
	 *
	 * @param locationMode
	 *            定位类型 参考类LocationMode
	 * @param spanMs
	 *            定位频率 单位毫秒
	 * @return void
	 * @author Huagx
	 * @date 2016-1-28 上午9:11:40
	 */
	private void location(int locationMode, int spanMs) {
		if (null == locationManager) {
			locationManager = new CldLocationClient(this);
		}
		// 如果已开启定位，先停掉
		if (locationManager.isStarted()) {
			locationManager.stop();
		}
		// 设置定位选项
		CldLocationOption option = new CldLocationOption();
		option.setLocationMode(locationMode);// 设置定位模式
		option.setNetworkScanSpan(spanMs);// 定位扫描时间
		locationManager.setLocOption(option);
		locationManager.registerLocationListener(new ICldLocationListener() {

			@Override
			public void onReceiveLocation(CldLocation location) {
				if (null != location) {
					double altitude = location.getAltitude();
					double latitude = location.getLatitude();
					double longitude = location.getLongitude();
					float accuracy = location.getAccuracy();
					float bearing = location.getBearing();
					float speed = location.getSpeed();
					long time = location.getTime();
					String addr = location.getAddress();
					String adCode = location.getAdCode();
					String dist = location.getDistrict();
					String city = location.getCity();
					String cityCode = location.getCityCode();
					String province = location.getProvince();
					String locInfo = "lat:" + latitude + ",lon:" + longitude
							+ "alt:" + altitude + ",acc:" + accuracy + ",bear:"
							+ bearing + ",spd:" + speed + ",time:" + time
							+ ",provice:" + province + "city:" + city
							+ ",code:" + cityCode + ",dist:" + dist + ",addr:"
							+ addr + ",adcode:" + adCode;
					Log.i("location", locInfo);
					mMapView.getMap().setNMapCenter(
							new LatLng(location.getLatitude(), location
									.getLongitude()));
					// 展示结果
					mTv_Result.setText("定位成功:("
							+ longitude
							+ ","
							+ latitude
							+ ")\n"
							+ "精        度:"
							+ accuracy
							+ "\n"
							+ "省            :"
							+ province
							+ "\n"
							+ "市            :"
							+ city
							+ "\n"
							+ "城市编码:"
							+ cityCode
							+ "\n"
							+ "区            :"
							+ dist
							+ "\n"
							+ "地址        :"
							+ addr
							+ "\n"
							+ "地址编码:"
							+ adCode
							+ "\n"
							+ "时间        :"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date(time)) + "\n");
				}
			}
		});
		locationManager.start();
	}

	/**
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// 关闭定位
		if (locationManager != null) {
			locationManager.stop();
		}
	}

	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}
	/**
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}
}
