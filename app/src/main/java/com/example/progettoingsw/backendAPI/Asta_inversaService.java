package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.Asta_inversa_DTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Asta_inversaService {
    @GET("/asta_inversaController/getAste_inversaScadenzaRecente")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaScadenzaRecente();

    @GET("/asta_inversaController/getAste_inversaNuove")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaNuove();

    @GET("/asta_inversaController/getAste_inversaNomeCategoria/{nomeCategoria}")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaCategoriaNome(@Path("nomeCategoria") String nomeCategoria);
}

