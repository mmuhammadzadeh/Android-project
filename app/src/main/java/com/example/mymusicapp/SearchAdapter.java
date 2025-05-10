package com.example.mymusicapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private List<Music> musicList;

    public SearchAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.textViewTitle.setText(music.getTitle());
        holder.textViewArtist.setText(music.getArtist());
        Glide.with(context).load(music.getCoverUrl()).into(holder.imageViewCover);

        // افزودن کلیک برای ارسال اطلاعات به MusicPlayerActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MusicPlayerActivity.class);
            intent.putExtra("title", music.getTitle());
            intent.putExtra("artist", music.getArtist());
            intent.putExtra("coverUrl", music.getCoverUrl());
            intent.putExtra("audioUrl", music.getAudioUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return musicList != null ? musicList.size() : 0;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewArtist;
        ImageView imageViewCover;

        public SearchViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewMusicTitle);
            textViewArtist = itemView.findViewById(R.id.textViewMusicArtist);
            imageViewCover = itemView.findViewById(R.id.imageViewMusicCover);
        }
    }
}

