package com.example.mymusicapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicApiService {
    @GET("path_to_your_api")
    Call<List<Song>> getSongs();
}
