package com.example.odev.data;

import com.google.gson.annotations.SerializedName;

// OMDb detay (i=imdbId) cevabi
public class MovieDetail {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Rated")
    private String rated;

    @SerializedName("Runtime")
    private String runtime;

    @SerializedName("Genre")
    private String genre;

    @SerializedName("Director")
    private String director;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Poster")
    private String poster;

    @SerializedName("imdbRating")
    private String imdbRating;

    @SerializedName("imdbID")
    private String imdbId;

    @SerializedName("Type")
    private String type;

    @SerializedName("Response")
    private String response;

    @SerializedName("Error")
    private String error;

    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getRated() { return rated; }
    public String getRuntime() { return runtime; }
    public String getGenre() { return genre; }
    public String getDirector() { return director; }
    public String getActors() { return actors; }
    public String getPlot() { return plot; }
    public String getPoster() { return poster; }
    public String getImdbRating() { return imdbRating; }
    public String getImdbId() { return imdbId; }
    public String getType() { return type; }
    public String getResponse() { return response; }
    public String getError() { return error; }
}
