package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.Asta_allinglese_DTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Asta_allingleseService {

    @GET("/asta_allingleseController/getAste_allingleseScadenzaRecente")
    Call<ArrayList<Asta_allinglese_DTO>> getAste_allingleseScadenzaRecente();

    @GET("/asta_allingleseController/getAste_allingleseNuove")
    Call<ArrayList<Asta_allinglese_DTO>> getAste_allingleseNuove();

    @GET("/asta_allingleseController/getAste_allingleseNomeCategoria/{nomeCategoria}")
    Call<ArrayList<Asta_allinglese_DTO>> getAste_allingleseCategoriaNome(@Path("nomeCategoria") String nomeCategoria);

    @GET("/asta_allingleseController/partecipaAstaInglese/{idAstaInglese}/{indirizzo_email}/{offerta}/{tempo_offerta}/{stato}")
    Call<Integer> partecipaAsta_allinglese(@Path("idAstaInglese") Long idAstaInglese, @Path("indirizzo_email") String indirizzo_email, @Path("offerta") String offerta,
                                           @Path("tempo_offerta") String tempo_offerta,@Path("stato") String stato);

    @GET("/asta_allingleseController/trovaAstaInglese/{idAstaInglese}")
    Call<Asta_allinglese_DTO> trovaAstaInglese(@Path("idAstaInglese") Long idAstaInglese);
}
