package com.example.mymusicapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.ViewGroup;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class HomeFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MusicFetcher.fetchMusic(getContext(), musicList -> {
            musicAdapter = new MusicAdapter(getContext(), musicList);
            recyclerView.setAdapter(musicAdapter);
        }, error -> {
            // هندل کردن ارور مثلاً Toast نشون بده
        });

        // اگه ProgressBar توی XML هست، پیدا کن
        progressBar = view.findViewById(R.id.progressBar);

        loadDataFromServer();

        Button btnGoogleSignIn = view.findViewById(R.id.btnGoogleSignIn);
        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), GoogleSignInActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadDataFromServer() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);

        String url = "https://musictag.ir/api/songs"; // آدرس API واقعی‌تو بذار اینجا

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (progressBar != null) progressBar.setVisibility(View.GONE);

                        // اینجا دیتای آهنگا رو پردازش کن و لیست نشون بده
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressBar != null) progressBar.setVisibility(View.GONE);

                        Context ctx = getContext();
                        if (ctx != null) {
                            Toast.makeText(ctx, "خطا در دریافت داده‌ها", Toast.LENGTH_SHORT).show();
                        }

                        // می‌تونی Log هم بزنی:
                        error.printStackTrace();
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }
}
