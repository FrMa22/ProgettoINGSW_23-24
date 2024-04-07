package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.DTO.Asta_alribasso_DTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Asta_alribassoService {

    @GET("/asta_alribassoController/getAste_alRibassoNuove")
    Call<ArrayList<Asta_alribasso_DTO>> getAste_alribassoNuove();
}
