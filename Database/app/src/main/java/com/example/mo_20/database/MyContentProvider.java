package com.example.mo_20.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private static String AUTHORITY ="com.example.mo_20.database.provider";

    private final static String DB_NAME="Project.db";
    private final static String TABLE_QUIZZ = "table_project";

    private final static String QUESTION = "question";
    private final static String ANSWER = "answer";
    private final static String LEVEL = "level";
    private final static String THEME ="theme";
    private final static String DATE_TO_SHOW = "date";
    private SQLiteDatabase db;
    private int code;



    Base base;

    //public final static String QUERY = "SELECT * FROM "+TABLE_QUIZZ;

    public MyContentProvider() {

    }

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        matcher.addURI(AUTHORITY, "quizz", 1);
        matcher.addURI(AUTHORITY, "parTheme", 2);
        matcher.addURI(AUTHORITY, "all",3);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = base.getWritableDatabase();
        code = matcher.match(uri);
        int res = 0;
        switch (code) {
            case 1:
                res = db.delete(TABLE_QUIZZ, "theme=?", selectionArgs);
                break;
            case 2:
                res = db.delete(TABLE_QUIZZ,"question=?",selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }

        return res;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
       db = base.getWritableDatabase();
        code = matcher.match(uri);
        long id = 0;
        Uri.Builder builder = new Uri.Builder();

        switch (code){
            case 1:
                id = db.insert(TABLE_QUIZZ,null,values);
                break;
            default:
                throw new UnsupportedOperationException("Not INSERT");

        }

        return ContentUris.withAppendedId(uri,id);
        //builder.authority(AUTHORITY);
        //builder = ContentUris.appendId(builder,id);
        //return builder.build();

    }

    @Override
    public boolean onCreate() {
        base = new Base(this.getContext());
        base = Base.getInstance(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = null;
        code = matcher.match(uri);
        switch (code){
            case 1:
                cursor = db.rawQuery("SELECT distinct "+THEME+" FROM "+TABLE_QUIZZ,null);
                break;
            case 2:
                cursor = db.rawQuery("SELECT * FROM "+TABLE_QUIZZ+" WHERE "+THEME+"=?",selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Not found ");
        }

        return cursor;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = base.getReadableDatabase();
        Cursor cursor = null;
        int res=0;

        code = matcher.match(uri);
        switch (code){
            case 1:
                cursor =  db.rawQuery("UPDATE "+TABLE_QUIZZ+" SET "+DATE_TO_SHOW+"=?",selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        return res;

    }
}
