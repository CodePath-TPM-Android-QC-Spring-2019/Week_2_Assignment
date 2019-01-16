package com.example.logan.movie1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.example.logan.movie1.adapters.MoviesAdapter;
import com.example.logan.movie1.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;
//Using RecyclerView steps:

//Add RecyclerView support library to the gradle build file
//Define a model class to use as the data source
//Add a RecyclerView to your activity to display the items
//Create a custom row layout XML file to visualize the item
//Create a RecyclerView.Adapter and ViewHolder to render the item
//Bind the adapter to the data source to populate the RecyclerView
public class MovieActivity extends AppCompatActivity {
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private List<Movie> movies;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        recyclerView = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        adapter = new MoviesAdapter(this, movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        getAllMoviesOnline();

    }
    private void getAllMoviesOnline() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_URL, new JsonHttpResponseHandler() {
            //define a call back method
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) { // choose this method because the data gets back is an object
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(movieJsonArray)); // get a list of movies from JsonArray
                    adapter.notifyDataSetChanged();
                    log.d("smile", movieJsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(MovieActivity.this, "Something went wrong while downloading content", Toast.LENGTH_SHORT).show();
                Log.e("Logan", "onFailure: " + errorResponse);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}

