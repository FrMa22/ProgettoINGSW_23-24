package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.AcquirenteDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AcquirenteService {
    @GET("/acquirenteController/login/{indirizzo_email}/{password}")
    Call<AcquirenteDTO> logIn(@Path("indirizzo_email") String indirizzo_email, @Path("password") String password);
}
