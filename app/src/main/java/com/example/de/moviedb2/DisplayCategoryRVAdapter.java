package com.example.de.moviedb2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DisplayCategoryRVAdapter extends RecyclerView.Adapter<DisplayCategoryRVViewHolder> {

    ArrayList<MovieResults> results;
    ArrayList<Genres> genres;
    Context context;
    MovieClickListener listener;

    public DisplayCategoryRVAdapter(ArrayList<MovieResults> results, ArrayList<Genres> genres,Context context, MovieClickListener listener) {
        this.results = results;
        this.context = context;
        this.listener = listener;
        this.genres=genres;
    }

    @NonNull
    @Override
    public DisplayCategoryRVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowRv=inflater.inflate(R.layout.row_layout_recyclerview,viewGroup,false);
        return new DisplayCategoryRVViewHolder(rowRv);
    }

    @Override
    public void onBindViewHolder(@NonNull final DisplayCategoryRVViewHolder displayCategoryRVViewHolder, int i) {

        MovieResults movie=results.get(i);
        displayCategoryRVViewHolder.itemTitle.setText(movie.title);
        displayCategoryRVViewHolder.itemRating.setText(movie.vote_average+"");
        displayCategoryRVViewHolder.itemLanguage.setText(movie.original_language);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster).into(displayCategoryRVViewHolder.itemImage);
        displayCategoryRVViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieClicked(view,displayCategoryRVViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }



}
