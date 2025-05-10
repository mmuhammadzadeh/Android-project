package com.example.mymusicapp;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MusicFetcher {

    private static final String API_URL = "https://musictag.ir//api/songs"; // آدرس واقعی API

    public interface MusicCallback {
        void onMusicReceived(List<Music> musicList);
    }

    public interface ErrorCallback {
        void onError(VolleyError error);
    }

    public static void fetchMusic(Context context, final MusicCallback callback, final ErrorCallback errorCallback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {
                    List<Music> musicList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            String title = obj.optString("title", "Unknown Title");
                            String artist = obj.optString("artist", "Unknown Artist");
                            String coverUrl = obj.optString("cover_url", "");
                            String audioUrl = obj.optString("audio_url", "");

                            Music music = new Music(title, artist, coverUrl, audioUrl);
                            musicList.add(music);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    callback.onMusicReceived(musicList);
                },
                error -> {
                    error.printStackTrace();
                    if (errorCallback != null) {
                        errorCallback.onError(error);
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }

    public static void searchMusicByQuery(Context context, String query, Response.Listener<List<Music>> listener, Response.ErrorListener errorCallback) {
        String searchUrl = API_URL + "?search=" + Uri.encode(query);  // مثل: https://yoursite.com/api/songs?search=arash

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, searchUrl, null,
                response -> {
                    List<Music> musicList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Music music = new Music(
                                    obj.getString("title"),
                                    obj.getString("artist"),
                                    obj.getString("cover_url"),
                                    obj.getString("audio_url")
                            );
                            musicList.add(music);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listener.onResponse(musicList);
                },
                error -> {
                    error.printStackTrace();
                    errorCallback.onErrorResponse(error);
                }
        );

        queue.add(request);
    }

}
