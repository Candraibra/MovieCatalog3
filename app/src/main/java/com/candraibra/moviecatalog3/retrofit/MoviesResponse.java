package com.candraibra.moviecatalog3.retrofit;


import com.candraibra.moviecatalog3.model.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

class MoviesResponse {

    @SerializedName("results")
    @Expose
    private ArrayList<Movie> movies;

    ArrayList<Movie> getMovies() {
        return movies;
    }


}