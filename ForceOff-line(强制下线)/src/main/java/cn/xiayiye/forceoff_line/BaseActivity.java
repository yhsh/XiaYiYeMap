package cn.xiayiye.forceoff_line;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/2/28.11:10
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.forceoff_line
 */
public class BaseActivity extends Activity {

    private Off_LineReceiver off_lineReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String save = savedInstanceState.getString("save");
            Log.e("打印保存后取出的信息", save);
        }
        //添加所有活动页面
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("save", "要保存的信息");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);//移除所有活动页面
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("com.yhsh.off-line");
        off_lineReceiver = new Off_LineReceiver();
        registerReceiver(off_lineReceiver, intentFilter);//注册广播
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (off_lineReceiver != null) {
            unregisterReceiver(off_lineReceiver);//解绑广播
            off_lineReceiver = null;
        }
    }

    class Off_LineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.e("打印动作：", intent.getAction());
            if ("com.yhsh.off-line".equals(intent.getAction())) {
                //弹框强制下线
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setCancelable(false);//点击对话框以外不取消对话框
                alert.setTitle("警告").setMessage("你已被强制下线").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //关闭所有的activity，直接进入登录页面
                        ActivityCollector.finishAll();
                        startActivity(new Intent(context, LoginActivity.class));
                        //通过下面的一个属性会让页面跳转之间无任何动画效果
//                        overridePendingTransition(0, 0);
                    }
                }).show();//显示对话框
            }
        }
    }
}
