package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.SocialAcquirenteDTO;
import com.example.progettoingsw.DTO.SocialVenditoreDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SocialVenditoreService {

    @GET("/socialVenditoreController/findSocialVenditore/{indirizzo_email}")
    Call<List<SocialVenditoreDTO>> findSocialAcquirente(@Path("indirizzo_email") String indirizzo_email);

}
