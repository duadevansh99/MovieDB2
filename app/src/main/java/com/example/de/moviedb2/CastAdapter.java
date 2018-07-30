package com.example.de.moviedb2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    ArrayList<CastPerson> cast=new ArrayList<>();
    Context context;
    CastClickListener listener;

    public CastAdapter(ArrayList<CastPerson> cast, Context context, CastClickListener listener) {
        this.cast = cast;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowRv=inflater.inflate(R.layout.row_layout_cast,viewGroup,false);
        return new CastViewHolder(rowRv);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastViewHolder castViewHolder, int i) {
        CastPerson person=cast.get(i);
        castViewHolder.name.setText(person.name);
        castViewHolder.role.setText(person.character);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + person.dpCastPerson).into(castViewHolder.castImage);
        castViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCastPersonClicked(view,castViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }
}
