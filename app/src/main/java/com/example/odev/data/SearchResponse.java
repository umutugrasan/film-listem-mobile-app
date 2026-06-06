package com.example.odev.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// OMDb arama (s=) cevabi
public class SearchResponse {

    @SerializedName("Search")
    private List<SearchItem> search;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("Response")
    private String response;

    @SerializedName("Error")
    private String error;

    public List<SearchItem> getSearch() { return search; }
    public String getTotalResults() { return totalResults; }
    public String getResponse() { return response; }
    public String getError() { return error; }
}
