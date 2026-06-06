package com.example.odev;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.odev.data.ApiClient;
import com.example.odev.data.SearchItem;
import com.example.odev.data.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// OMDb API ile film/dizi arama ekrani
public class SearchActivity extends AppCompatActivity {

    private EditText etArama;
    private RecyclerView rvSonuc;
    private ProgressBar progress;
    private TextView tvSonucYok;
    private SearchAdapter adapter;
    private final List<SearchItem> sonuclar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        etArama = findViewById(R.id.etArama);
        rvSonuc = findViewById(R.id.rvSonuc);
        progress = findViewById(R.id.progress);
        tvSonucYok = findViewById(R.id.tvSonucYok);
        Button btnAra = findViewById(R.id.btnAra);

        adapter = new SearchAdapter(this, sonuclar, this::sonucaTikla);
        rvSonuc.setLayoutManager(new LinearLayoutManager(this));
        rvSonuc.setAdapter(adapter);

        btnAra.setOnClickListener(v -> aramaYap());

        // Klavyedeki ara tusu
        etArama.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                aramaYap();
                return true;
            }
            return false;
        });
    }

    // Sonuca tiklayinca detay ekranina git (henuz listede degil)
    private void sonucaTikla(SearchItem item) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra(Constants.EXTRA_IMDB_ID, item.getImdbId());
        i.putExtra(Constants.EXTRA_FROM_LIST, false);
        startActivity(i);
    }

    // OMDb'ye arama istegi gonder
    private void aramaYap() {
        String q = etArama.getText().toString().trim();
        if (TextUtils.isEmpty(q)) {
            Toast.makeText(this, R.string.lutfen_yaz, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Constants.OMDB_API_KEY)
                || Constants.OMDB_API_KEY.equals("YOUR_OMDB_API_KEY")) {
            Toast.makeText(this, R.string.hata_apikey, Toast.LENGTH_LONG).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        tvSonucYok.setVisibility(View.GONE);
        sonuclar.clear();
        adapter.notifyDataSetChanged();

        ApiClient.getApi().search(Constants.OMDB_API_KEY, q).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call,
                                   @NonNull Response<SearchResponse> response) {
                progress.setVisibility(View.GONE);
                SearchResponse body = response.body();
                if (body != null && "True".equalsIgnoreCase(body.getResponse())
                        && body.getSearch() != null) {
                    sonuclar.addAll(body.getSearch());
                    adapter.notifyDataSetChanged();
                    tvSonucYok.setVisibility(View.GONE);
                } else {
                    tvSonucYok.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, R.string.hata_internet,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
