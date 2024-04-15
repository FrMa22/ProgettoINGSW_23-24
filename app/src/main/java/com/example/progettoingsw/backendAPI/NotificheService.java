package com.example.progettoingsw.backendAPI;

import com.example.progettoingsw.DTO.Asta_allinglese_DTO;
import com.example.progettoingsw.DTO.NotificheAcquirente_DTO;
import com.example.progettoingsw.DTO.NotificheVenditore_DTO;
import com.example.progettoingsw.model.NotificheAcquirenteModel;
import com.example.progettoingsw.model.NotificheVenditoreModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificheService {
    @GET("/notificheController/getNotificheAcquirente/{idAcquirente}")
    Call<ArrayList<NotificheAcquirente_DTO>> getNotificheAcquirente(@Path("idAcquirente") String idAcquirente);
    @GET("/notificheController/getNotificheVenditore/{idVenditore}")
    Call<ArrayList<NotificheVenditore_DTO>> getNotificheVenditore(@Path("idVenditore") String idVenditore);
    @DELETE("/notificheController/deleteNotificheAcquirente/{id}")
    Call<Integer> deleteNotificheAcquirente(@Path("id") Long id);
    @DELETE("/notificheController/deleteNotificheVenditore/{id}")
    Call<Integer> deleteNotificheVenditore(@Path("id") Long id);
}
