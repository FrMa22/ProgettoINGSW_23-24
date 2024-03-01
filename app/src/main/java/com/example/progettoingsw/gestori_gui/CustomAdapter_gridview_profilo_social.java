package com.example.progettoingsw.gestori_gui;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.progettoingsw.R;

import java.util.ArrayList;
import java.util.List;
public class CustomAdapter_gridview_profilo_social extends BaseAdapter {

    private Context context;
    private List<String> socialNames;
    private List<String> socialLinks; // Aggiunto

    public CustomAdapter_gridview_profilo_social(Context context) {
        this.context = context;
        this.socialNames = new ArrayList<>();
        this.socialLinks = new ArrayList<>(); // Aggiunto
    }

    public void setData(List<String> socialNames, List<String> socialLinks) {
        this.socialNames = socialNames;
        this.socialLinks = socialLinks;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return socialNames.size();
    }

    @Override
    public Object getItem(int position) {
        return socialNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_profilo_item_social, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewSocialName = convertView.findViewById(R.id.textview_social_name);
            viewHolder.textViewSocialLink = convertView.findViewById(R.id.textview_social_link); // Aggiunto
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Verifica che socialNames e socialLinks abbiano la stessa lunghezza
        if (position < socialNames.size() && position < socialLinks.size()) {
            String socialName = socialNames.get(position);
            String socialLink = socialLinks.get(position); // Aggiunto
            viewHolder.textViewSocialName.setText(socialName);
            viewHolder.textViewSocialLink.setText(socialLink); // Aggiunto
        } else {
            // Gestisci l'errore in modo appropriato
        }

        Log.d("CustomAdapter", "Social Name: " + viewHolder.textViewSocialName.getText());
        Log.d("CustomAdapter", "Social Link: " + viewHolder.textViewSocialLink.getText());

        return convertView;
    }



    private static class ViewHolder {
        TextView textViewSocialName;
        TextView textViewSocialLink; // Aggiunto
    }
}
