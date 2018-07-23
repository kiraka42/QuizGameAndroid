package com.example.mo_20.projetmobile2027;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import 	android.graphics.Color;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private TextView score, theme, question , answer, scoreValue, end;
    private EditText answerEntry;
    int cpt = -1;
    int scores = 0 ;
    List<String> answers, questions , levels, dates;
    List<Couple> gen;
    List<String> themes;
    Base base;
    Button pok ;
    Button pnext ;
    Button move;
    Thread t1;
    static int TIME = 10;
    Interface anInterface;
    public final static String QUESTION = "question";
    public final static String ANSWER = "answer";
    public final static String LEVEL = "level";
    public final static String THEME ="theme";
    public final static String DATE_TO_SHOW = "date";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar bar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        pok = (Button) findViewById(R.id.Pok);
        pnext = (Button) findViewById(R.id.Pnext);
        end = (Button) findViewById(R.id.end);
        move = (Button) findViewById(R.id.move);
        anInterface = new Interface(this);
        question = (TextView) findViewById(R.id.pquestion);
        answer   = (TextView) findViewById(R.id.panswer);
        gen = ThemeActivity.questRep;
        end.setVisibility(View.INVISIBLE);
        exec();
        if(savedInstanceState!=null)
            onRestoreInstanceState(savedInstanceState);

    }
    public void onSaveInstanceState(Bundle saved) {
        cpt = cpt--;
        saved.putInt("cpt", cpt);
        super.onSaveInstanceState(saved);
    }

    public void onRestoreInstanceState(Bundle saved){
        super.onRestoreInstanceState(saved);
        cpt = saved.getInt("cpt");
    }




    public void pok(View v){
        t1.interrupt();
        if(v == pok) {
            String res = gen.get(cpt).answer+" "+gen.get(cpt).date;
            answer.setText(res);
        }
        else if(v==move){
            anInterface.update(gen.get(cpt).question,gen.get(cpt).answer,gen.get(cpt).level,gen.get(cpt).theme,2);
        }
        else {
            answer.setText(new String());
            exec();
        }
    }

    public void reset(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();

    }
    public void exec(){
        answer.setText(new String());
        t1 = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(TIME*1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                exec();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            };

        };
        t1.start();
        int now = Interface.hour();
        cpt++;
        if(cpt<gen.size()) {
            if(Integer.parseInt(gen.get(cpt).date)<=now) {
                question.setText(gen.get(cpt).question);
                answer.setText("");
            }
        }
        else {
            pok.setEnabled(false);
            pnext.setEnabled(false);
            end.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
