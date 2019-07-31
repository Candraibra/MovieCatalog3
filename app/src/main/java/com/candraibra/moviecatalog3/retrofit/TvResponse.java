package com.candraibra.moviecatalog3.retrofit;

import com.candraibra.moviecatalog3.model.Tv;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class TvResponse {
    @SerializedName("results")
    @Expose
    private ArrayList<Tv> tvs = null;

    ArrayList<Tv> getTvs() {
        return tvs;
    }



}


