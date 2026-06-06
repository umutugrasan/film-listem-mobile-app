package com.example.odev;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.odev.data.ApiClient;
import com.example.odev.data.FirestoreHelper;
import com.example.odev.data.Movie;
import com.example.odev.data.MovieDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Film/dizi detay ekrani: OMDb'den detay cek, listeye ekle/cikar, izledim isaretle
public class DetailActivity extends AppCompatActivity {

    private ImageView ivPoster;
    private TextView tvBaslik, tvAltBilgi, tvPuan, tvOzet;
    private CheckBox cbIzledim;
    private Button btnKaydet;
    private ProgressBar progress;
    private ScrollView scroll;

    private String imdbId;
    private boolean kayitli;          // su an listede mi?
    private Movie aktifFilm;          // ekrandaki film

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        ivPoster = findViewById(R.id.ivPoster);
        tvBaslik = findViewById(R.id.tvBaslik);
        tvAltBilgi = findViewById(R.id.tvAltBilgi);
        tvPuan = findViewById(R.id.tvPuan);
        tvOzet = findViewById(R.id.tvOzet);
        cbIzledim = findViewById(R.id.cbIzledim);
        btnKaydet = findViewById(R.id.btnKaydet);
        progress = findViewById(R.id.progress);
        scroll = findViewById(R.id.scroll);

        imdbId = getIntent().getStringExtra(Constants.EXTRA_IMDB_ID);
        boolean fromList = getIntent().getBooleanExtra(Constants.EXTRA_FROM_LIST, false);

        if (TextUtils.isEmpty(imdbId)) {
            Toast.makeText(this, R.string.hata_genel, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnKaydet.setOnClickListener(v -> ekleVeyaCikar());
        cbIzledim.setOnCheckedChangeListener((b, isChecked) -> izledimGuncelle(isChecked));

        // Listeden geldiyse once Firestore'a bak, yoksa direkt OMDb'den cek
        if (fromList) {
            firestoreOku();
        } else {
            apiDetayCek();
        }
    }

    // Firestore'dan oku
    private void firestoreOku() {
        progress.setVisibility(View.VISIBLE);
        FirestoreHelper.moviesCollection().document(imdbId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        Movie m = doc.toObject(Movie.class);
                        kayitli = true;
                        ekraniGoster(m);
                    } else {
                        // Dokuman silinmis olabilir, API'den cek
                        apiDetayCek();
                    }
                })
                .addOnFailureListener(e -> {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(this, R.string.hata_genel, Toast.LENGTH_SHORT).show();
                });
    }

    // OMDb API'den detay cek
    private void apiDetayCek() {
        if (TextUtils.isEmpty(Constants.OMDB_API_KEY)
                || Constants.OMDB_API_KEY.equals("YOUR_OMDB_API_KEY")) {
            Toast.makeText(this, R.string.hata_apikey, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        progress.setVisibility(View.VISIBLE);
        ApiClient.getApi().detail(Constants.OMDB_API_KEY, imdbId).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetail> call,
                                   @NonNull Response<MovieDetail> response) {
                MovieDetail d = response.body();
                if (d != null && "True".equalsIgnoreCase(d.getResponse())) {
                    Movie m = new Movie(
                            d.getImdbId(), d.getTitle(), d.getYear(), d.getType(),
                            d.getPoster(), d.getPlot(), d.getImdbRating());
                    // Listede mi diye Firestore'dan kontrol et
                    FirestoreHelper.moviesCollection().document(imdbId).get()
                            .addOnSuccessListener(doc -> {
                                if (doc.exists()) {
                                    kayitli = true;
                                    Movie kayitliMovie = doc.toObject(Movie.class);
                                    if (kayitliMovie != null) m.setWatched(kayitliMovie.isWatched());
                                }
                                ekraniGoster(m);
                            })
                            .addOnFailureListener(e -> ekraniGoster(m));
                } else {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(DetailActivity.this, R.string.hata_genel,
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(DetailActivity.this, R.string.hata_internet,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // Veri geldikten sonra ekranda goster
    private void ekraniGoster(Movie m) {
        aktifFilm = m;
        progress.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);

        tvBaslik.setText(m.getTitle());

        // Yil / Tur / Sure satiri
        StringBuilder sb = new StringBuilder();
        if (m.getYear() != null) sb.append(m.getYear());
        if (m.getType() != null) sb.append("  ·  ").append(m.getType().toUpperCase());
        tvAltBilgi.setText(sb.toString());

        if (m.getImdbRating() != null && !m.getImdbRating().equals("N/A")) {
            tvPuan.setText(m.getImdbRating());
        } else {
            tvPuan.setText("-");
        }

        if (m.getPlot() != null && !m.getPlot().equals("N/A")) {
            tvOzet.setText(m.getPlot());
        } else {
            tvOzet.setText("-");
        }

        if (m.getPoster() != null && !m.getPoster().equals("N/A")) {
            Glide.with(this).load(m.getPoster()).into(ivPoster);
        }

        cbIzledim.setChecked(m.isWatched());
        butonuYazdir();
    }

    // Ekle/Cikar butonunun yazisini guncelle
    private void butonuYazdir() {
        btnKaydet.setText(kayitli ? R.string.listemden_cikar : R.string.listeme_ekle);
    }

    // Butona basildiginda: kayitliysa sil, degilse ekle
    private void ekleVeyaCikar() {
        if (aktifFilm == null) return;
        if (kayitli) {
            FirestoreHelper.moviesCollection().document(imdbId).delete()
                    .addOnSuccessListener(a -> {
                        kayitli = false;
                        butonuYazdir();
                        Toast.makeText(this, R.string.cikarildi, Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, R.string.hata_genel,
                                    Toast.LENGTH_SHORT).show());
        } else {
            aktifFilm.setAddedAt(System.currentTimeMillis());
            FirestoreHelper.moviesCollection().document(imdbId).set(aktifFilm)
                    .addOnSuccessListener(a -> {
                        kayitli = true;
                        butonuYazdir();
                        Toast.makeText(this, R.string.eklendi, Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, R.string.hata_genel,
                                    Toast.LENGTH_SHORT).show());
        }
    }

    // Izledim durumu degisince Firestore'a yaz (sadece kayitliysa)
    private void izledimGuncelle(boolean watched) {
        if (aktifFilm == null) return;
        aktifFilm.setWatched(watched);
        if (kayitli) {
            FirestoreHelper.moviesCollection().document(imdbId)
                    .update("watched", watched);
        }
    }
}
