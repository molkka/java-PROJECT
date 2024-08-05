package services;

import models.Facture;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FactureService implements Iservice<Facture>{

    Connection connection = MyDatabase.getInstance().getConnection();
    @Override
    public void ajouter(Facture facture) throws SQLException {
        String req = "INSERT INTO Facture (commandeid, date, totalCost, nom, prenom, email, adresse, numTelephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (var preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, facture.getCommandeid());
            preparedStatement.setDate(2, java.sql.Date.valueOf(facture.getDate()));
            preparedStatement.setInt(3, facture.getTotalCost());
            preparedStatement.setString(4, facture.getNom());
            preparedStatement.setString(5, facture.getPrenom());
            preparedStatement.setString(6, facture.getEmail());
            preparedStatement.setString(7, facture.getAdresse());
            preparedStatement.setString(8, facture.getNumTel());
            preparedStatement.executeUpdate();
            System.out.println("Facture ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la facture : " + e.getMessage());
        }
    }

    @Override
    public void Modifier(Facture facture) throws SQLException {
        String req = "UPDATE Facture set totalCost = ?, date = ? , nom = ? , prenom  = ? , " +
                "email = ? , adresse = ? , numTelephone = ? , commandeid = ? " +
                " WHERE factureid = ?";
        try (var preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, facture.getTotalCost());
            preparedStatement.setDate(2, java.sql.Date.valueOf(facture.getDate()));
            preparedStatement.setString(3, facture.getNom());
            preparedStatement.setString(4, facture.getPrenom());
            preparedStatement.setString(5, facture.getEmail());
            preparedStatement.setString(6, facture.getAdresse());
            preparedStatement.setString(7, facture.getNumTel());
            preparedStatement.setInt(8, facture.getCommandeid());
            preparedStatement.setInt(9, facture.getFactureId());
            preparedStatement.executeUpdate();
            System.out.println("Facture modifiée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la facture : " + e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM Facture WHERE factureid = ?";
        try (var preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Facture supprimée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la facture : " + e.getMessage());
        }
    }

    @Override
    public List<Facture> recuperer() throws SQLException {
        List<Facture> list = null;
        String req = "SELECT * FROM Facture";
        try (var statement = connection.createStatement();
             var rs = statement.executeQuery(req)) {
            while (rs.next()) {
                Facture facture = new Facture();
                facture.setFactureId(rs.getInt("factureid"));
                facture.setCommandeid(rs.getInt("commandeid"));
                facture.setDate(rs.getDate("date").toLocalDate());
                facture.setTotalCost(rs.getInt("totalCost"));
                facture.setNom(rs.getString("nom"));
                facture.setPrenom(rs.getString("prenom"));
                facture.setEmail(rs.getString("email"));
                facture.setAdresse(rs.getString("adresse"));
                facture.setNumTel(rs.getString("numTelephone"));
                list.add(facture);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des factures : " + e.getMessage());
        }

        return list;
    }

    @Override
    public Facture recupererParId(int id) throws SQLException {
        String req = "SELECT * FROM Facture WHERE factureid = ?";
        try (var preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Facture facture = new Facture();
                    facture.setFactureId(rs.getInt("factureid"));
                    facture.setCommandeid(rs.getInt("commandeid"));
                    facture.setDate(rs.getDate("date").toLocalDate());
                    facture.setTotalCost(rs.getInt("totalCost"));
                    facture.setNom(rs.getString("nom"));
                    facture.setPrenom(rs.getString("prenom"));
                    facture.setEmail(rs.getString("email"));
                    facture.setAdresse(rs.getString("adresse"));
                    facture.setNumTel(rs.getString("numTelephone"));
                    return facture;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la facture : " + e.getMessage());
        }
        return null;
    }

    public Facture getFactureByIdCommand(int idCommand)
    {
        Facture facture = new Facture();
        String req = "SELECT * FROM Facture WHERE commandeid = ?";
        try (var preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, idCommand);
            try (var rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    facture.setFactureId(rs.getInt("factureid"));
                    facture.setCommandeid(rs.getInt("commandeid"));
                    facture.setDate(rs.getDate("date").toLocalDate());
                    facture.setTotalCost(rs.getInt("totalCost"));
                    facture.setNom(rs.getString("nom"));
                    facture.setPrenom(rs.getString("prenom"));
                    facture.setEmail(rs.getString("email"));
                    facture.setAdresse(rs.getString("adresse"));
                    facture.setNumTel(rs.getString("numTelephone"));
                    return facture;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la facture : " + e.getMessage());
        }
        return null;
    }
}
