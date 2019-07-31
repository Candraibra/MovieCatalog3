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
import com.candraibra.moviecatalog3.model.Tv;

import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder> {

    public TvAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;


    public void setTvList(List<Tv> tvList) {
        this.tvList = tvList;
    }


    private List<Tv> tvList;

    @NonNull
    @Override
    public TvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row_tv, viewGroup, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TvAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.tvTitle.setText(tvList.get(i).getName());
        viewHolder.tvDescription.setText(tvList.get(i).getOverview());
        String vote = Double.toString(tvList.get(i).getVoteAverage() / 2);
        String voteReal = Double.toString(tvList.get(i).getVoteAverage() );
        viewHolder.tvRating.setText(voteReal);
        float rating = Float.parseFloat(vote);
        viewHolder.ratingBar.setRating(rating);

        String poster =  tvList.get(i).getPosterPath();

        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(viewHolder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvRating;
        TextView tvDescription;
        ImageView imgPhoto;
        RatingBar ratingBar;

        MyViewHolder(View view) {
            super(view);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            imgPhoto = itemView.findViewById(R.id.img_poster);
            ratingBar = itemView.findViewById(R.id.rb_userrating);
        }
    }
}