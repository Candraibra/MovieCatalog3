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
    ImageView imgBanner, imgPoster2;
    RatingBar ratingBar;
    ProgressBar progressBar;
    TextView txtTitle, txtDescription, txtRating, txtRealise;
    String vote, voteReal, banner, poster;
    float rating;

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
            imgBanner = findViewById(R.id.img_poster);
            banner = selectedMovie.getBackdropPath();
            poster = selectedMovie.getPosterPath();
            Glide.with(this)
                    .load(banner)
                    .placeholder(R.drawable.load)
                    .into(imgBanner);
            imgPoster2 = findViewById(R.id.img_poster2);
            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.load)
                    .into(imgPoster2);
            txtTitle = findViewById(R.id.txt_title);
            txtTitle.setText(selectedMovie.getTitle());
            txtDescription = findViewById(R.id.txt_description);
            vote = Double.toString(selectedMovie.getVoteAverage() / 2);
            voteReal = Double.toString(selectedMovie.getVoteAverage());
            rating = Float.parseFloat(vote);
            txtRating = findViewById(R.id.rating);
            txtRating.setText(voteReal);
            ratingBar = findViewById(R.id.rb_userrating);
            ratingBar.setRating(rating);
            txtDescription.setText(selectedMovie.getOverview());
            txtRealise = findViewById(R.id.txt_realise_date);
            txtRealise.setText(selectedMovie.getReleaseDate());
            progressBar.setVisibility(View.GONE);
        }
    }

    public void setupTv() {
        progressBar = findViewById(R.id.progressBar);
        Tv selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
        if (selectedTv != null) {
            imgBanner = findViewById(R.id.img_poster);
            banner = selectedTv.getBackdropPath();
            poster = selectedTv.getPosterPath();
            Glide.with(this)
                    .load(banner)
                    .placeholder(R.drawable.load)
                    .into(imgBanner);
            imgPoster2 = findViewById(R.id.img_poster2);
            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.load)
                    .into(imgPoster2);
            txtTitle = findViewById(R.id.txt_title);
            txtTitle.setText(selectedTv.getName());
            txtDescription = findViewById(R.id.txt_description);
            vote = Double.toString(selectedTv.getVoteAverage() / 2);
            voteReal = Double.toString(selectedTv.getVoteAverage());
            rating = Float.parseFloat(vote);
            txtRating = findViewById(R.id.rating);
            txtRating.setText(voteReal);
            ratingBar = findViewById(R.id.rb_userrating);
            ratingBar.setRating(rating);
            txtDescription.setText(selectedTv.getOverview());
            txtRealise = findViewById(R.id.txt_realise_date);
            txtRealise.setText(selectedTv.getFirstAirDate());
            progressBar.setVisibility(View.GONE);
        }
    }
}