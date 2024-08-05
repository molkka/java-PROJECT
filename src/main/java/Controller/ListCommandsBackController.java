package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.Commande;
import models.Facture;
import models.User;
import services.Commandeservices;
import services.FactureService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListCommandsBackController implements Initializable {
    @javafx.fxml.FXML
    private Button BtnSupprimer;
    private Commandeservices commandeservices = new Commandeservices();
    private FactureService factureService = new FactureService();
    private User user = new User(2, "mohamed", "mohamed@gmail.com", 12346578);

    private Alert a = new Alert(Alert.AlertType.NONE);
    @javafx.fxml.FXML
    private ListView<Commande> ListCommands;
    @javafx.fxml.FXML
    private Button BtnModifier;
    @javafx.fxml.FXML
    private TextField tfRecherche;
    @javafx.fxml.FXML
    private ComboBox<String> ComboColumn;
    @javafx.fxml.FXML
    private ComboBox<String> ComboOrder;
    @javafx.fxml.FXML
    private Button BtnVoirFacture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Commande> lc = commandeservices.recuperer();
            afficherCommandes(lc);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        this.ComboColumn.getItems().addAll( "nom","prenom", "date", "total",  "adresse", "numTel", "email");
        this.ComboOrder.getItems().addAll("ASC", "DESC");
        this.ComboOrder.getSelectionModel().select(0);
        this.ComboColumn.getSelectionModel().select(0);
        // order by the selected column and the selected order
        this.ComboColumn.setOnAction(event -> {
            List<Commande> lcf = null;
            try {
                lcf = commandeservices.recuperer();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            switch(this.ComboColumn.getValue())
            {
                case "nom":
                    lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getNom().compareTo(c2.getNom()) : c2.getNom().compareTo(c1.getNom()));
                    break;
                case "prenom":
                    lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getPrenom().compareTo(c2.getPrenom()) : c2.getPrenom().compareTo(c1.getPrenom()));
                    break;
                case "date":
                    lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getDate().compareTo(c2.getDate()) : c2.getDate().compareTo(c1.getDate()));
                    break;
                case "total":
                    lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getTotalCost() - (c2.getTotalCost()) : c2.getTotalCost() - (c1.getTotalCost()));
                    break;
                case "adresse":
                    lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getAdresse().compareTo(c2.getAdresse()) : c2.getAdresse().compareTo(c1.getAdresse()));
                    break;
                case "numTel":
                    lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getNumTel().compareTo(c2.getNumTel()) : c2.getNumTel().compareTo(c1.getNumTel()));
                    break;
                case "email":
                    lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getEmail().compareTo(c2.getEmail()) : c2.getEmail().compareTo(c1.getEmail()));
                    break;

            }
        });
        this.ComboOrder.setOnAction(event -> {
            List<Commande> lcf = null;
            try {
                lcf = commandeservices.recuperer();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                switch(this.ComboColumn.getValue())
                {
                    case "nom":
                        lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getNom().compareTo(c2.getNom()) : c2.getNom().compareTo(c1.getNom()));
                        break;
                    case "prenom":
                        lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getPrenom().compareTo(c2.getPrenom()) : c2.getPrenom().compareTo(c1.getPrenom()));
                        break;
                    case "date":
                        lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getDate().compareTo(c2.getDate()) : c2.getDate().compareTo(c1.getDate()));
                        break;
                    case "total":
                        lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getTotalCost() - (c2.getTotalCost()) : c2.getTotalCost() - (c1.getTotalCost()));
                        break;
                    case "adresse":
                        lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getAdresse().compareTo(c2.getAdresse()) : c2.getAdresse().compareTo(c1.getAdresse()));
                        break;
                    case "numTel":
                        lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getNumTel().compareTo(c2.getNumTel()) : c2.getNumTel().compareTo(c1.getNumTel()));
                        break;
                    case "email":
                        lcf.sort((c1, c2) -> this.ComboOrder.getValue().equals("ASC") ? c1.getEmail().compareTo(c2.getEmail()) : c2.getEmail().compareTo(c1.getEmail()));
                        break;

                }
                afficherCommandes(lcf);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        // now for the search input
        this.tfRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Commande> lcf = null;
            try {
                lcf = commandeservices.recuperer();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                // search by all field of command
                List<Commande> filteredList = lcf.stream().filter(c -> String.valueOf(c.getCommandeid()).contains(newValue) || c.getDate().toString().contains(newValue) || String.valueOf(c.getTotalCost()).contains(newValue) || c.getNom().contains(newValue) || c.getPrenom().contains(newValue) || c.getAdresse().contains(newValue) || c.getNumTel().contains(newValue) || c.getEmail().contains(newValue)).toList();
                afficherCommandes(filteredList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }


    void afficherCommandes(List<Commande> lc) throws SQLException {
        ListCommands.setCellFactory(listView -> new ListCell<>() {
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

        ListCommands.getItems().clear(); // Clear existing items before adding new ones
        for (Commande c : lc) {
            this.ListCommands.getItems().add(c);
        }
    }


    @javafx.fxml.FXML
    public void OnSupprimerClicked(ActionEvent actionEvent) {
        Commande c = ListCommands.getSelectionModel().getSelectedItem();
        if (c == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner une commande");
            a.show();
            return;
        }
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText("Voulez-vous vraiment supprimer cette commande ?");
        a.setTitle("Confirmation");
        Optional<ButtonType> buttonType = a.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            try {
                commandeservices.supprimer(c.getCommandeid());
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Commande supprimée avec succès");
                a.show();
                List<Commande> lc = commandeservices.recuperer();
                afficherCommandes(lc);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @javafx.fxml.FXML
    public void OnLisctClicked(Event event) {
        Commande c = ListCommands.getSelectionModel().getSelectedItem();
        System.out.println(c);
    }

    @javafx.fxml.FXML
    public void OnModifierClicked(ActionEvent actionEvent) throws IOException {
        Commande c = ListCommands.getSelectionModel().getSelectedItem();
        if (c == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner une commande");
            a.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCommand.fxml"));
        Parent root = loader.load();
        UpdateCommandController controller = loader.getController();
        controller.setCommande(c,"back");
        this.BtnModifier.getScene().setRoot(root);

    }

    @javafx.fxml.FXML
    public void GoFacture(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FactureDetails.fxml"));
        Parent root = loader.load();
        FactureDetailsController controller = loader.getController();
        Commande commande = ListCommands.getSelectionModel().getSelectedItem();
        if(commande == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner une commande");
            a.show();
            return;
        }
        Facture facture = factureService.getFactureByIdCommand(commande.getCommandeid());
        controller.setFacture(facture , "tous");
        this.BtnModifier.getScene().setRoot(root);
    }
}
