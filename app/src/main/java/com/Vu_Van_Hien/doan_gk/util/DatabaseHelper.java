package com.Vu_Van_Hien.doan_gk.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "qlPet.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_categories_table = "CREATE TABLE phanloai(id INTEGER PRIMARY KEY, tenloai TEXT)";
        String create_pets_table = "CREATE TABLE pet(id INTEGER PRIMARY KEY, tenpet TEXT, mota TEXT, gia INTEGER, hinh BLOB, maloai INTEGER,FOREIGN KEY(maloai) REFERENCES sach(id))";
        sqLiteDatabase.execSQL(create_categories_table);
        sqLiteDatabase.execSQL(create_pets_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_categories_table = "DROP TABLE IF EXISTS phanloai";
        String drop_pets_table = "DROP TABLE IF EXISTS pet";
        sqLiteDatabase.execSQL(drop_categories_table);
        sqLiteDatabase.execSQL(drop_pets_table);
        onCreate(sqLiteDatabase);
    }
}