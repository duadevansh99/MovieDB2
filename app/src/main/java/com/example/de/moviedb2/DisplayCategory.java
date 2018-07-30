package com.example.de.moviedb2;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.book.BookLoading;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayCategory extends AppCompatActivity {

    public static final String DISPLAY_MOVIE="open movie for display from main activity";
    public static final String DISPLAY_TV_SHOW="open show for display from main activity";
    TextView textViewToolbar;
    RecyclerView recyclerView;
    ArrayList<MovieResults> movies=new ArrayList<>();
    ArrayList<MovieResults> shows=new ArrayList<>();
    ArrayList<Genres> genres=new ArrayList<>();
    MovieResponse movieResponse;
    DisplayCategoryRVAdapter adapter;
    Boolean isScrolling = true;
    int currentItems,totalItems,scrolledItems,currentPage=1;
    String category;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_category);
        textViewToolbar=findViewById(R.id.toolbarTextView);
        recyclerView=findViewById(R.id.recyclerViewDisplayCategory);
        Toolbar toolbar = findViewById(R.id.toolbarDisplayCategory);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("");
        }

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        category=bundle.getString("category");

        textViewToolbar.setText(category);

        if(movies != null){

            adapter=new DisplayCategoryRVAdapter(movies, genres, this, new MovieClickListener() {
                @Override
                public void onMovieClicked(View view, int position) {
                    MovieResults movie= movies.get(position);
                    long id= movie.id;
                    Intent sendIntent= new Intent(DisplayCategory.this,DisplayMovieItem.class);
                    sendIntent.putExtra(DISPLAY_MOVIE,id);
                    startActivity(sendIntent);
                }
            });

        }
        else if(shows!= null){
            adapter=new DisplayCategoryRVAdapter(shows, genres, this, new MovieClickListener() {
                @Override
                public void onMovieClicked(View view, int position) {
                    MovieResults show= shows.get(position);
                    long id= show.id;
                    Intent sendIntent= new Intent(DisplayCategory.this,DisplayMovieItem.class);
                    sendIntent.putExtra(DISPLAY_TV_SHOW,id);
                    startActivity(sendIntent);
                }
            });

        }

        Call<MovieResponse> call;
        if(category.equals("Popular")){
            call=APIClient.getMovieService().getPopularMovies();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse=response.body();
                    movies.clear();
                    for(int i=0;i<movieResponse.results.size();i++){
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);
                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        else if(category.equals("Now Playing")){
            call=APIClient.getMovieService().getNowPlaying();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse = response.body();
                    movies.clear();
                    for (int i = 0; i < movieResponse.results.size(); i++) {
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);

                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        else if(category.equals("Top Rated")){
            call=APIClient.getMovieService().getTopRated();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse = response.body();
                    movies.clear();
                    for (int i = 0; i < movieResponse.results.size(); i++) {
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);

                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        else if(category.equals("Upcoming")){
            call=APIClient.getMovieService().getUpcoming();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse = response.body();
                    movies.clear();
                    for (int i = 0; i < movieResponse.results.size(); i++) {
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);

                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        else if(category.equals("Popular TV")){
            call=APIClient.getMovieService().getPopularTV();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse = response.body();
                    movies.clear();
                    for (int i = 0; i < movieResponse.results.size(); i++) {
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);

                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        else if(category.equals("Airing Today")){
            call=APIClient.getMovieService().getAiringTodayTV();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse = response.body();
                    movies.clear();
                    for (int i = 0; i < movieResponse.results.size(); i++) {
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);

                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        else if(category.equals("On The Air")){
            call=APIClient.getMovieService().getOnAirTV();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse = response.body();
                    movies.clear();
                    for (int i = 0; i < movieResponse.results.size(); i++) {
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);

                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
        else if(category.equals("Top Rated TV")){
            call=APIClient.getMovieService().getTopRatedTV();
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    movieResponse = response.body();
                    movies.clear();
                    for (int i = 0; i < movieResponse.results.size(); i++) {
                        movies.add(movieResponse.results.get(i));
                        adapter.notifyItemInserted(i);

                    }
                }
                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }


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

        final GridLayoutManager layoutManager=new GridLayoutManager(this, 3);
        FlipInBottomXAnimator animator1=new FlipInBottomXAnimator();
        animator1.setInterpolator(new OvershootInterpolator());
        recyclerView.setItemAnimator(animator1);
        recyclerView.getItemAnimator().setAddDuration(600);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }

            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems=layoutManager.getChildCount();
                totalItems= layoutManager.getItemCount();
                scrolledItems=layoutManager.findFirstVisibleItemPosition();
                if(isScrolling&&(currentItems+scrolledItems==totalItems)){
                    currentPage++;
                    isScrolling = false;
                    Call<MovieResponse> callNextPage;
                    if(category.equals("Popular")){
                        callNextPage=APIClient.getMovieService().getPopularMoviesNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Toast.makeText(DisplayCategory.this,"failure",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(category.equals("Now Playing")){
                        callNextPage=APIClient.getMovieService().getNowPlayingNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
                    }
                    else if(category.equals("Top Rated")){
                        callNextPage=APIClient.getMovieService().getTopRatedNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
                    }
                    else if(category.equals("Upcoming")){
                        callNextPage=APIClient.getMovieService().getUpcomingNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
                    }
                    else if(category.equals("Popular TV")){
                        callNextPage=APIClient.getMovieService().getPopularTVNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
                    }
                    else if(category.equals("Airing Today")){
                        callNextPage=APIClient.getMovieService().getAiringTodayNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
                    }
                    else if(category.equals("On The Air")){
                        callNextPage=APIClient.getMovieService().getOnAirNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
                    }
                    else if(category.equals("Top Rated Tv")){
                        callNextPage=APIClient.getMovieService().getTopRatedTVNextPage("d5a4a4cf505e0c8b4d9eea8ba684af9f","en-US",currentPage);
                        callNextPage.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                MovieResponse movieResponse=response.body();
                                movies.addAll(movieResponse.results);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
                    }

                }
            }
        });

    }

}
