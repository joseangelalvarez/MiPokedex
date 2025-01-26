package com.example.mipokedex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit);


    @GET
    Call<PokemonDetail> getPokemonDetails(@Url String url);
}