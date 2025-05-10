package com.example.mymusicapp;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private List<Playlist> playlists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_playlists);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        playlists = new ArrayList<>();

        // دیتای تستی
        Music m1 = new Music("آهنگ اول", "خواننده اول","https://musictag.ir/images/cover1.jpg", "https://musictag.ir/music/song1.mp3");
        Music m2 = new Music("آهنگ دوم", "خواننده دوم", "https://musictag.ir/images/cover2.jpg", "https://musictag.ir/music/song2.mp3");
        Music m3 = new Music("آهنگ سوم", "خواننده سوم", "https://musictag.ir/images/cover3.jpg", "https://musictag.ir/music/song3.mp3");

        Playlist playlist1 = new Playlist("پاپ", R.drawable.ic_account);
        playlist1.addMusic(m1);
        playlist1.addMusic(m2);

        Playlist playlist2 = new Playlist("راک", R.drawable.ic_account);
        playlist2.addMusic(m3);

        playlists.add(playlist1);
        playlists.add(playlist2);

        adapter = new PlaylistAdapter(getContext(), playlists);
        recyclerView.setAdapter(adapter);

        return view;
    }
}