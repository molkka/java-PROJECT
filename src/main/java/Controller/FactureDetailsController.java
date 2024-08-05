package Controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.webkit.WebConsoleListener;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Facture;
import models.User;
import netscape.javascript.JSObject;
import services.JavaAppBridge;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FactureDetailsController implements Initializable {
    @javafx.fxml.FXML
    private TextField tfCommandeid;
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
    private TextField tfPrixTotale;
    @javafx.fxml.FXML
    private Button BtnImprimer;
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private DatePicker datepicker;

    private String origin ;

    private Facture facture;
    private Alert a = new Alert(Alert.AlertType.NONE);
    private User user = new User(2, "mohamed", "mohamed@gmail.com", 12346578);
    @javafx.fxml.FXML
    private Button BtnPayer;


    @javafx.fxml.FXML
    public void OnImprimerClicked(ActionEvent actionEvent) {
        try {
            // Create a FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Facture");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setInitialFileName("facture_" + facture.getCommandeid() + ".pdf");
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                generateFacturePDF(facture, file);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Facture générée avec succès");
                a.show();
                // Open the file with the default program
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void OnREtourClciked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader;
        if(origin.equals("mes")) {
            loader = new FXMLLoader(getClass().getResource("/MesCommandes.fxml"));
        }
        else
        {
            loader = new FXMLLoader(getClass().getResource("/ListCommandsBack.fxml"));
        }

        this.BtnImprimer.getScene().setRoot(loader.load());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setFacture(Facture facture , String origin) {
        this.facture = facture;
        this.tfCommandeid.setText(String.valueOf(facture.getCommandeid()));
        this.tfNom.setText(facture.getNom());
        this.tfPrenom.setText(facture.getPrenom());
        this.tfEmail.setText(facture.getEmail());
        this.tfAdresse.setText(facture.getAdresse());
        this.tfNumero.setText(facture.getNumTel());
        this.tfPrixTotale.setText(String.valueOf(facture.getTotalCost()));
        this.datepicker.setValue(facture.getDate());
        this.tfNom.setEditable(false);
        this.tfPrenom.setEditable(false);
        this.tfEmail.setEditable(false);
        this.tfAdresse.setEditable(false);
        this.tfNumero.setEditable(false);
        this.tfPrixTotale.setEditable(false);
        this.datepicker.setEditable(false);
        this.tfCommandeid.setEditable(false);
        this.origin = origin ;
    }

    public void generateFacturePDF(Facture facture, File file) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        // Add document information (optional)
        document.addAuthor("Freelancy");
        document.addTitle("Facture #" + facture.getCommandeid());

        // Add heading (store name, logo, etc.)
        Paragraph header = new Paragraph("Facture #" + facture.getCommandeid() + " - Freelancy");
        header.setAlignment(Element.ALIGN_CENTER);
        com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
        header.setFont(font);
        document.add(header);

        // Add customer information (optional)

        Paragraph customerInfo = new Paragraph("Customer Name: " + facture.getNom());
        customerInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(customerInfo);
        customerInfo = new Paragraph("Customer Last Name: " + facture.getPrenom());
        customerInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(customerInfo);
        customerInfo = new Paragraph("Customer Phone: " + facture.getNumTel());
        customerInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(customerInfo);
        customerInfo = new Paragraph("Customer Address: " + facture.getAdresse());
        customerInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(customerInfo);
        customerInfo = new Paragraph("Customer Email: " + facture.getEmail());
        customerInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(customerInfo);


        // Add date
        Paragraph date = new Paragraph("Date: " + facture.getDate().toString());
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);

        // Add total price
        Paragraph totalPrice = new Paragraph("Total Price (DT): " + facture.getTotalCost());
        totalPrice.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalPrice);

        document.close();
    }

    @javafx.fxml.FXML
    public void OnPayerClicked(ActionEvent actionEvent) {


        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        JavaAppBridge javaAppBridge = new JavaAppBridge();

        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // Create a JavaScript-Java bridge
                System.out.println("success loading the webview");
                JSObject jsBridge = (JSObject) webEngine.executeScript("window");

                javaAppBridge.SetFacture(facture); // Set the Facture object here
                jsBridge.setMember("javaApp", javaAppBridge);
            }
        });
        webEngine.load((getClass().getResource("/StripeForm.html")).toExternalForm());
        webEngine.setJavaScriptEnabled(true);

        StackPane root = new StackPane(webView);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
