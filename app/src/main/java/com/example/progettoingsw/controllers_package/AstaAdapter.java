package com.example.progettoingsw.controllers_package;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.progettoingsw.item.AstaIngleseItem;
import com.example.progettoingsw.item.AstaRibassoItem;
import com.example.progettoingsw.item.AstaInversaItem;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AstaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TIPO_ASTA_INGLESE = 1;
    private static final int TIPO_ASTA_RIBASSO = 2;
    private static final int TIPO_ASTA_INVERSA = 3;
    private ArrayList<CountDownTimer> countDownTimers = new ArrayList<>();

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
            case TIPO_ASTA_INGLESE:
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
        } else if(item instanceof Asta_allingleseModel){
            Asta_allingleseModel astaModel = (Asta_allingleseModel) item;
            Bitmap bitmap = null;
            if(astaModel.getImmagine() != null && astaModel.getImmagine().length > 0){
                bitmap = BitmapFactory.decodeByteArray(astaModel.getImmagine(), 0, astaModel.getImmagine().length);
            }
            AstaIngleseItem astaItem = new AstaIngleseItem(Math.toIntExact(astaModel.getId()),astaModel.getNome(),astaModel.getDescrizione(), bitmap, Float.toString(astaModel.getBaseAsta()),astaModel.getIntervalloTempoOfferte()
            ,Float.toString(astaModel.getRialzoMin()),Float.toString(astaModel.getPrezzoAttuale()),astaModel.getCondizione(), astaModel.getId_venditore());
            ((AstaIngleseViewHolder) holder).bind((AstaIngleseItem) astaItem);
        } else if(item instanceof Asta_alribassoModel){
            Asta_alribassoModel astaModel = (Asta_alribassoModel) item;
            Bitmap bitmap = null;
            if(astaModel.getImmagine() != null && astaModel.getImmagine().length > 0){
                bitmap = BitmapFactory.decodeByteArray(astaModel.getImmagine(), 0, astaModel.getImmagine().length);
            }
            AstaRibassoItem astaItem = new AstaRibassoItem(Math.toIntExact(astaModel.getId()), astaModel.getNome(), astaModel.getDescrizione(), bitmap, Float.toString(astaModel.getPrezzoBase()), astaModel.getIntervalloDecrementale(),Float.toString(astaModel.getDecrementoAutomaticoCifra()),
                    Float.toString(astaModel.getPrezzoMin()),Float.toString(astaModel.getPrezzoAttuale()), astaModel.getCondizione(), astaModel.getId_venditore());
            ((AstaRibassoViewHolder) holder).bind((AstaRibassoItem) astaItem);
        } else if(item instanceof Asta_inversaModel){
            Asta_inversaModel astaModel = (Asta_inversaModel) item;
            Bitmap bitmap = null;
            if(astaModel.getImmagine() != null && astaModel.getImmagine().length > 0){
                bitmap = BitmapFactory.decodeByteArray(astaModel.getImmagine(), 0, astaModel.getImmagine().length);
            }
            AstaInversaItem astaItem = new AstaInversaItem(Math.toIntExact(astaModel.getId()),astaModel.getNome(),astaModel.getDescrizione(), bitmap, Float.toString(astaModel.getPrezzoMax()),astaModel.getDataDiScadenza()
                    ,astaModel.getCondizione(),Float.toString(astaModel.getPrezzoAttuale()), astaModel.getId_acquirente());
            ((AstaInversaViewHolder) holder).bind((AstaInversaItem) astaItem);
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
            return TIPO_ASTA_INGLESE;
        } else if (item instanceof AstaRibassoItem) {
            return TIPO_ASTA_RIBASSO;
        } else if (item instanceof AstaInversaItem) {
            return TIPO_ASTA_INVERSA;
        } else if(item instanceof Asta_allingleseModel){
            return TIPO_ASTA_INGLESE;
        } else if(item instanceof Asta_alribassoModel){
            return TIPO_ASTA_RIBASSO;
        } else if(item instanceof Asta_inversaModel){
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
            Log.d("bind di asta inglese view holder" , "entrato " );
            textViewNome = itemView.findViewById(R.id.textView_nome_item_asta_inglese);
            textViewDescrizione = itemView.findViewById(R.id.textView_descrizione_item_asta_inglese);
            imageView = itemView.findViewById(R.id.image_view_item_asta_inglese);
            prezzo = itemView.findViewById(R.id.textView_prezzo_item_asta_inglese);
            rialzo = itemView.findViewById(R.id.textView_rialzo_item_asta_inglese);
            intervalloOfferte = itemView.findViewById(R.id.textView_intervallo_offerte_item_asta_inglese);
        }

        public void bind(AstaIngleseItem item) {
            Log.d("bind di asta inglese view holder" , "nome: " + item.getNome());
            textViewNome.setText(item.getNome());
            textViewDescrizione.setText(item.getDescrizione());
            if(item.getImmagine() != null){
                imageView.setImageBitmap(item.getImmagine());
            }else{
                imageView.setImageResource(R.drawable.no_image_available);
            }
            intervalloOfferte.setText(item.getIntervalloTempoOfferte());
            prezzo.setText(item.getPrezzoAttuale());
            rialzo.setText(item.getRialzoMin());
            startCountDownTimer(item.getIntervalloTempoOfferte());
        }

        private void startCountDownTimer(String intervalloOfferteString) {
            Log.d("startCountDownTimer", "intervallo prima: " + intervalloOfferteString);
            long intervalloOfferteSeconds = convertiStringaInSecondi(intervalloOfferteString);
            Log.d("startCountDownTimer", "intervallo : " + intervalloOfferteSeconds);
            countDownTimer = new CountDownTimer(intervalloOfferteSeconds * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.d("asta adapter asta inglese", "aggiornato textview intervallo offerte");
                    intervalloOfferte.setText(calcolaTempoRimanente(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    Log.d("scaduto", "scaduto");
                    intervalloOfferte.setText("Asta Scaduta");
                }
            }.start();
            countDownTimers.add(countDownTimer);
        }


        private String calcolaTempoRimanente(long secondsUntilFinished) {
            long hours = secondsUntilFinished / 3600;
            long minutes = (secondsUntilFinished % 3600) / 60;
            long seconds = secondsUntilFinished % 60;

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }

        private long convertiStringaInSecondi(String intervalloOfferteString) {
            String[] tokens = intervalloOfferteString.split(":");
            int hours = Integer.parseInt(tokens[0]);
            int minutes = Integer.parseInt(tokens[1]);
            int seconds = Integer.parseInt(tokens[2]);

            long intervalloTotaleSecondi = hours * 3600 + minutes * 60 + seconds;

            // Ottieni il numero di secondi trascorsi nel minuto corrente
            long secondiTrascorsi = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) % 60;

            // Calcola i secondi rimanenti fino al prossimo minuto
            long secondiRimanenti = intervalloTotaleSecondi - secondiTrascorsi;

            return secondiRimanenti;
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
                imageView.setImageResource(R.drawable.no_image_available);
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
                imageView.setImageResource(R.drawable.no_image_available);
            }
            dataScadenza.setText(item.getDataDiScadenza());
            prezzo.setText(item.getPrezzoAttuale());
        }
    }

    public void setAste(ArrayList<Object> astaList) {
        if (astaItemList == null) {
            astaItemList = new ArrayList<>();
        }
        int startPosition = getItemCount();
        astaItemList.addAll(astaList);
        notifyItemRangeInserted(startPosition, astaList.size());
    }
    public void stopAllTimers() {
        for (CountDownTimer timer : countDownTimers) {
            timer.cancel();
        }
        countDownTimers.clear();
    }

}
