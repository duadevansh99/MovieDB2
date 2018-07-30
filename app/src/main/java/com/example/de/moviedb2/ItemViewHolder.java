package com.example.de.moviedb2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    ImageView itemImage;
    TextView itemTitle;
    TextView itemLanguage;
    TextView itemRating;
    View itemView;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView=itemView;
        itemImage=itemView.findViewById(R.id.itemImage);
        itemTitle=itemView.findViewById(R.id.itemTitle);
        itemLanguage=itemView.findViewById(R.id.itemLanguage);
        itemRating=itemView.findViewById(R.id.itemRating);
    }

}
