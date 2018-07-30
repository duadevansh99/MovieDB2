package com.example.de.moviedb2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CastViewHolder extends RecyclerView.ViewHolder {

    ImageView castImage;
    TextView name;
    TextView role;

    public CastViewHolder(@NonNull View itemView) {
        super(itemView);
        castImage=itemView.findViewById(R.id.castImage);
        name=itemView.findViewById(R.id.castName);
        role=itemView.findViewById(R.id.castRole);
    }
}
