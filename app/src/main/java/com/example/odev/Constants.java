package com.example.odev;

// Uygulama genelinde kullanilan sabit degerler
public class Constants {

    // OMDb API anahtari (https://www.omdbapi.com/apikey.aspx adresinden alinabilir)
    // NOT: Anahtari github'a koymamak icin burada bos birak ve calistirmadan once doldur.
    public static final String OMDB_API_KEY = "91f93dce";

    // OMDb API'nin temel adresi
    public static final String OMDB_BASE_URL = "http://www.omdbapi.com/";

    // Firestore koleksiyon adi
    public static final String MOVIES_COLLECTION = "movies";

    // Profil bilgilerinin tutuldugu koleksiyon ve dokuman
    public static final String PROFILE_COLLECTION = "profile";
    public static final String PROFILE_DOC = "me";

    // Intent extra anahtarlari
    public static final String EXTRA_IMDB_ID = "imdb_id";
    public static final String EXTRA_FROM_LIST = "from_list";
}
