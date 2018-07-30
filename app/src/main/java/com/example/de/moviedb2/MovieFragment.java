package com.example.de.moviedb2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 */
public class MovieFragment extends android.support.v4.app.Fragment {

    ArrayList<MovieResults> movieResultsPopular=new ArrayList<>();
    ArrayList<MovieResults> movieResultsNowPlaying=new ArrayList<>();
    ArrayList<MovieResults> movieResultsUpcoming=new ArrayList<>();
    ArrayList<MovieResults> movieResultsTopRated=new ArrayList<>();
    ArrayList<Genres> genres=new ArrayList<>();
    movieFragmentCallback listener;
    BookLoading bookLoading1,bookLoading2,bookLoading3,bookLoading4;

    ItemAdapter adapter2,adapter3,adapter4;
    MovieAutoAdapter adapter1;
    RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4;
    LinearLayoutManager layoutManager1=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManager2=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManager3=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManager4=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

    MovieResponse movieResponse;

    Timer timer;
    int cursor=0;

    @Override
    public void onStart() {
        super.onStart();
        timer= new Timer();
        timer.scheduleAtFixedRate(new MovieTimerTask(), 5000, 5000);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(timer!=null){
            timer.cancel();
        }
    }

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter1= new MovieAutoAdapter(movieResultsPopular, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=movieResultsPopular.get(position);
                long id=movie.id;
                listener.onMovieClicked(id);
            }
        });

        adapter2=new ItemAdapter(movieResultsNowPlaying,genres, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=movieResultsNowPlaying.get(position);
                long id=movie.id;
                listener.onMovieClicked(id);
            }
        });

        adapter3=new ItemAdapter(movieResultsUpcoming,genres, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=movieResultsUpcoming.get(position);
                long id=movie.id;
                listener.onMovieClicked(id);
            }
        });

        adapter4=new ItemAdapter(movieResultsTopRated,genres, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=movieResultsTopRated.get(position);
                long id=movie.id;
                listener.onMovieClicked(id);
            }
        });

        Call<MovieResponse> call1=APIClient.getMovieService().getPopularMovies();
        call1.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                movieResultsPopular.clear();
                bookLoading1.stop();
                bookLoading1.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    movieResultsPopular.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment","Movie Added");
                    adapter1.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        Call<MovieResponse> call2=APIClient.getMovieService().getNowPlaying();
        call2.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                movieResultsNowPlaying.clear();
                bookLoading2.stop();
                bookLoading2.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    movieResultsNowPlaying.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment","Movie Added");
                    adapter2.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        Call<MovieResponse> call3=APIClient.getMovieService().getUpcoming();
        call3.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                movieResultsUpcoming.clear();
                bookLoading3.stop();
                bookLoading3.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    movieResultsUpcoming.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment","Movie Added");
                    adapter3.notifyItemInserted(i);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        Call<MovieResponse> call4=APIClient.getMovieService().getTopRated();
        call4.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                movieResultsTopRated.clear();
                bookLoading4.stop();
                bookLoading4.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    movieResultsTopRated.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment","Movie Added");
                    adapter4.notifyItemInserted(i);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output=inflater.inflate(R.layout.fragment_movie, container, false);
        bookLoading1=output.findViewById(R.id.book1);
        bookLoading1.start();
        recyclerView1=output.findViewById(R.id.recyclerView1);
        ScaleInTopAnimator animator1=new ScaleInTopAnimator();
        animator1.setInterpolator(new OvershootInterpolator());
        recyclerView1.setItemAnimator(animator1);
        recyclerView1.getItemAnimator().setAddDuration(1000);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView1);

        bookLoading2=output.findViewById(R.id.book2);
        bookLoading2.start();
        recyclerView2=output.findViewById(R.id.recyclerView2);
        ScaleInTopAnimator animator2=new ScaleInTopAnimator();
        animator2.setInterpolator(new OvershootInterpolator());
        recyclerView2.setItemAnimator(animator2);
        recyclerView2.getItemAnimator().setAddDuration(1000);

        bookLoading3=output.findViewById(R.id.book3);
        bookLoading3.start();
        recyclerView3=output.findViewById(R.id.recyclerView3);
        ScaleInTopAnimator animator3=new ScaleInTopAnimator();
        animator3.setInterpolator(new OvershootInterpolator());
        recyclerView3.setItemAnimator(animator3);
        recyclerView3.getItemAnimator().setAddDuration(1000);

        bookLoading4=output.findViewById(R.id.book4);
        bookLoading4.start();
        recyclerView4=output.findViewById(R.id.recyclerView4);
        ScaleInTopAnimator animator4=new ScaleInTopAnimator();
        animator4.setInterpolator(new OvershootInterpolator());
        recyclerView4.setItemAnimator(animator4);
        recyclerView4.getItemAnimator().setAddDuration(1000);


        recyclerView1.setAdapter(adapter1);

          recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    cursor= layoutManager1.findLastCompletelyVisibleItemPosition();
                }
            }
        });

        recyclerView1.setLayoutManager(layoutManager1);

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(layoutManager2);

        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(layoutManager3);

        recyclerView4.setAdapter(adapter4);
        recyclerView4.setLayoutManager(layoutManager4);
        return output;
    }


    public interface movieFragmentCallback{

        void onMovieClicked(long id);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof movieFragmentCallback){
            listener=(movieFragmentCallback)context;
        }
    }

    public class MovieTimerTask extends TimerTask{

        final int count=20;

        @Override
        public void run() {
             if(cursor<count-1){
                 getActivity().runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         recyclerView1.scrollToPosition(cursor+1);
                         cursor++;
                     }
                 });
             }
             else{
                 cursor=0;
                 //recyclerView1.scrollToPosition(cursor);
             }

        }
    }

}
