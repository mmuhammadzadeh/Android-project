package com.example.mymusicapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private Context context;
    private List<Music> musicList;

    public MusicAdapter(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        Music music = musicList.get(position);
        holder.textViewTitle.setText(music.getTitle());
        holder.textViewArtist.setText(music.getArtist());
        holder.imageViewCover.setImageResource(music.getCoverResId());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewArtist;
        ImageView imageViewCover;

        public MusicViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewMusicTitle);
            textViewArtist = itemView.findViewById(R.id.textViewMusicArtist);
            imageViewCover = itemView.findViewById(R.id.imageViewMusicCover);
        }
    }
}
