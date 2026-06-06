package com.example.odev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.odev.data.Movie;

import java.util.List;

// Listem ekranindaki film kartlari icin adapter
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VH> {

    public interface OnItemClick {
        void onClick(Movie movie);
    }

    private final Context context;
    private final List<Movie> liste;
    private final OnItemClick clickListener;

    public MovieAdapter(Context context, List<Movie> liste, OnItemClick clickListener) {
        this.context = context;
        this.liste = liste;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Movie m = liste.get(position);

        h.tvBaslik.setText(m.getTitle());
        h.tvYil.setText(context.getString(R.string.yil) + " " + (m.getYear() != null ? m.getYear() : "-"));
        h.tvTur.setText(context.getString(R.string.tur) + " " + (m.getType() != null ? m.getType() : "-"));

        // IMDb puani yoksa gizle
        if (m.getImdbRating() != null && !m.getImdbRating().equals("N/A")) {
            h.tvPuan.setText(m.getImdbRating());
            h.tvPuan.setVisibility(View.VISIBLE);
            h.ivYildiz.setVisibility(View.VISIBLE);
        } else {
            h.tvPuan.setVisibility(View.GONE);
            h.ivYildiz.setVisibility(View.GONE);
        }

        // Izlendi rozeti
        h.tvIzlendi.setVisibility(m.isWatched() ? View.VISIBLE : View.GONE);

        // Poster goster
        String url = m.getPoster();
        if (url != null && !url.equals("N/A")) {
            Glide.with(context).load(url).into(h.ivPoster);
        } else {
            h.ivPoster.setImageDrawable(null);
        }

        // Karta tikla
        h.itemView.setOnClickListener(v -> clickListener.onClick(m));
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivPoster, ivYildiz;
        TextView tvBaslik, tvYil, tvTur, tvPuan, tvIzlendi;

        VH(View v) {
            super(v);
            ivPoster = v.findViewById(R.id.ivPoster);
            ivYildiz = v.findViewById(R.id.ivYildiz);
            tvBaslik = v.findViewById(R.id.tvBaslik);
            tvYil = v.findViewById(R.id.tvYil);
            tvTur = v.findViewById(R.id.tvTur);
            tvPuan = v.findViewById(R.id.tvPuan);
            tvIzlendi = v.findViewById(R.id.tvIzlendi);
        }
    }
}
