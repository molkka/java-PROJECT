package services;

import models.Cart;
import models.Commande;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Iservice <T>{
    void ajouter (T t) throws SQLException;
    void Modifier (T t) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> recuperer () throws SQLException;
    T recupererParId(int id) throws SQLException;

}
