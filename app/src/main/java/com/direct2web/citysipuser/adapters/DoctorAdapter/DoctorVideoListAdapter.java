package com.direct2web.citysipuser.adapters.DoctorAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.direct2web.citysipuser.R;
import com.direct2web.citysipuser.databinding.RawVideoRclrBinding;
import com.direct2web.citysipuser.model.DoctorModels.Video;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import java.util.List;

public class DoctorVideoListAdapter extends RecyclerView.Adapter<DoctorVideoListAdapter.ViewHolder>{

    Context context;
    List<Video> stringArrayList;
    Uri vid_url;
    private DefaultTrackSelector trackSelector;
    SimpleExoPlayer simpleExoPlayer;

    public DoctorVideoListAdapter(Context context, List<Video> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RawVideoRclrBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.raw_video_rclr, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            vid_url = Uri.parse(stringArrayList.get(position).getVideo());
        trackSelector = new DefaultTrackSelector(context);
        simpleExoPlayer = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();
        //playerView = findViewById(R.id.exoPlayerView);
        simpleExoPlayer.addListener(new PlayerEventListener());
        holder.binding.videoView.setPlayer(simpleExoPlayer);

        MediaItem mediaItem = MediaItem.fromUri(vid_url);

        simpleExoPlayer.addMediaItem(mediaItem);
        simpleExoPlayer.prepare();

    }

    @Override
    public int getItemCount() {
        return (stringArrayList != null) ? stringArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RawVideoRclrBinding binding;

        public ViewHolder(@NonNull RawVideoRclrBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    private class PlayerEventListener implements Player.Listener {

        @Override
        public void onPlaybackStateChanged(int state) {
            switch (state) {


                case Player.STATE_READY:

                    break;
                case Player.STATE_ENDED:


                    break;
            }

        }

    }
}
