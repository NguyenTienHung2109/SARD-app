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

import java.util.Objects;

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
        textViewEmail.setText(MainActivity.email);

        currencyBtn = binding.chooseCurrencyBtn;
        languageBtn = binding.languageBtn;
        logOutBtn = binding.signOutBtn;

        currencyBtn.setOnClickListener(view ->chooseCurrencyDialog());

        logOutBtn.setOnClickListener(view -> {
            MainActivity.displayName = "";
            MainActivity.email = "";

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
                            MainActivity.email = firebaseUser.getEmail();
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

    void chooseCurrencyDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.currency_dialog);

        final RadioButton usdBtn = dialog.findViewById(R.id.usdBtn);
        final RadioButton vndBtn = dialog.findViewById(R.id.vndBtn);
        final RadioButton unitBtn = dialog.findViewById(R.id.unitBtn);
        Button submitButton = dialog.findViewById(R.id.saveBtn);

        if(Objects.equals(MainActivity.currency, "$")) usdBtn.setChecked(true);
        if(Objects.equals(MainActivity.currency, "đ")) vndBtn.setChecked(true);
        if(Objects.equals(MainActivity.currency, "N/A")) unitBtn.setChecked(true);

        usdBtn.setOnClickListener(view -> {
            MainActivity.currency = "$";
            usdBtn.setChecked(true);
            vndBtn.setChecked(false);
            unitBtn.setChecked(false);
        });
        vndBtn.setOnClickListener(view -> {
            MainActivity.currency = "đ";
            usdBtn.setChecked(false);
            vndBtn.setChecked(true);
            unitBtn.setChecked(false);
        });
        unitBtn.setOnClickListener(view -> {
            MainActivity.currency = "N/A";
            usdBtn.setChecked(false);
            vndBtn.setChecked(false);
            unitBtn.setChecked(true);
        });

        submitButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

//    private void setCurrency(String currency){
//        fStore.collection("Data").document(fAuth.getUid()).update("currency", currency).addOnSuccessListener(view -> {
//            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
//        }).addOnFailureListener(e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());
//
//    }
}
