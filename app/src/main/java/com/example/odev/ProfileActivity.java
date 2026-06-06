package com.example.odev;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// Kullanici profili ekrani: hem yerel (SharedPreferences) hem bulut (Firestore) saklanir
public class ProfileActivity extends AppCompatActivity {

    private static final String PREFS = "odev_prefs";
    private static final String KEY_AD = "ad";
    private static final String KEY_TUR = "favori_tur";
    private static final String KEY_HAKKIMDA = "hakkimda";

    private EditText etAd, etFavoriTur, etHakkimda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Geri ok
        findViewById(R.id.btnGeri).setOnClickListener(v -> finish());

        etAd = findViewById(R.id.etAd);
        etFavoriTur = findViewById(R.id.etFavoriTur);
        etHakkimda = findViewById(R.id.etHakkimda);
        Button btnKaydet = findViewById(R.id.btnKaydet);

        // Once yerelden yukle (hizli)
        yereldenYukle();

        // Sonra Firestore'dan tazele (varsa)
        firestoreUzerindenYukle();

        // Kaydet butonu
        btnKaydet.setOnClickListener(v -> kaydet());
    }

    // SharedPreferences'tan onceki kayitli degerleri al
    private void yereldenYukle() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        etAd.setText(prefs.getString(KEY_AD, ""));
        etFavoriTur.setText(prefs.getString(KEY_TUR, ""));
        etHakkimda.setText(prefs.getString(KEY_HAKKIMDA, ""));
    }

    // Firestore'dan profil bilgisini al ve forma bas
    private void firestoreUzerindenYukle() {
        FirebaseFirestore.getInstance()
                .collection(Constants.PROFILE_COLLECTION)
                .document(Constants.PROFILE_DOC)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String ad = doc.getString("ad");
                        String tur = doc.getString("favori_tur");
                        String hakkimda = doc.getString("hakkimda");
                        if (ad != null) etAd.setText(ad);
                        if (tur != null) etFavoriTur.setText(tur);
                        if (hakkimda != null) etHakkimda.setText(hakkimda);
                    }
                });
    }

    // Form verilerini hem SharedPreferences'a hem Firestore'a yaz
    private void kaydet() {
        String ad = etAd.getText().toString().trim();
        String tur = etFavoriTur.getText().toString().trim();
        String hakkimda = etHakkimda.getText().toString().trim();

        // Yerel kayit
        SharedPreferences.Editor editor =
                getSharedPreferences(PREFS, MODE_PRIVATE).edit();
        editor.putString(KEY_AD, ad);
        editor.putString(KEY_TUR, tur);
        editor.putString(KEY_HAKKIMDA, hakkimda);
        editor.apply();

        // Bulut kayit (Firestore)
        Map<String, Object> veri = new HashMap<>();
        veri.put("ad", ad);
        veri.put("favori_tur", tur);
        veri.put("hakkimda", hakkimda);
        veri.put("guncellendi", System.currentTimeMillis());

        FirebaseFirestore.getInstance()
                .collection(Constants.PROFILE_COLLECTION)
                .document(Constants.PROFILE_DOC)
                .set(veri)
                .addOnSuccessListener(a -> {
                    Toast.makeText(this, R.string.ayarlar_kaydedildi,
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Bulut basarisiz olsa bile yerel kayit var, kullaniciyi cikar
                    Toast.makeText(this, R.string.ayarlar_kaydedildi,
                            Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}
