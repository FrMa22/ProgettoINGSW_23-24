package com.example.progettoingsw.controllers_package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.model.Prodotto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdottoAdapter extends RecyclerView.Adapter<ProdottoAdapter.ProdottoViewHolder> {

    private Context context;
    private List<Prodotto> prodotti;

    public ProdottoAdapter(Context context, List<Prodotto> prodotti) {
        this.context = context;
        this.prodotti = prodotti;
    }

    public void setProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdottoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prodotto, parent, false);
        return new ProdottoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdottoViewHolder holder, int position) {
        Prodotto prodotto = prodotti.get(position);
        holder.bind(prodotto);
    }

    @Override
    public int getItemCount() {
        return prodotti != null ? prodotti.size() : 0;
    }

    public static class ProdottoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nomeTextView;
        private TextView descrizioneTextView;

        public ProdottoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_prodotto);
            nomeTextView = itemView.findViewById(R.id.text_view_nome_prodotto);
            descrizioneTextView = itemView.findViewById(R.id.text_view_descrizione_prodotto);
        }

        public void bind(Prodotto prodotto) {
            Picasso.get().load(prodotto.getPathImmagine()).into(imageView);
            nomeTextView.setText(prodotto.getNome());
            descrizioneTextView.setText(prodotto.getDescrizione());
        }
    }
}
