package com.example.javaileoyundeneme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPuan;
    TextView tvRekor;
    SharedPreferences sharedPreferences;
    ImageView ivYeniRekor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvPuan = findViewById(R.id.tvPuan);
        tvRekor = findViewById(R.id.tvRekor);
        ivYeniRekor = findViewById(R.id.ivYeniRekor);
        int puan = getIntent().getExtras().getInt("puan");
        tvPuan.setText("" + puan);
        sharedPreferences = getSharedPreferences("tercihim", 0);
        int rekor = sharedPreferences.getInt("rekor",0);
        if(puan>rekor){
            ivYeniRekor.setVisibility(View.VISIBLE);
            rekor = puan;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("rekor", rekor);
            editor.commit();
        }
        tvRekor.setText("" + rekor);
    }
    public void tekrar(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void exit(View view){
        finish();
    }
}
