package com.projects.daniel.moviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.PosterViewHolder> {

    private int mNumberItems;
    private Context context;
    private final ListItemClickListener mClickListener;

    public interface ListItemClickListener {
        void onItemClick(int itemIndex);
    }

    public ListAdapter(Context context, int mNumberItems, ListItemClickListener mClickListener) {
        this.mNumberItems = mNumberItems;
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

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
                .fit().centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         ImageView imageView;

        public PosterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movies_list_image_view);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(getAdapterPosition());
        }
    }
}
