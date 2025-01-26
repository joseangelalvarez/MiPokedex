package com.example.mipokedex;

import java.util.List;

public class PokemonResponse {
    private int count;
    private String next;
    private String previous;
    private List<PokemonResult> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<PokemonResult> getResults() {
        return results;
    }
}