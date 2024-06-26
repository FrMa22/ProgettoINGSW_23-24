package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.AcquirenteDTO;
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

public interface Asta_allingleseService {
    @GET("/asta_allingleseController/getAste_allingleseScadenzaRecente")
    Call<ArrayList<Asta_allinglese_DTO>> getAste_allingleseScadenzaRecente();
    @GET("/asta_allingleseController/getAste_allingleseNuove")
    Call<ArrayList<Asta_allinglese_DTO>> getAste_allingleseNuove();
    @GET("/asta_allingleseController/getAste_allingleseNomeCategoria")
    Call<ArrayList<Asta_allinglese_DTO>> getAste_allingleseCategoriaNome(@Query("nomiCategorie") ArrayList<String> nomiCategorie);

    @GET("/asta_allingleseController/getAste_allingleseApertaByEmail/{indirizzo_email}")
    Call<List<Asta_allinglese_DTO>> getAste_allingleseApertaByEmail(@Path("indirizzo_email") String indirizzo_email);

    @GET("/asta_allingleseController/getAste_allingleseChiusaByEmail/{indirizzo_email}")
    Call<List<Asta_allinglese_DTO>> getAste_allingleseChiusaByEmail(@Path("indirizzo_email") String indirizzo_email);


    @POST("/asta_allingleseController/partecipaAstaInglese/{idAstaInglese}/{indirizzo_email}/{offerta}/{tempo_offerta}/{stato}")
    Call<Integer> partecipaAsta_allinglese(@Path("idAstaInglese") Long idAstaInglese, @Path("indirizzo_email") String indirizzo_email, @Path("offerta") String offerta,
                                           @Path("tempo_offerta") String tempo_offerta,@Path("stato") String stato);
    @GET("/asta_allingleseController/trovaAstaInglese/{idAstaInglese}")
    Call<Asta_allinglese_DTO> trovaAstaInglese(@Path("idAstaInglese") Long idAstaInglese);
    @GET("/asta_allingleseController/verificaAstaIngleseInPreferiti/{indirizzo_email}/{idAstaInglese}")
    Call<Integer> verificaAstaIngleseInPreferiti(@Path("indirizzo_email") String indirizzo_email, @Path("idAstaInglese") Long idAstaInglese);
    @POST("/asta_allingleseController/inserimentoAstaInPreferiti/{idAstaInglese}/{indirizzo_email}")
    Call<Integer> inserimentoAstaInPreferiti(@Path("idAstaInglese") Long idAstaInglese, @Path("indirizzo_email") String indirizzo_email);
    @DELETE("/asta_allingleseController/eliminazioneAstaInPreferiti/{idAstaInglese}/{indirizzo_email}")
    Call<Integer> eliminazioneAstaInPreferiti(@Path("idAstaInglese") Long idAstaInglese, @Path("indirizzo_email") String indirizzo_email);

    @GET("/asta_allingleseController/getAsteInglesePreferite/{indirizzo_email}")
    Call<ArrayList<Asta_allinglese_DTO>> getAsteInglesePreferite(@Path("indirizzo_email") String indirizzo_email);

    @GET("/asta_allingleseController/getAsteInglesePartecipate/{indirizzo_email}")
    Call<ArrayList<Asta_allinglese_DTO>> getAste_allinglesePartecipateByEmail(@Path("indirizzo_email") String indirizzo_email);

    @POST("/asta_allingleseController/insertAstaInglese")
    Call<Long> saveAsta_inglese(@Body Asta_allinglese_DTO asta_allinglese_dto, @Query("lista_categorie") ArrayList<String> lista_categorie);

    @GET("/asta_allingleseController/getEmailVincente/{indirizzo_email}/{idAstaInglese}")
    Call<Boolean> getEmailVincente(@Path("indirizzo_email") String indirizzo_email, @Path("idAstaInglese") Long idAstaInglese);

    @GET("/asta_allingleseController/getAstePerRicerca")
    Call<ArrayList<Asta_allinglese_DTO>> getAstePerRicerca(@Query("nome") String nome,@Query("ordinamento") String ordinamento,@Query("nomiCategorie") ArrayList<String> nomiCategorie );
}
