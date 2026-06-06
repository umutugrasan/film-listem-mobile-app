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
import com.example.odev.data.SearchItem;

import java.util.List;

// OMDb arama sonuclari icin adapter
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> {

    public interface OnItemClick {
        void onClick(SearchItem item);
    }

    private final Context context;
    private final List<SearchItem> liste;
    private final OnItemClick clickListener;

    public SearchAdapter(Context context, List<SearchItem> liste, OnItemClick clickListener) {
        this.context = context;
        this.liste = liste;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        SearchItem item = liste.get(position);
        h.tvBaslik.setText(item.getTitle());
        h.tvYil.setText(context.getString(R.string.yil) + " " + (item.getYear() != null ? item.getYear() : "-"));
        h.tvTur.setText(item.getType() != null ? item.getType().toUpperCase() : "-");

        String url = item.getPoster();
        if (url != null && !url.equals("N/A")) {
            Glide.with(context).load(url).into(h.ivPoster);
        } else {
            h.ivPoster.setImageDrawable(null);
        }

        h.itemView.setOnClickListener(v -> clickListener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvBaslik, tvYil, tvTur;

        VH(View v) {
            super(v);
            ivPoster = v.findViewById(R.id.ivPoster);
            tvBaslik = v.findViewById(R.id.tvBaslik);
            tvYil = v.findViewById(R.id.tvYil);
            tvTur = v.findViewById(R.id.tvTur);
        }
    }
}
