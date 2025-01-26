package com.example.mipokedex;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mipokedex.PokemonAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokedexFragment extends Fragment {
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private List<PokemonData> pokemonList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokedex, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPokedex);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PokemonAdapter(pokemonList, this::capturePokemon);
        recyclerView.setAdapter(adapter);

        fetchPokemonList();
        return view;
    }

    private void fetchPokemonList() {
        PokeApiService apiService = RetrofitClient.getInstance().create(PokeApiService.class);

        // Obtener lista de Pokémon desde la API
        apiService.getPokemonList(50).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PokemonResult> results = response.body().getResults();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    // Cargar Pokémon capturados desde Firebase
                    db.collection("captured_pokemons")
                            .document(userId)
                            .collection("myPokemons")
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                List<String> capturedNames = new ArrayList<>();
                                for (var document : queryDocumentSnapshots.getDocuments()) {
                                    capturedNames.add(document.getString("name")); // Nombre de Pokémon capturados
                                }

                                // Sincronizar datos
                                for (PokemonResult result : results) {
                                    String[] urlParts = result.getUrl().split("/");
                                    String id = urlParts[urlParts.length - 1];
                                    String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png";

                                    // Verificar si el Pokémon está capturado
                                    boolean isCaptured = capturedNames.contains(result.getName());
                                    pokemonList.add(new PokemonData(result.getName(), imageUrl, isCaptured));
                                }

                                // Notificar al adaptador que los datos han cambiado
                                adapter.notifyDataSetChanged();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Error loading captured Pokémon: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error fetching Pokémon: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void capturePokemon(PokemonData pokemon) {
        // Marcar el Pokémon como capturado
        pokemon.setCaptured(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("captured_pokemons")
                .document(userId)
                .collection("myPokemons")
                .add(pokemon)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(getContext(), "Captured: " + pokemon.getName(), Toast.LENGTH_SHORT).show();

                    // Actualizar el elemento en la lista y notificar al adaptador
                    int position = pokemonList.indexOf(pokemon);
                    if (position >= 0) {
                        adapter.notifyItemChanged(position);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error capturing: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
