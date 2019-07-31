package com.candraibra.moviecatalog3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.candraibra.moviecatalog3.DetailActivity;
import com.candraibra.moviecatalog3.ItemClickSupport;
import com.candraibra.moviecatalog3.R;
import com.candraibra.moviecatalog3.adapter.MoviesAdapter;
import com.candraibra.moviecatalog3.model.Movie;
import com.candraibra.moviecatalog3.retrofit.MoviesRepository;
import com.candraibra.moviecatalog3.retrofit.OnGetMoviesCallback;

import java.util.ArrayList;



public class MovieFragment extends Fragment {
    private MoviesAdapter adapter;
    private RecyclerView rvCategory;
    private ProgressBar progressBar;
    private MoviesRepository moviesRepository;
    public final static String LIST_STATE_KEY = "recycler_list_state";
    private ArrayList<Movie> movies1 = new ArrayList<>();

    public MovieFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_movies, container, false);
        rvCategory = fragmentView.findViewById(R.id.rv_category);
        progressBar = fragmentView.findViewById(R.id.progressBar);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setHasFixedSize(true);
        moviesRepository = MoviesRepository.getInstance();
        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            final ArrayList<Movie> moviesState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert moviesState != null;
            movies1.addAll(moviesState);
            adapter = new MoviesAdapter(getContext());
            adapter.setMovieList(moviesState);
            rvCategory.setAdapter(adapter);
            ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, moviesState.get(position));
                    startActivity(intent);
                }
            });
        } else {
            progressBar.setVisibility(View.VISIBLE);
            getData();
        }

        return fragmentView;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, movies1);
    }

    public void getData() {
        moviesRepository.getMoviesPopular(new OnGetMoviesCallback() {
            @Override
            public void onSuccess(final ArrayList<Movie> movies) {
                adapter = new MoviesAdapter(getContext());
                adapter.setMovieList(movies);
                rvCategory.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                movies1.addAll(movies);
                ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(position));
                        startActivity(intent);
                    }
                });


            }

            String toast_msg = getString(R.string.toastmsg);

            @Override
            public void onError() {
                Toast.makeText(getActivity(), toast_msg, Toast.LENGTH_SHORT).show();

            }

        });
    }

}

