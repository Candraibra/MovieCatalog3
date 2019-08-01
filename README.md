##  Submission 3: Aplikasi Movie Catalogue (API)

1. Terdapat 2 (Dua) Halaman Yang Menampilkan Daftar Film (Movies Dan Tv Show)

2. Menggunakan Fragment Untuk Menampung Halaman Movies Dan Tv Show.

3. Menggunakan Recyclerview Untuk Menampilkan Daftar Film.

4. Menggunakan Tablayout, Bottomnavigationview Atau Yang Lainnya Sebagai Navigasi Antara Halaman Movies Dan Tv Show.

5. Menampilkan Indikator Loading Ketika Data Sedang Dimuat.

6. Menampilkan Poster Dan Informasi Film Pada Halaman Detail Film.

7. Menggunakan Constraintlayout Untuk Menyusun Layout.

8. Menampilkan Indikator Loading Ketika Data Sedang Dimuat.

9. Aplikasi Harus Mendukung Bahasa Indonesia Dan Bahasa Inggris.

10. Aplikasi Harus Bisa Menjaga Data Yang Sudah Dimuat Ketika Terjadi Pergantian Orientasi Dari Potrait Ke Landscape Atau Sebaliknya.

#### Untuk Penggunaan Retrofit Sendiri Ada Beberapa Hal Yang Harus Diperhatikan
`MoviesRepository.java`
```java
package com.candraibra.moviecatalog3.retrofit;

import android.support.annotation.NonNull;

import com.candraibra.moviecatalog3.BuildConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static MoviesRepository repository;
    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    public void getMoviesPopular(final OnGetMoviesCallback callback) {
        String apiKey = BuildConfig.ApiKey;
        api.getPopularMovies(apiKey, LANGUAGE, 1)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

}

```
`MoviesResponse.java`
```java
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
```
`OneGetMoviesCallback.java`
```java
package com.candraibra.moviecatalog3.retrofit;

import com.candraibra.moviecatalog3.model.Movie;

import java.util.ArrayList;


public interface OnGetMoviesCallback {
    void onSuccess(final ArrayList<Movie>movies);

    void onError();
}

```
Untuk Kelas POJO.nya Sendiri Sebagai Berikut
dan Saya Menggunakan Website Ini Untuk Mengbuat Otomatis Kelas POJO Dari File JSON
[jsonschema2pojo ](http://www.jsonschema2pojo.org/ "jsonschema2pojo ")
`Movie.java`
```java
package com.candraibra.moviecatalog3.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @SerializedName("id")
    private Integer id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("video")
    private Boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;


    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }


    public String getOverview() {
        return overview;
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public String getBackdropPath() {
        return "https://image.tmdb.org/t/p/w500" + backdropPath;
    }


    public Double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeList(this.genreIds);
        dest.writeValue(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
    }

    private Movie(Parcel in) {
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

```
Penerapan Pada Fragment
`MovieFragment.java`
```java
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


```
Penerapan pada `DetailActivity.java`
```java
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
```

> Kali Ini Saya Menggunakan Recyclerview Dengan Mendapat Data Melalui [TheMovieDB](Http://Www.Themoviedb.Org/ "Themoviedb") dan Menggunakan Retrofit [Retrofit](https://square.github.io/retrofit/) Dengan Mengembangkan Ui Dari Submission Sebelumnya, dan Ada Satu Pengalaman Menarik Pada Project Ini Yaitu Saya Dibuat Pusing Oleh putParcalableArrayList dan Hampir 1 Minggu Saya Dapat Menangani itu:laughing: Namun Sekarang Saya Peham Kenapa Diberi Cobaan Seperti Itu, Ternyata Allah Memberikan Saya Kesempatan Untuk Memahami Baris Demi Baris Codingan Yang Saya Tulis sendiri:smile:
