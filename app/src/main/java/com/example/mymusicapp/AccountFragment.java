package com.example.mymusicapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AccountFragment extends Fragment {

    private EditText loginUsername, loginPassword;
    private EditText registerUsername, registerEmail, registerAge, registerPassword;
    private MaterialButton loginButton, registerButton, googleLoginButton;
    private TextView tvForgotPassword;
    private TabLayout tabLayout;
    private LinearLayout loginLayout, registerLayout;
    private ProgressBar progressBar;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        tabLayout = view.findViewById(R.id.tab_layout);
        loginLayout = view.findViewById(R.id.login_layout);
        registerLayout = view.findViewById(R.id.register_layout);
        progressBar = view.findViewById(R.id.progress_bar);
        loginUsername = view.findViewById(R.id.login_username);
        loginPassword = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.login_button);
        googleLoginButton = view.findViewById(R.id.google_login_button);
        registerUsername = view.findViewById(R.id.register_username);
        registerEmail = view.findViewById(R.id.register_email);
        registerAge = view.findViewById(R.id.register_age);
        registerPassword = view.findViewById(R.id.register_password);
        registerButton = view.findViewById(R.id.register_button);
        tvForgotPassword = view.findViewById(R.id.tv_forgot_password);

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Add this in strings.xml
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        // Set up TabLayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    loginLayout.setVisibility(View.VISIBLE);
                    registerLayout.setVisibility(View.GONE);
                } else {
                    loginLayout.setVisibility(View.GONE);
                    registerLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Login button listener
        loginButton.setOnClickListener(v -> attemptLogin());

        // Register button listener
        registerButton.setOnClickListener(v -> attemptRegister());

        // Google Sign-In button listener
        googleLoginButton.setOnClickListener(v -> signInWithGoogle());

        // Forgot password listener
        tvForgotPassword.setOnClickListener(v -> navigateToForgotPassword());
    }

    private void attemptLogin() {
        loginUsername.setError(null);
        loginPassword.setError(null);

        String username = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            loginPassword.setError(getString(R.string.error_field_required));
            focusView = loginPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            loginPassword.setError(getString(R.string.error_invalid_password));
            focusView = loginPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            loginUsername.setError(getString(R.string.error_field_required));
            focusView = loginUsername;
            cancel = true;
        } else if (!isEmailValid(username)) {
            loginUsername.setError(getString(R.string.error_invalid_email));
            focusView = loginUsername;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        showProgress(false);
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "ورود موفقیت‌آمیز", Toast.LENGTH_SHORT).show();
                            navigateToHome();
                        } else {
                            Toast.makeText(requireContext(), "خطا: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void attemptRegister() {
        registerUsername.setError(null);
        registerEmail.setError(null);
        registerAge.setError(null);
        registerPassword.setError(null);

        String username = registerUsername.getText().toString().trim();
        String email = registerEmail.getText().toString().trim();
        String ageStr = registerAge.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            registerUsername.setError(getString(R.string.error_field_required));
            focusView = registerUsername;
            cancel = true;
        } else if (username.length() < 4) {
            registerUsername.setError(getString(R.string.error_invalid_username));
            focusView = registerUsername;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            registerEmail.setError(getString(R.string.error_field_required));
            focusView = registerEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            registerEmail.setError(getString(R.string.error_invalid_email));
            focusView = registerEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(ageStr)) {
            registerAge.setError(getString(R.string.error_field_required));
            focusView = registerAge;
            cancel = true;
        } else {
            try {
                int age = Integer.parseInt(ageStr);
                if (age < 13 || age > 120) {
                    registerAge.setError("سن باید بین 13 تا 120 باشد");
                    focusView = registerAge;
                    cancel = true;
                }
            } catch (NumberFormatException e) {
                registerAge.setError("سن نامعتبر است");
                focusView = registerAge;
                cancel = true;
            }
        }

        if (TextUtils.isEmpty(password)) {
            registerPassword.setError(getString(R.string.error_field_required));
            focusView = registerPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            registerPassword.setError(getString(R.string.error_invalid_password));
            focusView = registerPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        showProgress(false);
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "ثبت‌نام موفقیت‌آمیز", Toast.LENGTH_SHORT).show();
                            navigateToHome();
                        } else {
                            Toast.makeText(requireContext(), "خطا: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Toast.makeText(requireContext(), "ورود با گوگل ناموفق بود: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        showProgress(true);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    showProgress(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "ورود با گوگل موفقیت‌آمیز", Toast.LENGTH_SHORT).show();
                        navigateToHome();
                    } else {
                        Toast.makeText(requireContext(), "خطا: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToHome() {
        if (isAdded()) {
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    private void navigateToForgotPassword() {
        if (isAdded()) {
            ForgotPasswordFragment forgotFragment = new ForgotPasswordFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, forgotFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void showProgress(boolean show) {
        if (isAdded()) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}