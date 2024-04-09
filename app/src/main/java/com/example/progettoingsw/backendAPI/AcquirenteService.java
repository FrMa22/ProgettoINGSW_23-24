package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.AcquirenteDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AcquirenteService {
    @GET("/utenteController/loginAcquirente/{indirizzo_email}/{password}")
    Call<AcquirenteDTO> logInAcquirente(@Path("indirizzo_email") String indirizzo_email, @Path("password") String password);

    @GET("/utenteController/findCategorieByIndirizzoEmailAcquirente/{indirizzo_email}")
    Call<ArrayList<String>> findCategorieByIndirizzoEmailAcquirente(@Path(("indirizzo_email")) String indirizzo_email);
}
