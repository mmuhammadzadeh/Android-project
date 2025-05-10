package com.example.mymusicapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Playlist> playlistList;

    public static PlaylistsFragment newInstance(List<Playlist> playlists) {
        PlaylistsFragment fragment = new PlaylistsFragment();
        fragment.playlistList = new ArrayList<>(playlists);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPlaylists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PlaylistAdapter adapter = new PlaylistAdapter(getContext(), playlistList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
