package com.example.odev.data;

import com.example.odev.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit istemcisini tekil olarak tutuyoruz
public class ApiClient {

    private static Retrofit retrofit;

    // Retrofit nesnesi yoksa yarat, varsa ayni nesneyi don
    public static OmdbApi getApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.OMDB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(OmdbApi.class);
    }
}
