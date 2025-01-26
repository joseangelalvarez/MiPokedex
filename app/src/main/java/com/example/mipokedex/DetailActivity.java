package com.example.mipokedex;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        ImageView imageView = findViewById(R.id.imageViewPokemon);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewType = findViewById(R.id.textViewType);
        TextView textViewWeight = findViewById(R.id.textViewWeight);
        TextView textViewHeight = findViewById(R.id.textViewHeight);
        Button buttonClose = findViewById(R.id.buttonClose);

        // Obtener el objeto Pokémon desde el Intent
        PokemonData pokemon = (PokemonData) getIntent().getSerializableExtra("pokemon");
        if (pokemon != null) {
            Log.d("DetailActivity", "Pokemon received: " + pokemon);
            textViewName.setText(pokemon.getName());
            textViewType.setText(pokemon.getTypes() != null ? String.join(", ", pokemon.getTypes()) : "Unknown");
            textViewWeight.setText(getString(R.string.weight)+ ": " + pokemon.getWeight() + " kg");
            textViewHeight.setText(getString(R.string.height) + ": " + pokemon.getHeight() + " m");

            Glide.with(this)
                    .load(pokemon.getImageUrl())
                    .placeholder(R.drawable.ic_pokeball)
                    .into(imageView);
        } else {
            Log.e("DetailActivity", "PokemonData is null");
            Toast.makeText(this, "Error loading Pokemon data", Toast.LENGTH_SHORT).show();
        }
        // Manejar el clic del botón de cierre
        buttonClose.setOnClickListener(v -> finish());
    }
}
