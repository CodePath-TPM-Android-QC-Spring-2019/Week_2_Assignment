package com.example.logan.movie1;


import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.logan.movie1.adapters.MoviesAdapter;
import com.example.logan.movie1.models.Movie;
import com.example.logan.movie1.utils.LoadYoutubeVideoTask;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;

public class DetailActivity extends YouTubeBaseActivity {
    private TextView tvTitle, tvOverView;
    private RatingBar ratingBar;
    private Movie movie;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

        tvTitle.setText(movie.getTitle());
        tvOverView.setText(movie.getOverView());
        ratingBar.setRating((float) movie.getRating());

        new LoadYoutubeVideoTask(youTubePlayerView).displayYouTubeVideo(movie);
    }

    private void init() {
        tvTitle = findViewById(R.id.title_detail);
        tvOverView = findViewById(R.id.tv_overview_dt);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        movie = getIntent().getParcelableExtra(MoviesAdapter.MVBUNDLE);
    }
}

