package cn.xiayiye.forceoff_line;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button off_line = (Button) findViewById(R.id.off_line);
        off_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送强制下线广播
                Intent intent = new Intent("com.yhsh.off-line");
                sendBroadcast(intent);
            }
        });
    }
}
