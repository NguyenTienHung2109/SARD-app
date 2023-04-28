package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    Button signUpBtn;
    TextView suggestLoginBtn;

    FirebaseFirestore fStore;
    FirebaseUser fUser;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //sẽ ẩn tiêu đề
        getSupportActionBar().hide(); // ẩn thanh tiêu đề

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //bật chế độ toàn màn hình

        setContentView(R.layout.activity_register);

//        suggestLoginBtn = findViewById(R.id.suggestLoginBtn);
//
//        suggestLoginBtn.setOnClickListener(view ->
//                startActivity(new Intent(RegisterActivity.this, MainActivity.class))
//        );

        nameInput = findViewById(R.id.editPersonName);
        emailInput = findViewById(R.id.editEmail);
        passwordInput = findViewById(R.id.editPassword);
        signUpBtn = findViewById(R.id.signUpBtn);
        suggestLoginBtn = findViewById(R.id.suggestLoginBtn);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        suggestLoginBtn.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signUpBtn.setOnClickListener(view -> {
            createAccount();
        });
    }

    private void createAccount() {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(TextUtils.isEmpty(name)){
            nameInput.setError("Name cannot be empty!");
            nameInput.requestFocus();
        } else if(TextUtils.isEmpty(email)){
            emailInput.setError("Email cannot be empty!");
            emailInput.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password cannot be empty!");
            passwordInput.requestFocus();
        } else  if (!isValidPassword(password.trim())) {
            Toast.makeText(this, "Choose a stronger password!", Toast.LENGTH_SHORT).show();
        } else{
            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                LayoutInflater li = getLayoutInflater();
                                //Getting the View object as defined in the customtoast.xml file
                                View layout = li.inflate(R.layout.register_done_toast,(ViewGroup) findViewById(R.id.register_done_toast));

                                //Creating the Toast object
                                Toast toast = new Toast(getApplicationContext());
                                toast.setDuration(Toast.LENGTH_SHORT);
//                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                toast.setView(layout);//setting the view of custom toast layout
                                toast.show();

                                Map<String, Object> info = new HashMap<>();
                                info.put("displayName", name);
                                info.put("currency", "$");

                            } else {
                                LayoutInflater li = getLayoutInflater();
                                //Getting the View object as defined in the customtoast.xml file
                                View layout = li.inflate(R.layout.register_fail_toast,(ViewGroup) findViewById(R.id.register_fail_toast));

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

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[!-~])(?=\\S+$).{1,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}