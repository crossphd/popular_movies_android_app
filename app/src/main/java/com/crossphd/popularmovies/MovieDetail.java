package com.crossphd.popularmovies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.title_tv)
    TextView title;
    @BindView(R.id.synopsis_tv)
    TextView synopsis;
    @BindView(R.id.release_date_tv)
    TextView releaseDate;
    @BindView(R.id.rating_tv)
    TextView rating;
    @BindView(R.id.image_iv)
    ImageView poster;


    //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        ButterKnife.bind(this);

//        Intent intent = getIntent();
//        if (intent == null) {
//            closeOnError();
//        }


        Movie movie = getIntent().getParcelableExtra("movie");
        if (movie == null) {
            Toast.makeText(this, "movie is null", Toast.LENGTH_LONG).show();
        }
        else {
            title.setText(movie.getmTitle());
        }

//        Toast.makeText(this, movie.toString(), Toast.LENGTH_LONG).show();
//        title.setText("title");

    }
}
