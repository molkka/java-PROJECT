package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Products;
import services.Productservices;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public class AddProductController {
    @javafx.fxml.FXML
    private TextField tfTitle;
    @javafx.fxml.FXML
    private TextField tfPrix;
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private Button BtnInserer;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private ImageView imageView;

    private Productservices productservices = new Productservices();
    private Alert a = new Alert(Alert.AlertType.NONE);

    private String ImagePath;
    @javafx.fxml.FXML
    private Button BtnRetour;

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException {
        if (tfTitle.getText().isEmpty() || tfPrix.getText().isEmpty() || ImagePath == null || ImagePath.isEmpty()) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez remplir tous les champs");
            a.show();
        }
        else if(!tfPrix.getText().matches("[0-9]+")) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Le prix doit être un nombre");
            a.show();
        }
        else if(Integer.parseInt(tfPrix.getText()) <= 0) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Le prix doit être supérieur à 0");
            a.show();
        }
        else {
            Products products = new Products( Integer.parseInt(tfPrix.getText()), tfTitle.getText(), ImagePath);
            List<Products> lp = productservices.recuperer();
            for(Products p : lp) {
                if(p.equals(products)) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setContentText("Ce produit existe déjà");
                    a.show();
                    return;
                }
            }
            productservices.ajouter(products);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Produit ajouté avec succès");
            a.show();
        }
        SmsController.Sms();

    }


    @javafx.fxml.FXML
    public void OnInsereClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/images/"));
        fileChooser.setTitle("Select Image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            ImagePath = selectedFile.toURI().toString();
            Image image = new Image(ImagePath);
            System.out.println(ImagePath);
            imageView.setImage(image);
        }
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        tfTitle.clear();
        tfPrix.clear();
        imageView.setImage(null);
        ImagePath = null;
    }

    @javafx.fxml.FXML
    public void OnREtourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListProductsBack.fxml"));
        this.BtnAjouter.getScene().setRoot(loader.load());
    }
}
