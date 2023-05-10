package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityMainBinding;

import com.example.myapplication.model.Category;
import com.example.myapplication.model.ExpenseType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static String displayName = "";
    public static String email = "";
    public static String currency = "";

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser currentUser;
    TextView suggestSignUpBtn;

    public static void updateProfileInfo(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        if(firebaseUser != null){
            String userId = firebaseUser.getUid();
            firebaseFirestore.collection("Data").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            currency = document.getString("currency");
                        } else {
                            Log.d("userInfo", "No such document");
                        }
                    } else {
                        Log.d("userInfo", "get failed with ", task.getException());
                    }
                }
            });
        }
    }

    public static String intToMoneyFormat(int amount){
        if(Objects.equals(currency, "đ"))
            return (String.format("%,d", amount) + " " + currency);
        else
            return (currency + " " + String.format("%,d", amount));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //sẽ ẩn tiêu đề
        getSupportActionBar().hide(); // ẩn thanh tiêu đề

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //bật chế độ toàn màn hình

        setContentView(R.layout.activity_login);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        updateProfileInfo();
        AndroidThreeTen.init(this);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser();

        if(currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        int fragmentId = getIntent().getIntExtra("FRAGMENT_ID", 0);

        setContentView(binding.getRoot());

        ExpenseType.initExpenseType();
        Category.initCategory();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_overview, R.id.navigation_settings)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
    public static String getMonthTitle(Calendar cur){
        return new SimpleDateFormat("MMM, yyyy").format(cur.getTime());
    }
}