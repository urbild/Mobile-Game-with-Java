package com.example.javaileoyundeneme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    Bitmap arkaplan, yer, ugur;
    Rect rectArkaplan, rectYer;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int puan = 0;
    int can = 3;
    static int dWidth, dHeigth;
    Random random;
    float ugurX, ugurY;
    float oldX;
    float oldUgurX;
    ArrayList<Otrivin> otrivins;
    ArrayList<Patlama> patlamas;

    public GameView(Context context){
        super(context);
        this.context = context;
        arkaplan = BitmapFactory.decodeResource(getResources(),R.drawable.arkaplan);
        yer = BitmapFactory.decodeResource(getResources(),R.drawable.yer);
        ugur = BitmapFactory.decodeResource(getResources(),R.drawable.halil);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeigth = size.y;
        rectArkaplan = new Rect(0,0,dWidth,dHeigth);
        rectYer = new Rect(0,dHeigth - yer.getHeight(),dWidth,dHeigth);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        textPaint.setColor(Color.rgb(255,165,0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.cyrene));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        ugurX = dWidth / 2 - ugur.getWidth() / 2 ;
        ugurY = dHeigth - yer.getHeight() - ugur.getHeight();
        otrivins = new ArrayList<>();
        patlamas = new ArrayList<>();
        for(int i = 0;i<3;i++){
            Otrivin otrivin = new Otrivin(context);
            otrivins.add(otrivin);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(arkaplan,null,rectArkaplan,null);
        canvas.drawBitmap(yer,null,rectYer,null);
        canvas.drawBitmap(ugur,ugurX,ugurY,null);
        for(int i = 0;i<otrivins.size();i++){
            canvas.drawBitmap(otrivins.get(i).getOtrivin(otrivins.get(i).otrivinFrame), otrivins.get(i).otrivinX, otrivins.get(i).otrivinY,null);
            otrivins.get(i).otrivinFrame++;
            if(otrivins.get(i).otrivinFrame > 2){
                otrivins.get(i).otrivinFrame = 0;
            }
            otrivins.get(i).otrivinY += otrivins.get(i).otrivinHiz;
            if(otrivins.get(i).otrivinY + otrivins.get(i).getOtrivinHeight() >= dHeigth -  yer.getHeight()){
                puan += 10;
                Patlama patlama = new Patlama(context);
                patlama.patlamaX = otrivins.get(i).otrivinX;
                patlama.patlamaY = otrivins.get(i).otrivinY;
                patlamas.add(patlama);
                otrivins.get(i).resetPosition();
            }
        }
        for(int i=0;i<otrivins.size();i++){
            if(otrivins.get(i).otrivinX + otrivins.get(i).getOtrivinWidth() >= ugurX
                && otrivins.get(i).otrivinX <= ugurX + ugur.getWidth()
                && otrivins.get(i).otrivinY + otrivins.get(i).getOtrivinWidth() >= ugurY
                && otrivins.get(i).otrivinY + otrivins.get(i).getOtrivinWidth() <= ugurY + ugur.getHeight()){
                    can--;
                    otrivins.get(i).resetPosition();
                    if(can==0){
                        Intent intent = new Intent(context,GameOver.class);
                        intent.putExtra("puan",puan);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
            }
        }

        for(int i=0;i<patlamas.size();i++){
            canvas.drawBitmap(patlamas.get(i).getPatlama(patlamas.get(i).patlamaFrame), patlamas.get(i).patlamaX,
                    patlamas.get(i).patlamaY,null);
            patlamas.get(i).patlamaFrame++;
            if(patlamas.get(i).patlamaFrame>3){
                patlamas.remove(i);
            }
        }

        if (can==2){
            healthPaint.setColor(Color.YELLOW);
        }else if(can==1){
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth-200,30,dWidth-200+60*can,80,healthPaint);
        canvas.drawText(""+ puan, 20, TEXT_SIZE, textPaint);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if(touchY>=ugurY){
            int action = event.getAction();
            if(action==MotionEvent.ACTION_DOWN){
                oldX=event.getX();
                oldUgurX=ugurX;
            }
            if(action==MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                float newUgurX = oldUgurX - shift;
                if(newUgurX <= 0)
                    ugurX = 0;
                else if(newUgurX >= dWidth - ugur.getWidth())
                    ugurX = dWidth-ugur.getWidth();
                else
                    ugurX=newUgurX;
            }
        }
        return true;
    }
}
