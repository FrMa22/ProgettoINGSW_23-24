package com.example.progettoingsw.repository;

import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.model.VenditoreModel;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private AcquirenteModel acquirenteModel;
    private VenditoreModel venditoreModel;
    private List<SocialAcquirenteModel> socialAcquirenteModelList;
    private List<SocialVenditoreModel> socialVenditoreModelList;
    public static final String backendUrl = "http://13.60.50.25:8080";
    public static Repository questaRepository = null;

    private Repository(){
        socialAcquirenteModelList=new ArrayList<>();
        socialVenditoreModelList=new ArrayList<>();

    }
    public static Repository getInstance() {
        if (questaRepository == null) {
            questaRepository = new Repository();
        }
        return questaRepository;
    }

    public void setAcquirenteModel(AcquirenteModel acquirenteModel){
        this.acquirenteModel = acquirenteModel;
    }

    public AcquirenteModel getAcquirenteModel(){
        return acquirenteModel;
    }
    public void setVenditoreModel(VenditoreModel venditoreModel){
        this.venditoreModel = venditoreModel;
    }

    public VenditoreModel getVenditoreModel(){
        return venditoreModel;
    }

    public List<SocialAcquirenteModel> getSocialAcquirenteModelList() {
        return socialAcquirenteModelList;
    }

    public List<SocialVenditoreModel> getSocialVenditoreModelList() {
        return socialVenditoreModelList;
    }

    public void setSocialAcquirenteModelList(List<SocialAcquirenteModel> socialAcquirenteModelList) {
        this.socialAcquirenteModelList = socialAcquirenteModelList;
    }

    public void setSocialVenditoreModelList(List<SocialVenditoreModel> socialVenditoreModelList) {
        this.socialVenditoreModelList = socialVenditoreModelList;
    }

    public void addSocialAcquirente(SocialAcquirenteModel socialAcquirenteModel){
        this.socialAcquirenteModelList.add(socialAcquirenteModel);
    }

    public void deleteSocialAcquirente(SocialAcquirenteModel socialAcquirenteModel){
        this.socialAcquirenteModelList.remove(socialAcquirenteModel);
    }

    public void updateSocialAcquirente(String oldNome,String oldLink,String newNome,String newLink){
        for(SocialAcquirenteModel s:socialAcquirenteModelList){
            if (s.getNome().equals(oldNome) && s.getLink().equals(oldLink)) {
                s.setNome(newNome);
                s.setLink(newLink);
                break;
            }
        }

    }

    public void updateAcquirente(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo){
         this.acquirenteModel.setNome(nomeNuovo);
        this.acquirenteModel.setCognome(cognomeNuovo);
        this.acquirenteModel.setBio(bioNuovo);
        this.acquirenteModel.setAreaGeografica(areageograficaNuovo);
        this.acquirenteModel.setLink(linkNuovo);
    }

    public void updatePasswordAcquirente(String passwordNuovo){
        this.acquirenteModel.setPassword(passwordNuovo);
    }



    public List<String> getNomiSocialAcquirenteModelList(List<SocialAcquirenteModel> socialAcquirenteModelList){
        List<String> nomi = new ArrayList<>();
        for (SocialAcquirenteModel social : socialAcquirenteModelList) {
            nomi.add(social.getNome());
        }
        return nomi;
    }

    public List<String> getLinksSocialAcquirenteModelList(List<SocialAcquirenteModel> socialAcquirenteModelList){
        List<String> links = new ArrayList<>();
        for (SocialAcquirenteModel social : socialAcquirenteModelList) {
            links.add(social.getLink());
        }
        return links;
    }


}
