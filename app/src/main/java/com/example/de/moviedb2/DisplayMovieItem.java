package com.example.de.moviedb2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayMovieItem extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    public static final String DISPLAY_MOVIE_FROM_DISPLAY="same activity intent";
    public static final String CAST_ID="sending cast id";
    public static final String CAST_NAME="sending cast name";
    ImageView background,movieImage,imageToolBar,ratingStar;
    TextView titleCollapse, genreCollapse, rating, movieDescription, runtime, releaseDate, titleTextView;
    ArrayList<Genres> genres=new ArrayList<>();
    ArrayList<MovieResults> similarMoviesList=new ArrayList<>();
    ArrayList<MovieResults> recommMoviesList=new ArrayList<>();
    ArrayList<CastPerson> castPeople=new ArrayList<>();
    ArrayList<Trailer> trailerVideos=new ArrayList<>();
    AppBarLayout appBarLayout;
    RotateLoading rotateLoadingBackground,rotateLoadingMovieImage;
    RecyclerView trailers,cast,similarMovies,recommendedMovies;
    ItemAdapter adapterSimilarMovies,adapterRecommMovies;
    CastAdapter castAdapter;
    TrailerAdapter trailerAdapter;
    LinearLayoutManager layoutManagerSM=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManagerRM=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManagerCast= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManagerTrailer= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//    CollapsingToolbarLayout collapsingToolbarLayout;

    TextView appBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie_item);
        toolbar=findViewById(R.id.toolbarMovieItemDisplay);
        setSupportActionBar(toolbar);


        imageToolBar=findViewById(R.id.imageToolBar);
//        collapsingToolbarLayout=findViewById(R.id.collapsingToolbar);
        appBarLayout=findViewById(R.id.appBarLayout);
        //appBarTitle = findViewById(R.id.titleTextView);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangedListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if(state.name().equals("EXPANDED")){
                    imageToolBar.setVisibility(View.INVISIBLE);
                    titleTextView.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
//                    collapsingToolbarLayout.setEnabled(false);
                }
                else if(state.name().equals("COLLAPSED")){
                    imageToolBar.setVisibility(View.VISIBLE);
                    titleTextView.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    titleTextView.setText("Pro Bro");
//                    collapsingToolbarLayout.setEnabled(true);
                }
                else if(state.name().equals("IDLE")){
                    imageToolBar.setVisibility(View.INVISIBLE);
                    titleTextView.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
//                    collapsingToolbarLayout.setEnabled(false);
                }
            }
        });
        titleTextView=findViewById(R.id.titleTextView);
        genreCollapse=findViewById(R.id.genreCollapse);
        rating=findViewById(R.id.movieRating);
        movieDescription=findViewById(R.id.movieDescription);
        runtime=findViewById(R.id.movieRunTime);
        releaseDate=findViewById(R.id.movieDate);
        ratingStar=findViewById(R.id.ratingStar);
        titleCollapse=findViewById(R.id.titleCollapse);
        background=findViewById(R.id.background);
        movieImage=findViewById(R.id.movieImage);
        rotateLoadingBackground=findViewById(R.id.rotateLoadingBackground);
        rotateLoadingMovieImage=findViewById(R.id.rotateLoadingMovieImage);

        trailers=findViewById(R.id.trailers);
        FlipInTopXAnimator animatorTrailers=new FlipInTopXAnimator();
        animatorTrailers.setInterpolator(new OvershootInterpolator());
        trailers.setItemAnimator(animatorTrailers);
        trailers.getItemAnimator().setAddDuration(1000);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(trailers);

        cast=findViewById(R.id.cast);
        FlipInTopXAnimator animatorCast=new FlipInTopXAnimator();
        animatorCast.setInterpolator(new OvershootInterpolator());
        cast.setItemAnimator(animatorCast);
        cast.getItemAnimator().setAddDuration(1000);

        similarMovies=findViewById(R.id.similarMovies);
        FlipInTopXAnimator animatorSM=new FlipInTopXAnimator();
        animatorSM.setInterpolator(new OvershootInterpolator());
        similarMovies.setItemAnimator(animatorSM);
        similarMovies.getItemAnimator().setAddDuration(1000);

        recommendedMovies=findViewById(R.id.recommendedMovies);
        FlipInTopXAnimator animatorRM=new FlipInTopXAnimator();
        animatorRM.setInterpolator(new OvershootInterpolator());
        recommendedMovies.setItemAnimator(animatorRM);
        recommendedMovies.getItemAnimator().setAddDuration(1000);

        rotateLoadingBackground.start();
        rotateLoadingMovieImage.start();
        ratingStar.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("");
        }
        final Intent intent=getIntent();
        long idMainActivity=intent.getLongExtra(MainActivity.DISPLAY_MOVIE,0);
        long idDisplayCategory=intent.getLongExtra(DisplayCategory.DISPLAY_MOVIE,0);
        long idSameActivity=intent.getLongExtra(DisplayMovieItem.DISPLAY_MOVIE_FROM_DISPLAY,0);
        long idCastMovie=intent.getLongExtra(CastDisplayActivity.DISPLAY_MOVIE,0);
        long id;
        if(idMainActivity!=0){
            id=idMainActivity;
        }
        else if(idDisplayCategory != 0){
            id=idDisplayCategory;
        }
        else if(idSameActivity != 0){
            id=idSameActivity;
        }
        else if(idCastMovie != 0){
            id=idCastMovie;
        }
        else{
            id=0;
        }

        adapterSimilarMovies= new ItemAdapter(similarMoviesList, genres, DisplayMovieItem.this, new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=similarMoviesList.get(position);
                long movieId=movie.id;
                Intent sameIntent=new Intent(DisplayMovieItem.this,DisplayMovieItem.class);
                sameIntent.putExtra(DISPLAY_MOVIE_FROM_DISPLAY,movieId);
                startActivity(sameIntent);

            }
        });

        adapterRecommMovies= new ItemAdapter(recommMoviesList, genres, DisplayMovieItem.this, new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=recommMoviesList.get(position);
                long movieId=movie.id;
                Intent sameIntent=new Intent(DisplayMovieItem.this,DisplayMovieItem.class);
                sameIntent.putExtra(DISPLAY_MOVIE_FROM_DISPLAY,movieId);
                startActivity(sameIntent);

            }
        });

        castAdapter= new CastAdapter(castPeople, DisplayMovieItem.this, new CastClickListener() {
            @Override
            public void onCastPersonClicked(View view, int position) {
                CastPerson person=castPeople.get(position);
                long personId=person.id;
                String personName=person.name;
                Intent castIntent=new Intent(DisplayMovieItem.this,CastDisplayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putLong(CAST_ID,personId);
                bundle.putString(CAST_NAME,personName);
                castIntent.putExtras(bundle);
                startActivity(castIntent);
            }
        });

        trailerAdapter= new TrailerAdapter(trailerVideos, DisplayMovieItem.this, new TrailerClickListener() {
            @Override
            public void onTrailerClicked(View view, int position) {
                //Toast.makeText(DisplayMovieItem.this,"trailer clicked",Toast.LENGTH_SHORT).show();
                Trailer trailer=trailerVideos.get(position);
                String key=trailer.key;
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + key));
                try {
                    DisplayMovieItem.this.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    DisplayMovieItem.this.startActivity(webIntent);
                }
            }
        });

        //DETAILS FOR THIS MOVIE
        retrofit2.Call<MovieDetails> call= APIClient.getMovieService().getMovieDetails(id);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(retrofit2.Call<MovieDetails> call, Response<MovieDetails> response) {
                MovieDetails movie=response.body();
                titleCollapse.setText(movie.original_title);
                rating.setText(" Rating: " + movie.vote_average);
                ratingStar.setVisibility(View.VISIBLE);
                runtime.setText("Runtime: " + movie.runtime + " minutes");
                movieDescription.setText("Overview: " + movie.overview);
                releaseDate.setText("Release Date: " + movie.release_date);
                Picasso.get().load("https://image.tmdb.org/t/p/original" + movie.background).into(background);
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.poster).into(movieImage);
                rotateLoadingMovieImage.stop();
                rotateLoadingBackground.stop();
                String movieGenres="";
                for(int j=0;j<movie.genres.size();j++){
                    movieGenres=movieGenres+movie.genres.get(j).name;
                    if(j<movie.genres.size()-1){
                        movieGenres=movieGenres+", ";
                    }
                }
                genreCollapse.setText(movieGenres);
                titleTextView.setText(movie.original_title);
