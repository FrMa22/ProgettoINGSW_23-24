    package com.example.progettoingsw.controllers_package;

    import android.content.Context;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.progettoingsw.R;
    import com.example.progettoingsw.model.NotificheAcquirenteModel;
    import com.example.progettoingsw.model.NotificheVenditoreModel;

    import java.util.ArrayList;

    public class NotificheAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListener {

        private static final int TIPO_NOTIFICA_ACQUIRENTE = 0;
        private static final int TIPO_NOTIFICA_VENDITORE = 1;

        private Context context;
        private ArrayList<NotificheAcquirenteModel> notificheAcquirenteItemList;
        private ArrayList<NotificheVenditoreModel> notificheVenditoreItemList;

        private View.OnClickListener mItemClickListener;

        public NotificheAdapter(Context context) {
            this.context = context;
            this.notificheAcquirenteItemList = new ArrayList<>();
            this.notificheVenditoreItemList = new ArrayList<>();
        }

        public void setOnItemClickListener(View.OnClickListener listener) {
            mItemClickListener = listener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            switch (viewType) {
                case TIPO_NOTIFICA_ACQUIRENTE:
                    view = inflater.inflate(R.layout.item_notifica, parent, false);
                    return new NotificaAcquirenteViewHolder(view);
                case TIPO_NOTIFICA_VENDITORE:
                    view = inflater.inflate(R.layout.item_notifica, parent, false);
                    return new NotificaVenditoreViewHolder(view);
                default:
                    throw new IllegalArgumentException("Tipo di notifica non supportato: " + viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);

            switch (viewType) {
                case TIPO_NOTIFICA_ACQUIRENTE:
                    ((NotificaAcquirenteViewHolder) holder).bind(notificheAcquirenteItemList.get(position));
                    break;
                case TIPO_NOTIFICA_VENDITORE:
                    ((NotificaVenditoreViewHolder) holder).bind(notificheVenditoreItemList.get(position));
                    break;
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onClick(v);
                    }
                }
            });
        }

        @Override
        public void onItemClick(Object notifica, int position) {
            // Gestisci il clic sull'elemento del RecyclerView qui
            // Esegui le azioni desiderate con l'oggetto Notifica
        }

        @Override
        public int getItemCount() {
            if (!notificheAcquirenteItemList.isEmpty()) {
                return notificheAcquirenteItemList.size();
            } else if (!notificheVenditoreItemList.isEmpty()) {
                return notificheVenditoreItemList.size();
            }
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (!notificheAcquirenteItemList.isEmpty()) {
                return TIPO_NOTIFICA_ACQUIRENTE;
            } else if (!notificheVenditoreItemList.isEmpty()) {
                return TIPO_NOTIFICA_VENDITORE;
            }
            return -1;
        }
        public NotificheAcquirenteModel getClickedNotificaAcquirente(int position) {
            if (!notificheAcquirenteItemList.isEmpty()) {
                return notificheAcquirenteItemList.get(position);
            }
            return null;
        }
        public NotificheVenditoreModel getClickedNotificaVenditore(int position) {
            if (!notificheVenditoreItemList.isEmpty()) {
                return notificheVenditoreItemList.get(position);
            }
            return null;
        }

        public void setNotificheAcquirente(ArrayList<NotificheAcquirenteModel> notificheList) {
            if (notificheList != null) {
                this.notificheAcquirenteItemList = notificheList;
            } else {
                this.notificheAcquirenteItemList = new ArrayList<>();
            }
            notifyDataSetChanged();
        }

        public void setNotificheVenditore(ArrayList<NotificheVenditoreModel> notificheList) {
            if (notificheList != null) {
                this.notificheVenditoreItemList = notificheList;
            } else {
                this.notificheVenditoreItemList = new ArrayList<>();
            }
            notifyDataSetChanged();
        }


        private static class NotificaAcquirenteViewHolder extends RecyclerView.ViewHolder {
            private TextView textViewTitolo;

            public NotificaAcquirenteViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitolo = itemView.findViewById(R.id.textview_titolo_notifica);
            }

            public void bind(NotificheAcquirenteModel item) {
                textViewTitolo.setText(item.getTitolo());
                // Eseguire altre operazioni di binding se necessario
            }
        }

        private static class NotificaVenditoreViewHolder extends RecyclerView.ViewHolder {
            private TextView textViewTitolo;

            public NotificaVenditoreViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitolo = itemView.findViewById(R.id.textview_titolo_notifica);
            }

            public void bind(NotificheVenditoreModel item) {
                textViewTitolo.setText(item.getTitolo());
                // Eseguire altre operazioni di binding se necessario
            }
        }
    }
