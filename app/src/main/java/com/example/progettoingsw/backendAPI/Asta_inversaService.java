package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.DTO.Asta_inversa_DTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Asta_inversaService {
    @GET("/asta_inversaController/getAste_inversaScadenzaRecente")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaScadenzaRecente();

    @GET("/asta_inversaController/getAste_inversaNuove")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaNuove();

    @GET("/asta_inversaController/getAste_inversaNomeCategoria/{nomiCategorie}")
    Call<ArrayList<Asta_inversa_DTO>> getAste_inversaCategoriaNome(@Query("nomiCategorie") ArrayList<String> nomiCategorie);

    @GET("/asta_inversaController/getAste_inversaApertaByEmail/{indirizzo_email}")
    Call<List<Asta_inversa_DTO>> getAste_inversaApertaByEmail(@Path("indirizzo_email") String indirizzo_email);

    @GET("/asta_inversaController/getAste_inversaChiusaByEmail/{indirizzo_email}")
    Call<List<Asta_inversa_DTO>> getAste_inversaChiusaByEmail(@Path("indirizzo_email") String indirizzo_email);


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

    @POST("/asta_inversaController/insertAstaInversa/{asta_inversa}/{lista_categorie}")
    Call<Long> saveAsta_inversa(@Body Asta_inversa_DTO asta_inversa_dto, @Query("lista_categorie") ArrayList<String> lista_categorie);
    @GET("/asta_inversaController/getEmailVincente/{indirizzo_email}/{idAstaInversa}")
    Call<Boolean> getEmailVincente(@Path("indirizzo_email") String indirizzo_email, @Path("idAstaInversa") Long idAstaInversa);

    @GET("/asta_inversaController/getAstePerRicerca/{nome}/{ordinamento}/{nomiCategorie}")
    Call<ArrayList<Asta_inversa_DTO>> getAstePerRicerca(@Query("nome") String nome, @Query("ordinamento") String ordinamento, @Query("nomiCategorie") ArrayList<String> nomiCategorie );
}

