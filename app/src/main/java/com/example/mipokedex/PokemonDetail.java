package com.example.mipokedex;

public class PokemonDetail {
    private int id;
    private String name;
    private Sprites sprites; // Objeto anidado que contendrá las URLs de imágenes

    // Getters (y setters si los necesitas)
    public int getId() { return id; }
    public String getName() { return name; }
    public Sprites getSprites() { return sprites; }

    // Clase interna o separada para mapear 'sprites'
    public static class Sprites {
        private String front_default;
        // Podrías mapear más campos si deseas

        public String getFront_default() { return front_default; }
    }
}
