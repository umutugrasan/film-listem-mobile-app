package com.example.odev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.odev.data.Movie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

// Uygulamanin acilis ekrani: kullanicinin listesindeki filmler
public class MainActivity extends AppCompatActivity {

    private RecyclerView rvFilmler;
    private TextView tvBosListe;
    private MovieAdapter adapter;
    private final List<Movie> liste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvFilmler = findViewById(R.id.rvFilmler);
        tvBosListe = findViewById(R.id.tvBosListe);
        FloatingActionButton fabEkle = findViewById(R.id.fabEkle);

        // Sag ust profil ikonuna basinca profile ekranina git
        findViewById(R.id.btnProfil).setOnClickListener(v -> {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        });

        // Liste icin adapter ve layout manager
        adapter = new MovieAdapter(this, liste, this::filmeTikla);
        rvFilmler.setLayoutManager(new LinearLayoutManager(this));
        rvFilmler.setAdapter(adapter);

        // FAB'a tiklayinca arama ekranina git
        fabEkle.setOnClickListener(v -> {
            Intent i = new Intent(this, SearchActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ekran her acildiginda listeyi tazele
        listeyiYukle();
    }

    // Karta tiklaninca detay ekranina git
    private void filmeTikla(Movie m) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(Constants.EXTRA_IMDB_ID, m.getImdbId());
        i.putExtra(Constants.EXTRA_FROM_LIST, true);
        startActivity(i);
    }

    // Firestore'dan film listesini cek
    private void listeyiYukle() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.MOVIES_COLLECTION)
                .orderBy("addedAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(snap -> {
                    liste.clear();
                    for (QueryDocumentSnapshot doc : snap) {
                        Movie m = doc.toObject(Movie.class);
                        liste.add(m);
                    }
                    adapter.notifyDataSetChanged();
                    // Liste bossa mesaj goster
                    tvBosListe.setVisibility(liste.isEmpty() ? View.VISIBLE : View.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, getString(R.string.hata_genel), Toast.LENGTH_SHORT).show();
                });
    }
}
