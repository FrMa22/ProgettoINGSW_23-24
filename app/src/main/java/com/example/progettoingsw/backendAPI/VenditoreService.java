package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.VenditoreDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VenditoreService {
    @GET("/utenteController/loginVenditore/{indirizzo_email}/{password}")
    Call<VenditoreDTO> logInVenditore(@Path("indirizzo_email") String indirizzo_email, @Path("password") String password);
}
