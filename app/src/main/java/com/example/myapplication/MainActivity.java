package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView suggestSignUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //sẽ ẩn tiêu đề
        getSupportActionBar().hide(); // ẩn thanh tiêu đề

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //bật chế độ toàn màn hình

        startActivity(new Intent(MainActivity.this, LoginActivity.class));

//        setContentView(R.layout.activity_login);
//
//        suggestSignUpBtn = findViewById(R.id.suggestRegisterBtn);
//
//        suggestSignUpBtn.setOnClickListener(view ->
//                startActivity(new Intent(MainActivity.this, RegisterActivity.class))
//        );

    }
}