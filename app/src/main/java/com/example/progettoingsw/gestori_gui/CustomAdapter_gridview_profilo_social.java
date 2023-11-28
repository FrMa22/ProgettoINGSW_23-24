package com.example.progettoingsw.gestori_gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import com.example.progettoingsw.R;

import java.util.ArrayList;

public class CustomAdapter_gridview_profilo_social extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> array_social_gridview;
    private ArrayList<String> array_links_gridview;

    public CustomAdapter_gridview_profilo_social(Context context) {
        this.mContext = context;
        this.array_social_gridview = new ArrayList<>();
        this.array_links_gridview = new ArrayList<>();
    }

    public void setData(ArrayList<String> array_campi, ArrayList<String> array_dati) {
        this.array_social_gridview = array_campi;
        this.array_links_gridview = array_dati;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return array_social_gridview.size();
    }

    @Override
    public Object getItem(int position) {
        return array_social_gridview.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_campi.ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_profilo_item_layout, parent, false);

            holder = new com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_campi.ViewHolder();
            holder.textViewLeft = convertView.findViewById(R.id.textViewLeftSocial);
            holder.editTextRight = convertView.findViewById(R.id.editTextRightSocial);

            convertView.setTag(holder);
        } else {
            holder = (com.example.progettoingsw.gestori_gui.CustomAdapter_gridview_profilo_campi.ViewHolder) convertView.getTag();
        }

        // Setta il testo per il TextView a sinistra e l'EditText a destra
        /*holder.textViewLeft.setText("Elemento " + (position + 1));
        holder.editTextRight.setText(array_campi_gridview.get(position));*/

        if (position < array_social_gridview.size() && position < array_links_gridview.size()) {
            holder.textViewLeft.setText(array_social_gridview.get(position));
            holder.editTextRight.setText(array_links_gridview.get(position));
        }


        // Impedisce la modifica dell'EditText
        holder.editTextRight.setFocusable(false);
        holder.editTextRight.setClickable(false);
        holder.editTextRight.setFocusableInTouchMode(false);

        return convertView;
    }

    static class ViewHolder {
        TextView textViewLeft;
        EditText editTextRight;
    }
}
