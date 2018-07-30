package com.example.de.moviedb2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> castPictures;
    LayoutInflater inflater;

    public CastViewPagerAdapter(Context context, ArrayList<String> castPictures) {
        this.context = context;
        this.castPictures = castPictures;
    }

    @Override
    public int getCount() {
        return castPictures.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.viewpager_cast_view,null);
        ImageView imageView= view.findViewById(R.id.castViewPagerPicture);
        Picasso.get().load("https://image.tmdb.org/t/p/original" + castPictures.get(position)).into(imageView);
        ViewPager vp= (ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp=(ViewPager) container;
        View view=(View) object;
        vp.removeView(view);

    }
}
