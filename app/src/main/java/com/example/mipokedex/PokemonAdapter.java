package com.example.mipokedex;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<PokemonData> pokemonDataList;
    private OnPokemonClickListener listener;

    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonData pokemonData);
    }

    public PokemonAdapter(List<PokemonData> pokemonDataList, OnPokemonClickListener listener) {
        this.pokemonDataList = pokemonDataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon_name, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PokemonData pokemon = pokemonDataList.get(position);;
        holder.bind(pokemon);
        if (pokemon.isCaptured()) {
            holder.itemView.setBackgroundColor(Color.RED); // Fondo rojo si está capturado
        } else {
            holder.itemView.setBackgroundColor(Color.GREEN); // Fondo verde si no está capturado
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null && !pokemon.isCaptured()) { // Solo permitir clics en no capturados
                listener.onPokemonClick(pokemon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewPokemon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewPokemon = itemView.findViewById(R.id.imageViewPokemon);
        }

        void bind(PokemonData pokemon) {
            textViewName.setText(pokemon.getName());
            // Usamos Glide para cargar la imagen
            Glide.with(imageViewPokemon.getContext())
                    .load(pokemon.getImageUrl()) // URL de la imagen
                    .placeholder(R.drawable.ic_pokedex) // Imagen de carga
                    .error(R.drawable.ic_pokeball) // Imagen de error
                    .into(imageViewPokemon);
        }
    }
}
