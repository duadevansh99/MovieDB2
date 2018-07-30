package com.example.de.moviedb2;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastDisplayActivity extends AppCompatActivity {

    ImageView castPersonDP;
    TextView castPersonName,overview,birthday,deathday,placeOfBirth, titleToolbar;
    RecyclerView castMovies, castTVShows;
    ItemAdapter adapterMovies,adapterTV;
    ArrayList<MovieResults> movies= new ArrayList<>();
    ArrayList<MovieResults> shows= new ArrayList<>();
    ArrayList<Genres> genres=new ArrayList<>();
    ArrayList<String> castPictures=new ArrayList<>();
    public static final String DISPLAY_MOVIE="displaying cast movie";
    RotateLoading rotateLoadingDP;
    ViewPager viewPagerCast;
    CastViewPagerAdapter castViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_display);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarDisplayCategory);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("");
        }

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String name=bundle.getString(DisplayMovieItem.CAST_NAME);
        long id=bundle.getLong(DisplayMovieItem.CAST_ID);

        viewPagerCast=findViewById(R.id.castViewPager);
        rotateLoadingDP=findViewById(R.id.loadingCastDP);
        rotateLoadingDP.start();
        castPersonDP=findViewById(R.id.castPersonDP);
        castPersonName=findViewById(R.id.castPersonName);
        overview=findViewById(R.id.castPersonOverview);
        birthday=findViewById(R.id.castPersonBday);
        deathday=findViewById(R.id.castPersonDeath);
        placeOfBirth=findViewById(R.id.castPersonPlaceOfBirth);
        titleToolbar=findViewById(R.id.toolbarTextViewCast);

        castMovies=findViewById(R.id.castPersonMovies);
        FlipInTopXAnimator animatorSM=new FlipInTopXAnimator();
        animatorSM.setInterpolator(new OvershootInterpolator());
        castMovies.setItemAnimator(animatorSM);
        castMovies.getItemAnimator().setAddDuration(1000);

        castTVShows=findViewById(R.id.castPersonTVShows);
        FlipInTopXAnimator animatorTV=new FlipInTopXAnimator();
        animatorSM.setInterpolator(new OvershootInterpolator());
        castTVShows.setItemAnimator(animatorTV);
        castTVShows.getItemAnimator().setAddDuration(1000);


        LinearLayoutManager layoutManagerMovies=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManagerTVShows=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        titleToolbar.setText(name);

        adapterMovies=new ItemAdapter(movies, genres, this, new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=movies.get(position);
                long idMovie=movie.id;
                Intent intentMovie=new Intent(CastDisplayActivity.this,DisplayMovieItem.class);
                intentMovie.putExtra(DISPLAY_MOVIE,idMovie);
                startActivity(intentMovie);
            }
        });

        adapterTV= new ItemAdapter(shows, genres, this, new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                //TV SHOW CLICKED
            }
        });

        castViewPagerAdapter= new CastViewPagerAdapter(this, castPictures);
        viewPagerCast.setAdapter(castViewPagerAdapter);


        //CAST DETAILS
        Call<CastDisplayResponse> callDetails=APIClient.getMovieService().getCastPerson(id);
        callDetails.enqueue(new Callback<CastDisplayResponse>() {
            @Override
            public void onResponse(Call<CastDisplayResponse> call, Response<CastDisplayResponse> response) {
                CastDisplayResponse displayResponse=response.body();
                castPersonName.setText(displayResponse.name);
                birthday.setText(displayResponse.birthday);
                overview.setText(displayResponse.biography);
                placeOfBirth.setText(displayResponse.place_of_birth);
                if(displayResponse.deathday == null){
                    deathday.setText("-");
                }
                else{
                    deathday.setText(displayResponse.deathday);
                }
                Picasso.get().load("https://image.tmdb.org/t/p/w500"+ displayResponse.castDP).into(castPersonDP);
                rotateLoadingDP.stop();
            }

            @Override
            public void onFailure(Call<CastDisplayResponse> call, Throwable t) {

            }
        });

        //CAST MOVIES
        Call<CastPersonMovieResponse> callMovies=APIClient.getMovieService().getCastMovies(id);
        callMovies.enqueue(new Callback<CastPersonMovieResponse>() {
            @Override
            public void onResponse(Call<CastPersonMovieResponse> call, Response<CastPersonMovieResponse> response) {
                CastPersonMovieResponse movieResponse=response.body();
                movies.clear();
                for(int i=0;i<movieResponse.cast.size();i++){
                    movies.add(movieResponse.cast.get(i));
                    adapterMovies.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(Call<CastPersonMovieResponse> call, Throwable t) {

            }
        });

        //CAST TV SHOWS
        Call<CastPersonMovieResponse> callTV=APIClient.getMovieService().getCastShows(id);
        callTV.enqueue(new Callback<CastPersonMovieResponse>() {
            @Override
            public void onResponse(Call<CastPersonMovieResponse> call, Response<CastPersonMovieResponse> response) {
                CastPersonMovieResponse movieResponse=response.body();
                shows.clear();
                for(int i=0;i<movieResponse.cast.size();i++){
                    shows.add(movieResponse.cast.get(i));
                    adapterTV.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(Call<CastPersonMovieResponse> call, Throwable t) {

            }
        });

        //GENRES
        Call<GenreResponse> genreResponseCall=APIClient.getMovieService().getGenres();
        genreResponseCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                GenreResponse gr=response.body();
                genres.clear();
                genres.addAll(gr.genres);
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });

        //PICTURES
        Call<CastPersonPictureResponse> callPictures=APIClient.getMovieService().getPictures(id);
        callPictures.enqueue(new Callback<CastPersonPictureResponse>() {
            @Override
            public void onResponse(Call<CastPersonPictureResponse> call, Response<CastPersonPictureResponse> response) {
                CastPersonPictureResponse pictureResponse=response.body();
                castPictures.clear();
                for(int i=0;i<pictureResponse.results.size();i++){
                    castPictures.add(pictureResponse.results.get(i).picture);
                    castViewPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CastPersonPictureResponse> call, Throwable t) {

            }
        });

        castMovies.setAdapter(adapterMovies);
        castMovies.setLayoutManager(layoutManagerMovies);
        castTVShows.setAdapter(adapterTV);
        castTVShows.setLayoutManager(layoutManagerTVShows);

        Timer timer= new Timer();
        timer.scheduleAtFixedRate(new PictureTimerTask(),5000,7000);


    }

    public class PictureTimerTask extends TimerTask{

        @Override
        public void run() {

            CastDisplayActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPagerCast.getCurrentItem() != castPictures.size()-1){
                        viewPagerCast.setCurrentItem(viewPagerCast.getCurrentItem()+1);
                    }
                    else{
                        viewPagerCast.setCurrentItem(0);
                    }
                }
            });

        }
    }


}
