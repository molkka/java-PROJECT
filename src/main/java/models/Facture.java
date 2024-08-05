package models;

import java.time.LocalDate;

public class Facture {

    private int FactureId;
    private int commandeid;
    private String nom;
    private String prenom;
    private String email ;
    private String adresse;
    private String numTel;
    private LocalDate date;
    private int totalCost;

    public Facture() {
    }

    public Facture(int factureId, int commandeid, String nom, String prenom, String email, String adresse, String numTel, LocalDate date, int totalCost) {
        FactureId = factureId;
        this.commandeid = commandeid;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.numTel = numTel;
        this.date = date;
        this.totalCost = totalCost;
    }

    public Facture(int commandeid, String nom, String prenom, String email, String adresse, String numTel, LocalDate date, int totalCost) {
        this.commandeid = commandeid;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.numTel = numTel;
        this.date = date;
        this.totalCost = totalCost;
    }

    public int getFactureId() {
        return FactureId;
    }

    public void setFactureId(int factureId) {
        FactureId = factureId;
    }

    public int getCommandeid() {
        return commandeid;
    }

    public void setCommandeid(int commandeid) {
        this.commandeid = commandeid;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "FactureId=" + FactureId +
                ", commandeid=" + commandeid +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numTel='" + numTel + '\'' +
                ", date=" + date +
                ", totalCost=" + totalCost +
                '}';
    }
}
