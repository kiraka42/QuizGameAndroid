package com.example.mo_20.projetmobile2027;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mo-20 on 26/12/2016.
 */

public class Base extends SQLiteOpenHelper{

    public final static int VERSION = 8;

    public final static String DB_NAME="Quizz.db";
    public final static String TABLE_QUIZZ = "table_quizz";

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

    public boolean ajouteLigne(String question, String answer, String level, String theme,String date){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QUESTION,question);
        cv.put(ANSWER,answer);
        cv.put(LEVEL,level);
        cv.put(THEME,theme);
        cv.put(DATE_TO_SHOW, date);
        long res = db.insert(TABLE_QUIZZ,null,cv);
        db.close();
        return res!=-1;
    }





    public String findinfo(String info){
        SQLiteDatabase db=getReadableDatabase();
        String[] selectionArgs={info};
        Cursor cur=db.query(TABLE_QUIZZ,null,"question=?",selectionArgs,null,null,null);
        String s=null;
        if (cur!=null && cur.moveToFirst()){
            s =cur.getString(cur.getColumnIndex("answer"));

            System.out.println(s + "  tete");
        }
        db.close();
        return s;
    }
    public List<String> findQuestion(String info){
        SQLiteDatabase db=getReadableDatabase();
        String[] selectionArgs={info};
        Cursor cur=db.query(TABLE_QUIZZ,null,"theme=?",selectionArgs,null,null,null);
        List<String> s=new ArrayList<>();
        if (cur!=null){
            while(cur.moveToNext())
                s.add(cur.getString(cur.getColumnIndex("question")));

            System.out.println(s + "  tete");
        }
        db.close();
        return s;
    }


    public List<String> all(String s){
        List<String> answers = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        String[] column = {s};
        Cursor cur = db.query(TABLE_QUIZZ,column,null,null,null,null,null);
        //cur.moveToFirst();
        if(cur!=null){
            while (cur.moveToNext()){
                answers.add(cur.getString(cur.getColumnIndex(s)));

            }
        }
        cur.close();
        for(int i=0 ;  i < answers.size() ; i++)
        {
            System.out.println(answers.get(i) + "   TEST   "+s);
        }
        db.close();
        return answers;
    }

}
