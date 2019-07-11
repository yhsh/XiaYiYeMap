package cn.xiayiye.permissionsapply;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    StringBuffer contacts = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button apply = findViewById(R.id.apply);
        Button get_contacts = findViewById(R.id.get_contacts);
        apply.setOnClickListener(this);
        get_contacts.setOnClickListener(this);
    }

    private void callPhone() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10010"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.e("异常", "权限异常");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    Toast.makeText(MainActivity.this, "请申请权限再打电话", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactsData();
                } else {
                    Toast.makeText(MainActivity.this, "请申请权限再读取联系人", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    } else {
                        callPhone();
                    }
                } else {
                    callPhone();
                }
                break;
            case R.id.get_contacts:
                getContacts();
                break;
            default:
                break;
        }
    }

    private void getContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Android6.0以上版本先申请权限
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
            } else {
                //获取联系人
                getContactsData();
            }
        } else {
            //Android6.0以下版本直接读取联系人
            getContactsData();
        }
    }

    private void getContactsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Cursor query = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
                    if (query != null) {
                        while (query.moveToNext()) {
                            String name = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));//获取联系人
                            String phone = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));//获取手机号
                            Log.e("打印联系人", name + "===" + phone);
                            contacts.append(name);
                            contacts.append("===");
                            contacts.append(phone + "\n");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                                alertDialog.setMessage(contacts.toString()).setNegativeButton("确定", null).show();
                            }
                        });
                        query.close();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "没有联系人", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "联系人读取异常", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
