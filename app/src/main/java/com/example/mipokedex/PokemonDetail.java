package com.example.mipokedex;

import java.util.List;

public class PokemonDetail {

    private double height;
    private double weight;
    private List<TypeSlot> types;
    private int id;
    private String name;
    private Sprites sprites; // Objeto anidado que contendrá las URLs de imágenes


    // Getters (y setters si los necesitas)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public double getHeight() {return height;}
    public double getWeight() {return weight;}
    public List<TypeSlot> getTypes() {
        return types;
    }

    public void setTypes(List<TypeSlot> types) {
        this.types = types;
    }

    public Sprites getSprites() {
        return sprites;
    }

    // Clase interna o separada para mapear 'sprites'
    public static class Sprites {
        private String front_default;
        // Podrías mapear más campos si deseas

        public String getFront_default() {
            return front_default;
        }
    }

    class TypeSlot {
        private Type type;

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }
    }
    public static class Type {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
