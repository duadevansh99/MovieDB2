package com.example.de.moviedb2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    ArrayList<Trailer> trailers=new ArrayList<>();
    Context context;
    TrailerClickListener listener;

    public TrailerAdapter(ArrayList<Trailer> trailers, Context context, TrailerClickListener listener) {
        this.trailers = trailers;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View trailerRow= inflater.inflate(R.layout.row_layout_trailer,viewGroup,false);
        return new TrailerViewHolder(trailerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerViewHolder trailerViewHolder, int i) {
        Trailer trailer= trailers.get(i);
        trailerViewHolder.trailerTitle.setText(trailer.name);
        Picasso.get().load("http://img.youtube.com/vi/" + trailer.key + "/hqdefault.jpg").into(trailerViewHolder.trailerThumbnail);
        trailerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTrailerClicked(view,trailerViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
