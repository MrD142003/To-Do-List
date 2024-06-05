package com.example.to_dolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class JobDB extends SQLiteOpenHelper {
    public static final String DATABASE = "todolist.db";
    public static final String TABLE_NAME = "TODOLIST";
    public static final int VERSION = 1;
    public static final String MACONGVIEC = "macongviec";
    public static final String TENCONGVIEC = "tencongviec";
    public static final String TENNHANVIEN = "tennhanvien";
    public static final String SODIENTHOAI = "sodienthoai";

    public SQLiteDatabase myDB;

    public JobDB(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME +
                "(" + MACONGVIEC + " TEXT PRIMARY KEY , " +
                TENCONGVIEC + " TEXT NOT NULL, " +
                TENNHANVIEN + " TEXT NOT NULL, " +
                SODIENTHOAI + " TEXT NOT NULL" + ")";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onOpen() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long Insert(String macongviec, String tencongviec, String tennhanvien, String sodienthoai){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MACONGVIEC, macongviec);
        values.put(TENCONGVIEC, tencongviec);
        values.put(TENNHANVIEN, tennhanvien);
        values.put(SODIENTHOAI, sodienthoai);
        return db.insert(TABLE_NAME,null,values);
    }

    public long Update(String macongviec, String tencongviec, String tennhanvien, String sodienthoai){
        ContentValues values = new ContentValues();
        values.put(MACONGVIEC, macongviec);
        values.put(TENCONGVIEC, tencongviec);
        values.put(TENNHANVIEN, tennhanvien);
        values.put(SODIENTHOAI, sodienthoai);
        String where = MACONGVIEC +" = '"+macongviec+"'";
        return myDB.update(TABLE_NAME,values,where,null);
    }
    public long Delete(String macongviec){
        String where = MACONGVIEC +" = '"+macongviec+"'";
        return myDB.delete(TABLE_NAME,where,null);
    }
    public Cursor DisplayAll() {
        SQLiteDatabase db = getReadableDatabase();
        String query = " Select * From " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor DisplayByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        String query =" Select * From "+ TABLE_NAME+" WHERE "+TENCONGVIEC+" like '%"+name+"%'";
        return db.rawQuery(query,null);
    }


}
