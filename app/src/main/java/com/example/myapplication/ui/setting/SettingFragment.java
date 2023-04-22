package com.example.myapplication.ui.setting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSettingBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding binding;
    Button currencyBtn, languageBtn, faqBtn, logOutBtn;
    TextView textViewUsername, textViewEmail;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        textViewUsername = binding.textViewUsername;
        textViewEmail = binding.textViewEmail;

        if(MainActivity.displayName.length() == 0)
            saveProfileInfo();

        textViewUsername.setText(MainActivity.displayName);

        currencyBtn = binding.chooseCurrencyBtn;
        languageBtn = binding.languageBtn;
        logOutBtn = binding.signOutBtn;

        logOutBtn.setOnClickListener(view -> {
            MainActivity.displayName = "";

            fAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void saveProfileInfo(){
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
                            MainActivity.displayName = document.getString("displayName");
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
}
