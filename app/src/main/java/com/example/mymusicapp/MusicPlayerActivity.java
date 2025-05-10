// MusicPlayerActivity.java
package com.example.mymusicapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

public class MusicPlayerActivity extends AppCompatActivity {
    private List<Music> musicList;
    private int currentPosition;

    private TextView titleText, artistText;
    private ImageView coverImage, playPauseBtn, btnNext, btnPrev;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private String audioUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleText = findViewById(R.id.textViewSongTitle);
        artistText = findViewById(R.id.textViewArtist);
        coverImage = findViewById(R.id.imageViewCover);
        playPauseBtn = findViewById(R.id.buttonPlayPause);
        btnNext = findViewById(R.id.buttonNext);
        btnPrev = findViewById(R.id.buttonPrevious);

        // دریافت داده‌ها از Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String artist = intent.getStringExtra("artist");
        String coverUrl = intent.getStringExtra("coverUrl");
        String audioUrl = intent.getStringExtra("audioUrl");

        musicList = getIntent().getParcelableArrayListExtra("musicList");
        currentPosition = getIntent().getIntExtra("position", 0);

        if (musicList != null && !musicList.isEmpty()) {
            playMusic(audioUrl);
        }

        // نمایش اطلاعات آهنگ
        titleText.setText(title);
        artistText.setText(artist);
        Glide.with(this).load(coverUrl).into(coverImage);

        // پخش آهنگ
        playMusic(audioUrl);

        playPauseBtn.setOnClickListener(v -> {
            if (isPlaying) {
                pauseMusic();
            } else {
                playMusic(audioUrl);
            }
        });
        btnNext.setOnClickListener(v -> playNext());
        btnPrev.setOnClickListener(v -> playPrevious());

    }

    private void playMusic(String audioUrl) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audioUrl); // لینک آدرس آنلاین آهنگ
                mediaPlayer.prepare(); // آماده‌سازی برای پخش
                mediaPlayer.start(); // شروع پخش
                isPlaying = true;
                playPauseBtn.setImageResource(R.drawable.ic_pause);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mediaPlayer.start();
            isPlaying = true;
            playPauseBtn.setImageResource(R.drawable.ic_pause);
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            playPauseBtn.setImageResource(R.drawable.ic_play);
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release(); // آزادسازی منابع
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    private void playNext() {
        if (musicList != null && currentPosition < musicList.size() - 1) {
            currentPosition++;
            playMusic(audioUrl);
        }
    }

    private void playPrevious() {
        if (musicList != null && currentPosition > 0) {
            currentPosition--;
            playMusic(audioUrl);
        }
    }

}
