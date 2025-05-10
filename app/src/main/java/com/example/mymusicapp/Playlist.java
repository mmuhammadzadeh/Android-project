package com.example.mymusicapp;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private int coverImageResourceId;
    private List<Music> musicList;

    public Playlist(String name, int coverImageResourceId) {
        this.name = name;
        this.coverImageResourceId = coverImageResourceId;
        this.musicList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoverImageResourceId() {
        return coverImageResourceId;
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public void addMusic(Music music) {
        musicList.add(music);
    }

    public int getSongCount() {
        return musicList.size();
    }
}