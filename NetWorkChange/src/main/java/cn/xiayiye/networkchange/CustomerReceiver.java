package cn.xiayiye.networkchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String send = intent.getStringExtra("send");//拿到发送广播发过来的数据
        Toast.makeText(context, "发送了自定义广播,内容为:\n" + send, Toast.LENGTH_LONG).show();
//        abortBroadcast();//从此处阻断广播
    }
}
