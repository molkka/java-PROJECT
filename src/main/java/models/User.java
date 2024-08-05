package models;

public class User {

    int id ;
    String nom ;
    String email ;
    int numero ;

    public User() {
    }

    public User(int id, String nom, String email, int numero) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.numero = numero;
    }

    public User(String nom, String email, int numero) {
        this.nom = nom;
        this.email = email;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", numero=" + numero +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


}
