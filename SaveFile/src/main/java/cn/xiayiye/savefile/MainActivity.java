package cn.xiayiye.savefile;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {

    private String trim;
    private EditText save_data;
    private BufferedReader bufferedReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save_data = (EditText) findViewById(R.id.save_data);
        try {
            FileInputStream save = openFileInput("save");
            bufferedReader = new BufferedReader(new InputStreamReader(save));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            save_data.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "未找到保存的文件", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trim = save_data.getText().toString().trim();
        saveFile(trim);
    }

    private void saveFile(String trim) {
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(this, "填写数据在保存！", Toast.LENGTH_SHORT).show();
        } else {
            try {
                //save代表保存的文件名称
                FileOutputStream save = openFileOutput("save", MODE_PRIVATE);//默认保存到data/data/包名/files/目录下
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(save));
                try {
                    bufferedWriter.write(trim);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
