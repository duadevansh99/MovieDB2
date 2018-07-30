package com.example.de.moviedb2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAutoAdapter extends RecyclerView.Adapter<MovieAutoViewHolder> {

    ArrayList<MovieResults> movies;
    Context context;
    MovieClickListener listener;
    int lastPosition=-1;

    public MovieAutoAdapter(ArrayList<MovieResults> movies, Context context, MovieClickListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieAutoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View autoRow= inflater.inflate(R.layout.row_layout_movie_autoscroll,viewGroup,false);
        return new MovieAutoViewHolder(autoRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAutoViewHolder movieAutoViewHolder, int i) {
        MovieResults movie=movies.get(i);
        movieAutoViewHolder.titleAuto.setText(movie.title);
        movieAutoViewHolder.languageAuto.setText(movie.original_language);
        movieAutoViewHolder.ratingAuto.setText(movie.vote_average + " ");
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster).into(movieAutoViewHolder.movieImageAuto);
        movieAutoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieClicked(view,movieAutoViewHolder.getAdapterPosition());
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context,
                (i > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        movieAutoViewHolder.itemView.startAnimation(animation);
        lastPosition = i;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MovieAutoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}
