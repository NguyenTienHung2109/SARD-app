package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    EditText emailInput;
    EditText passwordInput;
    Button loginBtn;
    TextView suggestSignUpBtn, forgotPasswordBtn;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //sẽ ẩn tiêu đề
        getSupportActionBar().hide(); // ẩn thanh tiêu đề

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //bật chế độ toàn màn hình

        setContentView(R.layout.activity_login);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

//        if(fUser != null)
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        emailInput = findViewById(R.id.loginEmailText);
        passwordInput = findViewById(R.id.loginPasswordText);
        loginBtn = findViewById(R.id.loginBtn);
        suggestSignUpBtn = findViewById(R.id.suggestRegisterBtn);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordBtn);

        suggestSignUpBtn.setOnClickListener(view ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

//        forgotPasswordBtn.setOnClickListener(view -> showResetDialog());

        loginBtn.setOnClickListener(view -> {
            signIn();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void signIn(){
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email cannot be empty!");
            emailInput.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password cannot be empty!");
            passwordInput.requestFocus();
        } else {
            fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                saveProfileInfo();
                                Log.d(TAG, "signInWithEmail:success");
                                LayoutInflater li = getLayoutInflater();
                                //Getting the View object as defined in the customtoast.xml file
                                View layout = li.inflate(R.layout.login_done_toast,(ViewGroup) findViewById(R.id.login_done_toast));

                                //Creating the Toast object
                                Toast toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.setView(layout);//setting the view of custom toast layout
                                toast.show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                LayoutInflater li = getLayoutInflater();
                                //Getting the View object as defined in the customtoast.xml file
                                View layout = li.inflate(R.layout.login_fail_toast,(ViewGroup) findViewById(R.id.login_fail_toast));

                                //Creating the Toast object
                                Toast toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
//                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.setView(layout);//setting the view of custom toast layout
                                toast.show();
                            }
                        }
                    });
        }
    }
}