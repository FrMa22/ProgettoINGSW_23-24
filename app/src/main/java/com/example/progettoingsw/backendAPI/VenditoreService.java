package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.VenditoreDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VenditoreService {
    @GET("/utenteController/loginVenditore/{indirizzo_email}/{password}")
    Call<VenditoreDTO> logInVenditore(@Path("indirizzo_email") String indirizzo_email, @Path("password") String password);
    @GET("/utenteController/loginVenditoreConToken/{token}")
    Call<VenditoreDTO> loginVenditoreConToken(@Path("token") String token);
    @GET("/utenteController/findCategorieByIndirizzoEmailVenditore/{indirizzo_email}")
    Call<ArrayList<String>> findCategorieByIndirizzoEmailVenditore(@Path(("indirizzo_email")) String indirizzo_email);
    @GET("/utenteController/registrazioneVenditoreDoppio/{indirizzo_email}")
    Call<VenditoreDTO> registrazioneVenditoreDoppio(@Path("indirizzo_email") String indirizzo_email);




    @PUT("/utenteController/updateVenditore")
    Call<Void> updateVenditore(@Body VenditoreDTO venditoreDTO);


    @PUT("/utenteController/updatePasswordVenditore/{password}/{indirizzo_email}")
    Call<Void> updatePasswordVenditore(@Path("password") String password,@Path("indirizzo_email") String indirizzo_email);
    @POST("/utenteController/insertVenditore")
    Call<Long> saveVenditore(@Body VenditoreDTO venditoreDTO);
    @POST("utenteController/saveCategorieVenditore/{indirizzo_email}")
    Call<Void> saveCategorieVenditore(@Path("indirizzo_email")String email, @Query("lista_categorie")ArrayList<String> lista_categorie);

    @PUT("/utenteController/setTokenVenditore/{indirizzo_email}/{token}")
    Call<Integer> setTokenVenditore(@Path("indirizzo_email") String indirizzo_email,@Path(("token")) String token);

    @PUT("/utenteController/removeTokenFromVenditore/{indirizzo_email}")
    Call<Integer> removeTokenFromVenditore(@Path("indirizzo_email") String indirizzo_email);

    @GET("/utenteController/getVenditoreByIndirizzo_email/{indirizzo_email}")
    Call<VenditoreDTO> getVenditoreByIndirizzo_email(@Path("indirizzo_email") String indirizzo_email);
}
