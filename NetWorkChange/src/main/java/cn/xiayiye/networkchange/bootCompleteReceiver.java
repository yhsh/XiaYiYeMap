package cn.xiayiye.networkchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class bootCompleteReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "开机了！", Toast.LENGTH_LONG).show();
//        throw new UnsupportedOperationException("Not yet implemented");
        if (intent.getAction().equals(ACTION)) {
            //APP的开机自启实现方法
            Intent mainActivityIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
        }
    }
}
