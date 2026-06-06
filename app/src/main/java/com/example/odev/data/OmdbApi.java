package com.example.odev.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// OMDb API icin Retrofit interface'i
public interface OmdbApi {

    // Anahtar kelimeyle film/dizi arama (s = search)
    @GET("/")
    Call<SearchResponse> search(
            @Query("apikey") String apiKey,
            @Query("s") String query);

    // imdbID ile detay alma (i = id)
    @GET("/")
    Call<MovieDetail> detail(
            @Query("apikey") String apiKey,
            @Query("i") String imdbId);
}
