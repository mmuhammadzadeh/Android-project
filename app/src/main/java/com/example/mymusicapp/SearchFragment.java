package com.example.mymusicapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // اتصال ویوها
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerViewSearch); // مطمئن شو این آیدی در xml هست

        // تنظیم RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        musicAdapter = new MusicAdapter(getContext(), null);
        recyclerView.setAdapter(musicAdapter);

        // لیسنر جستجو
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMusic(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // اگه بخوای زنده جستجو کنه:
                // searchMusic(newText);
                return false;
            }
        });

        return view;
    }

    private void searchMusic(String keyword) {
        MusicFetcher.searchMusicByQuery(getContext(), keyword,
                musicList -> musicAdapter.setMusicList(musicList),
                error -> Toast.makeText(getContext(), "خطا در جستجو", Toast.LENGTH_SHORT).show()
        );
    }
}
