package com.candraibra.moviecatalog3.retrofit;

import com.candraibra.moviecatalog3.model.Movie;

import java.util.ArrayList;


public interface OnGetMoviesCallback {
    void onSuccess(final ArrayList<Movie>movies);

    void onError();
}
