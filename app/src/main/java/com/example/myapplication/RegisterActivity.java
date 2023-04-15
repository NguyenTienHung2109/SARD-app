package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    TextView suggestLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        suggestLoginBtn = findViewById(R.id.suggestLoginBtn);

        suggestLoginBtn.setOnClickListener(view ->
                startActivity(new Intent(RegisterActivity.this, MainActivity.class))
        );
    }
}
