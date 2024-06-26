package com.example.progettoingsw.repository;


import com.example.progettoingsw.model.AcquirenteModel;
import com.example.progettoingsw.model.Asta_allingleseModel;
import com.example.progettoingsw.model.Asta_alribassoModel;
import com.example.progettoingsw.model.Asta_inversaModel;
import com.example.progettoingsw.model.NotificheAcquirenteModel;
import com.example.progettoingsw.model.NotificheVenditoreModel;
import com.example.progettoingsw.model.SocialAcquirenteModel;
import com.example.progettoingsw.model.SocialVenditoreModel;
import com.example.progettoingsw.model.VenditoreModel;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private AcquirenteModel acquirenteModel;
    private ArrayList<String> listaCategorieAcquirente;
    private VenditoreModel venditoreModel;
    private List<SocialAcquirenteModel> socialAcquirenteModelList;
    private List<SocialVenditoreModel> socialVenditoreModelList;
    private String acquirenteEmailDaAsta;
    private String venditoreEmailDaAsta;
    private String nomeSocialSelezionato;
    private String nomeLinkSelezionato;



    private Boolean leMieAsteUtenteAttuale;

    private ArrayList<SocialAcquirenteModel> listaSocialAcquirenteRecuperati;

    private ArrayList<SocialVenditoreModel> listaSocialVenditoreRecuperati;

    private ArrayList<String> listaCategorieVenditore;


    private String categoriaSelezionata;
    public static final String backendUrl = "http:/15.188.26.17:8080/";
    public static Repository questaRepository = null;
    //liste per le aste all'inglese nel caso di accesso come acquirente (aste in home)
    private ArrayList<Asta_allingleseModel> listaAsteAllIngleseInScadenza;
    private ArrayList<Asta_allingleseModel> listaAsteAllIngleseCategoriaNome;
    private ArrayList<Asta_allingleseModel> listaAsteAllIngleseNuove;
    //liste per le aste al ribasso nel caso di accesso come acquirente (aste in home)
    private ArrayList<Asta_alribassoModel> listaAsteAlRibassoCategoriaNome;
    private ArrayList<Asta_alribassoModel> listaAsteAlRibassoNuove;
    //liste per le aste inverse nel caso di accesso come venditore (aste in home)
    private ArrayList<Asta_inversaModel> listaAsteInversaInScadenza;
    private ArrayList<Asta_inversaModel> listaAsteInversaNuove;
    //private Set<Asta_inversaModel> listaAsteInversaCategoriaNome;
    private ArrayList<Asta_inversaModel> listaAsteInversaCategoriaNome;
    private NotificheAcquirenteModel notificaAcquirenteScelta;
    private NotificheVenditoreModel notificaVenditoreScelta;

    private String nome_socialAcquirenteSelezionato;
    private String link_socialAcquirenteSelezionato;

    //questi servono per accedere a un asta cliccandoci sopra, i commentati sono per testare senza db
    private Asta_allingleseModel asta_allingleseSelezionata;

    private Asta_alribassoModel asta_alribassoSelezionata;

    private Asta_inversaModel asta_inversaSelezionata;



    private Repository(){
        socialAcquirenteModelList=new ArrayList<>();
        socialVenditoreModelList=new ArrayList<>();
        listaSocialAcquirenteRecuperati=new ArrayList<>();

    }
    public static Repository getInstance() {
        if (questaRepository == null) {
            questaRepository = new Repository();
        }
        return questaRepository;
    }
    public void deleteRepository(){
        questaRepository=null;
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


    public boolean findSocialAcquirente(String oldNome,String oldLink){
        for(SocialAcquirenteModel s:socialAcquirenteModelList){
            if (s.getNome().equals(oldNome) && s.getLink().equals(oldLink)) {
                return true;
            }
        }
        return false;
    }


    public void updateAcquirente(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo){
         this.acquirenteModel.setNome(nomeNuovo);
        this.acquirenteModel.setCognome(cognomeNuovo);
        this.acquirenteModel.setBio(bioNuovo);
        this.acquirenteModel.setAreageografica(areageograficaNuovo);
        this.acquirenteModel.setLink(linkNuovo);
    }

    public void updatePasswordAcquirente(String passwordNuovo){
        this.acquirenteModel.setPassword(passwordNuovo);
    }

//versione venditore

    public void addSocialVenditore(SocialVenditoreModel socialVenditoreModel){
        this.socialVenditoreModelList.add(socialVenditoreModel);
    }

    public void deleteSocialVenditore(SocialVenditoreModel socialVenditoreModel){
        this.socialVenditoreModelList.remove(socialVenditoreModel);
    }

    public void updateSocialVenditore(String oldNome,String oldLink,String newNome,String newLink){
        for(SocialVenditoreModel s:socialVenditoreModelList){
            if (s.getNome().equals(oldNome) && s.getLink().equals(oldLink)) {
                s.setNome(newNome);
                s.setLink(newLink);
                break;
            }
        }

    }

    public boolean findSocialVenditore(String oldNome,String oldLink){
        for(SocialVenditoreModel s:socialVenditoreModelList){
            if (s.getNome().equals(oldNome) && s.getLink().equals(oldLink)) {
                return true;
            }
        }
        return false;
    }


    public void updateVenditore(String nomeNuovo,String cognomeNuovo,String bioNuovo,String linkNuovo,String areageograficaNuovo){
        this.venditoreModel.setNome(nomeNuovo);
        this.venditoreModel.setCognome(cognomeNuovo);
        this.venditoreModel.setBio(bioNuovo);
        this.venditoreModel.setAreageografica(areageograficaNuovo);
        this.venditoreModel.setLink(linkNuovo);
    }

    public void updatePasswordVenditore(String passwordNuovo){
        this.venditoreModel.setPassword(passwordNuovo);
    }

    public List<String> getNomiSocialAcquirenteModelList(){
        List<String> nomi = new ArrayList<>();
        for (SocialAcquirenteModel social : socialAcquirenteModelList) {
            nomi.add(social.getNome());
        }
        return nomi;
    }

    public List<String> getLinksSocialAcquirenteModelList(){
        List<String> links = new ArrayList<>();
        for (SocialAcquirenteModel social : socialAcquirenteModelList) {
            links.add(social.getLink());
        }
        return links;
    }



    //metodi getter e setter per le aste inglesi
    public ArrayList<Asta_allingleseModel> getListaAsteAllIngleseInScadenza() {
        return listaAsteAllIngleseInScadenza;
    }
    public void setListaAsteAllIngleseInScadenza(ArrayList<Asta_allingleseModel> listaAsteAllIngleseInScadenza) {
        this.listaAsteAllIngleseInScadenza = listaAsteAllIngleseInScadenza;
    }
    public ArrayList<Asta_allingleseModel> getListaAsteAllIngleseNuove() {
        return listaAsteAllIngleseNuove;
    }
    public void setListaAsteAllIngleseNuove(ArrayList<Asta_allingleseModel> listaAsteAllIngleseNuove) {
        this.listaAsteAllIngleseNuove = listaAsteAllIngleseNuove;
    }
    public ArrayList<Asta_allingleseModel> getListaAsteAllIngleseCategoriaNome() {
        return listaAsteAllIngleseCategoriaNome;
    }
    public void setListaAsteAllIngleseCategoriaNome(ArrayList<Asta_allingleseModel> listaAsteAllIngleseCategoriaNome) {
        this.listaAsteAllIngleseCategoriaNome = listaAsteAllIngleseCategoriaNome;
    }
    public Asta_allingleseModel getAsta_allingleseSelezionata() {
        return asta_allingleseSelezionata;
    }
    public void setAsta_allingleseSelezionata(Asta_allingleseModel asta_allingleseSelezionata) {
        this.asta_allingleseSelezionata = asta_allingleseSelezionata;
    }

    //metodi getter e setter per le aste al ribasso
    public void setListaAsteAlRibassoNuove(ArrayList<Asta_alribassoModel> listaAsteAlRibassoNuove){
        this.listaAsteAlRibassoNuove = listaAsteAlRibassoNuove;
    }
    public ArrayList<Asta_alribassoModel> getListaAsteAlRibassoNuove(){
        return listaAsteAlRibassoNuove;
    }
    public ArrayList<Asta_alribassoModel> getListaAsteAlRibassoCategoriaNome() {
        return listaAsteAlRibassoCategoriaNome;
    }
    public void setListaAsteAlRibassoCategoriaNome(ArrayList<Asta_alribassoModel> listaAsteAlRibassoCategoriaNome) {
        this.listaAsteAlRibassoCategoriaNome = listaAsteAlRibassoCategoriaNome;
    }
    public Asta_alribassoModel getAsta_alribassoSelezionata() {
        return asta_alribassoSelezionata;
    }

    public void setAsta_alribassoSelezionata(Asta_alribassoModel asta_alribassoSelezionata) {
        this.asta_alribassoSelezionata = asta_alribassoSelezionata;
    }

    //metodi getter e setter per le aste inverse
    public ArrayList<Asta_inversaModel> getListaAsteInversaInScadenza() {
        return listaAsteInversaInScadenza;
    }
    public void setListaAsteInversaInScadenza(ArrayList<Asta_inversaModel> listaAsteInversaInScadenza) {
        this.listaAsteInversaInScadenza = listaAsteInversaInScadenza;
    }
    public ArrayList<Asta_inversaModel> getListaAsteInversaNuove() {
        return listaAsteInversaNuove;
    }
    public void setListaAsteInversaNuove(ArrayList<Asta_inversaModel> listaAsteInversaNuove) {
        this.listaAsteInversaNuove = listaAsteInversaNuove;
    }
    public ArrayList<Asta_inversaModel> getListaAsteInversaCategoriaNome() {
        return listaAsteInversaCategoriaNome;
    }
    public void setListaAsteInversaCategoriaNome(ArrayList<Asta_inversaModel> listaAsteInversaCategoriaNome) {
        this.listaAsteInversaCategoriaNome = listaAsteInversaCategoriaNome;
    }
    public Asta_inversaModel getAsta_inversaSelezionata() {
        return asta_inversaSelezionata;
    }
    public void setAsta_inversaSelezionata(Asta_inversaModel asta_inversaSelezionata) {
        this.asta_inversaSelezionata = asta_inversaSelezionata;
    }





    public ArrayList<String> getListaCategorieAcquirente() {
        return listaCategorieAcquirente;
    }

    public void setListaCategorieAcquirente(ArrayList<String> listaCategorieAcquirente) {
        this.listaCategorieAcquirente = listaCategorieAcquirente;
    }
    public ArrayList<String> getListaCategorieVenditore() {
        return listaCategorieVenditore;
    }

    public void setListaCategorieVenditore(ArrayList<String> listaCategorieVenditore) {
        this.listaCategorieVenditore = listaCategorieVenditore;
    }


    public void setSocialAcquirenteSelezionato(String nome,String link) {
        this.nome_socialAcquirenteSelezionato=nome;
        this.link_socialAcquirenteSelezionato=link;
    }


    public ArrayList<SocialAcquirenteModel> getListaSocialAcquirenteRecuperati() {
        return (ArrayList<SocialAcquirenteModel>) socialAcquirenteModelList;
    }
    public void setListaSocialAcquirenteRecuperati(ArrayList<SocialAcquirenteModel> listaSocialAcquirenteRecuperati) {
        this.listaSocialAcquirenteRecuperati = listaSocialAcquirenteRecuperati;
    }
    public NotificheAcquirenteModel getNotificaAcquirenteScelta() {
        return notificaAcquirenteScelta;
    }
    public void setNotificaAcquirenteScelta(NotificheAcquirenteModel notificaAcquirenteScelta) {
        this.notificaAcquirenteScelta = notificaAcquirenteScelta;
    }
    public NotificheVenditoreModel getNotificaVenditoreScelta() {
        return notificaVenditoreScelta;
    }

    public void setNotificaVenditoreScelta(NotificheVenditoreModel notificaVenditoreScelta) {
        this.notificaVenditoreScelta = notificaVenditoreScelta;
    }


    public ArrayList<SocialVenditoreModel> getListaSocialVenditoreRecuperati() {
        return (ArrayList<SocialVenditoreModel>) socialVenditoreModelList;
    }
    public void setListaSocialVenditoreRecuperati(ArrayList<SocialVenditoreModel> listaSocialVenditoreRecuperati) {
        this.listaSocialVenditoreRecuperati = listaSocialVenditoreRecuperati;
    }

    public String getCategoriaSelezionata() {
        return categoriaSelezionata;
    }
    public void setCategoriaSelezionata(String categoriaSelezionata) {
        this.categoriaSelezionata = categoriaSelezionata;
    }
    public String getAcquirenteEmailDaAsta() {
        return acquirenteEmailDaAsta;
    }
    public void setAcquirenteEmailDaAsta(String acquirenteEmailDaAsta) {
        this.acquirenteEmailDaAsta = acquirenteEmailDaAsta;
    }
    public String getVenditoreEmailDaAsta() {
        return venditoreEmailDaAsta;
    }
    public void setVenditoreEmailDaAsta(String venditoreEmailDaAsta) {
        this.venditoreEmailDaAsta = venditoreEmailDaAsta;
    }
    public Boolean getLeMieAsteUtenteAttuale() {
        return leMieAsteUtenteAttuale;
    }
    public void setLeMieAsteUtenteAttuale(Boolean leMieAsteUtenteAttuale) {
        this.leMieAsteUtenteAttuale = leMieAsteUtenteAttuale;
    }


    public void setNomeSocialSelezionato(String nomeSocialSelezionato) {
        this.nomeSocialSelezionato = nomeSocialSelezionato;
    }

    public String getNomeSocialSelezionato() {
        return nomeSocialSelezionato;
    }

    public void setNomeLinkSelezionato(String nomeLinkSelezioanto) {
        this.nomeLinkSelezionato = nomeLinkSelezioanto;
    }

    public String getNomeLinkSelezionato() {
        return nomeLinkSelezionato;
    }
}
