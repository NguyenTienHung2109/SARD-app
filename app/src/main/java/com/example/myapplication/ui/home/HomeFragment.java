package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.databinding.FragmentHomeBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private int totalExpenses = 0, currentBalance = 0, totalIncome = 0;
    private TextView expenseStat, balanceStat, incomeStat, monthTitle, balanceHeaderStat;
    private ImageView prevMonthBtn, nextMonthBtn, reloadBtn;
    private ExtendedFloatingActionButton addExpenseFab;
    private Calendar currentMonth;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        currentMonth = Calendar.getInstance();

        monthTitle = binding.monthTitle;

        prevMonthBtn = binding.prevMonthBtn;
        nextMonthBtn = binding.nextMonthBtn;

        addExpenseFab = binding.addExpenseFab;

        reloadBtn = binding.reloadBtn;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}