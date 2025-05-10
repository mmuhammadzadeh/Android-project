package com.example.mymusicapp;

import android.os.Parcelable;
import android.os.Parcel;

public class Music implements Parcelable {
    private String title;
    private String artist;
    private String coverUrl;
    private String audioUrl;

    public Music(String title, String artist, String coverUrl, String audioUrl) {
        this.title = title;
        this.artist = artist;
        this.coverUrl = coverUrl;
        this.audioUrl = audioUrl;
    }

    protected Music(Parcel in) {
        title = in.readString();
        artist = in.readString();
        coverUrl = in.readString();
        audioUrl = in.readString();
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(coverUrl);
        dest.writeString(audioUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getCoverUrl() { return coverUrl; }
    public String getAudioUrl() { return audioUrl; }
}