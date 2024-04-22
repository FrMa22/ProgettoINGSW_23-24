package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.VenditoreDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VenditoreService {
    @GET("/utenteController/loginVenditore/{indirizzo_email}/{password}")
    Call<VenditoreDTO> logInVenditore(@Path("indirizzo_email") String indirizzo_email, @Path("password") String password);
    @GET("/utenteController/loginVenditoreConToken/{token}")
    Call<VenditoreDTO> loginVenditoreConToken(@Path("token") String token);
    @GET("/utenteController/findCategorieByIndirizzoEmailVenditore/{indirizzo_email}")
    Call<ArrayList<String>> findCategorieByIndirizzoEmailVenditore(@Path(("indirizzo_email")) String indirizzo_email);

    @PUT("/utenteController/updateVenditore/{oldNome}/{oldLink}/{newNome}/{newLink}")
    Call<Void> updateVenditore(@Path("oldNome") String oldNome,@Path("oldLink") String oldLink,@Path("newNome") String newNome,@Path("newLink") String newLink);

    @PUT("/utenteController/updateVenditore/{nome}/{cognome}/{bio}/{link}/{areageografica}/{indirizzo_email}")
    Call<Void> updateVenditore(@Path("nome") String nome, @Path("cognome") String cognome, @Path("bio") String bio, @Path("link") String link, @Path("areageografica") String areageografica, @Path("indirizzo_email") String indirizzo_email);


    @PUT("/utenteController/updatePasswordVenditore/{password}/{indirizzo_email}")
    Call<Void> updatePasswordVenditore(@Path("password") String password,@Path("indirizzo_email") String indirizzo_email);

    @PUT("/utenteController/setTokenVenditore/{indirizzo_email}/{token}")
    Call<Integer> setTokenVenditore(@Path("indirizzo_email") String indirizzo_email,@Path(("token")) String token);

    @PUT("/utenteController/removeTokenFromVenditore/{indirizzo_email}")
    Call<Integer> removeTokenFromVenditore(@Path("indirizzo_email") String indirizzo_email);

    @GET("/utenteController/getVenditoreByIndirizzo_email/{indirizzo_email}")
    Call<VenditoreDTO> getVenditoreByIndirizzo_email(@Path("indirizzo_email") String indirizzo_email);
}
