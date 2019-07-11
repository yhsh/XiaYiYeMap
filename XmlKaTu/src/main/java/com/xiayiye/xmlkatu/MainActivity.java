package com.xiayiye.xmlkatu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button tv;
    private Animation animation;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.startAnimation(animation);
                Toast.makeText(MainActivity.this,"点击了",Toast.LENGTH_LONG).show();
            }
        });
    }

}
