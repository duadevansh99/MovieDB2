package com.example.de.moviedb2;

import com.google.gson.annotations.SerializedName;

public class CastPerson {

    long id;
    String name,character;
    @SerializedName("profile_path")
    String dpCastPerson;

}
