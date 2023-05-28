package com.example.javaileoyundeneme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Otrivin {
    Bitmap otrivin[] = new Bitmap[3];
    int otrivinFrame = 0;
    int otrivinX, otrivinY, otrivinHiz;
    Random random;

    public Otrivin(Context context){
        otrivin[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.kayisi);
        otrivin[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.kayisisag);
        otrivin[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.kayisisol);
        random = new Random();
        resetPosition();
    }

    public Bitmap getOtrivin(int otrivinFrame){
        return otrivin[otrivinFrame];
    }

    public int getOtrivinWidth(){
        return otrivin[0].getWidth();
    }

    public int getOtrivinHeight(){
        return otrivin[0].getHeight();
    }

    void resetPosition() {
        otrivinX = random.nextInt(GameView.dWidth - getOtrivinWidth());
        otrivinY = -200 + random.nextInt(600) * -1;
        otrivinHiz = 35 + random.nextInt(16);
    }

}
