package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.AcquirenteDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AcquirenteService {
    @GET("/utenteController/loginAcquirente/{indirizzo_email}/{password}")
    Call<AcquirenteDTO> logInAcquirente(@Path("indirizzo_email") String indirizzo_email, @Path("password") String password);

    @GET("/utenteController/findCategorieByIndirizzoEmailAcquirente/{indirizzo_email}")
    Call<ArrayList<String>> findCategorieByIndirizzoEmailAcquirente(@Path(("indirizzo_email")) String indirizzo_email);

    @PUT("/utenteController/updateAcquirente/{oldNome}/{oldLink}/{newNome}/{newLink}")
    Call<Void> updateAcquirente(@Path("oldNome") String oldNome,@Path("oldLink") String oldLink,@Path("newNome") String newNome,@Path("newLink") String newLink);

    @PUT("/utenteController/updateAcquirente/{nome}/{cognome}/{bio}/{link}/{areageografica}/{indirizzo_email}")
    Call<Void> updateAcquirente(@Path("nome") String nome, @Path("cognome") String cognome, @Path("bio") String bio, @Path("link") String link, @Path("areageografica") String areageografica, @Path("indirizzo_email") String indirizzo_email);


    @PUT("/utenteController/updatePasswordAcquirente/{password}/{indirizzo_email}")
    Call<Void> updatePasswordAcquirente(@Path("password") String password,@Path("indirizzo_email") String indirizzo_email);



}
