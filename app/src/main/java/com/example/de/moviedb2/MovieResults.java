package com.example.de.moviedb2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResults {

    long id;
    String title;
    String name;
    @SerializedName("poster_path")
    String poster;
    @SerializedName("background_path")
    String background;
    float vote_average;
    ArrayList<Integer> genre_ids;
    String original_language;

}
