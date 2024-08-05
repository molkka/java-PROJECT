package Controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Commande;
import models.Products;
import services.Productservices;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListProductsBackController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Products> ListProduits;
    @javafx.fxml.FXML
    private Button GoAjouter;
    @javafx.fxml.FXML
    private TextField tfTitle;
    @javafx.fxml.FXML
    private TextField tfPrix;
    @javafx.fxml.FXML
    private ImageView imageView;
    @javafx.fxml.FXML
    private Button BtnModifier;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private Button BtnInserer;

    private Alert a = new Alert(Alert.AlertType.NONE);

    private Productservices productservices = new Productservices();
    private String ImagePath;
    @FXML
    private Button BtnSupprimer;
    @FXML
    private TextField tfRecherche;
    @FXML
    private ComboBox<String> ComboColumn;
    @FXML
    private ComboBox<String> ComboOrder;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Products> lp = this.productservices.recuperer();
            afficherProduits(lp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.ComboColumn.getItems().addAll("title", "price");
        this.ComboOrder.getItems().addAll("ASC", "DESC");
        ComboColumn.setValue("title");
        ComboOrder.setValue("ASC");
        // filter by order and column using stream filter
        tfRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                List<Products> lp = this.productservices.recuperer();
                if (newValue.isEmpty()) {
                    afficherProduits(lp);
                } else {
                    ListProduits.getItems().clear();
                    List<Products> filteredList = lp.stream().filter(p -> p.getTitle().contains(newValue) || String.valueOf(p.getPrice()).contains(newValue)).toList();
                    afficherProduits(filteredList);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // now sort the item base on the combo oreder and the combo column , use stream . filter for sorting
        ComboColumn.setOnAction(event -> {
            List<Products> lcf = null;
            try {
                lcf = this.productservices.recuperer();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            List<Products> filteredList = lcf.stream().sorted((c1, c2) -> {
                if (this.ComboColumn.getSelectionModel().getSelectedItem().equals("title")) {
                    return this.ComboOrder.getSelectionModel().getSelectedItem().equals("ASC") ? c1.getTitle().compareTo(c2.getTitle()) : c2.getTitle().compareTo(c1.getTitle());
                } else {
                    return this.ComboOrder.getSelectionModel().getSelectedItem().equals("ASC") ? Integer.compare(c1.getPrice(), c2.getPrice()) : Integer.compare(c2.getPrice(), c1.getPrice());
                }
            }).toList();
            try {
                afficherProduits(filteredList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        ComboOrder.setOnAction(event -> {
            List<Products> lcf = null;
            try {
                lcf = this.productservices.recuperer();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            List<Products> filteredList = lcf.stream().sorted((c1, c2) -> {
                if (this.ComboColumn.getSelectionModel().getSelectedItem().equals("title")) {
                    return this.ComboOrder.getSelectionModel().getSelectedItem().equals("ASC") ? c1.getTitle().compareTo(c2.getTitle()) : c2.getTitle().compareTo(c1.getTitle());
                } else {
                    return this.ComboOrder.getSelectionModel().getSelectedItem().equals("ASC") ? Integer.compare(c1.getPrice(), c2.getPrice()) : Integer.compare(c2.getPrice(), c1.getPrice());
                }
            }).toList();
            try {
                afficherProduits(filteredList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void afficherProduits(List<Products> lp) throws SQLException {
        ListProduits.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Products products, boolean empty) {
                super.updateItem(products, empty);

                if (empty || products == null) {
                    setText(null);
                } else {
                    setText(products.getTitle() + " - " + products.getPrice());
                }
            }
        });
        ListProduits.getItems().clear(); // Clear existing items before adding new ones
        for (Products p : lp) {
            this.ListProduits.getItems().add(p);
        }
    }

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProduct.fxml"));
        this.BtnAnnuler.getScene().setRoot(loader.load());
    }

    @javafx.fxml.FXML
    public void OnModifierClicked(ActionEvent actionEvent) {
        Products p = ListProduits.getSelectionModel().getSelectedItem();
        if (p == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner un produit");
            a.show();
        } else {
            if (tfTitle.getText().isEmpty() || tfPrix.getText().isEmpty()) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Veuillez remplir tous les champs");
                a.show();
            } else if (!tfPrix.getText().matches("[0-9]+")) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Le prix doit être un nombre");
                a.show();
            } else {
                p.setTitle(tfTitle.getText());
                p.setPrice(Integer.parseInt(tfPrix.getText()));
                if (ImagePath != null) {
                    System.out.printf(ImagePath);
                    p.setImage(ImagePath);
                }
                try {
                    productservices.Modifier(p);
                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setContentText("Produit modifié avec succès");
                    a.show();
                    List<Products> lp = this.productservices.recuperer();
                    afficherProduits(lp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @javafx.fxml.FXML
    public void OnAnnuulerClicked(ActionEvent actionEvent) {
        tfTitle.clear();
        tfPrix.clear();
        imageView.setImage(null);
    }

    @javafx.fxml.FXML
    public void OnInsererClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/resources/images/"));
        fileChooser.setTitle("Select Image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            ImagePath = selectedFile.toURI().toString();
            Image image = new Image(ImagePath);
            imageView.setImage(image);
        }
    }


    @javafx.fxml.FXML
    public void OnListClicked(Event event) {
        Products p = ListProduits.getSelectionModel().getSelectedItem();
        tfTitle.setText(p.getTitle());
        tfPrix.setText(String.valueOf(p.getPrice()));
        try {
            Image image = new Image(p.getImage());
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    @FXML
    public void OnSupprimerClicked(ActionEvent actionEvent) {
        Products p = ListProduits.getSelectionModel().getSelectedItem();
        if (p == null) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Veuillez sélectionner un produit");
            a.show();
        } else {
            try {
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setContentText("Voulez-vous vraiment supprimer le produit dont l'id est : " + p.getId());

                Optional<ButtonType> buttonType = a.showAndWait();
                if (buttonType.get() == ButtonType.CANCEL) {
                    return;
                }
                productservices.supprimer(p.getId());
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Produit supprimé avec succès");
                a.show();
                List<Products> lp = this.productservices.recuperer();
                afficherProduits(lp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
