package com.example.mipokedex;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CapturedAdapter extends RecyclerView.Adapter<CapturedAdapter.ViewHolder> {

    private List<PokemonData> list;

    public CapturedAdapter(List<PokemonData> list) {
        this.list = list;
    }

    // 1. Inflamos el layout que representará cada ítem de la lista:
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usar tu layout para cada Pokémon, por ejemplo: R.layout.item_captured_pokemon
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_captured_pokemon, parent, false);
        return new ViewHolder(view);
    }

    // 2. Asignamos datos (bind) a cada ítem según la posición
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PokemonData pokemon = list.get(position);
        holder.bind(pokemon);

        // Obtenemos SharedPreferences para ver si está habilitada la eliminación
        SharedPreferences prefs = holder.itemView.getContext()
                .getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        boolean deleteEnabled = prefs.getBoolean("deleteEnabled", false);

        if (deleteEnabled) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(v -> {
                // Eliminar de Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String userId = auth.getCurrentUser().getUid();

                db.collection("captured_pokemons")
                        .document(userId)
                        .collection("myPokemons")
                        .document(pokemon.getDocumentId())
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            // Eliminar también de la lista local
                            list.remove(position);
                            notifyItemRemoved(position);
                        });
            });
        } else {
            holder.deleteButton.setVisibility(View.GONE);
        }
    }

    // 3. Cantidad de ítems de la lista
    @Override
    public int getItemCount() {
        return list.size();
    }

    // 4. Clase interna ViewHolder que referencia los elementos del layout
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Ejemplo: un botón para eliminar
        Button deleteButton;
        TextView textViewPokemonName;  // <-- Necesario para mostrar el nombre
        ImageView imageViewPokemon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // "buttonDelete" es el id del botón en item_captured_pokemon.xml (ajústalo a tu layout)
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            imageViewPokemon = itemView.findViewById(R.id.imageViewPokemon);  // <-- en el XML
            textViewPokemonName = itemView.findViewById(R.id.textViewPokemonName);
        }

        // Método para enlazar la data del PokemonData a los componentes
        public void bind(PokemonData pokemon) {
            textViewPokemonName.setText(pokemon.getName());

            // Comprobar si la URL no es nula
            if (pokemon.getImageUrl() != null && !pokemon.getImageUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(pokemon.getImageUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.ic_pokeball))
                        .into(imageViewPokemon);
            } else {
                // Si no hay URL, podrías poner una imagen por defecto
                imageViewPokemon.setImageResource(R.drawable.ic_pokeball);
            }
        }
    }
}

