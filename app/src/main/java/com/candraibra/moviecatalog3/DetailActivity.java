package com.candraibra.moviecatalog3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.candraibra.moviecatalog3.model.Movie;
import com.candraibra.moviecatalog3.model.Tv;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TV = "extra_tv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        setupMovie();
        setupTv();

    }

    public void setupMovie() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Movie selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (selectedMovie != null) {
            ImageView imgBanner = findViewById(R.id.img_poster);
            String banner = selectedMovie.getBackdropPath();
            String poster = selectedMovie.getPosterPath();
            Glide.with(this)
                    .load(banner)
                    .placeholder(R.drawable.load)
                    .into(imgBanner);
            ImageView imgPoster2 = findViewById(R.id.img_poster2);
            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.load)
                    .into(imgPoster2);
            TextView txtTitle = findViewById(R.id.txt_title);
            txtTitle.setText(selectedMovie.getTitle());
            TextView txtDescription = findViewById(R.id.txt_description);
            String vote = Double.toString(selectedMovie.getVoteAverage() / 2);
            String voteReal = Double.toString(selectedMovie.getVoteAverage());
            float rating = Float.parseFloat(vote);
            TextView txtRating = findViewById(R.id.rating);
            txtRating.setText(voteReal);
            RatingBar ratingBar = findViewById(R.id.rb_userrating);
            ratingBar.setRating(rating);
            txtDescription.setText(selectedMovie.getOverview());
            TextView txtRealise = findViewById(R.id.txt_realise_date);
            txtRealise.setText(selectedMovie.getReleaseDate());
            progressBar.setVisibility(View.GONE);
        }
    }

    public void setupTv() {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Tv selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
        if (selectedTv != null) {
            ImageView imgBanner = findViewById(R.id.img_poster);
            String banner = selectedTv.getBackdropPath();
            String poster = selectedTv.getPosterPath();
            Glide.with(this)
                    .load(banner)
                    .placeholder(R.drawable.load)
                    .into(imgBanner);
            ImageView imgPoster2 = findViewById(R.id.img_poster2);
            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.load)
                    .into(imgPoster2);
            TextView txtTitle = findViewById(R.id.txt_title);
            txtTitle.setText(selectedTv.getName());
            TextView txtDescription = findViewById(R.id.txt_description);
            String vote = Double.toString(selectedTv.getVoteAverage() / 2);
            String voteReal = Double.toString(selectedTv.getVoteAverage());
            float rating = Float.parseFloat(vote);
            TextView txtRating = findViewById(R.id.rating);
            txtRating.setText(voteReal);
            RatingBar ratingBar = findViewById(R.id.rb_userrating);
            ratingBar.setRating(rating);
            txtDescription.setText(selectedTv.getOverview());
            TextView txtRealise = findViewById(R.id.txt_realise_date);
            txtRealise.setText(selectedTv.getFirstAirDate());
            progressBar.setVisibility(View.GONE);
        }
    }
}