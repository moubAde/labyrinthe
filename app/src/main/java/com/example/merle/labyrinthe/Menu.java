
package com.example.merle.labyrinthe;

/**
 * Created by merle on 07/03/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends Activity {

    public static boolean son=true;
    private MediaPlayer mediaPlayer;
    Button b3;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Desactiver la barre de titre de notre application
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }catch (Exception e){}
        // Passer la fenêtre en full-creen == cacher la barre de notification
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }catch (Exception e){}

        setContentView(R.layout.activity_menu);
        mediaPlayer = MediaPlayer.create(this, R.raw.gamestart);
        mediaPlayer.start();
        b3=(Button)findViewById(R.id.button3);
        b3.setEnabled(true);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(savedInstanceState != null)
                // Toast.makeText(getApplicationContext(), "Playing sound",Toast.LENGTH_SHORT).show();
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    b3.setText("Son désactivé");
                    son=false;
                }
                else{
                    mediaPlayer.start();
                    b3.setText("Son activé");
                    son=true;
                }
            }
        });
        Button stop=(Button)findViewById(R.id.button4);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitter();
            }
        });
    }

    public void onResume(){
        super.onResume();
        if(son)
            mediaPlayer.start();
    }

    public void onPause(){
        super.onPause();
        if (son)
            mediaPlayer.pause();
    }


    public void start (View view){
        mediaPlayer.pause();
        Intent intent = new Intent(this, LabyrintheActivity.class);
        startActivity(intent);
    }
    public void aide (View view){
        //onResume();
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
       // mFancyToast.show();
    }

    public void niveaux (View view){
        Intent intent = new Intent(this, Niveau.class);
        startActivity(intent);
    }

    public void onStop(){
        super.onStop();///
        //this.finish();
        //mediaPlayer.release();
        //mediaPlayer.stop();
    }

    public void quitter(){
        onStop();
        this.finish();
    }

    /*public void parametres (View view){
        if(!mediaPlayer.isPlaying()){

        }
        Intent intent = new Intent(this, Parametres.class);
        System.out.println("OK");
        startActivity(intent);
    }*/
}