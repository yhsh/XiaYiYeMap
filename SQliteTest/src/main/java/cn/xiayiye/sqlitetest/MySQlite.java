package cn.xiayiye.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * 创 建 者：下一页5（轻飞扬）
 * 创建时间：2018/3/19.17:38
 * 个人小站：http://wap.yhsh.ai(已挂)
 * 最新小站：http://www.iyhsh.icoc.in
 * 联系作者：企鹅 13343401268(请用手机QQ添加)
 * 博客地址：http://blog.csdn.net/xiayiye5
 * 空间名称：XiaYiYeMap
 * 项目包名：cn.xiayiye.sqlitetest
 */
class MySQlite extends SQLiteOpenHelper {
    private static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement,"
            + "author text,"
            + "price real,"
            + "pages integer,"
            + "name text)";
    private static final String CREATE_YHSH = "create table Yhsh ("
            + "id integer primary key autoincrement,"
            + "age integer,"
            + "type integer,"
            + "address text,"
            + "name text)";
    private Context context;

    MySQlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_YHSH);
        Toast.makeText(context, "创建成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Yhsh");
        onCreate(db);
    }
}
