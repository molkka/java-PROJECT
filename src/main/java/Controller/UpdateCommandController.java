package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Commande;
import models.Facture;
import services.Commandeservices;
import services.FactureService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class UpdateCommandController {
    @javafx.fxml.FXML
    private TextField tfNom;
    @javafx.fxml.FXML
    private TextField tfPrenom;
    @javafx.fxml.FXML
    private TextField tfEmail;
    @javafx.fxml.FXML
    private TextField tfAdresse;
    @javafx.fxml.FXML
    private TextField tfNumero;
    @javafx.fxml.FXML
    private DatePicker datepicker;
    @javafx.fxml.FXML
    private TextField tfPrixTotale;
    private String origin ;
    private Commande commande ;
    @javafx.fxml.FXML
    private Button BtnModifire;
    @javafx.fxml.FXML
    private Button BtnRetour;
    private Commandeservices commandeservices = new Commandeservices();
    private FactureService factureService = new FactureService();
    private Alert a = new Alert(Alert.AlertType.NONE);


    public void setCommande(Commande commande, String origin )
    {
        this.commande = commande;
        tfNom.setText(commande.getNom());
        tfPrenom.setText(commande.getPrenom());
        tfEmail.setText(commande.getEmail());
        tfAdresse.setText(commande.getAdresse());
        tfNumero.setText(String.valueOf(commande.getNumTel()));
        datepicker.setValue(commande.getDate());
        tfPrixTotale.setText(String.valueOf(commande.getTotalCost()));
        tfPrixTotale.setEditable(false);
        datepicker.setValue(LocalDate.now());
        datepicker.setEditable(false);
        datepicker.setDisable(true);
        this.origin = origin ;
    }

    @javafx.fxml.FXML
    public void OnModifierClicked(ActionEvent actionEvent) {
        if(tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfEmail.getText().isEmpty() || tfAdresse.getText().isEmpty() || tfNumero.getText().isEmpty() || datepicker.getValue() == null || tfPrixTotale.getText().isEmpty())
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez remplir tous les champs");
            a.show();
            return;
        }
        if(!tfNumero.getText().matches("[0-9]+"))
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Le numéro de téléphone doit contenir que des chiffres");
            a.show();
            return;
        }
        // check numero is exactly 8 length
        else if(tfNumero.getText().length() != 8)
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Le numéro de téléphone doit contenir exactement 8 chiffres");
            a.show();
            return;
        }
        // check email
        if(!tfEmail.getText().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
        {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez saisir un email valide");
            a.show();
            return;
        }

        this.commande.setNom(tfNom.getText());
        this.commande.setPrenom(tfPrenom.getText());
        this.commande.setEmail(tfEmail.getText());
        this.commande.setAdresse(tfAdresse.getText());
        this.commande.setNumTel(tfNumero.getText());
        this.commande.setDate(LocalDate.now());
        this.commande.setTotalCost(Integer.parseInt(tfPrixTotale.getText()));
        try {
            commandeservices.Modifier(commande);
            Facture facture = factureService.getFactureByIdCommand(commande.getCommandeid());
            facture.setNom(commande.getNom());
            facture.setPrenom(commande.getPrenom());
            facture.setEmail(commande.getEmail());
            facture.setNumTel(commande.getNumTel());
            facture.setAdresse(facture.getAdresse());
            factureService.Modifier(facture);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Commande modifiée avec succès");
            a.show();
        } catch (Exception e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Erreur lors de la modification de la commande : " + e.getMessage());
            a.show();
        }
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = null ;
        if(origin.equals("back"))
        {
            loader = new FXMLLoader(getClass().getResource("/ListCommandsBack.fxml"));
        }
        else
        {
            loader = new FXMLLoader(getClass().getResource("/MesCommandes.fxml"));
        }
        this.BtnModifire.getScene().setRoot(loader.load());
    }
}
