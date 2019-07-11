package cn.xiayiye.networkchange;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends Activity implements View.OnClickListener {

    private NetworkChange networkChange;
    private TextView change;
    private Button set_network;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        change = (TextView) findViewById(R.id.change);
        Button send_broadcast = (Button) findViewById(R.id.send_broadcast);
        set_network = (Button) findViewById(R.id.set_network);
        message = (EditText) findViewById(R.id.message);
        change.setFocusable(true);
        change.setClickable(true);
        listenerNetWorkChange();
        send_broadcast.setOnClickListener(this);
        change.setOnClickListener(this);
        set_network.setOnClickListener(MainActivity.this);
    }

    private void listenerNetWorkChange() {
/*        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//一样的效果
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);//一样的效果和下面*/
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");//网络状态改变的广播
        networkChange = new NetworkChange();
        registerReceiver(networkChange, intentFilter);
    }


    class NetworkChange extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();//获取网络状态
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                Toast.makeText(MainActivity.this, "已连网！", Toast.LENGTH_LONG).show();
                change.setText("网络已连接！");
                set_network.setVisibility(View.GONE);
            } else {
                Toast.makeText(MainActivity.this, "已断网！", Toast.LENGTH_LONG).show();
                set_network.setVisibility(View.VISIBLE);
                change.setText("网络已断开！");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change:
                setMobileDataEnabled(true);
                Toast.makeText(MainActivity.this, "点击了！", Toast.LENGTH_LONG).show();
                break;
            case R.id.send_broadcast:
                String trim = message.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(MainActivity.this, "请输入内容，在发送广播！", Toast.LENGTH_LONG).show();
                } else {
                    //发送自定义广播
//                    Intent intent = new Intent();
                    Intent intent = new Intent("com.yhsh.xiayiye");
//                    intent.setAction("com.yhsh.xiayiye");//一样的效果，这里是setAction,在intentFilter里面是addAction
                    intent.putExtra("send", "扬宏豕慧接收到的广播：" + trim);
                    sendBroadcast(intent);
                    sendOrderedBroadcast(intent, null);
                }
                break;
            case R.id.set_network:
                //跳转到网络设置页面
                Intent intent_net = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                startActivity(intent_net);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChange);
    }

    public boolean setMobileDataEnabled(boolean enabled) {
        final TelephonyManager mTelManager;
        mTelManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method m = mTelManager.getClass().getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            Object telephony = m.invoke(mTelManager);
            m = telephony.getClass().getMethod((enabled ? "enable" : "disable") + "DataConnectivity");
            m.invoke(telephony);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
