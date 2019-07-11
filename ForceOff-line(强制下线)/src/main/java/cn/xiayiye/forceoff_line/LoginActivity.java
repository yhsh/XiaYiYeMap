package cn.xiayiye.forceoff_line;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_act;
    private EditText et_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_act = (EditText) findViewById(R.id.et_act);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        Button bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void login() {
        String trim_act = et_act.getText().toString().trim();
        String trim_pwd = et_pwd.getText().toString().trim();
        if (!TextUtils.isEmpty(trim_act) && !TextUtils.isEmpty(trim_pwd)) {
            if (trim_act.equals("admin") && trim_pwd.equals("123456")) {
                //登录
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();//关闭登录页面
            } else {
                Toast.makeText(this, "账号密码不正确！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "账号密码不能为空！", Toast.LENGTH_SHORT).show();
        }
    }
}

