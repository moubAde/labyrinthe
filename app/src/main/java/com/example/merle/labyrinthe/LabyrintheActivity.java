package com.example.merle.labyrinthe;

/**
 * Created by merle on 07/03/2017.
 */

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LabyrintheActivity extends Activity {
    // Identifiant de la boîte de dialogue de victoire
    public static final int VICTORY_DIALOG = 0;
    // Identifiant de la boîte de dialogue de défaite
    public static final int DEFEAT_DIALOG = 1;
    // Identifiant de la boîte de dialogue de pause
    public static final int PAUSE_DIALOG = 2;

    public static int level=0;
    public Context c;
    MediaPlayer mp1;
    int nbr=2;
    // Le moteur graphique du jeu
    private LabyrintheView mView = null;
    // Le moteur physique du jeu
    private LabyrintheEngine mEngine = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp1=MediaPlayer.create(this, R.raw.partystart);

        // Desactiver la barre de titre de notre application
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }catch (Exception e){}
        // Passer la fenêtre en full-creen == cacher la barre de notification
        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }catch (Exception e){}

        //MediaPlayer mediaPlayer=  MediaPlayer.create(this, R.raw.sms);
        /*if (mediaPlayer!=null) {
            mediaPlayer.start();
        }*/

        c=getBaseContext();
        mView = new LabyrintheView(this,this);
        setContentView(mView);

        mEngine = new LabyrintheEngine(this);

        Boule b = new Boule();
        mView.setBoule(b);
        mEngine.setBoule(b);

        List<Bloc> mList = mEngine.buildLabyrinthe(level);
        mView.setBlocks(mList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mEngine.resume();
        if (Menu.son)
            mp1.start();
    }

    @Override
    protected void onPause() {
        super.onStop();
        if (Menu.son)
            mp1.stop();
        mEngine.stop();
    }

    @Override
    public Dialog onCreateDialog (int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch(id) {
            case VICTORY_DIALOG:
                if (level==nbr) {
                    builder.setCancelable(false)
                            .setMessage("Bravo, tu as sauvé la terre \uD83D\uDCAA \uD83D\uDE42 !")
                            .setTitle("Champion ! Le roi des Gorgotes est mort!")
                            .setPositiveButton("Recommencer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // L'utilisateur peut recommencer s'il le veut
                                    mEngine.reset();
                                    mEngine.resume();
                                    if (Menu.son) {
                                        mEngine.loose.pause();
                                        LabyrintheView.win.pause();
                                        mp1.start();
                                    }
                                }
                            })
                            .setNeutralButton("Suivant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    level += 1;
                                    next();
                                    mEngine.resume();
                                    if (Menu.son) {
                                        mEngine.loose.pause();
                                        LabyrintheView.win.pause();
                                        mp1.start();
                                    }
                                }
                            });
                }
                else{
                    builder.setCancelable(false)
                            .setMessage("Reste sur tes gardes Le roi des Gorgotes n'est pas mort!")
                            .setTitle("Bravo, tu as repoussé l'invasion \uD83D\uDCAA \uD83D\uDE42!")
                            .setPositiveButton("Recommencer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // L'utilisateur peut recommencer s'il le veut
                                    mEngine.reset();
                                    mEngine.resume();
                                    if (Menu.son) {
                                        mEngine.loose.pause();
                                        LabyrintheView.win.pause();
                                        mp1.start();
                                    }
                                }
                            })
                            .setNeutralButton("Suivant", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    level += 1;
                                    next();
                                    mEngine.resume();
                                    if (Menu.son) {
                                        mEngine.loose.pause();
                                        LabyrintheView.win.pause();
                                        mp1.start();
                                    }
                                }
                            });
                }
                break;

            case DEFEAT_DIALOG:
                builder.setCancelable(false)
                        .setMessage("La Terre a été détruite à cause de tes erreurs \uD83D\uDE21.")
                        .setTitle("Bah bravo !")
                        .setPositiveButton("Recommencer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mEngine.reset();
                                mEngine.resume();
                                if(Menu.son){
                                    mEngine.loose.pause();
                                    LabyrintheView.win.pause();
                                    mp1.start();
                                }
                            }
                        })
                        .setNeutralButton("Abandoner", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(c,Menu.class);
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        });
                break;
            case PAUSE_DIALOG:
                builder.setCancelable(false)
                        .setMessage("Dépêche toi la Terre va a être détruite \uD83D\uDE2B")
                        .setTitle("PAUSE")
                        .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mEngine.resume();
                                if(Menu.son){
                                    mp1.start();
                                }
                            }
                        })
                        .setNeutralButton("Abandoner", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(c,Menu.class);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                }
                        });
                break;
        }
        return builder.create();
    }

    @Override
    public void onPrepareDialog (int id, Dialog box) {
        // A chaque fois qu'une boîte de dialogue est lancée, on arrête le moteur physique
        mEngine.stop();
    }

    public void next() {
        List<Bloc> mList = mEngine.buildLabyrinthe(level);
        mView.setBlocks(mList);
    }
}
