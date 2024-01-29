package com.example.progettoingsw.gestori_gui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettoingsw.R;

import java.util.List;

public class CheckboxGridAdapter extends RecyclerView.Adapter<CheckboxGridAdapter.ViewHolder> {
    private List<String> itemNamesAndImages;
    private Context context;

    public CheckboxGridAdapter(List<String> itemNamesAndImages, Context context) {
        this.itemNamesAndImages = itemNamesAndImages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkbox_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itemName = itemNamesAndImages.get(position * 2);
        String imageName = itemNamesAndImages.get(position * 2 + 1);

        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        holder.textView.setText(itemName);
        holder.imageView.setImageResource(imageResId);
        holder.checkBox.setOnCheckedChangeListener(null);
        // Inserisci qui la logica per impostare il checkbox a seconda delle tue esigenze
        /*holder.checkBox.setChecked(*//* Inserisci qui la logica per impostare il checkbox a seconda delle tue esigenze *//*);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Inserisci qui la logica per gestire il cambio di stato della casella di controllo
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return itemNamesAndImages.size() / 2; // Diviso per 2 poiché ogni elemento ha un nome e un'immagine
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}

