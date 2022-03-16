package com.hiaryabeer.transferapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hiaryabeer.transferapp.R;

public class SplashActivity extends AppCompatActivity {

//    private Handler handler;

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), Login.class));
            overridePendingTransition(R.anim.sd_scale_fade_and_translate_in, R.anim.sd_scale_fade_and_translate_out);
            finish();

        });

//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, Login.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 3000);

    }
}