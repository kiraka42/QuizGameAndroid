package com.example.mo_20.projetmobile2027;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OptionActivity extends AppCompatActivity {
    EditText time, link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        time = (EditText) findViewById(R.id.time);
        time.setText(PlayActivity.TIME+"");
    }

    public void optionOk(View v){
        String s = time.getText().toString();
        int t = Integer.parseInt(s);
        PlayActivity.TIME=t;
        Toast toast = Toast.makeText(this,"changement validé",Toast.LENGTH_LONG);
        toast.show();
    }


}
