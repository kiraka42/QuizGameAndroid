package com.example.mo_20.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mo-20 on 02/01/2017.
 */

public class Base extends SQLiteOpenHelper {

    public final static int VERSION = 8;

    private final static String DB_NAME="Project.db";
    private final static String TABLE_QUIZZ = "table_project";

    public final static String QUESTION = "question";
    public final static String ANSWER = "answer";
    public final static String LEVEL = "level";
    public final static String THEME ="theme";
    public final static String DATE_TO_SHOW = "date";





    public Base(Context context){
        super(context,DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_QUIZZ + "(" + QUESTION + " text, " +
                ANSWER + " text, " +
                LEVEL + " text, " +
                THEME + " text, " +
                DATE_TO_SHOW + " text, " +
                "primary key(" + QUESTION + "));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion){
            db.execSQL("drop table if exists "+ TABLE_QUIZZ);
            onCreate(db);
        }
    }
    public static Base instance;
    public static Base getInstance(Context context){
        if(instance ==  null){
            instance = new Base(context);
        }
        return instance;
    }

}
