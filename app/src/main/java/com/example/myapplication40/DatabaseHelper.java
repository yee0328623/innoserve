package com.example.myapplication40;

// DatabaseHelper.java
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "yee0328.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库表的代码放在这里
        db.execSQL("CREATE TABLE IF NOT EXISTS acc (id INTEGER PRIMARY KEY, name TEXT, phonenumber TEXT, mail TEXT,password TEXT, sex TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库的代码放在这里
    }
}
