package com.example.javaileoyundeneme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Patlama {
    Bitmap patlama[] = new Bitmap[4];
    int patlamaFrame = 0;
    int patlamaX, patlamaY;

    public Patlama(Context context){
        patlama[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.patlama);
        patlama[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.patlamasag);
        patlama[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.patlamasol);
        patlama[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.patlama);

    }

    public Bitmap getPatlama(int patlamaFrame){
        return patlama[patlamaFrame];
    }

}
