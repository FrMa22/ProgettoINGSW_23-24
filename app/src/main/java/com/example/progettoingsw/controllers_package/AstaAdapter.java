package com.example.progettoingsw.controllers_package;

import android.content.Context;
import android.os.CountDownTimer;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    private View.OnClickListener mItemClickListener;

    // Metodo per impostare il listener degli eventi di clic
    public void setOnItemClickListener(View.OnClickListener listener) {
        mItemClickListener = listener;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Controlla se è stato impostato un listener e se sì, chiama il metodo onClick
                if (mItemClickListener != null) {
                    mItemClickListener.onClick(v);
                }
            }
        });
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
    public Object getItem(int position) {
        if (position >= 0 && position < astaItemList.size()) {
            return astaItemList.get(position);
        }
        return null;
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
    public class AstaIngleseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewDescrizione;
        private ImageView imageView;
        private TextView intervalloOfferte;
        private TextView prezzo;
        private TextView rialzo;

        private CountDownTimer countDownTimer;

        public AstaIngleseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textView_nome_item_asta_inglese);
            textViewDescrizione = itemView.findViewById(R.id.textView_descrizione_item_asta_inglese);
            imageView = itemView.findViewById(R.id.image_view_item_asta_inglese);
            prezzo = itemView.findViewById(R.id.textView_prezzo_item_asta_inglese);
            rialzo = itemView.findViewById(R.id.textView_rialzo_item_asta_inglese);
            intervalloOfferte = itemView.findViewById(R.id.textView_intervallo_offerte_item_asta_inglese);
        }

        public void bind(AstaIngleseItem item) {
            textViewNome.setText(item.getNome());
            textViewDescrizione.setText(item.getDescrizione());
            if(item.getImmagine() != null){
                imageView.setImageBitmap(item.getImmagine());
            }else{
                imageView.setImageResource(R.drawable.img_default);
            }
            intervalloOfferte.setText(item.getIntervalloTempoOfferte());
            prezzo.setText(item.getPrezzoAttuale());
            rialzo.setText(item.getRialzoMin());

            startCountDownTimer(item.getIntervalloTempoOfferte());
        }

        private void startCountDownTimer(String intervalloOfferteString) {
            long intervalloOfferteSeconds = convertiStringaInSecondi(intervalloOfferteString);
            countDownTimer = new CountDownTimer(intervalloOfferteSeconds * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("aggiornato", "aggiornato");
                    intervalloOfferte.setText(calcolaTempoRimanente(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    Log.d("scaduto", "scaduto");
                }
            }.start();
        }

        private String calcolaTempoRimanente(long secondsUntilFinished) {
            long hours = secondsUntilFinished / 3600;
            long minutes = (secondsUntilFinished % 3600) / 60;
            long seconds = secondsUntilFinished % 60;

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        private long convertiStringaInSecondi(String intervalloOfferteString) {
            // Assumiamo che l'intervallo sia nel formato "HH:mm:ss"
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            try {
                Date intervallo = sdf.parse(intervalloOfferteString);
                long milliseconds = intervallo.getTime();
                return TimeUnit.MILLISECONDS.toSeconds(milliseconds);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
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
