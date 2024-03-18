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
import com.example.progettoingsw.model.NotificaItem;

import java.util.ArrayList;
import java.util.List;

public class NotificheAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TIPO_NOTIFICA = 2;

    private Context context;
    private ArrayList<Object> notificheItemList;

    public NotificheAdapter(Context context, ArrayList<Object> notificheItemList) {
        this.context = context;
        this.notificheItemList= notificheItemList;
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
            case TIPO_NOTIFICA:
                view = inflater.inflate(R.layout.item_notifica, parent, false);
                return new NotificaViewHolder(view);
            default:
                throw new IllegalArgumentException("Tipo di asta non supportato: " + viewType);
        }
    }

    //questo chiama il metodo bind corrispondente al tipo di asta corrente
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = notificheItemList.get(position);

          if (item instanceof NotificaItem) {
            ((NotificaViewHolder) holder).bind((NotificaItem) item);
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
        if (notificheItemList == null) {
            Log.d("NotificheAdapter", "count = " + 0);
            return 0; // Se la lista è nulla, non ci sono elementi da visualizzare
        } else {
            Log.d("NotificheAdapter", "count = " + notificheItemList.size());
            return notificheItemList.size();
        }
    }
    public Object getItem(int position) {
        if (position >= 0 && position < notificheItemList.size()) {
            return notificheItemList.get(position);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = notificheItemList.get(position);
          if (item instanceof NotificaItem) {
            return TIPO_NOTIFICA;
        }
        return -1;
    }





    // ViewHolder per l'asta inversa
    private static class NotificaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitolo;


        public NotificaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitolo = itemView.findViewById(R.id.textview_titolo_notifica);

        }

        public void bind(NotificaItem item) {
            //adattare ai get di notificaItem
            textViewTitolo.setText(item.getTitolo());
        }
    }

    public void setNotifiche(ArrayList<Object> notificheList) {
        this.notificheItemList = notificheList;
        notifyDataSetChanged(); // Notifica all'adapter che i dati sono stati modificati
    }
}

