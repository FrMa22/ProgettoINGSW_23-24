package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.SocialAcquirenteDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SocialAcquirenteService {

    @GET("/socialAcquirenteController/findSocialAcquirente/{indirizzo_email}")
    Call<List<SocialAcquirenteDTO>> findSocialAcquirente(@Path("indirizzo_email") String indirizzo_email);

    @POST("/socialAcquirenteController/insertSocialAcquirente/{nome}/{link}/{indirizzo_email}")
    Call<SocialAcquirenteDTO> insertSocialAcquirente(@Path("nome") String nome,@Path("link") String link,@Path("indirizzo_email") String indirizzo_email);

    @DELETE("/socialAcquirenteController/deleteSocialAcquirente/{nome}/{link}/{indirizzo_email}")
    Call<Void> deleteSocialAcquirente(@Path("nome") String nome,@Path("link") String link,@Path("indirizzo_email") String indirizzo_email);

    @PUT("/socialAcquirenteController/updateSocialAcquirente/{oldNome}/{oldLink}/{newNome}/{newLink}")
    Call<Void> updateSocialAcquirente(@Path("oldNome") String oldNome,@Path("oldLink") String oldLink,@Path("newNome") String newNome,@Path("newLink") String newLink);

    @POST("/socialAcquirenteController/insertSocialAcquirenteRegistrazione")
    Call<Void> insertSocialAcquirenteRegistrazione(@Body ArrayList<SocialAcquirenteDTO> listaSocialDTO);

}