//                collapsingToolbarLayout.setTitle(movie.original_title);
                }

            @Override
            public void onFailure(retrofit2.Call<MovieDetails> call, Throwable t) {

            }
        });

        //FOR SIMILAR MOVIES
        retrofit2.Call<MovieResponse> callSimilar= APIClient.getMovieService().getSimilarMovies(id);
        callSimilar.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse=response.body();
                similarMoviesList.clear();
                for(int i=0;i<movieResponse.results.size();i++){
                    similarMoviesList.add(movieResponse.results.get(i));
                    adapterSimilarMovies.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<MovieResponse> call, Throwable t) {

            }
        });

        //FOR RECOMM MOVIES
        retrofit2.Call<MovieResponse> callRecomm= APIClient.getMovieService().getRecommMovies(id);
        callRecomm.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse=response.body();
                recommMoviesList.clear();
                for(int i=0;i<movieResponse.results.size();i++){
                    recommMoviesList.add(movieResponse.results.get(i));
                    adapterRecommMovies.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<MovieResponse> call, Throwable t) {

            }
        });

        //FOR CAST
        retrofit2.Call<CastResponse> callCast= APIClient.getMovieService().getCast(id);
        callCast.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CastResponse> call, Response<CastResponse> response) {
                CastResponse castResponse=response.body();
                castPeople.clear();
                for(int i=0;i<castResponse.cast.size();i++){
                    castPeople.add(castResponse.cast.get(i));
                    castAdapter.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CastResponse> call, Throwable t) {

            }
        });

        //FOR TRAILERS
        retrofit2.Call<VideoResponse> callVideo= APIClient.getMovieService().getMovieTrailers(id);
        callVideo.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(retrofit2.Call<VideoResponse> call, Response<VideoResponse> response) {
                VideoResponse videoResponse=response.body();
                trailerVideos.clear();
                for(int i=0;i<videoResponse.results.size();i++){
                    trailerVideos.add(videoResponse.results.get(i));
                    trailerAdapter.notifyItemInserted(i);
                }

            }

            @Override
            public void onFailure(retrofit2.Call<VideoResponse> call, Throwable t) {

            }
        });

        similarMovies.setAdapter(adapterSimilarMovies);
        similarMovies.setLayoutManager(layoutManagerSM);
        cast.setAdapter(castAdapter);
        cast.setLayoutManager(layoutManagerCast);
        trailers.setAdapter(trailerAdapter);
        trailers.setLayoutManager(layoutManagerTrailer);
        recommendedMovies.setAdapter(adapterRecommMovies);
        recommendedMovies.setLayoutManager(layoutManagerRM);

    }
}
