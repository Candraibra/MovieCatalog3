package com.candraibra.moviecatalog3.retrofit;

import com.candraibra.moviecatalog3.model.Tv;

import java.util.ArrayList;

public interface OnGetTvCallback {
    void onSuccess(ArrayList<Tv> tvs);

    void onError();
}
