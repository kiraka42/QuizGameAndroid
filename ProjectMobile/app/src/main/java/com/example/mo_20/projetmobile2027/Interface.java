package com.example.mo_20.projetmobile2027;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mo-20 on 02/01/2017.
 */

public class Interface {

    private Context context;
    private ContentResolver contentResolver;

    public final static String TABLE = "table_project";

    public final static String QUESTION = "question";
    public final static String ANSWER = "answer";
    public final static String LEVEL = "level";
    public final static String THEME ="theme";
    public final static String DATE_TO_SHOW = "date";

    private static String AUTHORITY ="com.example.mo_20.database.provider";

    public Interface(Context c){
        context = c;
        contentResolver = c.getContentResolver();
    }

    public void insert(String question, String answer, String level, String theme, String date){
        try {
            //java.util.GregorianCalendar now = new GregorianCalendar();
            ContentValues values = new ContentValues();

            values.put(QUESTION,question);
            values.put(ANSWER,answer);
            values.put(LEVEL,level);
            values.put(THEME,theme);
            values.put(DATE_TO_SHOW,date);
            Uri.Builder buider = new Uri.Builder();
            buider.scheme("content").authority(AUTHORITY).appendPath("quizz");
            Uri uri = buider.build();
            contentResolver.insert(uri, values);
        }

        catch (Exception e){
            e.printStackTrace();
            Log.d("tag","insert failed");
        }
    }

    public static int today(){
        int res=0;
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = d.getDay();
        int month = cal.get(Calendar.MONTH);
        for (int i=0; i<month; i++){
            res+=daysInMonth(i);
        }
        return res+day;
    }
    public static int hour(){
        int res=0;
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = d.getMinutes();
        int month = d.getHours();
        for (int i=0; i<month; i++){
            res+=60;
        }
        return res+day;
    }

    public static int daysInMonth(int month){
        int res=0;
        if(month == 3 || month == 5 || month == 8 || month==10 ){
            return 30;
        }
        else if( month == 28 ){
            return 28;
        }
        else return 31;

    }


    public void update(String question, String answer, String level, String theme, int date){
        this.deleteQuestion(question);
        int da = hour()+date;
        String s = da+"";
        this.insert(question,answer,level,theme,s);
    }

    public static int dateSince2017(){
        java.util.GregorianCalendar now = new GregorianCalendar();
        Calendar c  = Calendar.getInstance();
        java.util.GregorianCalendar Jan12017 = new java.util.GregorianCalendar(2017,0,1);
        int j = (int) Jan12017.getTimeInMillis() / (24*60*60*1000);
        int days = (int) now.getTimeInMillis() / (24*60*60*1000);

        return (days - j);
    }

    public List<String> queryTheme (){
        List<String> list = new ArrayList<String>();
        Uri.Builder buider = new Uri.Builder();
        buider.scheme("content").authority(AUTHORITY).appendPath("quizz");
        Uri uri = buider.build();
        Cursor  cursor  = contentResolver.query(uri,null,null,null,null);
        if(cursor != null){

            while (cursor.moveToNext())
                list.add(cursor.getString(cursor.getColumnIndex(THEME)));
        }
        for(String s: list)
            System.out.println(s+" ");
        return list;

    }
    public void delete (List<String> th ){
        for(String s : th) {
            this.delete(s);
        }
    }
    public void delete(String s){
        Uri.Builder buider = new Uri.Builder();
        buider.scheme("content").authority(AUTHORITY).appendPath("quizz");
        Uri uri = buider.build();
        contentResolver.delete(uri,THEME,new String[]{s});
    }
    public void deleteQuestion(String s){
        Uri.Builder buider = new Uri.Builder();
        buider.scheme("content").authority(AUTHORITY).appendPath("parTheme");
        Uri uri = buider.build();
        contentResolver.delete(uri,QUESTION,new String[]{s});
    }

    public List<Couple> queryQuestRep(List<String> th ){
        List<Couple> list = new ArrayList<Couple>();
        for(String s : th) {
            list.addAll(queryQuestRep(s));
        }
        return list;
    }
    public List<Couple> queryQuestRep(String s){
        String[] themes = new String[1];
        //themes = th.toArray(themes);
        themes[0] = s;
        List<Couple> list = new ArrayList<Couple>();
        Uri.Builder buider = new Uri.Builder();
        buider.scheme("content").authority(AUTHORITY).appendPath("parTheme");
        Uri uri = buider.build();
        Cursor  cursor  = contentResolver.query(uri,null,null,themes,null);
        if(cursor != null ){
            String q="";
            String a="";
            String l="";
            String d="";
            String t="";
            while ((cursor.moveToNext())){
                q= cursor.getString(cursor.getColumnIndex(QUESTION));
                a= cursor.getString(cursor.getColumnIndex(ANSWER));
                l= cursor.getString(cursor.getColumnIndex(LEVEL));
                d = cursor.getString(cursor.getColumnIndex(DATE_TO_SHOW));
                t=  cursor.getString(cursor.getColumnIndex(THEME));
                list.add(new Couple(q,a,l,d,t));

            }
        }
        return list;
    }




}
