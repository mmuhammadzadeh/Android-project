package com.example.mymusicapp;
public class Music {
    private String title;
    private String artist;
    private int coverResId;

    public Music(String title, String artist, int coverResId) {
        this.title = title;
        this.artist = artist;
        this.coverResId = coverResId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getCoverResId() {
        return coverResId;
    }
}