package com.example.progettoingsw.controllers_package;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.R;
import com.example.progettoingsw.model.AstaIngleseItem;
import com.example.progettoingsw.model.AstaRibassoItem;
import com.example.progettoingsw.model.AstaInversaItem;

import java.util.ArrayList;
import java.util.List;

public class AstaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TIPO_ASTA_INGESE = 1;
    private static final int TIPO_ASTA_RIBASSO = 2;
    private static final int TIPO_ASTA_INVERSA = 3;

    private Context context;
    private ArrayList<Object> astaItemList;

    public AstaAdapter(Context context, ArrayList<Object> astaItemList) {
        this.context = context;
        this.astaItemList = astaItemList;
    }

    //serve a scegliere quale layout applicare per ogni tipo di asta
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TIPO_ASTA_INGESE:
                view = inflater.inflate(R.layout.item_asta_inglese, parent, false);
                return new AstaIngleseViewHolder(view);
            case TIPO_ASTA_RIBASSO:
                view = inflater.inflate(R.layout.item_asta_ribasso, parent, false);
                return new AstaRibassoViewHolder(view);
            case TIPO_ASTA_INVERSA:
                view = inflater.inflate(R.layout.item_asta_inversa, parent, false);
                return new AstaInversaViewHolder(view);
            default:
                throw new IllegalArgumentException("Tipo di asta non supportato: " + viewType);
        }
    }

    //questo chiama il metodo bind corrispondente al tipo di asta corrente
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = astaItemList.get(position);

        if (item instanceof AstaIngleseItem) {
            ((AstaIngleseViewHolder) holder).bind((AstaIngleseItem) item);
        } else if (item instanceof AstaRibassoItem) {
            ((AstaRibassoViewHolder) holder).bind((AstaRibassoItem) item);
        } else if (item instanceof AstaInversaItem) {
            ((AstaInversaViewHolder) holder).bind((AstaInversaItem) item);
        }
    }

    @Override
    public int getItemCount() {
        if (astaItemList == null) {
            Log.d("AstaAdapter", "count = " + 0);
            return 0; // Se la lista è nulla, non ci sono elementi da visualizzare
        } else {
            Log.d("AstaAdapter", "count = " + astaItemList.size());
            return astaItemList.size();
        }
    }


    @Override
    public int getItemViewType(int position) {
        Object item = astaItemList.get(position);
        if (item instanceof AstaIngleseItem) {
            return TIPO_ASTA_INGESE;
        } else if (item instanceof AstaRibassoItem) {
            return TIPO_ASTA_RIBASSO;
        } else if (item instanceof AstaInversaItem) {
            return TIPO_ASTA_INVERSA;
        }
        return -1;
    }

    // ViewHolder per l'asta inglese
    private static class AstaIngleseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewDescrizione;
        private ImageView imageView;
        private TextView dataDiScadenza;
        private TextView prezzo;
        private TextView rialzo;

        public AstaIngleseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textView_nome_item_asta_inglese);
            textViewDescrizione = itemView.findViewById(R.id.textView_descrizione_item_asta_inglese);
            imageView = itemView.findViewById(R.id.image_view_item_asta_inglese);
            dataDiScadenza = itemView.findViewById(R.id.textView_data_scadenza_item_asta_inglese);
            prezzo = itemView.findViewById(R.id.textView_prezzo_item_asta_inglese);
            rialzo = itemView.findViewById(R.id.textView_rialzo_item_asta_inglese);
        }

        public void bind(AstaIngleseItem item) {
            textViewNome.setText(item.getNome());
            textViewDescrizione.setText(item.getDescrizione());
            if(item.getImmagine() != null){
                imageView.setImageBitmap(item.getImmagine());
            }else{
                imageView.setImageResource(R.drawable.img_default);
            }
            dataDiScadenza.setText(item.getDataDiScadenza());
            prezzo.setText(item.getPrezzoAttuale());
            rialzo.setText(item.getRialzoMin());
        }
    }

    // ViewHolder per l'asta al ribasso
    private static class AstaRibassoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewDescrizione;
        private ImageView imageView;
        private TextView prezzoAttuale;
        private TextView intervalloDecremento;
        private TextView prezzoDecremento;

        public AstaRibassoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textView_nome_item_asta_ribasso);
            textViewDescrizione = itemView.findViewById(R.id.textView_descrizione_item_asta_ribasso);
            imageView = itemView.findViewById(R.id.image_view_item_asta_ribasso);
            prezzoAttuale = itemView.findViewById(R.id.textView_prezzo_item_asta_ribasso);
            intervalloDecremento = itemView.findViewById(R.id.textView_intervallo_decremento_item_asta_ribasso);
            prezzoDecremento = itemView.findViewById(R.id.textView_prezzo_decremento_item_asta_ribasso);
        }

        public void bind(AstaRibassoItem item) {
            textViewNome.setText(item.getNome());
            textViewDescrizione.setText(item.getDescrizione());
            if(item.getImmagine() != null){
                imageView.setImageBitmap(item.getImmagine());
            }else{
                imageView.setImageResource(R.drawable.img_default);
            }
            prezzoAttuale.setText(item.getPrezzoAttuale());
            intervalloDecremento.setText(item.getIntervalloDecrementale());
            prezzoDecremento.setText(item.getDecrementoAutomatico());
        }
    }

    // ViewHolder per l'asta inversa
    private static class AstaInversaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewDescrizione;
        private ImageView imageView;
        private TextView dataScadenza;
        private TextView prezzo;

        public AstaInversaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textView_nome_item_asta_inversa);
            textViewDescrizione = itemView.findViewById(R.id.textView_descrizione_item_asta_inversa);
            imageView = itemView.findViewById(R.id.image_view_item_asta_inversa);
            dataScadenza = itemView.findViewById(R.id.textView_data_scadenza_item_asta_inversa);
            prezzo = itemView.findViewById(R.id.textView_prezzo_item_asta_inversa);
        }

        public void bind(AstaInversaItem item) {
            textViewNome.setText(item.getNome());
            textViewDescrizione.setText(item.getDescrizione());
            if(item.getImmagine() != null){
                imageView.setImageBitmap(item.getImmagine());
            }else{
                imageView.setImageResource(R.drawable.img_default);
            }
            dataScadenza.setText(item.getDataDiScadenza());
            prezzo.setText(item.getPrezzoMax());
        }
    }

    public void setAste(ArrayList<Object> astaList) {
        this.astaItemList = astaList;
        notifyDataSetChanged(); // Notifica all'adapter che i dati sono stati modificati
    }
}
