package com.example.de.moviedb2;


import android.content.Context;
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

import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 */
public class TVShowsFragment extends android.support.v4.app.Fragment {


    ArrayList<MovieResults> TVResultsPopular=new ArrayList<>();
    ArrayList<MovieResults> TVResultsAiringToday=new ArrayList<>();
    ArrayList<MovieResults> TVResultsOnAir=new ArrayList<>();
    ArrayList<MovieResults> TVResultsTopRated=new ArrayList<>();
    ArrayList<Genres> genres=new ArrayList<>();
    TVShowsFragmentCallback listener;
    BookLoading bookLoading1TV,bookLoading2TV,bookLoading3TV,bookLoading4TV;

    ItemAdapter adapter2TV,adapter3TV,adapter4TV;
    MovieAutoAdapter adapter1TV;
    RecyclerView recyclerView1TV,recyclerView2TV,recyclerView3TV,recyclerView4TV;
    LinearLayoutManager layoutManager1TV=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManager2TV=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManager3TV=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager layoutManager4TV=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

    MovieResponse movieResponse;

    Timer timer;
    int cursor=0;


    public TVShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        timer= new Timer();
        timer.scheduleAtFixedRate( new MovieTimerTask(), 5000, 5000);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(timer!=null){
            timer.cancel();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter1TV= new MovieAutoAdapter(TVResultsPopular, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=TVResultsPopular.get(position);
                long id=movie.id;
                listener.onShowClicked(id);
            }
        });

        adapter2TV=new ItemAdapter(TVResultsAiringToday,genres, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=TVResultsAiringToday.get(position);
                long id=movie.id;
                listener.onShowClicked(id);
            }
        });

        adapter3TV=new ItemAdapter(TVResultsOnAir,genres, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=TVResultsOnAir.get(position);
                long id=movie.id;
                listener.onShowClicked(id);
            }
        });

        adapter4TV=new ItemAdapter(TVResultsTopRated,genres, getContext(), new MovieClickListener() {
            @Override
            public void onMovieClicked(View view, int position) {
                MovieResults movie=TVResultsTopRated.get(position);
                long id=movie.id;
                listener.onShowClicked(id);
            }
        });

        Call<MovieResponse> call1=APIClient.getMovieService().getPopularTV();
        call1.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                TVResultsPopular.clear();
                bookLoading1TV.stop();
                bookLoading1TV.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    TVResultsPopular.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment",movieResponse.results.get(i).name);
                    adapter1TV.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        Call<MovieResponse> call2=APIClient.getMovieService().getAiringTodayTV();
        call2.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                TVResultsAiringToday.clear();
                bookLoading2TV.stop();
                bookLoading2TV.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    TVResultsAiringToday.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment","Movie Added");
                    adapter2TV.notifyItemInserted(i);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        Call<MovieResponse> call3=APIClient.getMovieService().getOnAirTV();
        call3.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                TVResultsOnAir.clear();
                bookLoading3TV.stop();
                bookLoading3TV.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    TVResultsOnAir.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment","Movie Added");
                    adapter3TV.notifyItemInserted(i);
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        Call<MovieResponse> call4=APIClient.getMovieService().getTopRatedTV();
        call4.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieResponse=response.body();
                TVResultsTopRated.clear();
                bookLoading4TV.stop();
                bookLoading4TV.setVisibility(View.GONE);
                for(int i=0;i<movieResponse.results.size();i++){
                    TVResultsTopRated.add(movieResponse.results.get(i));
                    //Log.i("MovieFragment","Movie Added");
                    adapter4TV.notifyItemInserted(i);
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
        View output=inflater.inflate(R.layout.fragment_tvshows, container, false);

        bookLoading1TV=output.findViewById(R.id.book1TV);
        bookLoading1TV.start();
        recyclerView1TV=output.findViewById(R.id.recyclerView1TV);
        ScaleInTopAnimator animator1=new ScaleInTopAnimator();
        animator1.setInterpolator(new OvershootInterpolator());
        recyclerView1TV.setItemAnimator(animator1);
        recyclerView1TV.getItemAnimator().setAddDuration(1000);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView1TV);

        bookLoading2TV=output.findViewById(R.id.book2TV);
        bookLoading2TV.start();
        recyclerView2TV=output.findViewById(R.id.recyclerView2TV);
        ScaleInTopAnimator animator2=new ScaleInTopAnimator();
        animator2.setInterpolator(new OvershootInterpolator());
        recyclerView2TV.setItemAnimator(animator2);
        recyclerView2TV.getItemAnimator().setAddDuration(1000);

        bookLoading3TV=output.findViewById(R.id.book3TV);
        bookLoading3TV.start();
        recyclerView3TV=output.findViewById(R.id.recyclerView3TV);
        ScaleInTopAnimator animator3=new ScaleInTopAnimator();
        animator3.setInterpolator(new OvershootInterpolator());
        recyclerView3TV.setItemAnimator(animator3);
        recyclerView3TV.getItemAnimator().setAddDuration(1000);

        bookLoading4TV=output.findViewById(R.id.book4TV);
        bookLoading4TV.start();
        recyclerView4TV=output.findViewById(R.id.recyclerView4TV);
        ScaleInTopAnimator animator4=new ScaleInTopAnimator();
        animator4.setInterpolator(new OvershootInterpolator());
        recyclerView4TV.setItemAnimator(animator4);
        recyclerView4TV.getItemAnimator().setAddDuration(1000);


        recyclerView1TV.setAdapter(adapter1TV);

        recyclerView1TV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    cursor= layoutManager1TV.findLastCompletelyVisibleItemPosition();
                }
            }
        });

        recyclerView1TV.setLayoutManager(layoutManager1TV);

        recyclerView2TV.setAdapter(adapter2TV);
        recyclerView2TV.setLayoutManager(layoutManager2TV);

        recyclerView3TV.setAdapter(adapter3TV);
        recyclerView3TV.setLayoutManager(layoutManager3TV);

        recyclerView4TV.setAdapter(adapter4TV);
        recyclerView4TV.setLayoutManager(layoutManager4TV);


        return  output;
    }

    public interface TVShowsFragmentCallback{

        void onShowClicked(long id);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof TVShowsFragmentCallback){
            listener=(TVShowsFragmentCallback)context;
        }
    }

    public class MovieTimerTask extends TimerTask {

        final int count=20;

        @Override
        public void run() {
            if(cursor<count-1){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView1TV.scrollToPosition(cursor+1);
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
