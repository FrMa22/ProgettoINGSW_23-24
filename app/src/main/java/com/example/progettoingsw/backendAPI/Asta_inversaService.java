package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.Asta_inversa_DTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Asta_inversaService {
    @GET("/asta_inversaController/getAste_inversaScadenzaRecente")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaScadenzaRecente();

    @GET("/asta_inversaController/getAste_inversaNuove")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaNuove();

    @GET("/asta_inversaController/getAste_inversaNomeCategoria/{nomeCategoria}")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaCategoriaNome(@Path("nomeCategoria") String nomeCategoria);

    @POST("/asta_inversaController/partecipaAstaInversa/{idAstaInversa}/{indirizzo_email}/{offerta}/{tempo_offerta}/{stato}")
    Call<Integer> partecipaAsta_inversa(@Path("idAstaInversa") Long idAstaInversa, @Path("indirizzo_email") String indirizzo_email, @Path("offerta") String offerta,
                                           @Path("tempo_offerta") String tempo_offerta,@Path("stato") String stato);

    @GET("/asta_inversaController/trovaAstaInversa/{idAstaInversa}")
    Call<Asta_inversa_DTO> trovaAstaInversa(@Path("idAstaInversa") Long idAstaInversa);
    @GET("/asta_inversaController/verificaAstaInversaInPreferiti/{indirizzo_email}/{idAstaInversa}")
    Call<Integer> verificaAstaInversaInPreferiti(@Path("indirizzo_email") String indirizzo_email, @Path("idAstaInversa") Long idAstaInversa);
    @POST("/asta_inversaController/inserimentoAstaInPreferiti/{idAstaInversa}/{indirizzo_email}")
    Call<Integer> inserimentoAstaInPreferiti(@Path("idAstaInversa") Long idAstaInversa, @Path("indirizzo_email") String indirizzo_email);
    @DELETE("/asta_inversaController/eliminazioneAstaInPreferiti/{idAstaInversa}/{indirizzo_email}")
    Call<Integer> eliminazioneAstaInPreferiti(@Path("idAstaInversa") Long idAstaInversa, @Path("indirizzo_email") String indirizzo_email);

    @GET("/asta_inversaController/getAsteInversaPreferite/{indirizzo_email}")
    Call<ArrayList<Asta_inversa_DTO>> getAsteInversaPreferite(@Path("indirizzo_email") String indirizzo_email);
}

