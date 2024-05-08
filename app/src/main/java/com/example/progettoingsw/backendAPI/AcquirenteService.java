package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.AcquirenteDTO;
import com.example.progettoingsw.DTO.Asta_allinglese_DTO;

import net.bytebuddy.implementation.bind.annotation.Empty;

import java.util.ArrayList;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AcquirenteService {
    @GET("/utenteController/loginAcquirente/{indirizzo_email}/{password}")
    Call<AcquirenteDTO> logInAcquirente(@Path("indirizzo_email") String indirizzo_email, @Path("password") String password);
    @GET("/utenteController/loginAcquirenteConToken/{token}")
    Call<AcquirenteDTO> loginAcquirenteConToken(@Path("token") String token);
    @GET("/utenteController/findCategorieByIndirizzoEmailAcquirente/{indirizzo_email}")
    Call<ArrayList<String>> findCategorieByIndirizzoEmailAcquirente(@Path(("indirizzo_email")) String indirizzo_email);


    @PUT("/utenteController/updateAcquirente/{acquirente}")
    Call<Void> updateAcquirente(@Body AcquirenteDTO acquirenteDTO);


    @PUT("/utenteController/updatePasswordAcquirente/{password}/{indirizzo_email}")
    Call<Void> updatePasswordAcquirente(@Path("password") String password,@Path("indirizzo_email") String indirizzo_email);

    @GET("/utenteController/registrazioneAcquirenteDoppio/{indirizzo_email}")
    Call<AcquirenteDTO> registrazioneAcquirenteDoppio(@Path("indirizzo_email") String indirizzo_email);
    @PUT("/utenteController/setTokenAcquirente/{indirizzo_email}/{token}")
    Call<Integer> setTokenAcquirente(@Path("indirizzo_email") String indirizzo_email,@Path(("token")) String token);

    @POST("/utenteController/insertAcquirente/{acquirente}")
    Call<Long> saveAcquirente(@Body AcquirenteDTO acquirenteDTO);
    @POST("utenteController/saveCategorieAcquirente/{indirizzo_email}/{lista_categorie}")
    Call<Void> saveCategorieAcquirente(@Path("indirizzo_email")String email, @Query("lista_categorie")ArrayList<String> lista_categorie);
    @PUT("/utenteController/removeTokenFromAcquirente/{indirizzo_email}")
    Call<Integer> removeTokenFromAcquirente(@Path("indirizzo_email") String indirizzo_email);

    @GET("/utenteController/getAcquirenteByIndirizzo_email/{indirizzo_email}")
    Call<AcquirenteDTO> getAcquirenteByIndirizzo_email(@Path("indirizzo_email") String indirizzo_email);
}
