package Controller;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import models.Cart;
import models.Commande;
import models.Products;
import models.User;
import org.w3c.dom.Text;
import services.Commandeservices;
import services.Productservices;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {
    @javafx.fxml.FXML
    private ScrollPane ScrollPane;
    private Productservices productservices = new Productservices();
    private Commandeservices commandeservices = new Commandeservices();
    private Cart cart = new Cart();

    private Alert a = new Alert(Alert.AlertType.NONE);
    @javafx.fxml.FXML
    private ScrollPane scrollPaneItems;
    @javafx.fxml.FXML
    private Label labelPrix;
    @javafx.fxml.FXML
    private Button BtnCommander;

    private  User user ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduits();
        afficherMap();
        user = new User(2,"mohamed","mohamed@gmail.com" , 12346578);
    }

    public void afficherProduits() {
        try {
            ScrollPane.setContent(null);
            List<Products> products = productservices.recuperer();
            System.out.println("list size : " + products.size());
            // create a card for each sponsor and add the cards 3 by 3 in  to a VBoxex then add the VBoxex to a Hbox and add the HBox to the the scrollpane
            VBox V = new VBox();
            V.setSpacing(30);
            for (int i = 0; i < products.size(); i += 3) {
                HBox hBox = new HBox();
                hBox.setSpacing(30);
                hBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                for (int j = i; j < i + 3 && j < products.size(); j++) {
                    hBox.getChildren().add(ProduitCard(products.get(j)));
                }
                V.getChildren().add(hBox);
            }
            ScrollPane.setContent(V);
            ScrollPane.setFitToWidth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public VBox ProduitCard(Products products) {
        VBox vBox = new VBox();
        vBox.getStyleClass().add("sponsor-card");
        vBox.setSpacing(10);
        Image image = new Image(products.getImage(), 230, 230, true, true); // Adjust width, height, preserve ratio, smooth scaling
        ImageView imageView = new ImageView(image);
        imageView.getStyleClass().add("image-view");
        vBox.getChildren().add(imageView);
        Label Title = new Label("Titre: " + products.getTitle());
        Title.getStyleClass().add("card-title");
        vBox.getChildren().add(Title);
        Label Price = new Label("Prix: " + products.getPrice() + " DT");
        Price.getStyleClass().add("card-price");
        vBox.getChildren().add(Price);
        Button BtnDecrementer = new Button(" - ");
        BtnDecrementer.setOnMouseClicked(e -> {
            cart.DecrementQuantity(products, 1);
            afficherMap();
        });

        Button BtnAjouter = new Button(" + ");
        BtnAjouter.setOnMouseClicked(e -> {
            cart.addQuantity(products, 1);
            afficherMap();
        });
        HBox h = new HBox();
        h.setSpacing(5);
        h.getChildren().addAll(BtnAjouter,BtnDecrementer);
        vBox.getChildren().addAll(h);

        return vBox;
    }


    void afficherMap() {
        try {
            scrollPaneItems.setContent(null);
            VBox V = new VBox();
            V.setSpacing(30);
            for (Map.Entry<Products, Integer> entry : this.cart.getProducts().entrySet()) {
                HBox hBox = new HBox();
                hBox.setSpacing(10);
                hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
                hBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
                Label Title = new Label( entry.getKey().getTitle());
                Label Price = new Label("Prix: " + entry.getKey().getPrice() + " DT");
                TextField quantity = new TextField("" + entry.getValue());
                Button BtnSupprimer = new Button("Supprimer");

                BtnSupprimer.setOnMouseClicked(e -> {
                    cart.removeQuantity(entry.getKey());
                    afficherMap();
                });

                hBox.getChildren().addAll(Title,Price,quantity,BtnSupprimer);
                V.getChildren().add(hBox);
            }
            scrollPaneItems.setContent(V);
            scrollPaneItems.setFitToWidth(true);
            labelPrix.setText("Prix total: " + cart.getTotalCost() + " DT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @javafx.fxml.FXML
    public void OnCommanderClicked(ActionEvent actionEvent) {
        Commande command = new Commande();
        command.setTotalCost(cart.getTotalCost());
        command.setDate(LocalDate.now());
        command.setIdUser(user.getId());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddCommand.fxml"));
            Parent root = loader.load();
            AddCommandController addCommandController = loader.getController();

            addCommandController.setCommand(command,cart);
            this.labelPrix.getScene().setRoot(root);
        } catch (Exception e) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Passage ajout commande impossible");
            a.show();
        }
    }


}
