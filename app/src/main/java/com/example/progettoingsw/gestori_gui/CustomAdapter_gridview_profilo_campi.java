package com.example.progettoingsw.gestori_gui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.progettoingsw.R;

import java.util.ArrayList;

public class CustomAdapter_gridview_profilo_campi extends BaseAdapter {

    private boolean modificaAbilitata = false;
    private Context mContext;
    private ArrayList<String> array_campi_gridview;
    private ArrayList<String> array_dati_gridview;

    public CustomAdapter_gridview_profilo_campi(Context context) {
        this.mContext = context;
        this.array_campi_gridview = new ArrayList<>();
        this.array_dati_gridview = new ArrayList<>();
    }

    public void setData(ArrayList<String> array_campi, ArrayList<String> array_dati) {
        this.array_campi_gridview = array_campi;
        this.array_dati_gridview = array_dati;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return array_campi_gridview.size();
    }

    @Override
    public Object getItem(int position) {
        return array_campi_gridview.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setModificaAbilitata(boolean abilitata) {
        modificaAbilitata = abilitata;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_profilo_item_layout, parent, false);

            holder = new ViewHolder();
            holder.textViewLeft = convertView.findViewById(R.id.textViewLeft);
            holder.editTextRight = convertView.findViewById(R.id.editTextRight);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Setta il testo per il TextView a sinistra e l'EditText a destra
        /*holder.textViewLeft.setText("Elemento " + (position + 1));
        holder.editTextRight.setText(array_campi_gridview.get(position));*/

        if (position < array_campi_gridview.size() && position < array_dati_gridview.size()) {
            holder.textViewLeft.setText(array_campi_gridview.get(position));
            holder.editTextRight.setText(array_dati_gridview.get(position));
//            holder.buttonModifica = convertView.findViewById(R.id.tuo_id_del_bottone);
        }


        // Impedisce la modifica dell'EditText
        holder.editTextRight.setFocusable(false);
        holder.editTextRight.setClickable(false);
        holder.editTextRight.setFocusableInTouchMode(false);

        try{
            if (modificaAbilitata) {
                holder.buttonModifica.setVisibility(View.VISIBLE);
                holder.buttonModifica.setEnabled(true);
            } else {
                holder.buttonModifica.setVisibility(View.INVISIBLE);
                holder.buttonModifica.setEnabled(false);
            }
        }catch(Exception e){
            Log.e("NomeClasse", "Messaggio di errore: " + e.getMessage());
            e.printStackTrace();
        }


        return convertView;
    }

    static class ViewHolder {
        TextView textViewLeft;
        EditText editTextRight;
        ImageButton buttonModifica;
    }
}
