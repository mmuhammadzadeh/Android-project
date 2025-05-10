package com.example.mymusicapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private Context context;
    private List<Playlist> playlists;
    private OnPlaylistClickListener listener;

    public interface OnPlaylistClickListener {
        void onPlaylistClick(Playlist playlist);
    }

    public PlaylistAdapter(Context context, List<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    public void setOnPlaylistClickListener(OnPlaylistClickListener listener) {
        this.listener = listener;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.textViewName.setText(playlist.getName());
        holder.itemView.setOnClickListener(v -> listener.onPlaylistClick(playlist));
        // هنگام کلیک روی هر پلی‌لیست
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlaylistDetailsActivity.class);
            intent.putExtra("PLAYLIST_NAME", playlist.getName());
            intent.putExtra("SONG_COUNT", playlist.getSongCount());  // فرض کنید لیست آهنگ‌ها دارید
            intent.putExtra("COVER_IMAGE", playlist.getCoverImageResourceId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewPlaylistName);
        }
    }
}
