package cn.xiayiye.gotsmscode;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author 轻飞扬
 *         2018年5月31日11:26:28
 */
public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.requestPermission(this, PermissionUtils.CODE_READ_PHONE_STATE, mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    initSMS();
                    break;
                default:
                    break;
            }
        }
    };

    private void initSMS() {
        SmsContentObserver smsContentObserver = new SmsContentObserver(mHandler);
        Uri smsUri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(smsUri, true, smsContentObserver);
        Cursor cursor = MainActivity.this.managedQuery(Uri.parse("content://sms/inbox"),
                new String[]{"_id", "address", "read", "body"},
                "address=? and read=?", new String[]{"1069004885881", "0"}, "_id desc");//按id排序
        if (cursor != null && cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("read", "1");//修改短信为已读模式
            cursor.moveToNext();
            int smsbodyColumn = cursor.getColumnIndex("body");
            String smsBody = cursor.getString(smsbodyColumn);
            Log.e("打印验证码：", smsBody + "====");
        }
    }

    public String getVerificationCode(String str) {
        Pattern pattern = compile("(\\d{6})");
        Matcher matcher = pattern.matcher(str);
        String verificationCode;
        if (matcher.find()) {
            verificationCode = matcher.group(0);
            return verificationCode;
        }
        return "";
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    private class SmsContentObserver extends ContentObserver {
        private Cursor cursor;

        SmsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //读取收件箱中指定号码的短信
            try {
                cursor = managedQuery(Uri.parse("content://sms/inbox"),
                        new String[]{"_id", "address", "read", "body"},
                        "address=? and read=?", new String[]{"10690XXXXX", "0"}, "_id desc");//按id排序
                if (cursor != null && cursor.getCount() > 0) {
                    ContentValues values = new ContentValues();
                    values.put("read", "1");//修改短信为已读模式
                    cursor.moveToNext();
                    int smsBodyColumn = cursor.getColumnIndex("body");
                    String smsBody = cursor.getString(smsBodyColumn);
                    final String code = getVerificationCode(smsBody);
                    Message msg = new Message();
                    msg.what = 111;
                    msg.obj = code;
                    mHandler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //在用managedQuery的时候，不能主动调用close()方法，否则在Android 4.0+的系统上会发生崩溃
            if (Build.VERSION.SDK_INT < 14) {
                cursor.close();
            }
        }
    }
}
