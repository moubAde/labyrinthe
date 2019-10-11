package com.example.merle.labyrinthe;

/**
 * Created by merle on 07/03/2017.
 */

import java.util.ArrayList;
import java.util.List;

import com.example.merle.labyrinthe.Bloc.Type;

import android.app.Service;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;

public class LabyrintheEngine {
    private Boule mBoule = null;
    public Boule getBoule() {
        return mBoule;
    }
    public MediaPlayer loose;
    public void setBoule(Boule pBoule) {
        this.mBoule = pBoule;
    }

    // Le labyrinthe
    private List<Bloc> mBlocks = null;

    private LabyrintheActivity mActivity = null;

    private SensorManager mManager = null;
    private Sensor mAccelerometre = null;

    SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            float x = pEvent.values[0];
            float y = pEvent.values[1];
            if(mBoule != null) {
                // On met à jour les coordonnées de la boule
                RectF hitBox = mBoule.putXAndY(x, y);

                // Pour tous les blocs du labyrinthe
                for(Bloc block : mBlocks) {
                    // On crée un nouveau rectangle pour ne pas modifier celui du bloc
                    RectF inter = new RectF(block.getRectangle());
                    if(inter.intersect(hitBox)) {
                        // On agit différement en fonction du type de bloc
                        switch(block.getType()) {
                            case TROU:
                                mActivity.showDialog(LabyrintheActivity.DEFEAT_DIALOG);
                                if (Menu.son) {
                                    mActivity.mp1.pause();
                                    loose.start();
                                }
                                break;

                            case DEPART:
                                break;

                            case ARRIVEE:
                                mActivity.showDialog(LabyrintheActivity.VICTORY_DIALOG);
                                if (Menu.son) {
                                    mActivity.mp1.pause();
                                    LabyrintheView.win.start();
                                }
                                break;

                            case MUR:
                                    mBoule.changeXSpeed();
                                    mBoule.changeYSpeed();
                                break;
                        }
                        break;
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor pSensor, int pAccuracy) {

        }
    };

    public LabyrintheEngine(LabyrintheActivity pView) {
        mActivity = pView;
        mManager = (SensorManager) mActivity.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
        mAccelerometre = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        loose=  MediaPlayer.create(pView, R.raw.loose);
    }

    // Remet à zéro l'emplacement de la boule
    public void reset() {
        mBoule.reset();
    }

    // Arrête le capteur
    public void stop() {
        mManager.unregisterListener(mSensorEventListener, mAccelerometre);
    }

    // Redémarre le capteur
    public void resume() {
        mManager.registerListener(mSensorEventListener, mAccelerometre, SensorManager.SENSOR_DELAY_GAME);
    }

    public List<Bloc> buildLabyrinthe(int level) {
        mBlocks=null;
        switch (level) {
            case 0:
                return buildLabyrinthe0();
            case 1:
                return buildLabyrinthe1();
            case 2:
                return buildLabyrinthe2();
        }
        return null;
    }
    // Construit le labyrinthe
    public List<Bloc> buildLabyrinthe0() {
        mBlocks = new ArrayList<Bloc>();
        mBlocks.add(new Bloc(Type.TROU, 3, 5));
        Bloc b = new Bloc(Type.DEPART, 13, 17);
        mBoule.setInitialRectangle(new RectF(b.getRectangle()));
        mBlocks.add(b);

        mBlocks.add(new Bloc(Type.ARRIVEE, 18, 13));

        return mBlocks;
    }

    public List<Bloc> buildLabyrinthe1() {
        mBlocks = new ArrayList<Bloc>();
        mBlocks.add(new Bloc(Type.MUR, 0, 0));
        mBlocks.add(new Bloc(Type.MUR, 0, 1));
        mBlocks.add(new Bloc(Type.MUR, 0, 2));
        mBlocks.add(new Bloc(Type.MUR, 0, 3));
        mBlocks.add(new Bloc(Type.MUR, 0, 4));
        mBlocks.add(new Bloc(Type.MUR, 0, 5));
        mBlocks.add(new Bloc(Type.MUR, 0, 6));
        mBlocks.add(new Bloc(Type.MUR, 0, 7));
        mBlocks.add(new Bloc(Type.MUR, 0, 8));
        mBlocks.add(new Bloc(Type.MUR, 0, 9));
        mBlocks.add(new Bloc(Type.MUR, 0, 10));
        mBlocks.add(new Bloc(Type.MUR, 0, 11));
        mBlocks.add(new Bloc(Type.MUR, 0, 12));
        mBlocks.add(new Bloc(Type.MUR, 0, 13));
        mBlocks.add(new Bloc(Type.MUR, 0, 14));
        mBlocks.add(new Bloc(Type.MUR, 0, 15));
        mBlocks.add(new Bloc(Type.MUR, 0, 16));
        mBlocks.add(new Bloc(Type.MUR, 0, 17));
        mBlocks.add(new Bloc(Type.MUR, 0, 18));

        mBlocks.add(new Bloc(Type.MUR, 1, 0));
        mBlocks.add(new Bloc(Type.MUR, 1, 18));

        mBlocks.add(new Bloc(Type.TROU, 3, 5));
        mBlocks.add(new Bloc(Type.TROU, 1, 10));
        mBlocks.add(new Bloc(Type.TROU, 1, 16));

        mBlocks.add(new Bloc(Type.MUR, 2, 0));
        mBlocks.add(new Bloc(Type.MUR, 2, 18));

        mBlocks.add(new Bloc(Type.MUR, 3, 0));
        mBlocks.add(new Bloc(Type.MUR, 3, 18));

        mBlocks.add(new Bloc(Type.MUR, 4, 0));
        mBlocks.add(new Bloc(Type.MUR, 4, 1));
        mBlocks.add(new Bloc(Type.MUR, 4, 2));
        mBlocks.add(new Bloc(Type.MUR, 4, 3));
        mBlocks.add(new Bloc(Type.MUR, 4, 4));
        mBlocks.add(new Bloc(Type.MUR, 4, 5));
        mBlocks.add(new Bloc(Type.MUR, 4, 6));
        mBlocks.add(new Bloc(Type.MUR, 4, 7));
        mBlocks.add(new Bloc(Type.MUR, 4, 8));
        mBlocks.add(new Bloc(Type.MUR, 4, 9));
        mBlocks.add(new Bloc(Type.MUR, 4, 10));
        mBlocks.add(new Bloc(Type.MUR, 4, 11));
        mBlocks.add(new Bloc(Type.MUR, 4, 12));
        mBlocks.add(new Bloc(Type.MUR, 4, 13));
        mBlocks.add(new Bloc(Type.MUR, 4, 14));
        mBlocks.add(new Bloc(Type.MUR, 4, 18));

        mBlocks.add(new Bloc(Type.MUR, 5, 0));
        mBlocks.add(new Bloc(Type.MUR, 5, 18));

        mBlocks.add(new Bloc(Type.TROU, 5, 10));

        mBlocks.add(new Bloc(Type.MUR, 6, 0));
        mBlocks.add(new Bloc(Type.MUR, 6, 18));

        mBlocks.add(new Bloc(Type.MUR, 7, 0));
        mBlocks.add(new Bloc(Type.MUR, 7, 18));

        mBlocks.add(new Bloc(Type.MUR, 8, 0));
        mBlocks.add(new Bloc(Type.MUR, 8, 4));
        mBlocks.add(new Bloc(Type.MUR, 8, 5));
        mBlocks.add(new Bloc(Type.MUR, 8, 6));
        mBlocks.add(new Bloc(Type.MUR, 8, 7));
        mBlocks.add(new Bloc(Type.MUR, 8, 8));
        mBlocks.add(new Bloc(Type.MUR, 8, 9));
        mBlocks.add(new Bloc(Type.MUR, 8, 10));
        mBlocks.add(new Bloc(Type.MUR, 8, 11));
        mBlocks.add(new Bloc(Type.MUR, 8, 12));
        mBlocks.add(new Bloc(Type.MUR, 8, 13));
        mBlocks.add(new Bloc(Type.MUR, 8, 14));
        mBlocks.add(new Bloc(Type.MUR, 8, 15));
        mBlocks.add(new Bloc(Type.MUR, 8, 16));
        mBlocks.add(new Bloc(Type.MUR, 8, 17));
        mBlocks.add(new Bloc(Type.MUR, 8, 18));

        mBlocks.add(new Bloc(Type.MUR, 9, 0));
        mBlocks.add(new Bloc(Type.MUR, 9, 17));

        mBlocks.add(new Bloc(Type.MUR, 10, 0));
        mBlocks.add(new Bloc(Type.MUR, 10, 17));

        mBlocks.add(new Bloc(Type.MUR, 11, 0));
        mBlocks.add(new Bloc(Type.MUR, 11, 1));
        mBlocks.add(new Bloc(Type.MUR, 11, 2));
        mBlocks.add(new Bloc(Type.MUR, 11, 3));
        mBlocks.add(new Bloc(Type.MUR, 11, 4));
        mBlocks.add(new Bloc(Type.MUR, 11, 5));
        mBlocks.add(new Bloc(Type.MUR, 11, 6));
        mBlocks.add(new Bloc(Type.MUR, 11, 7));
        mBlocks.add(new Bloc(Type.MUR, 11, 8));
        mBlocks.add(new Bloc(Type.MUR, 11, 9));
        mBlocks.add(new Bloc(Type.MUR, 11, 12));
        mBlocks.add(new Bloc(Type.MUR, 11, 14));
        mBlocks.add(new Bloc(Type.MUR, 11, 17));

        mBlocks.add(new Bloc(Type.MUR, 12, 0));
        mBlocks.add(new Bloc(Type.MUR, 12, 9));
        mBlocks.add(new Bloc(Type.MUR, 12, 12));
        mBlocks.add(new Bloc(Type.MUR, 12, 14));
        mBlocks.add(new Bloc(Type.MUR, 12, 17));

        mBlocks.add(new Bloc(Type.MUR, 13, 0));
        mBlocks.add(new Bloc(Type.MUR, 13, 9));
        mBlocks.add(new Bloc(Type.MUR, 13, 12));
        mBlocks.add(new Bloc(Type.MUR, 13, 14));
        mBlocks.add(new Bloc(Type.MUR, 13, 17));

        mBlocks.add(new Bloc(Type.MUR, 14, 0));
        mBlocks.add(new Bloc(Type.MUR, 14, 5));
        mBlocks.add(new Bloc(Type.MUR, 14, 9));
        mBlocks.add(new Bloc(Type.MUR, 14, 12));
        mBlocks.add(new Bloc(Type.MUR, 14, 14));
        mBlocks.add(new Bloc(Type.MUR, 14, 17));

        mBlocks.add(new Bloc(Type.MUR, 15, 0));
        mBlocks.add(new Bloc(Type.MUR, 15, 5));
        mBlocks.add(new Bloc(Type.MUR, 15, 9));
        mBlocks.add(new Bloc(Type.MUR, 15, 12));
        mBlocks.add(new Bloc(Type.MUR, 15, 14));
        mBlocks.add(new Bloc(Type.MUR, 15, 17));

        mBlocks.add(new Bloc(Type.MUR, 16, 0));
        mBlocks.add(new Bloc(Type.MUR, 16, 5));
        mBlocks.add(new Bloc(Type.MUR, 16, 9));
        mBlocks.add(new Bloc(Type.MUR, 16, 12));
        mBlocks.add(new Bloc(Type.MUR, 16, 14));
        mBlocks.add(new Bloc(Type.MUR, 16, 17));

        mBlocks.add(new Bloc(Type.MUR, 17, 0));
        mBlocks.add(new Bloc(Type.MUR, 17, 5));
        mBlocks.add(new Bloc(Type.MUR, 17, 9));
        mBlocks.add(new Bloc(Type.MUR, 17, 12));
        mBlocks.add(new Bloc(Type.MUR, 17, 14));
        mBlocks.add(new Bloc(Type.MUR, 17, 17));

        mBlocks.add(new Bloc(Type.MUR, 18, 0));
        mBlocks.add(new Bloc(Type.MUR, 18, 5));
        mBlocks.add(new Bloc(Type.MUR, 18, 9));
        mBlocks.add(new Bloc(Type.MUR, 18, 12));
        mBlocks.add(new Bloc(Type.MUR, 18, 14));
        mBlocks.add(new Bloc(Type.MUR, 18, 17));

        mBlocks.add(new Bloc(Type.MUR, 19, 0));
        mBlocks.add(new Bloc(Type.MUR, 19, 5));
        mBlocks.add(new Bloc(Type.MUR, 19, 9));
        mBlocks.add(new Bloc(Type.MUR, 19, 12));
        mBlocks.add(new Bloc(Type.MUR, 19, 14));
        mBlocks.add(new Bloc(Type.MUR, 19, 17));

        mBlocks.add(new Bloc(Type.MUR, 20, 0));
        mBlocks.add(new Bloc(Type.MUR, 20, 5));
        mBlocks.add(new Bloc(Type.MUR, 20, 9));
        mBlocks.add(new Bloc(Type.MUR, 20, 12));
        mBlocks.add(new Bloc(Type.MUR, 20, 14));
        mBlocks.add(new Bloc(Type.MUR, 20, 17));

        mBlocks.add(new Bloc(Type.MUR, 21, 0));
        mBlocks.add(new Bloc(Type.MUR, 21, 5));
        mBlocks.add(new Bloc(Type.MUR, 21, 12));
        mBlocks.add(new Bloc(Type.MUR, 21, 14));
        mBlocks.add(new Bloc(Type.MUR, 21, 17));

        mBlocks.add(new Bloc(Type.MUR, 22, 0));
        mBlocks.add(new Bloc(Type.MUR, 22, 5));
        mBlocks.add(new Bloc(Type.MUR, 22, 12));
        mBlocks.add(new Bloc(Type.MUR, 22, 14));
        mBlocks.add(new Bloc(Type.MUR, 22, 17));

        mBlocks.add(new Bloc(Type.MUR, 23, 0));
        mBlocks.add(new Bloc(Type.MUR, 23, 5));
        mBlocks.add(new Bloc(Type.MUR, 23, 14));
        mBlocks.add(new Bloc(Type.MUR, 23, 12));
        mBlocks.add(new Bloc(Type.MUR, 23, 17));

        mBlocks.add(new Bloc(Type.MUR, 24, 0));
        mBlocks.add(new Bloc(Type.MUR, 24, 5));
        mBlocks.add(new Bloc(Type.MUR, 24, 12));
        mBlocks.add(new Bloc(Type.MUR, 24, 14));
        mBlocks.add(new Bloc(Type.MUR, 24, 17));

        mBlocks.add(new Bloc(Type.MUR, 25, 0));
        mBlocks.add(new Bloc(Type.MUR, 25, 5));
        mBlocks.add(new Bloc(Type.MUR, 25, 6));
        mBlocks.add(new Bloc(Type.MUR, 25, 7));
        mBlocks.add(new Bloc(Type.MUR, 25, 8));
        mBlocks.add(new Bloc(Type.MUR, 25, 9));
        mBlocks.add(new Bloc(Type.MUR, 25, 10));
        mBlocks.add(new Bloc(Type.MUR, 25, 11));
        mBlocks.add(new Bloc(Type.MUR, 25, 12));
        mBlocks.add(new Bloc(Type.MUR, 25, 14));
        mBlocks.add(new Bloc(Type.MUR, 25, 17));

        mBlocks.add(new Bloc(Type.MUR, 26, 0));
        mBlocks.add(new Bloc(Type.MUR, 26, 12));
        mBlocks.add(new Bloc(Type.MUR, 26, 14));
        mBlocks.add(new Bloc(Type.MUR, 26, 17));

        mBlocks.add(new Bloc(Type.MUR, 27, 0));
        mBlocks.add(new Bloc(Type.MUR, 27, 12));
        mBlocks.add(new Bloc(Type.MUR, 27, 17));

        mBlocks.add(new Bloc(Type.MUR, 28, 0));
        mBlocks.add(new Bloc(Type.MUR, 28, 12));
        mBlocks.add(new Bloc(Type.MUR, 28, 17));

        mBlocks.add(new Bloc(Type.MUR, 29, 0));
        mBlocks.add(new Bloc(Type.MUR, 29, 12));
        mBlocks.add(new Bloc(Type.MUR, 29, 17));

        mBlocks.add(new Bloc(Type.MUR, 30, 0));
        mBlocks.add(new Bloc(Type.MUR, 30, 12));
        mBlocks.add(new Bloc(Type.MUR, 30, 17));

        mBlocks.add(new Bloc(Type.MUR, 31, 0));
        mBlocks.add(new Bloc(Type.MUR, 31, 12));
        mBlocks.add(new Bloc(Type.MUR, 31, 1));
        mBlocks.add(new Bloc(Type.MUR, 31, 2));
        mBlocks.add(new Bloc(Type.MUR, 31, 3));
        mBlocks.add(new Bloc(Type.MUR, 31, 4));
        mBlocks.add(new Bloc(Type.MUR, 31, 5));
        mBlocks.add(new Bloc(Type.MUR, 31, 6));
        mBlocks.add(new Bloc(Type.MUR, 31, 7));
        mBlocks.add(new Bloc(Type.MUR, 31, 8));
        mBlocks.add(new Bloc(Type.MUR, 31, 9));
        mBlocks.add(new Bloc(Type.MUR, 31, 10));
        mBlocks.add(new Bloc(Type.MUR, 31, 11));
        mBlocks.add(new Bloc(Type.MUR, 31, 12));
        mBlocks.add(new Bloc(Type.MUR, 31, 13));
        mBlocks.add(new Bloc(Type.MUR, 31, 14));
        mBlocks.add(new Bloc(Type.MUR, 31, 15));
        mBlocks.add(new Bloc(Type.MUR, 31, 16));
        mBlocks.add(new Bloc(Type.MUR, 31, 17));

        Bloc b = new Bloc(Type.DEPART, 2, 2);
        mBoule.setInitialRectangle(new RectF(b.getRectangle()));
        mBlocks.add(b);

        mBlocks.add(new Bloc(Type.ARRIVEE, 18, 8));

        return mBlocks;
    }

    public List<Bloc> buildLabyrinthe2() {
        mBlocks = new ArrayList<Bloc>();
        mBlocks.add(new Bloc(Type.MUR, 0, 0));
        mBlocks.add(new Bloc(Type.MUR, 0, 1));
        mBlocks.add(new Bloc(Type.MUR, 0, 2));
        mBlocks.add(new Bloc(Type.MUR, 0, 3));
        mBlocks.add(new Bloc(Type.MUR, 0, 4));
        mBlocks.add(new Bloc(Type.MUR, 0, 5));
        mBlocks.add(new Bloc(Type.MUR, 0, 6));
        mBlocks.add(new Bloc(Type.MUR, 0, 7));
        mBlocks.add(new Bloc(Type.MUR, 0, 8));
        mBlocks.add(new Bloc(Type.MUR, 0, 9));
        mBlocks.add(new Bloc(Type.MUR, 0, 10));
        mBlocks.add(new Bloc(Type.MUR, 0, 11));
        mBlocks.add(new Bloc(Type.MUR, 0, 12));
        mBlocks.add(new Bloc(Type.MUR, 0, 13));
        mBlocks.add(new Bloc(Type.MUR, 0, 14));
        mBlocks.add(new Bloc(Type.MUR, 0, 15));
        mBlocks.add(new Bloc(Type.MUR, 0, 16));
        mBlocks.add(new Bloc(Type.MUR, 0, 17));
        mBlocks.add(new Bloc(Type.MUR, 0, 18));

        mBlocks.add(new Bloc(Type.MUR, 1, 0));
        mBlocks.add(new Bloc(Type.MUR, 1, 8));
        mBlocks.add(new Bloc(Type.MUR, 1, 18));

        mBlocks.add(new Bloc(Type.TROU, 3, 5));
        mBlocks.add(new Bloc(Type.TROU, 1, 10));
        mBlocks.add(new Bloc(Type.TROU, 5, 10));

        mBlocks.add(new Bloc(Type.MUR, 2, 0));
        mBlocks.add(new Bloc(Type.MUR, 2, 8));
        mBlocks.add(new Bloc(Type.MUR, 2, 10));
        mBlocks.add(new Bloc(Type.MUR, 2, 18));

        mBlocks.add(new Bloc(Type.MUR, 3, 0));
        mBlocks.add(new Bloc(Type.MUR, 3, 8));
        mBlocks.add(new Bloc(Type.MUR, 3, 10));
        mBlocks.add(new Bloc(Type.MUR, 3, 13));
        mBlocks.add(new Bloc(Type.MUR, 3, 14));
        mBlocks.add(new Bloc(Type.MUR, 3, 15));
        mBlocks.add(new Bloc(Type.MUR, 3, 18));

        mBlocks.add(new Bloc(Type.MUR, 4, 0));
        //mBlocks.add(new Bloc(Type.MUR, 4, 8));
        mBlocks.add(new Bloc(Type.MUR, 4, 10));
        mBlocks.add(new Bloc(Type.MUR, 4, 15));
        mBlocks.add(new Bloc(Type.MUR, 4, 18));

        mBlocks.add(new Bloc(Type.MUR, 5, 0));
        mBlocks.add(new Bloc(Type.MUR, 5, 15));
        mBlocks.add(new Bloc(Type.MUR, 5, 18));

        mBlocks.add(new Bloc(Type.MUR, 6, 0));
        mBlocks.add(new Bloc(Type.MUR, 6, 8));
        mBlocks.add(new Bloc(Type.MUR, 6, 15));
        mBlocks.add(new Bloc(Type.MUR, 6, 18));

        mBlocks.add(new Bloc(Type.MUR, 7, 0));
        mBlocks.add(new Bloc(Type.MUR, 7, 8));
        mBlocks.add(new Bloc(Type.MUR, 7, 15));
        mBlocks.add(new Bloc(Type.MUR, 7, 18));

        mBlocks.add(new Bloc(Type.MUR, 8, 0));
        mBlocks.add(new Bloc(Type.MUR, 8, 1));
        mBlocks.add(new Bloc(Type.MUR, 8, 2));
        mBlocks.add(new Bloc(Type.MUR, 8, 3));
        mBlocks.add(new Bloc(Type.MUR, 8, 7));
        mBlocks.add(new Bloc(Type.MUR, 8, 8));
        mBlocks.add(new Bloc(Type.MUR, 8, 9));
        mBlocks.add(new Bloc(Type.MUR, 8, 10));
        mBlocks.add(new Bloc(Type.MUR, 8, 11));
        mBlocks.add(new Bloc(Type.MUR, 8, 12));
        mBlocks.add(new Bloc(Type.MUR, 8, 13));
        mBlocks.add(new Bloc(Type.MUR, 8, 14));
        mBlocks.add(new Bloc(Type.MUR, 8, 15));
        mBlocks.add(new Bloc(Type.MUR, 8, 18));

        mBlocks.add(new Bloc(Type.MUR, 9, 0));
        mBlocks.add(new Bloc(Type.MUR, 9, 15));
        mBlocks.add(new Bloc(Type.MUR, 9, 18));

        mBlocks.add(new Bloc(Type.TROU, 9, 3));
        mBlocks.add(new Bloc(Type.TROU, 9, 8));

        mBlocks.add(new Bloc(Type.MUR, 10, 0));
        mBlocks.add(new Bloc(Type.MUR, 10, 15));
        mBlocks.add(new Bloc(Type.MUR, 10, 18));

        mBlocks.add(new Bloc(Type.TROU, 11, 5));
        mBlocks.add(new Bloc(Type.TROU, 11, 9));

        mBlocks.add(new Bloc(Type.MUR, 11, 0));
        mBlocks.add(new Bloc(Type.MUR, 11, 15));
        mBlocks.add(new Bloc(Type.MUR, 11, 18));

        mBlocks.add(new Bloc(Type.MUR, 12, 0));
        mBlocks.add(new Bloc(Type.MUR, 12, 15));
        mBlocks.add(new Bloc(Type.MUR, 12, 18));

        mBlocks.add(new Bloc(Type.MUR, 13, 0));
        mBlocks.add(new Bloc(Type.MUR, 13, 15));
        mBlocks.add(new Bloc(Type.MUR, 13, 18));

        mBlocks.add(new Bloc(Type.TROU, 13, 6));

        mBlocks.add(new Bloc(Type.MUR, 14, 0));
        mBlocks.add(new Bloc(Type.MUR, 14, 1));
        mBlocks.add(new Bloc(Type.MUR, 14, 2));
        mBlocks.add(new Bloc(Type.MUR, 14, 3));
        mBlocks.add(new Bloc(Type.MUR, 14, 4));
        mBlocks.add(new Bloc(Type.MUR, 14, 5));
        mBlocks.add(new Bloc(Type.MUR, 14, 6));
        //mBlocks.add(new Bloc(Type.MUR, 14, 7));
        //mBlocks.add(new Bloc(Type.MUR, 14, 8));
        mBlocks.add(new Bloc(Type.MUR, 14, 9));
        //mBlocks.add(new Bloc(Type.MUR, 14, 10));
        mBlocks.add(new Bloc(Type.MUR, 14, 10));
        mBlocks.add(new Bloc(Type.MUR, 14, 11));
        mBlocks.add(new Bloc(Type.MUR, 14, 12));
        mBlocks.add(new Bloc(Type.MUR, 14, 13));
        mBlocks.add(new Bloc(Type.MUR, 14, 14));
        mBlocks.add(new Bloc(Type.MUR, 14, 15));
        mBlocks.add(new Bloc(Type.MUR, 14, 16));
        mBlocks.add(new Bloc(Type.MUR, 14, 17));
        mBlocks.add(new Bloc(Type.MUR, 14, 18));

        mBlocks.add(new Bloc(Type.MUR, 15, 0));
        mBlocks.add(new Bloc(Type.MUR, 15, 9));
        mBlocks.add(new Bloc(Type.MUR, 15, 18));

        mBlocks.add(new Bloc(Type.TROU, 15, 1));
        mBlocks.add(new Bloc(Type.TROU, 15, 11));

        mBlocks.add(new Bloc(Type.MUR, 16, 0));
       // mBlocks.add(new Bloc(Type.MUR, 16, 6));
        //mBlocks.add(new Bloc(Type.MUR, 16, 7));
        //mBlocks.add(new Bloc(Type.MUR, 16, 8));
        mBlocks.add(new Bloc(Type.MUR, 16, 9));
        //mBlocks.add(new Bloc(Type.MUR, 16, 12));
       // mBlocks.add(new Bloc(Type.MUR, 16, 14));
        mBlocks.add(new Bloc(Type.MUR, 16, 18));

        mBlocks.add(new Bloc(Type.MUR, 17, 0));
        mBlocks.add(new Bloc(Type.MUR, 17, 5));
        mBlocks.add(new Bloc(Type.MUR, 17, 6));
        mBlocks.add(new Bloc(Type.MUR, 17, 7));
        mBlocks.add(new Bloc(Type.MUR, 17, 8));
        mBlocks.add(new Bloc(Type.MUR, 17, 9));
        mBlocks.add(new Bloc(Type.MUR, 17, 12));
        mBlocks.add(new Bloc(Type.MUR, 17, 13));
        mBlocks.add(new Bloc(Type.MUR, 17, 14));
        mBlocks.add(new Bloc(Type.MUR, 17, 15));
        mBlocks.add(new Bloc(Type.MUR, 17, 18));

        mBlocks.add(new Bloc(Type.TROU, 17, 1));
        mBlocks.add(new Bloc(Type.TROU, 17, 4));

        mBlocks.add(new Bloc(Type.MUR, 18, 0));
        mBlocks.add(new Bloc(Type.MUR, 18, 5));
        mBlocks.add(new Bloc(Type.MUR, 18, 9));
        mBlocks.add(new Bloc(Type.MUR, 18, 12));
        //mBlocks.add(new Bloc(Type.MUR, 18, 14));
        mBlocks.add(new Bloc(Type.MUR, 18, 15));
        mBlocks.add(new Bloc(Type.MUR, 18, 18));

        mBlocks.add(new Bloc(Type.MUR, 19, 0));
        mBlocks.add(new Bloc(Type.MUR, 19, 5));
        mBlocks.add(new Bloc(Type.MUR, 19, 9));
        mBlocks.add(new Bloc(Type.MUR, 19, 12));
        //mBlocks.add(new Bloc(Type.MUR, 19, 14));
        mBlocks.add(new Bloc(Type.MUR, 19, 18));

        mBlocks.add(new Bloc(Type.MUR, 20, 0));
        mBlocks.add(new Bloc(Type.MUR, 20, 5));
        mBlocks.add(new Bloc(Type.MUR, 20, 9));
        mBlocks.add(new Bloc(Type.MUR, 20, 12));
        //mBlocks.add(new Bloc(Type.MUR, 20, 14));
        mBlocks.add(new Bloc(Type.MUR, 20, 18));

        mBlocks.add(new Bloc(Type.MUR, 21, 0));
        mBlocks.add(new Bloc(Type.MUR, 21, 5));
        mBlocks.add(new Bloc(Type.MUR, 21, 12));
        //mBlocks.add(new Bloc(Type.MUR, 21, 15));
        mBlocks.add(new Bloc(Type.MUR, 21, 18));

        mBlocks.add(new Bloc(Type.TROU, 21, 9));
        mBlocks.add(new Bloc(Type.TROU, 21, 15));

        mBlocks.add(new Bloc(Type.MUR, 22, 0));
        mBlocks.add(new Bloc(Type.MUR, 22, 5));
        mBlocks.add(new Bloc(Type.MUR, 22, 12));
        mBlocks.add(new Bloc(Type.MUR, 22, 15));
        mBlocks.add(new Bloc(Type.MUR, 22, 18));

        mBlocks.add(new Bloc(Type.MUR, 23, 0));
        //mBlocks.add(new Bloc(Type.MUR, 23, 5));
        mBlocks.add(new Bloc(Type.MUR, 23, 15));
        mBlocks.add(new Bloc(Type.MUR, 23, 12));
        mBlocks.add(new Bloc(Type.MUR, 23, 18));

        mBlocks.add(new Bloc(Type.MUR, 24, 0));
        //mBlocks.add(new Bloc(Type.MUR, 24, 5));
        mBlocks.add(new Bloc(Type.MUR, 24, 12));
        mBlocks.add(new Bloc(Type.MUR, 24, 15));
        mBlocks.add(new Bloc(Type.MUR, 24, 18));

        mBlocks.add(new Bloc(Type.TROU, 24, 11));

        mBlocks.add(new Bloc(Type.MUR, 25, 0));
        mBlocks.add(new Bloc(Type.MUR, 25, 1));
        mBlocks.add(new Bloc(Type.MUR, 25, 2));
        mBlocks.add(new Bloc(Type.MUR, 25, 3));
        mBlocks.add(new Bloc(Type.MUR, 25, 4));
        mBlocks.add(new Bloc(Type.MUR, 25, 5));
        mBlocks.add(new Bloc(Type.MUR, 25, 6));
        mBlocks.add(new Bloc(Type.MUR, 25, 7));
        mBlocks.add(new Bloc(Type.MUR, 25, 8));
        mBlocks.add(new Bloc(Type.MUR, 25, 9));
        mBlocks.add(new Bloc(Type.MUR, 25, 10));
        mBlocks.add(new Bloc(Type.MUR, 25, 11));
        mBlocks.add(new Bloc(Type.MUR, 25, 12));
        mBlocks.add(new Bloc(Type.MUR, 25, 13));
        mBlocks.add(new Bloc(Type.MUR, 25, 14));
        mBlocks.add(new Bloc(Type.MUR, 25, 15));
        mBlocks.add(new Bloc(Type.MUR, 25, 16));
        mBlocks.add(new Bloc(Type.MUR, 25, 17));
        mBlocks.add(new Bloc(Type.MUR, 25, 18));

        mBlocks.add(new Bloc(Type.MUR, 26, 0));
        mBlocks.add(new Bloc(Type.MUR, 26, 12));
        mBlocks.add(new Bloc(Type.MUR, 26, 14));
        mBlocks.add(new Bloc(Type.MUR, 26, 18));

        mBlocks.add(new Bloc(Type.MUR, 27, 0));
        mBlocks.add(new Bloc(Type.MUR, 27, 12));
        mBlocks.add(new Bloc(Type.MUR, 27, 18));

        mBlocks.add(new Bloc(Type.MUR, 28, 0));
        mBlocks.add(new Bloc(Type.MUR, 28, 12));
        mBlocks.add(new Bloc(Type.MUR, 28, 18));

        mBlocks.add(new Bloc(Type.MUR, 29, 0));
        mBlocks.add(new Bloc(Type.MUR, 29, 12));
        mBlocks.add(new Bloc(Type.MUR, 29, 18));

        mBlocks.add(new Bloc(Type.MUR, 30, 0));
        mBlocks.add(new Bloc(Type.MUR, 30, 12));
        mBlocks.add(new Bloc(Type.MUR, 30, 18));

        mBlocks.add(new Bloc(Type.TROU, 30, 11));

        mBlocks.add(new Bloc(Type.MUR, 31, 0));
       // mBlocks.add(new Bloc(Type.MUR, 31, 12));
        mBlocks.add(new Bloc(Type.MUR, 31, 1));
        mBlocks.add(new Bloc(Type.MUR, 31, 2));
        mBlocks.add(new Bloc(Type.MUR, 31, 3));
        mBlocks.add(new Bloc(Type.MUR, 31, 4));
        mBlocks.add(new Bloc(Type.MUR, 31, 5));
        mBlocks.add(new Bloc(Type.MUR, 31, 6));
        mBlocks.add(new Bloc(Type.MUR, 31, 7));
        mBlocks.add(new Bloc(Type.MUR, 31, 8));
        mBlocks.add(new Bloc(Type.MUR, 31, 9));
        mBlocks.add(new Bloc(Type.MUR, 31, 10));
        mBlocks.add(new Bloc(Type.MUR, 31, 11));
        mBlocks.add(new Bloc(Type.MUR, 31, 12));
        mBlocks.add(new Bloc(Type.MUR, 31, 13));
        mBlocks.add(new Bloc(Type.MUR, 31, 14));
        mBlocks.add(new Bloc(Type.MUR, 31, 15));
        mBlocks.add(new Bloc(Type.MUR, 31, 16));
        mBlocks.add(new Bloc(Type.MUR, 31, 17));
        mBlocks.add(new Bloc(Type.MUR, 31, 18));

        Bloc b = new Bloc(Type.DEPART, 13, 17);
        mBoule.setInitialRectangle(new RectF(b.getRectangle()));
        mBlocks.add(b);

        mBlocks.add(new Bloc(Type.ARRIVEE, 18, 13));

        return mBlocks;
    }

}
