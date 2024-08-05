package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Products.fxml"));
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProduct.fxml"));
      // FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListProductsBack.fxml"));
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListCommandsBack.fxml"));
       //FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesCommandes.fxml"));


        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/product-card.css")).toExternalForm());


        stage.setTitle("Freelancy");
        stage.setScene(scene);
        stage.show();
    }
}
