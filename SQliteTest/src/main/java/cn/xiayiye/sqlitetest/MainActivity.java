package cn.xiayiye.sqlitetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private MySQlite mySQlite;
    private SQLiteDatabase wtd;
    ArrayList<String> SQLData = new ArrayList<>();
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySQlite = new MySQlite(this, "book.db", null, 2);
        Button sqlite_add = findViewById(R.id.sqlite_add);
        Button sqlite_del = findViewById(R.id.sqlite_del);
        Button sqlite_updata = findViewById(R.id.sqlite_updata);
        Button sqlite_query = findViewById(R.id.sqlite_query);
        Button sqlite = findViewById(R.id.sqlite);
        sqlite.setOnClickListener(this);
        sqlite_add.setOnClickListener(this);
        sqlite_del.setOnClickListener(this);
        sqlite_updata.setOnClickListener(this);
        sqlite_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        wtd = mySQlite.getWritableDatabase();
        switch (v.getId()) {
            case R.id.sqlite:
                Toast.makeText(MainActivity.this, "点击了创建数据库", Toast.LENGTH_SHORT).show();
                //创建数据库
                break;
            case R.id.sqlite_add:
                addData();
                break;
            case R.id.sqlite_del:
                delData();
                break;
            case R.id.sqlite_updata:
                updateData();
                break;
            case R.id.sqlite_query:
                queryData();
                break;
        }
    }


    private void addData() {
        num++;
        ContentValues values = new ContentValues();
        values.put("age", 26);
        values.put("type", 100);
        values.put("address", "北京昌平" + num);
        values.put("name", "下一页" + num);
        wtd.insert("Yhsh", null, values);
        values.clear();
        values.put("author", "轻飞扬" + num);
        values.put("price", 8.05);
        values.put("pages", num);
        values.put("name", "天使" + num);
        wtd.insert("Book", null, values);
        Toast.makeText(MainActivity.this, "数据添加成功", Toast.LENGTH_SHORT).show();
    }

    private void delData() {
        wtd.delete("Book", "price < ?", new String[]{"8"});//删除价格小于8这行
        Toast.makeText(this, "删除了数据", Toast.LENGTH_SHORT).show();
    }

    private void updateData() {
        Toast.makeText(MainActivity.this, "点击了修改数据库", Toast.LENGTH_SHORT).show();
        ContentValues values = new ContentValues();
        values.put("age", 27);
        wtd.update("Yhsh", values, "name=?", new String[]{"下一页2"});//根据name名称为下一页2查找修改这行的age年纪改为27
        values.clear();
        values.put("price", 5.68);
        wtd.update("Book", values, "name=?", new String[]{"天使8"});//根据name名称为天使8查找修改这行的价格为5.68
    }

    private void queryData() {
        SQLData.clear();//查询之前清空集合
        Cursor yhsh = wtd.query("Yhsh", null, null, null, null, null, null);
        while (yhsh.moveToNext()) {
            String SQL = yhsh.getString(yhsh.getColumnIndex("name"));
            SQLData.add(SQL);
        }
        yhsh.close();//关闭数据库
        Log.e("打印查询到的数据库", SQLData.toString());
    }
}
