package com.example.progettoingsw.backendAPI;


import com.example.progettoingsw.DTO.SocialAcquirenteDTO;
import com.example.progettoingsw.DTO.SocialVenditoreDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SocialVenditoreService {

    @GET("/socialVenditoreController/findSocialVenditore/{indirizzo_email}")
    Call<List<SocialVenditoreDTO>> findSocialVenditore(@Path("indirizzo_email") String indirizzo_email);

    @POST("/socialVenditoreController/insertSocialVenditore/{nome}/{link}/{indirizzo_email}")
    Call<SocialVenditoreDTO> insertSocialVenditore(@Path("nome") String nome,@Path("link") String link,@Path("indirizzo_email") String indirizzo_email);

    @DELETE("/socialVenditoreController/deleteSocialVenditore/{nome}/{link}/{indirizzo_email}")
    Call<Void> deleteSocialVenditore(@Path("nome") String nome,@Path("link") String link,@Path("indirizzo_email") String indirizzo_email);

    @PUT("/socialVenditoreController/updateSocialVenditore/{oldNome}/{oldLink}/{newNome}/{newLink}")
    Call<Void> updateSocialVenditore(@Path("oldNome") String oldNome,@Path("oldLink") String oldLink,@Path("newNome") String newNome,@Path("newLink") String newLink);
    @POST("/socialVenditoreController/insertSocialVenditoreRegistrazione/{listaSocialDTO}")
    Call<Void> insertSocialVenditoreRegistrazione(@Body ArrayList<SocialVenditoreDTO> listaSocialDTO);
}
