package com.example.mo_20.projetmobile2027;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {


    Button pl;
    Button ad;
    Button rm;
    Interface i;
    static boolean adding;
    static String choice;
    static String FILELINK ="https://raw.githubusercontent.com/cloudStrif/English/master/quizz.txt";
    TextView baseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = new Interface(this);
        pl = (Button) findViewById(R.id.Play);
        ad = (Button) findViewById(R.id.Add);
        rm = (Button) findViewById(R.id.Remove);
        baseData = (TextView) findViewById(R.id.baseData);

    }

    public void click(View v){
        Intent i = null;
        if(v == ad){
            choice="add";
        }

        else if(v == pl){
            choice="play";

        }

        else {
            choice="remove";
        }

        i = new Intent(this, ThemeActivity.class);
        startActivity(i);
    }
    public void optionclick(View view){
        Intent i = new Intent(this, OptionActivity.class);
        startActivity(i);
    }
    public void download(View view){
        Thread t2 = new Thread(){
            @Override
            public void run() {

                    synchronized (this) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("mlml");
                                String file = "https://raw.githubusercontent.com/cloudStrif/English/master/quizz.txt";
                                if(!FILELINK.equals(""))
                                    file = FILELINK;
                                Uri uri = Uri.parse(file);
                                DownloadManager.Request r = new DownloadManager.Request(uri);
                                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                long id  =  dm.enqueue(r);

                                DownloadManager.Query query = new DownloadManager.Query();
                                query.setFilterById(id).setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
                                Cursor cur = dm.query(query);
                                while(true){
                                    cur = dm.query(query);
                                    if (cur!=null&&cur.getCount()!=0) break ;
                                    try {
                                        Thread.sleep(1000);
                                    }catch(Exception exp){

                                    }
                                }
                                cur.moveToFirst();
                                String chemin = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                                Memory mem = new Memory() ;
                                String chaine = mem.af(chemin) ;
                                String[] tab = chaine.split("\n");
                                String res ="";
                                for (int ii = 0; ii < mem.numberLine-1 ; ii++) {
                                    res=Interface.hour()+"";
                                    i.insert(tab[ii].trim().split(",")[0],tab[ii].trim().split(",")[1],tab[ii].trim().split(",")[2],tab[ii].trim().split(",")[3],res);
                                    System.out.println();
                                }
                                //baseData.setText(chaine);
                            }
                        });
                    }


            };

        };
        t2.start();



    }
    /*
    private final void createNotification(){
        final NotificationManager mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final Intent launchNotifiactionIntent = new Intent(this, MainActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this,
                50, launchNotifiactionIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setTicker("")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_desc))
                .setContentIntent(pendingIntent);

        mNotification.notify(2, builder.build());
    }
    */
}
