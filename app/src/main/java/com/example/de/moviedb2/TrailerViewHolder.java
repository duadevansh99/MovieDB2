package com.example.de.moviedb2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TrailerViewHolder extends RecyclerView.ViewHolder{

    ImageView trailerThumbnail;
    TextView trailerTitle;

    public TrailerViewHolder(@NonNull View itemView) {
        super(itemView);
        trailerThumbnail=itemView.findViewById(R.id.trailerThumbnail);
        trailerTitle=itemView.findViewById(R.id.trailerTitle);
    }
}
