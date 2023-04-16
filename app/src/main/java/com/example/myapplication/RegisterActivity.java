package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    TextView suggestLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //sẽ ẩn tiêu đề
        getSupportActionBar().hide(); // ẩn thanh tiêu đề

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //bật chế độ toàn màn hình
        setContentView(R.layout.activity_register);

        suggestLoginBtn = findViewById(R.id.suggestLoginBtn);

        suggestLoginBtn.setOnClickListener(view ->
                startActivity(new Intent(RegisterActivity.this, MainActivity.class))
        );
    }
}
