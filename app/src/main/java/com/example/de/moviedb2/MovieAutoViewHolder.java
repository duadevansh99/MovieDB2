package com.example.de.moviedb2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAutoViewHolder extends RecyclerView.ViewHolder {

    ImageView movieImageAuto;
    TextView ratingAuto, languageAuto, titleAuto;

    public MovieAutoViewHolder(@NonNull View itemView) {
        super(itemView);
        movieImageAuto=itemView.findViewById(R.id.movieImageAuto);
        ratingAuto=itemView.findViewById(R.id.ratingAuto);
        languageAuto=itemView.findViewById(R.id.languageAuto);
        titleAuto=itemView.findViewById(R.id.titleAuto);
    }
}
