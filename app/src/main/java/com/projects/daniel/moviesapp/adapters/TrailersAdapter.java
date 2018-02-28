package com.projects.daniel.moviesapp.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.daniel.moviesapp.NetworkUtils;
import com.projects.daniel.moviesapp.R;
import com.projects.daniel.moviesapp.models.Trailer;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Daniel on 2/28/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> trailers;
    private Context context;

    public TrailersAdapter(Context context, ArrayList<Trailer> trailers) {
        this.context = context;
        this.trailers = trailers;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailer_list_item, parent, false);

        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        holder.setTrailer(trailers.get(position));
        holder.textView.setText(trailers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if(trailers == null) return 0;
        return trailers.size();
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Trailer trailer;
        TextView textView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.trailer_item_textview);
            itemView.setOnClickListener(this);
        }

        public void setTrailer(Trailer trailer) {
            this.trailer = trailer;
        }

        @Override
        public void onClick(View view) {
            // Open link in youtube app:
            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:" + trailer.getYoutubeKey()));

            try {
                context.startActivity(youtubeIntent);
            } catch (ActivityNotFoundException ex) {
                Uri linkToTrailer = Uri.parse(NetworkUtils.YOUTUBE_BASE_LINK).buildUpon()
                        .appendEncodedPath(NetworkUtils.YOUTUBE_PARAM_WATCH)
                        .appendQueryParameter(NetworkUtils.YOUTUBE_ID_KEY, trailer.getYoutubeKey())
                        .build();
                Intent webIntent = new Intent(Intent.ACTION_VIEW, linkToTrailer);
                context.startActivity(webIntent);
            }
        }
    }

}
