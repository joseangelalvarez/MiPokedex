package com.example.mipokedex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private Switch switchDelete;
    private Button buttonAbout, buttonLogout;
    private Spinner spinnerLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchDelete = view.findViewById(R.id.switchDelete);
        buttonAbout = view.findViewById(R.id.buttonAbout);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        spinnerLanguage = view.findViewById(R.id.spinnerLanguage);

        SharedPreferences prefs = requireContext().getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        boolean isDeleteEnabled = prefs.getBoolean("deleteEnabled", false);
        switchDelete.setChecked(isDeleteEnabled);

        // Listener del switch
        switchDelete.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("deleteEnabled", isChecked).apply();
        });

        // Listener del spinner para idioma
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = position == 0 ? "es" : "en";
                prefs.edit().putString("language", selectedLang).apply();
                // Podríamos forzar recarga de la Activity para cambiar el idioma
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        buttonAbout.setOnClickListener(v -> showAboutDialog());
        buttonLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Acerca de")
                .setMessage("Desarrollador: Jose Angel Alvarez\nVersión: 1.0.0")
                .setPositiveButton("OK", null)
                .show();
    }

    private void logout() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                    requireActivity().finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
