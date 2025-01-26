package com.example.mipokedex;


public class PokemonData {
    private String documentId; // ID en Firestore
    private String name;
    private String imageUrl;
    // Agrega otros campos que te interese almacenar, por ejemplo:
    private double weight;
    private double height;
    private int index; // Número en la Pokédex, etc.

    private boolean isCaptured; // Indica si está capturado

    // Constructor vacío (necesario para Firebase)
    public PokemonData() {
    }

    // Constructor opcional con parámetros
    public PokemonData(String name, String imageUrl, double weight, double height) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.height = height;
    }
    public PokemonData(String name, String imageUrl, boolean isCaptured) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.isCaptured = isCaptured;
    }

    // Getters y Setters
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public boolean isCaptured() {
        return isCaptured;
    }
    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }
}

