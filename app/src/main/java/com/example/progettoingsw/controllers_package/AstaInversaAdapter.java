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
import com.example.progettoingsw.model.AstaInversaItem;

import java.util.List;

public class AstaInversaAdapter extends RecyclerView.Adapter<AstaInversaAdapter.AstaInversaItemViewHolder> {

    private Context context;
    private List<AstaInversaItem> aste;

    public AstaInversaAdapter(Context context, List<AstaInversaItem> aste) {
        this.context = context;
        this.aste = aste;
    }

    public void setAste(List<AstaInversaItem> aste) {
        this.aste = aste;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AstaInversaItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_asta_inversa, parent, false);
        return new AstaInversaItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AstaInversaItemViewHolder holder, int position) {
        AstaInversaItem astaInversaItem = aste.get(position);
        holder.bind(astaInversaItem);
    }

    @Override
    public int getItemCount() {
        return aste != null ? aste.size() : 0;
    }

    public static class AstaInversaItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView nomeTextView;
        private TextView descrizioneTextView;
        private TextView prezzo;
        private TextView dataDiScadenza;

        public AstaInversaItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_item_asta_inversa);
            nomeTextView = itemView.findViewById(R.id.textView_nome_item_asta_inversa);
            descrizioneTextView = itemView.findViewById(R.id.textView_descrizione_item_asta_inversa);
            prezzo = itemView.findViewById(R.id.textView_prezzo_item_asta_inversa);
            dataDiScadenza = itemView.findViewById(R.id.textView_data_scadenza_item_asta_inversa);
        }

        public void bind(AstaInversaItem astaInversaItem) {
            imageView.setImageBitmap(astaInversaItem.getImmagine());
            nomeTextView.setText(astaInversaItem.getNome());
            descrizioneTextView.setText(astaInversaItem.getDescrizione());
            prezzo.setText(astaInversaItem.getPrezzoMax());
            dataDiScadenza.setText(astaInversaItem.getDataDiScadenza());
        }
    }
}
