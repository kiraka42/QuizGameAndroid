package com.example.mo_20.projetmobile2027;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.GregorianCalendar;

public class AddQuestionActivity extends AppCompatActivity  {
    //Base base;
    Interface i;
    EditText question , answer, level, theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        //base = new Base(this);
        i = new Interface(this);
        question= (EditText) findViewById(R.id.question);
        answer  = (EditText) findViewById(R.id.answer);
        level   = (EditText) findViewById(R.id.level);
        theme   = (EditText) findViewById(R.id.theme);
        String res = ThemeActivity.ajoutDansTheme;
        if(!res.equals("")){
            theme.setText(res);
            theme.setEnabled(false);
        }
    }

    public void add(View v ){
        String q = question.getText().toString();
        String a = answer.getText().toString();
        String l = level.getText().toString();
        String t = theme.getText().toString();
        String d = Interface.hour()+"";
        //boolean res = base.ajouteLigne(q,a,l,t,new Date().toString());

        i.insert(q,a,l,t,d);
        /*if(res){
            Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"failure",Toast.LENGTH_LONG).show();

        }
        */
        question.setText("");
        answer.setText("");
        level.setText("");
        theme.setText("");


    }



}
