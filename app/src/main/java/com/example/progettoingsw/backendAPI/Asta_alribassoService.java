package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.DTO.Asta_alribasso_DTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Asta_alribassoService {

    @GET("/asta_alribassoController/getAste_alRibassoNuove")
    Call<ArrayList<Asta_alribasso_DTO>> getAste_alribassoNuove();

    @GET("/asta_alribassoController/getAste_alribassoNomeCategoria")
    Call<ArrayList<Asta_alribasso_DTO>> getAste_alribassoCategoriaNome(@Query("nomiCategorie") ArrayList<String> nomiCategorie);

    @GET("/asta_alribassoController/getAste_alribassoApertaByEmail/{indirizzo_email}")
    Call<List<Asta_alribasso_DTO>> getAste_alribassoApertaByEmail(@Path("indirizzo_email") String indirizzo_email);

    @GET("/asta_alribassoController/getAste_alribassoChiusaByEmail/{indirizzo_email}")
    Call<List<Asta_alribasso_DTO>> getAste_alribassoChiusaByEmail(@Path("indirizzo_email") String indirizzo_email);

    @POST("/asta_alribassoController/acquistaAstaAlRibasso/{idAstaAlRibasso}/{indirizzo_email}/{prezzoAcquisto}")
    Call<Integer> acquistaAstaAlRibasso(@Path("idAstaAlRibasso") Long idAstaAlRibasso, @Path("indirizzo_email") String indirizzo_email, @Path("prezzoAcquisto") String prezzoAcquisto);

    @GET("/asta_alribassoController/trovaAstaRibasso/{idAstaRibasso}")
    Call<Asta_alribasso_DTO> trovaAstaRibasso(@Path("idAstaRibasso") Long idAstaRibasso);
    @GET("/asta_alribassoController/verificaAstaAlRibassoInPreferiti/{indirizzo_email}/{idAstaRibasso}")
    Call<Integer> verificaAstaAlRibassoInPreferiti(@Path("indirizzo_email") String indirizzo_email, @Path("idAstaRibasso") Long idAstaRibasso);
    @POST("/asta_alribassoController/inserimentoAstaInPreferiti/{idAstaRibasso}/{indirizzo_email}")
    Call<Integer> inserimentoAstaInPreferiti(@Path("idAstaRibasso") Long idAstaRibasso, @Path("indirizzo_email") String indirizzo_email);
    @DELETE("/asta_alribassoController/eliminazioneAstaInPreferiti/{idAstaRibasso}/{indirizzo_email}")
    Call<Integer> eliminazioneAstaInPreferiti(@Path("idAstaRibasso") Long idAstaRibasso, @Path("indirizzo_email") String indirizzo_email);

    @GET("/asta_alribassoController/getAsteRibassoPreferite/{indirizzo_email}")
    Call<ArrayList<Asta_alribasso_DTO>> getAsteRibassoPreferite(@Path("indirizzo_email") String indirizzo_email);

    @POST("/asta_alribassoController/insertAstaRibasso")
    Call<Long> saveAsta_ribasso(@Body Asta_alribasso_DTO asta_ribasso_dto, @Query("lista_categorie") ArrayList<String> lista_categorie);

    @GET("/asta_alribassoController/getAstePerRicerca")
    Call<ArrayList<Asta_alribasso_DTO>> getAstePerRicerca(@Query("nome") String nome,@Query("ordinamento") String ordinamento,@Query("nomiCategorie") ArrayList<String> nomiCategorie );
}
