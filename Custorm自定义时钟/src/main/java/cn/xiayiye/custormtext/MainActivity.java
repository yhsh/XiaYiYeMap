package cn.xiayiye.custormtext;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            String save = savedInstanceState.getString("save");
            Log.e("拿到保存的数据：", save);
        }
        WatcherBoard shi_zhong = (WatcherBoard) findViewById(R.id.shi_zhong);
        shi_zhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://blog.csdn.net/xiayiye5"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("save", "保存此页面由于内存不足而会回收的数据");
    }
}
