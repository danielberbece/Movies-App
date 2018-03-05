package com.projects.daniel.moviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.daniel.moviesapp.R;
import com.projects.daniel.moviesapp.models.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private ArrayList<Review> trailers;

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_list_iem, parent, false);
        ReviewViewHolder holder = new ReviewViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.authorTv.setText(trailers.get(position).getAuthor());
        holder.bodyTv.setText(trailers.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if(trailers == null) return 0;
        return trailers.size();
    }

    public void setTrailers(ArrayList<Review> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView authorTv;
        TextView bodyTv;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            authorTv = itemView.findViewById(R.id.review_author_list_item);
            bodyTv = itemView.findViewById(R.id.review_body_list_item);
        }
    }
}
