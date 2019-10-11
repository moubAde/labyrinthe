package com.example.merle.labyrinthe;

/**
 * Created by merle on 07/03/2017.
 */

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class LabyrintheView extends SurfaceView implements SurfaceHolder.Callback {
    LabyrintheActivity mActivity;
    public static MediaPlayer win;
    Boule mBoule;
    public Boule getBoule() {
        return mBoule;
    }

    public void setBoule(Boule pBoule) {
        this.mBoule = pBoule;
    }

    SurfaceHolder mSurfaceHolder;
    DrawingThread mThread;

    private List<Bloc> mBlocks = null;
    public List<Bloc> getBlocks() {
        return mBlocks;
    }

    public void setBlocks(List<Bloc> pBlocks) {
        this.mBlocks = pBlocks;
    }

    Paint mPaint;

    public LabyrintheView(Context pContext,LabyrintheActivity p) {
        super(pContext);
        mActivity=p;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mThread = new DrawingThread();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mBoule = new Boule();
        win=  MediaPlayer.create(mActivity, R.raw.win);
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        // Dessiner le fond de l'écran en premier
        //Resources res=getResources();
        //Bitmap bitmap= BitmapFactory.decodeResource(res,R.drawable.bois);
        //pCanvas= new Canvas(bitmap.copy(Bitmap.Config.ARGB_8888,true));
        pCanvas.drawColor(Color.CYAN);
       // Drawable d =getResources().getDrawable(R.drawable.bois);
        //d.setBounds(-1,1,1,-1);
        //d.draw(pCanvas);
        //this.setBackgroundResource(bois);
        /*Drawable sky = (getResources().getDrawable(R.drawable.bois));
        this.setBackgroundDrawable(sky);*/
        if(mBlocks != null) {
            // Dessiner tous les blocs du labyrinthe
            for(Bloc b : mBlocks) {
                switch(b.getType()) {
                    case DEPART:
                        mPaint.setColor(Color.WHITE);
                        break;
                    case ARRIVEE:
                        mPaint.setColor(Color.RED);
                        break;
                    case TROU:
                        mPaint.setColor(Color.BLACK);
                        break;
                    case MUR:
                        mPaint.setColor(Color.GRAY);
                        break;
                }
                pCanvas.drawRect(b.getRectangle(), mPaint);
            }
        }

        // Dessiner la boule
        if(mBoule != null) {
            mPaint.setColor(mBoule.getCouleur());
            pCanvas.drawCircle(mBoule.getX(), mBoule.getY(), Boule.RAYON, mPaint);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder pHolder, int pFormat, int pWidth, int pHeight) {
        //
    }

    @Override
    public void surfaceCreated(SurfaceHolder pHolder) {
        mThread.keepDrawing = true;
        mThread.start();
        // Quand on crée la boule, on lui indique les coordonnées de l'écran
        if(mBoule != null ) {
            this.mBoule.setHeight(getHeight());
            this.mBoule.setWidth(getWidth());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder pHolder) {
        mThread.keepDrawing = false;
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }

    }

    private class DrawingThread extends Thread {
        boolean keepDrawing = true;

        @Override
        public void run() {
            Canvas canvas;
            while (keepDrawing) {
                canvas = null;

                try {
                    canvas = mSurfaceHolder.lockCanvas();
                    synchronized (mSurfaceHolder) {
                        onDraw(canvas);
                    }
                } finally {
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }

                // Pour dessiner à 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {}
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        System.out.println("marche");
        mActivity.showDialog(LabyrintheActivity.PAUSE_DIALOG);
        if(Menu.son){
            mActivity.mp1.pause();
        }
        invalidate();// permet une reactualisation de la vue
        return true;
    }
}
