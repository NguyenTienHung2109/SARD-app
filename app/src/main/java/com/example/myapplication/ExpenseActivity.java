package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExpenseActivity extends AppCompatActivity {
    Button dateButton, saveBtn, deleteBtn;
    Calendar cal = Calendar.getInstance();
    int day, month, year;

    Spinner expenseTypeSpinner, categorySpinner;
    TextInputEditText amount, description;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.addExpenseTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

//        amount = findViewById(R.id.textAddExpenseAmount);
//        description = findViewById(R.id.textAddExpenseDescription);

        clear();

        btnsSetup();
    }

    private void btnsSetup(){
        saveBtn = findViewById(R.id.saveExpenseBtn);
        saveBtn.setOnClickListener(view -> saveExpenseHandled());

//        deleteBtn = findViewById(R.id.deleteExpenseBtn);
//        deleteBtn.setOnClickListener(view -> deleteExpenseHandled());
    }

    private void saveExpenseHandled(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear(){
//        amount.setText("");
//        description.setText("");
        cal = Calendar.getInstance();
    }
}
