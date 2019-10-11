package com.example.merle.labyrinthe;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Instructions extends Activity {

    private MediaPlayer mediaPlayer;
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

        TextView tv=(TextView)findViewById(R.id.textView);
        tv.setText("Arriveras-tu as sauver la Terre contre le terrible rois des Gorgotes? Si tu t'en sent capable lit attentivement les instructions a suivre\n" +
                "Pour sauver la terre il te faudra mener la boule à la sortie du labyrinthe.\n"+
                "Tu dois réussir à la diriger à travers le parcour mais attention car le terrible rois des Gogortes a disimulé des piéges sur ton chemin. \n" +
                "Des désintégrateurs moléculaire placés un peu partout sur la surface de jeu qu'il te faudra eviter. Si tu te fait avoir la Terre sera détruite.\n" +
                "Inclines la tablette pour te déplacer.\n" +
                "Tape sur l'ecran pour mettre le jeu en pause.\n" +
                "A toi de jouer Tu es le seul espoir de la Terre \uD83D\uDE42");
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
}
