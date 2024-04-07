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

}
