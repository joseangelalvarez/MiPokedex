package com.example.mipokedex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PokemonData implements Serializable {
    private String name;
    private int index;
    private String imageUrl;
    private List<String> types;
    private double weight;
    private double height;
    private String documentId;
    private Boolean isCaptured;

    // Constructor vacío necesario para Firebase
    public PokemonData() {
        this.types = new ArrayList<>(); // Inicializa como lista vacía
    }

    // Constructor principal
    public PokemonData(String name, int index, String imageUrl, List<String> types, double weight, double height, Boolean isCaptured, String documentId) {
        this.name = name;
        this.index = index;
        this.imageUrl = imageUrl;
        this.types = types != null ? types : new ArrayList<>();
        this.weight = weight;
        this.height = height;
        this.isCaptured = isCaptured;
        this.documentId = documentId;
    }

    // Constructor auxiliar 1
    public PokemonData(String name, String imageUrl, Boolean isCaptured) {
        this(name, 0, imageUrl, null, 0.0, 0.0, isCaptured, null);
    }

    // Constructor auxiliar 2
    public PokemonData(String name, int index, String imageUrl, List<String> types, double weight, double height) {
        this(name, index, imageUrl, types, weight, height, false, null);
    }

    // Nuevo Constructor para tu uso específico
    public PokemonData(String name, int index, String imageUrl, List<String> types, double weight, double height, Boolean isCaptured) {
        this.name = name;
        this.index = index;
        this.imageUrl = imageUrl;
        this.types = types != null ? types : new ArrayList<>();
        this.weight = weight;
        this.height = height;
        this.isCaptured = isCaptured;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return "PokemonData{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", imageUrl='" + imageUrl + '\'' +
                ", types=" + types +
                ", weight=" + weight +
                ", height=" + height +
                ", isCaptured=" + getCaptured() +
                '}';
    }

    public Boolean getCaptured() {
        return isCaptured;
    }

    public void setCaptured(Boolean captured) {
        isCaptured = captured;
    }
}
