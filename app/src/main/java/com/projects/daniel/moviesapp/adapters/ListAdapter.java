package com.projects.daniel.moviesapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projects.daniel.moviesapp.NetworkUtils;
import com.projects.daniel.moviesapp.R;
import com.projects.daniel.moviesapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.PosterViewHolder> {

    private ArrayList<Movie> list;
    private Context context;
    private final ListItemClickListener mClickListener;

    public interface ListItemClickListener {
        void onItemClick(int itemIndex);
    }

    public ListAdapter(Context context, ArrayList<Movie> list, ListItemClickListener mClickListener) {
        this.list = list;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.poster_list_item, parent, false);

        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {

        String posterId = list.get(position).getPosterId();
        Uri uri = Uri.parse(NetworkUtils.POSTER_BASE_URL).buildUpon()
                .appendEncodedPath(NetworkUtils.POSTER_SIZE)
                .appendEncodedPath(posterId)
                .build();

        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_photo_black_24dp)
                .error(R.drawable.ic_error_outline_24dp)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         ImageView imageView;

        public PosterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movies_list_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(getAdapterPosition());
        }
    }
}
