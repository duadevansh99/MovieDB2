package com.example.de.moviedb2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit;

    private static MovieService service;

    static  Retrofit getInstance(){
       if(retrofit==null){
           Retrofit.Builder builder=new Retrofit.Builder()
                   .baseUrl("https://api.themoviedb.org/3/")
                   .addConverterFactory(GsonConverterFactory.create());
           retrofit =builder.build();
       }
       return retrofit;
    }

    static  MovieService getMovieService(){
        if(service==null){
            service=getInstance().create(MovieService.class);
        }
        return service;
    }
}
