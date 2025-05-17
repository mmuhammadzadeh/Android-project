package com.example.mymusicapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    private EditText etEmail;
    private MaterialButton btnReset;
    private FirebaseAuth mAuth;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull   Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.forgot_password_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // اینجا می‌توانید رابط کاربری بازیابی رمز عبور را پیاده‌سازی کنید
        // به عنوان مثال، دکمه‌ای برای ارسال کد بازیابی

        mAuth = FirebaseAuth.getInstance();
        etEmail = view.findViewById(R.id.et_email);

        view.findViewById(R.id.btn_send_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ارسال کد بازیابی رمز عبور
                Toast.makeText(requireContext(), "کد بازیابی ارسال شد", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.tv_back_to_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // بازگشت به صفحه ورود
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void resetPassword() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "ایمیل بازیابی رمز ارسال شد", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(requireContext(), "خطا: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}