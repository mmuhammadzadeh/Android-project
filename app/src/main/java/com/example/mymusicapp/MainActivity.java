package com.example.mymusicapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusicapp.AccountFragment;
import com.example.mymusicapp.HomeFragment;
import com.example.mymusicapp.LibraryFragment;
import com.example.mymusicapp.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    private TextView tvWelcome;
    private Button btnSignOut;
    private RecyclerView rvSongs;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private MediaPlayer mediaPlayer;
    private SongAdapter songAdapter;
    private List<Song> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvWelcome = findViewById(R.id.tv_welcome);
        btnSignOut = findViewById(R.id.btn_sign_out);
        rvSongs = findViewById(R.id.rv_songs);

        // Initialize song list
        songList = new ArrayList<>();
        songList.add(new Song("Song 1", R.raw.rann));
        songList.add(new Song("Song 2", R.raw.rann));


        // Set up RecyclerView
        songAdapter = new SongAdapter(songList);
        rvSongs.setLayoutManager(new LinearLayoutManager(this));
        rvSongs.setAdapter(songAdapter);

        // چک کردن وضعیت لاگین
        if (mAuth.getCurrentUser() != null) {
            // کاربر لاگین کرده، داده‌ها رو بارگذاری کن
            loadUserData();
        } else {
            // کاربر لاگین نکرده، به صفحه ورود برو
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AccountFragment())
                    .commit();
            bottomNav.setSelectedItemId(R.id.nav_account); // رفتن به تب حساب کاربری
            return; // از ادامه متد خارج شو
        }
        btnSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            tvWelcome.setText("Welcome, Guest"); // خالی کردن TextView
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AccountFragment())
                    .commit();
            bottomNav.setSelectedItemId(R.id.nav_account);
        });

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_library) {
                selectedFragment = new LibraryFragment();
            } else if (itemId == R.id.nav_account) {
                selectedFragment = new AccountFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });
        // بررسی اینکه کدوم فرگمنت باید اول نمایش داده بشه
        boolean openAccount = getIntent().getBooleanExtra("openAccount", false);
        if (openAccount) {
            bottomNav.setSelectedItemId(R.id.nav_account); // رفتن مستقیم به فرگمنت حساب کاربری
        } else {
            bottomNav.setSelectedItemId(R.id.nav_home); // پیش‌فرض: خانه
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            loadUserData();
        } else {
            tvWelcome.setText("Welcome, Guest");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AccountFragment())
                    .commit();
            bottomNav.setSelectedItemId(R.id.nav_account);
        }
    }
    private void loadUserData() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(document -> {
                    if (document != null && document.exists()) {
                        String username = document.getString("username");
                        tvWelcome.setText("Welcome, " + (username != null ? username : "User") + "!");
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show();
                });
    }
    private void playSong(Song song) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, song.getId());
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}