package com.crossphd.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {


    public MovieAdapter(@NonNull Context context, @NonNull ArrayList<Movie> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }


        Movie currentMovie = getItem(position);

        ImageView image = listItemView.findViewById(R.id.movie_image);

        if (currentMovie != null) {
            Picasso.with(this.getContext())
                    .load(currentMovie.getmPosterImage())
                    .centerCrop()
                    .fit()
                    .into(image);
        }


//        TextView title = (TextView) listItemView.findViewById(R.id.article_title);
//        title.setText(currentArticle.getmTitle());

//        TextView author = (TextView) listItemView.findViewById(R.id.article_author);
//        author.setText(currentArticle.getmAuthor());

//        TextView source = (TextView) listItemView.findViewById(R.id.article_source);
//        source.setText(currentArticle.getmSource());
//
//        TextView date = (TextView) listItemView.findViewById(R.id.date_text_view);
//        String dateSubstring = currentArticle.getmDate().substring(0,10);
//        date.setText(dateSubstring);

        return listItemView;
    }
}
