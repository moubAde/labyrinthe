package com.example.merle.labyrinthe;

/**
 * Created by merle on 07/03/2017.
 */

import android.graphics.RectF;
import com.example.merle.labyrinthe.Boule;

public class Bloc {
    enum  Type { TROU, DEPART, ARRIVEE, MUR };

    private float SIZE = Boule.RAYON * 2;

    private Type mType = null;
    private RectF mRectangle = null;

    public Type getType() {
        return mType;
    }

    public RectF getRectangle() {
        return mRectangle;
    }

    public Bloc(Type pType, int pX, int pY) {
        this.mType = pType;
        this.mRectangle = new RectF(pX * SIZE, pY * SIZE, (pX + 1) * SIZE, (pY + 1) * SIZE);
    }
}

