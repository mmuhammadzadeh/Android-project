package com.example.mymusicapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MusicPlayerActivity extends AppCompatActivity {

    private TextView textViewSongTitle, textViewArtist;
    private ImageView imageViewCover;
    private SeekBar seekBarProgress;
    private ImageButton  buttonPlayPause, buttonPrevious, buttonNext;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        textViewSongTitle = findViewById(R.id.textViewSongTitle);
        textViewArtist = findViewById(R.id.textViewArtist);
        imageViewCover = findViewById(R.id.imageViewCover);
        seekBarProgress = findViewById(R.id.seekBarProgress);
        buttonPlayPause = findViewById(R.id.buttonPlayPause);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);

        // دریافت داده‌ها از Intent (مثلاً عنوان آهنگ، هنرمند و تصویر کاور)
        String songTitle = getIntent().getStringExtra("SONG_TITLE");
        String artist = getIntent().getStringExtra("ARTIST");
        int coverImage = getIntent().getIntExtra("COVER_IMAGE", R.drawable.ic_music_logo);

        textViewSongTitle.setText(songTitle);
        textViewArtist.setText(artist);
        imageViewCover.setImageResource(coverImage);

        // Initialize the MediaPlayer
        mediaPlayer = new MediaPlayer();

        // Example file path (update with the real path or URL)
        String songPath = "https://dl.musicdel.ir/Music/1402/09/mostafa_fatahi_mahigir.mp3"; // or a local file path

        try {
            mediaPlayer.setDataSource(songPath); // Set the music file source
            mediaPlayer.prepare(); // Prepare the MediaPlayer
        } catch (IOException e) {
            e.printStackTrace();
        }

        // عملکرد دکمه play/pause
        buttonPlayPause.setOnClickListener(v -> {
            // Add logic for play/pause functionality
            if (isPlaying()) {
                // Pause the song
                pauseSong();
            } else {
                // Play the song
                playSong();
            }
        });
        // Next/Previous buttons functionality
        buttonPrevious.setOnClickListener(view -> {
            // Add logic for previous song
            playPreviousSong();
        });

        buttonNext.setOnClickListener(view -> {
            // Add logic for next song
            playNextSong();
        });
    }
    private boolean isPlaying() {
        // Add logic to check if the song is currently playing
        return mediaPlayer.isPlaying();
    }

    private void playSong() {
        // Logic to start the song
        mediaPlayer.start();
        buttonPlayPause.setImageResource(R.drawable.ic_pause); // Change icon to Pause
    }

    private void pauseSong() {
        // Logic to pause the song
        mediaPlayer.pause();
        buttonPlayPause.setImageResource(R.drawable.ic_play); // Change icon to Play
    }

    private void playPreviousSong() {
        // Logic to play the previous song
    }

    private void playNextSong() {
        // Logic to play the next song
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the MediaPlayer when activity is destroyed
        }
    }
}
