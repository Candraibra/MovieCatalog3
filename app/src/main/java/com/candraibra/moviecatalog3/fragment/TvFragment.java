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
import com.candraibra.moviecatalog3.adapter.TvAdapter;
import com.candraibra.moviecatalog3.model.Tv;
import com.candraibra.moviecatalog3.retrofit.OnGetTvCallback;
import com.candraibra.moviecatalog3.retrofit.TvRepository;

import java.util.ArrayList;

public class TvFragment extends Fragment {
    private TvAdapter adapter;
    private TvRepository tvRepository;
    private ProgressBar progressBar;
    private RecyclerView rvCategory;
    public final static String LIST_STATE_KEY = "recycler_list_state";
    private ArrayList<Tv> tvs1 = new ArrayList<>();

    public TvFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_tv_show, container, false);
        rvCategory = fragmentView.findViewById(R.id.rv_category);
        progressBar = fragmentView.findViewById(R.id.progressBar);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        tvRepository = TvRepository.getInstance();
        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            final ArrayList<Tv> tvState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert tvState != null;
            tvs1.addAll(tvState);
            adapter = new TvAdapter(getActivity());
            adapter.setTvList(tvState);
            rvCategory.setAdapter(adapter);
            ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_TV, tvState.get(position));
                    startActivity(intent);
                }
            });
        } else {
            progressBar.setVisibility(View.VISIBLE);
            getData();
        }

        return fragmentView;

    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, tvs1);
        super.onSaveInstanceState(outState);
    }

    public void getData() {
        tvRepository.getTvPopular(new OnGetTvCallback() {
            @Override
            public void onSuccess(final ArrayList<Tv> tvs) {
                adapter = new TvAdapter(getActivity());
                adapter.setTvList(tvs);
                rvCategory.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                tvs1.addAll(tvs);
                ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_TV, tvs.get(position));
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
