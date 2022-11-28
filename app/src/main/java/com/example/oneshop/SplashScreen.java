package com.example.oneshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {


    TextView textViewtitle;
    ImageView imageView;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = findViewById(R.id.SplashImg);
        textViewtitle = findViewById(R.id.SplashText);


//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//        );

        Animation animationUtils = AnimationUtils.loadAnimation(this,R.anim.side_slide);
        imageView.startAnimation(animationUtils);
        textViewtitle.startAnimation(animationUtils);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this,loginActivity.class);
                startActivity(intent);
                finish();

            }
        }, 5000);

    }
}