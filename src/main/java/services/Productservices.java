package services;

import models.Products;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Productservices implements Iservice<Products> {
    Connection connection = MyDatabase.getInstance().getConnection();

    @Override
    public void ajouter(Products products) throws SQLException {
        String req = "INSERT INTO Products (price, title, image) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, products.getPrice());
            preparedStatement.setString(2, products.getTitle());
            preparedStatement.setString(3, products.getImage());
            preparedStatement.executeUpdate();
            System.out.println("Produit ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du produit : " + e.getMessage());
        }
    }

    @Override
    public void Modifier(Products products) throws SQLException {
        String req = "UPDATE Products SET price = ?, title = ?, image = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, products.getPrice());
            preparedStatement.setString(2, products.getTitle());
            preparedStatement.setString(3, products.getImage());
            preparedStatement.setInt(4, products.getId());
            // Exécution de la requête
            preparedStatement.executeUpdate();
            System.out.println("Produit modifié avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du produit : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM Products WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Produit supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du produit : " + e.getMessage());
        }
    }

    @Override
    public List<Products> recuperer() throws SQLException {
        String req = "SELECT * FROM Products";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        List<Products> list = new ArrayList<>();
        while (rs.next()) {
            Products product = new Products();
            product.setId(rs.getInt("id"));
            product.setPrice(rs.getInt("price"));
            product.setTitle(rs.getString("title"));
            product.setImage(rs.getString("image"));
            list.add(product);
        }
        return list;
    }

    @Override
    public Products recupererParId(int id) throws SQLException {
        String req = "SELECT * FROM Products WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Products product = new Products();
                    product.setId(rs.getInt("id"));
                    product.setPrice(rs.getInt("price"));
                    product.setTitle(rs.getString("title"));
                    product.setImage(rs.getString("image"));
                    return product;
                }
            }
        }
        return null;
    }



}
