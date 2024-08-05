package services;

import models.Commande;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Commandeservices implements Iservice<Commande> {
    Connection connection = MyDatabase.getInstance().getConnection();

    @Override
    public void ajouter(Commande commande) throws SQLException {
        // Requête SQL paramétrée
        String req = "INSERT INTO Commande (idcart ,totalCost, date, idUser , nom , prenom , email , adresse , numTelephone ) VALUES (?, ?, ? , ? , ? , ? , ? , ? , ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, commande.getIdcart());
            preparedStatement.setInt(2, commande.getTotalCost());
            preparedStatement.setDate(3, java.sql.Date.valueOf(commande.getDate()));
            preparedStatement.setInt(4, commande.getIdUser());
            preparedStatement.setString(5, commande.getNom());
            preparedStatement.setString(6, commande.getPrenom());
            preparedStatement.setString(7, commande.getEmail());
            preparedStatement.setString(8, commande.getAdresse());
            preparedStatement.setString(9, commande.getNumTel());

            // Exécution de la requête
            preparedStatement.executeUpdate();
            System.out.println("Commande ajoutée avec succès");

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la commande : " + e.getMessage());
        }
    }

    @Override
    public void Modifier(Commande commande) {
        // Vérifier que les champs requis ne sont ni vides ni nuls
        String req = "UPDATE Commande set totalCost = ?, date = ? , nom = ? , prenom  = ? , " +
                "email = ? , adresse = ? , numTelephone = ?" +
                " WHERE commandeid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, commande.getTotalCost());
            preparedStatement.setDate(2, java.sql.Date.valueOf(commande.getDate()));
            preparedStatement.setString(3, commande.getNom());
            preparedStatement.setString(4, commande.getPrenom());
            preparedStatement.setString(5, commande.getEmail());
            preparedStatement.setString(6, commande.getAdresse());
            preparedStatement.setString(7, commande.getNumTel());
            preparedStatement.setInt(8, commande.getCommandeid());
            // Exécution de la requête
            preparedStatement.executeUpdate();
            System.out.println("Commande modifiée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la Commande : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        // Vérifier si la commande avec l'ID spécifié existe
        String req = "DELETE FROM Commande WHERE commandeid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Commande supprimée avec succès");
        }
    }
    @Override
    public List<Commande> recuperer() throws SQLException {
        String req = "SELECT * FROM Commande";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        List<Commande> list = new ArrayList<>();
        while (rs.next()) {
            Commande commande = new Commande();
            commande.setCommandeid(rs.getInt("commandeid"));
            commande.setIdcart(rs.getInt("idcart"));
            commande.setTotalCost(rs.getInt("totalCost"));
            commande.setDate(rs.getDate("date").toLocalDate());
            commande.setIdUser(rs.getInt("idUser"));
            commande.setNom(rs.getString("nom"));
            commande.setPrenom(rs.getString("prenom"));
            commande.setEmail(rs.getString("email"));
            commande.setAdresse(rs.getString("adresse"));
            commande.setNumTel(rs.getString("numTelephone"));

            list.add(commande);
        }
        return list;
    }

    @Override
    public Commande recupererParId(int id) throws SQLException {
        String req = "SELECT * FROM Commande WHERE commandeid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Commande commande = new Commande();
                commande.setCommandeid(rs.getInt("commandeid"));
                commande.setIdcart(rs.getInt("idcart"));
                commande.setTotalCost(rs.getInt("totalCost"));
                commande.setDate(rs.getDate("date").toLocalDate());
                commande.setIdUser(rs.getInt("idUser"));
                commande.setNom(rs.getString("nom"));
                commande.setPrenom(rs.getString("prenom"));
                commande.setEmail(rs.getString("email"));
                commande.setAdresse(rs.getString("adresse"));
                commande.setNumTel(rs.getString("numTelephone"));
                return commande;

            }
        }
        return null;
    }

    public List<Commande> recupererParIdUtilisateur(int idUser) throws SQLException {
        String req = "SELECT * FROM Commande where idUser = ? ";
        PreparedStatement statement = connection.prepareStatement(req);
        statement.setInt(1, idUser);
        ResultSet rs = statement.executeQuery();
        List<Commande> list = new ArrayList<>();
        while (rs.next()) {
            Commande commande = new Commande();
            commande.setCommandeid(rs.getInt("commandeid"));
            commande.setIdcart(rs.getInt("idcart"));
            commande.setTotalCost(rs.getInt("totalCost"));
            commande.setDate(rs.getDate("date").toLocalDate());
            commande.setIdUser(rs.getInt("idUser"));
            commande.setNom(rs.getString("nom"));
            commande.setPrenom(rs.getString("prenom"));
            commande.setEmail(rs.getString("email"));
            commande.setAdresse(rs.getString("adresse"));
            commande.setNumTel(rs.getString("numTelephone"));
            list.add(commande);
        }
        return list;
    }

    public int addAndGetLastId(Commande commande)
    {
        String req = "INSERT INTO Commande (idcart ,totalCost, date, idUser , nom , prenom , email , adresse , numTelephone ) VALUES (?, ?, ? , ? , ? , ? , ? , ? , ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, commande.getIdcart());
            preparedStatement.setInt(2, commande.getTotalCost());
            preparedStatement.setDate(3, java.sql.Date.valueOf(commande.getDate()));
            preparedStatement.setInt(4, commande.getIdUser());
            preparedStatement.setString(5, commande.getNom());
            preparedStatement.setString(6, commande.getPrenom());
            preparedStatement.setString(7, commande.getEmail());
            preparedStatement.setString(8, commande.getAdresse());
            preparedStatement.setString(9, commande.getNumTel());

            // Exécution de la requête
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la commande : " + e.getMessage());
        }
        return -1;
    }
}
