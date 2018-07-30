package com.example.de.moviedb2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getPopularMovies();

    @GET("movie/popular")
    Call<MovieResponse> getPopularMoviesNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

    @GET("movie/now_playing?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getNowPlaying();

    @GET("movie/upcoming?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getUpcoming();

    @GET("movie/top_rated?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getTopRated();

    @GET("genre/movie/list?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US")
    Call<GenreResponse> getGenres();

    @GET("movie/{id}?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US")
    Call<MovieDetails> getMovieDetails(@Path("id") long id);

    @GET("movie/{id}/similar?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getSimilarMovies(@Path("id") long id);

    @GET("movie/{id}/recommendations?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getRecommMovies(@Path("id") long id);

    @GET("movie/{id}/credits?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f")
    Call<CastResponse> getCast(@Path("id") long id);

    @GET("person/{id}?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US")
    Call<CastDisplayResponse> getCastPerson(@Path("id") long id);

    @GET("person/{id}/movie_credits?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US")
    Call<CastPersonMovieResponse> getCastMovies(@Path("id") long id);

    @GET("person/{id}/tv_credits?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US")
    Call<CastPersonMovieResponse> getCastShows(@Path("id") long id);

    @GET("movie/{id}/videos?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US")
    Call<VideoResponse> getMovieTrailers(@Path("id") long id);

    @GET("person/{id}/tagged_images?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<CastPersonPictureResponse> getPictures(@Path("id") long id);

    @GET("tv/popular?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getPopularTV();

    @GET("tv/airing_today?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getAiringTodayTV();

    @GET("tv/on_the_air?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getOnAirTV();

    @GET("tv/top_rated?api_key=d5a4a4cf505e0c8b4d9eea8ba684af9f&language=en-US&page=1")
    Call<MovieResponse> getTopRatedTV();

    @GET("tv/popular")
    Call<MovieResponse> getPopularTVNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

    @GET("tv/top_rated")
    Call<MovieResponse> getTopRatedTVNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

    @GET("tv/airing_today")
    Call<MovieResponse> getAiringTodayNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

    @GET("tv/on_the_air")
    Call<MovieResponse> getOnAirNextPage(@Query("api_key")String key, @Query("language")String lang, @Query("page") int page);

}
