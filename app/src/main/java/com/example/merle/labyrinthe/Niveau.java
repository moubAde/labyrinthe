package com.example.merle.labyrinthe;

/**
 * Created by Big_TM on 19/03/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class Niveau extends Activity {

    private MediaPlayer mediaPlayer;
    private ArrayList<ToggleButton> level;
    public static int nbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Desactiver la barre de titre de notre application
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }catch (Exception e){}
        // Passer la fenêtre en full-creen == cacher la barre de notification
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }catch (Exception e){}
        setContentView(R.layout.activity_instructions);
        mediaPlayer = MediaPlayer.create(this, R.raw.gamestart);
        if (Menu.son)
            mediaPlayer.start();

        Button menu=(Button)findViewById(R.id.button2);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu();
            }
        });
        level=new ArrayList<ToggleButton>();
        nbr=16;
        ToggleButton b;
        int x=130,y=180;
        RelativeLayout cadre=(RelativeLayout)findViewById(R.id.activity_instructions);
        //TextView cadre=(TextView)findViewById(R.id.textView);
        for(int i=0,u=0;i<nbr;i++) {
            b=new ToggleButton(this);
            b.setText(""+i);
            b.setX(x+(u*80));
            b.setY(y);
            level.add(b);
            cadre.addView(b,75,75);
            u++;
            if(i==7) {
                u=0;
                y += 80;
            }
        }

        for (int i=0;i<nbr;i++){
            ((ToggleButton) level.get(i)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j=0;j<nbr;j++){
                        if (((ToggleButton) level.get(j)).isChecked()){
                            ((ToggleButton) level.get(j)).setChecked(false);
                            ((ToggleButton) level.get(j)).setText(""+j);
                            begin(j);
                        }
                    }
                }
            });
        }
    }

    public void onPause(){
        super.onPause();
        if (Menu.son)
            mediaPlayer.pause();
    }

    public void menu(){
        Intent intent = new Intent(this,Menu.class);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void onResume(){
        super.onResume();
        if(Menu.son)
            mediaPlayer.start();
    }

    public void begin (int level){
        if (Menu.son)
            mediaPlayer.pause();
        LabyrintheActivity.level=level;
        System.out.println("ici:"+level+" la ba:"+LabyrintheActivity.level);
        Intent intent = new Intent(this, LabyrintheActivity.class);
        startActivity(intent);
    }
}
