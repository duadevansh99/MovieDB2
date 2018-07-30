package com.example.de.moviedb2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieDetails {

    String original_title;
    @SerializedName("poster_path")
    String poster;
    @SerializedName("backdrop_path")
    String background;
    float vote_average;
    ArrayList<Genres> genres;
    String original_language;
    int runtime;
    String release_date;
    String overview;

}
