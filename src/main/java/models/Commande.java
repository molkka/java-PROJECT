package models;

import java.time.LocalDate;

public class Commande {
    private int commandeid;
    private int idcart;
    private String nom;
    private String prenom;
    private String email ;
    private String adresse;
    private String numTel;
    private LocalDate date;
    private int totalCost;
    private int idUser;



    public Commande() {
    }

    public Commande(String nom, String prenom, String email, String adresse, String numTel, LocalDate date, int totalCost, int idUser) {
        this.idcart = 0 ;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.numTel = numTel;
        this.date = date;
        this.totalCost = totalCost;
        this.idUser = idUser;
    }

    public Commande(int commandeid, String nom, String prenom, String email, String adresse, String numTel, LocalDate date, int totalCost, int idUser) {
        this.commandeid = commandeid;
        this.idcart = 0;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.numTel = numTel;
        this.date = date;
        this.totalCost = totalCost;
        this.idUser = idUser;
    }

    public int getCommandeid() {
        return commandeid;
    }

    public void setCommandeid(int commandeid) {
        this.commandeid = commandeid;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdcart() {
        return idcart;
    }

    public void setIdcart(int idcart) {
        this.idcart = idcart;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "commandeid=" + commandeid +
                ", idcart=" + idcart +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numTel='" + numTel + '\'' +
                ", date=" + date +
                ", totalCost=" + totalCost +
                ", idUser=" + idUser +
                '}';
    }
}
