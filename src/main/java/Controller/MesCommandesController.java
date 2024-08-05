package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import models.Commande;
import models.Facture;
import models.Products;
import models.User;
import services.Commandeservices;
import services.FactureService;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MesCommandesController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Commande>ListCommandes;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private Button BtnFacture;

    private Commandeservices commandeservices = new Commandeservices();
    private FactureService factureService = new FactureService();

    private Alert a = new Alert(Alert.AlertType.NONE);

    private User user = new User(2,"mohamed","mohamed@gmail.com", 12346578);
    @FXML
    private Button BtnModif;

    @javafx.fxml.FXML
    public void OnListClicked(Event event) {
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        Commande c = ListCommandes.getSelectionModel().getSelectedItem();
        if (c == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner une commande");
            a.show();
            return;
        }
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Voulez-vous vraiment annuler cette commande ?");
        a.setTitle("Confirmation");
        if (a.showAndWait().get() == ButtonType.OK) {
            try {
                commandeservices.supprimer(c.getCommandeid());
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Commande annulée avec succès");
                a.show();
                afficherMesCommandes();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @javafx.fxml.FXML
    public void onFactureClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FactureDetails.fxml"));
        Parent root = loader.load();
        FactureDetailsController controller = loader.getController();
        Commande commande = ListCommandes.getSelectionModel().getSelectedItem();
        if(commande == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner une commande");
            a.show();
            return;
        }
        Facture facture = factureService.getFactureByIdCommand(commande.getCommandeid());
        controller.setFacture(facture , "mes");
        this.BtnAnnuler.getScene().setRoot(root);
    }

    void afficherMesCommandes() throws SQLException {
        ListCommandes.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Commande commande, boolean empty) {
                super.updateItem(commande, empty);

                if (empty || commande == null) {
                    setText(null);
                } else {
                    setText(commande.getNom() + " - " + commande.getPrenom() + " - " + commande.getDate() + " - " + commande.getTotalCost() + " - " + commande.getAdresse() + " - " + commande.getNumTel() + " - " + commande.getEmail());

                }
            }
        });

        List<Commande> lc = this.commandeservices.recupererParIdUtilisateur(user.getId()); // Call getAll() here
        ListCommandes.getItems().clear(); // Clear existing items before adding new ones
        for (Commande c : lc) {
            this.ListCommandes.getItems().add(c);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            afficherMesCommandes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void OnModifClicked(ActionEvent actionEvent) throws IOException {
        Commande c = ListCommandes.getSelectionModel().getSelectedItem();
        if (c == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner une commande");
            a.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCommand.fxml"));
        Parent root = loader.load();
        UpdateCommandController controller = loader.getController();
        controller.setCommande(c,"front");
        this.BtnAnnuler.getScene().setRoot(root);
    }
}

