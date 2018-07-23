package com.example.mo_20.projetmobile2027;

import android.app.ActivityOptions;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThemeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    List<String> stringList;
    List<CheckBox> checkBoxList;
    LinearLayout list;
    Button ok;
    Base base;
    Interface anInterface;
    static List<String> themeList;
    static String ajoutDansTheme = "";
    static List<Couple> questRep;
    Set<String> set;
    CheckBox tmp;

    public final static String QUESTION = "question";
    public final static String ANSWER = "answer";
    public final static String LEVEL = "level";
    public final static String THEME ="theme";
    public final static String DATE_TO_SHOW = "date";

    private static String AUTHORITY ="com.example.mo_20.database.provider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        base = new Base(this);
        list = (LinearLayout) findViewById(R.id.list);
        ok = (Button) findViewById(R.id.Tok);
        anInterface = new Interface(this);
        checkBoxList = new ArrayList<>();
        questRep = new ArrayList<Couple>();
        themeList = new ArrayList<String>();
        stringList = anInterface.queryTheme(); //base.all("theme");;
        set = new HashSet<>();
        set.addAll(stringList);
        for (String s : set) {
            tmp = new CheckBox(this);
            tmp.setText(s);
            checkBoxList.add(tmp);
            list.addView(tmp);
        }
    }


    public void tok(View view) {
        Intent i = null;
        questRep = new ArrayList<Couple>();
        String choice=MainActivity.choice;

        if(choice.equals("add")){
            int cpt = 0;
            for (CheckBox c : checkBoxList) {
                if (c.isChecked()) {
                    cpt++;
                    ajoutDansTheme = (String) c.getText();
                }
            }
            if (cpt > 1)
                Toast.makeText(this, "vous ne devez cochez qu'une case", Toast.LENGTH_LONG).show();
            else {
                i = new Intent(this, AddQuestionActivity.class);
                this.finish();
                startActivity(i);

            }
        }
        else {
            for (CheckBox c : checkBoxList) {
                if (c.isChecked()) {
                    themeList.add((String) c.getText());
                }
            }
            if(choice.equals("play")) {
                questRep = anInterface.queryQuestRep(themeList);
                i = new Intent(this, PlayActivity.class);
                this.finish();
                startActivity(i);
            }
            else {
                Toast t = Toast.makeText(this,"Remove", Toast.LENGTH_SHORT);
                t.show();
                for (String s : themeList){
                    anInterface.delete(s);

                }
                i = new Intent(this, ThemeActivity.class);
                this.finish();
                startActivity(i);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(AUTHORITY).appendPath("quizz");
        Uri uri = builder.build();
        return new CursorLoader(this,uri,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Couple c;
        String q , a, l, t, d;
        while (cursor.moveToNext()){
            //themeList.add(cursor.getString(cursor.getColumnIndex(THEME)));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}