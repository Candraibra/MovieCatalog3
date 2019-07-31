package com.candraibra.moviecatalog3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.candraibra.moviecatalog3.R;
import com.candraibra.moviecatalog3.model.Movie;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    public MoviesAdapter(Context mContext) {
        this.mContext = mContext;
        this.movieList = new ArrayList<>();
    }

    private Context mContext;

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }


    private ArrayList<Movie> movieList;


    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row_movie, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(movieList.get(i).getTitle());
        viewHolder.tvDescription.setText(movieList.get(i).getOverview());
        String vote = Double.toString(movieList.get(i).getVoteAverage() / 2);
        String voteReal = Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.tvRating.setText(voteReal);
        float rating = Float.parseFloat(vote);
        viewHolder.ratingBar.setRating(rating);


        String poster = movieList.get(i).getPosterPath();

        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(viewHolder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRating, tvDescription;
        ImageView imgPhoto;
        RatingBar ratingBar;

        MyViewHolder(View view) {
            super(view);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            imgPhoto = itemView.findViewById(R.id.img_poster);
            ratingBar = itemView.findViewById(R.id.rb_userrating);
        }
    }
}
