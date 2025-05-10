package com.example.mymusicapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDetailsActivity extends AppCompatActivity {

    private TextView textViewPlaylistName, textViewSongCount;
    private ImageView imageViewCover;
    private Button buttonPlay;
    private RecyclerView recyclerViewSongs;
    private MusicAdapter musicAdapter;
    private List<Music> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);

        textViewPlaylistName = findViewById(R.id.textViewPlaylistName);
        textViewSongCount = findViewById(R.id.textViewSongCount);
        imageViewCover = findViewById(R.id.imageViewCover);
        buttonPlay = findViewById(R.id.buttonPlay);
        recyclerViewSongs = findViewById(R.id.recyclerViewSongs);

        // دریافت داده‌ها از Intent
        String playlistName = getIntent().getStringExtra("PLAYLIST_NAME");
        int songCount = getIntent().getIntExtra("SONG_COUNT", 0);
        int coverImage = getIntent().getIntExtra("COVER_IMAGE", R.drawable.ic_music_logo);

        textViewPlaylistName.setText(playlistName);
        textViewSongCount.setText("Songs: " + songCount);
        imageViewCover.setImageResource(coverImage);

        // ساخت لیست آهنگ‌ها برای نمایش
        musicList = new ArrayList<>();
        musicList.add(new Music("Song 1", "Artist 1", R.drawable.ic_music_logo));
        musicList.add(new Music("Song 2", "Artist 2", R.drawable.ic_music_logo));

        // تنظیم RecyclerView
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(this));
        musicAdapter = new MusicAdapter(this, musicList);
        recyclerViewSongs.setAdapter(musicAdapter);

        // عملکرد دکمه پخش پلی‌لیست
        buttonPlay.setOnClickListener(v -> {
            // اینجا می‌تونید کد برای پخش پلی‌لیست رو اضافه کنید
        });
    }
}
