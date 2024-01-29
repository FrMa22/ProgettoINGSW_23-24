package com.example.progettoingsw.gestori_gui;
public class GridItem {
    private String name;
    private int imageResourceId;

    public GridItem(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
