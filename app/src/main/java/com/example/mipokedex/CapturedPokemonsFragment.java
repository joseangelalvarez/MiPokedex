package com.example.mipokedex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class CapturedPokemonsFragment extends Fragment {

    private RecyclerView recyclerView;
    private CapturedAdapter adapter;
    private List<PokemonData> pokemonList = new ArrayList<>();
    private ListenerRegistration listenerRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_captured_pokemons, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCaptured);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CapturedAdapter(pokemonList);
        recyclerView.setAdapter(adapter);

        // Iniciamos el SnapshotListener
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CollectionReference myPokemonsRef = db.collection("captured_pokemons")
                .document(userId)
                .collection("myPokemons");
        listenerRegistration = myPokemonsRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w("CapturedFragment", "Listen failed.", e);
                return;
            }
            if (snapshot != null) {
                pokemonList.clear();
                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    PokemonData p = doc.toObject(PokemonData.class);
                    p.setDocumentId(doc.getId());
                    pokemonList.add(p);
                }
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }
}