package com.example.de.moviedb2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    ArrayList<MovieResults> results;
    ArrayList<Genres> genres;
    Context context;
    MovieClickListener listener;

    public ItemAdapter(ArrayList<MovieResults> results, ArrayList<Genres> genres,Context context, MovieClickListener listener) {
        this.results = results;
        this.context = context;
        this.listener = listener;
        this.genres=genres;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowRv=inflater.inflate(R.layout.row_layout_recyclerview,viewGroup,false);
        return new ItemViewHolder(rowRv);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {

        MovieResults movie=results.get(i);
        if(movie.name == null){
            itemViewHolder.itemTitle.setText(movie.title);
        }
        else{
            itemViewHolder.itemTitle.setText(movie.name);
        }
        itemViewHolder.itemRating.setText(movie.vote_average+"");
        itemViewHolder.itemLanguage.setText(movie.original_language);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster).into(itemViewHolder.itemImage);
        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieClicked(view,itemViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


}
