package com.example.mymusicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistManager {

    private Context context;
    private List<Playlist> playlists;
    private PlaylistAdapter adapter;

    public PlaylistManager(Context context, List<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
        this.adapter = new PlaylistAdapter(context, playlists); // اصلاح شده
    }

    public void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(adapter);

        // Set click listener for playlist items
        adapter.setOnPlaylistClickListener(new PlaylistAdapter.OnPlaylistClickListener() {
            @Override
            public void onPlaylistClick(Playlist playlist) {
                // Handle playlist click
                showPlaylistDetails(playlist);
            }
        });
    }

    public void addPlaylist(String name) {
        // You may want to handle adding playlist to the list
        Playlist newPlaylist = new Playlist(name, R.drawable.ic_music_logo); // استفاده از یک تصویر پیشفرض
        playlists.add(newPlaylist);
        adapter.notifyDataSetChanged();
        Toast.makeText(context, "Playlist added!", Toast.LENGTH_SHORT).show();
    }

    public void showAddPlaylistDialog() {
        // Inflate the dialog layout for adding a playlist
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_playlist, null);
        final EditText editTextName = view.findViewById(R.id.editTextPlaylistName);

        // Set up the dialog and button click listener
        Button buttonAdd = view.findViewById(R.id.buttonAddPlaylist);
        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            if (!name.isEmpty()) {
                addPlaylist(name);
            } else {
                Toast.makeText(context, "Please enter a name for the playlist.", Toast.LENGTH_SHORT).show();
            }
        });

        // Show dialog (this part may vary depending on how you want to show the dialog)
    }

    public void showPlaylistDetails(Playlist playlist) {
        // Inflate the dialog layout for showing playlist details
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_playlist_details, null);

        ImageView imageViewCover = view.findViewById(R.id.imageViewDetailPlaylistCover);
        TextView textViewName = view.findViewById(R.id.textViewDetailPlaylistName);
        TextView textViewSongCount = view.findViewById(R.id.textViewDetailPlaylistSongCount);
        Button buttonPlay = view.findViewById(R.id.buttonPlayPlaylist);
        RecyclerView recyclerViewSongs = view.findViewById(R.id.recyclerViewPlaylistSongs);

        // Set playlist data
        textViewName.setText(playlist.getName());
        textViewSongCount.setText(String.valueOf(playlist.getMusicList().size())); // تعداد آهنگ‌ها
        imageViewCover.setImageResource(playlist.getCoverImageResourceId());

        // Set adapter for songs in this playlist
        MusicAdapter musicAdapter = new MusicAdapter(context, playlist.getMusicList());
        recyclerViewSongs.setAdapter(musicAdapter);

        // Handle play button click
        buttonPlay.setOnClickListener(v -> {
            // Handle the play button action, like playing the playlist
            Toast.makeText(context, "Playing playlist: " + playlist.getName(), Toast.LENGTH_SHORT).show();
        });

        // Show the playlist details dialog
        // (You can use a dialog builder or any custom view logic you prefer)
    }
}
