package com.example.peter.mob403_asm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    Context context;

    public MyDatabase(Context context){
        super(context, "truyen", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table giotruyen " +
                "( " +
                "_id integer primary key autoincrement, " +

                "token text, " +
                "userId text, " +
                "userName text" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists giotruyen");
        onCreate(sqLiteDatabase);
    }

    public void setTokenAndUserId(String token, String userId, String userName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("token",token);
        values.put("userId", userId);
        values.put("userName", userName);

        db.insert("giotruyen",null,values);
    }

    public TokenAndUserId getTokenAndUserId(){
        String token = null;
        String userId = null;
        String userEmail = null;
        TokenAndUserId tokenAndUserId = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from giotruyen",null);
        if(c.moveToFirst()){
            do{
                token = c.getString(1);
                userId = c.getString(2);
                userEmail = c.getString(3);
                tokenAndUserId = new TokenAndUserId(token, userId, userEmail);
            }while(c.moveToNext());
        }
        return tokenAndUserId;
    }

    public void deleteToken(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from giotruyen");
    }
}
