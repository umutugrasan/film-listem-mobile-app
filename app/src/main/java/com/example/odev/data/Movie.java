package com.example.odev.data;

// Firestore'a kaydedilen film modeli (POJO).
// Firestore otomatik serialize edebilmek icin bos constructor + get/set ister.
public class Movie {

    private String imdbId;
    private String title;
    private String year;
    private String type;     // movie, series, episode
    private String poster;   // poster URL'i
    private String plot;     // ozet
    private String imdbRating;
    private boolean watched;
    private long addedAt;    // listeye eklenme zamani (millis)

    // Firestore icin bos constructor zorunlu
    public Movie() {
    }

    public Movie(String imdbId, String title, String year, String type,
                 String poster, String plot, String imdbRating) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
        this.plot = plot;
        this.imdbRating = imdbRating;
        this.watched = false;
        this.addedAt = System.currentTimeMillis();
    }

    public String getImdbId() { return imdbId; }
    public void setImdbId(String imdbId) { this.imdbId = imdbId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public String getPlot() { return plot; }
    public void setPlot(String plot) { this.plot = plot; }

    public String getImdbRating() { return imdbRating; }
    public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }

    public boolean isWatched() { return watched; }
    public void setWatched(boolean watched) { this.watched = watched; }

    public long getAddedAt() { return addedAt; }
    public void setAddedAt(long addedAt) { this.addedAt = addedAt; }
}
