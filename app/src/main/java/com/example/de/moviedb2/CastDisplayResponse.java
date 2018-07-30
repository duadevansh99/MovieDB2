package com.example.de.moviedb2;

import com.google.gson.annotations.SerializedName;

public class CastDisplayResponse {

    String name, biography, place_of_birth, birthday, deathday;
    @SerializedName("profile_path")
    String castDP;

}
