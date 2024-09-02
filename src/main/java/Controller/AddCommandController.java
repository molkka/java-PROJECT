package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.fxml.FXMLLoaderHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Cart;
import models.Commande;
import models.Facture;
import models.Products;
import services.Commandeservices;
import services.FactureService;
import services.Mailing;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.Map;

public class AddCommandController {

    private Commande commande;
    @javafx.fxml.FXML
    private TextField tfNom;
    @javafx.fxml.FXML
    private TextField tfPrenom;
    @javafx.fxml.FXML
    private TextField tfEmial;
    @javafx.fxml.FXML
    private TextField tfAdresse;
    @javafx.fxml.FXML
    private TextField tfNumero;
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private Button BtnAnnuler;

    private Alert alert = new Alert(Alert.AlertType.NONE);
    private Cart cart = new Cart();
    private Commandeservices commandeservices = new Commandeservices();

    private FactureService factureService = new FactureService();



    public void setCommand(Commande command , Cart cart)
    {
        this.commande = command ;
        this.cart = cart;
    }


    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws Exception {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfEmial.getText().isEmpty() || tfAdresse.getText().isEmpty() || tfNumero.getText().isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        }
        // check if tfNumero contains only numbers using regular expression
        else if (!tfNumero.getText().matches("[0-9]+")) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Veuillez saisir un numéro valide");
            alert.show();
        }
        // check if numero length equals 8
        else if (tfNumero.getText().length() != 8) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Le numéro doit contenir 9 chiffres");
            alert.show();
        }

        // chck if email is valid
        else if (!tfEmial.getText().matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Veuillez saisir un email valide");
            alert.show();
        }
        else {

            this.commande.setNom(tfNom.getText());
            this.commande.setPrenom(tfPrenom.getText());
            this.commande.setEmail(tfEmial.getText());
            this.commande.setAdresse(tfAdresse.getText());
            this.commande.setNumTel(tfNumero.getText());
            int lastInsertedId = commandeservices.addAndGetLastId(this.commande);
            Facture facture = new Facture() ;
            facture.setCommandeid(lastInsertedId);
            facture.setAdresse(tfAdresse.getText());
            facture.setNom(tfNom.getText());
            facture.setPrenom(tfPrenom.getText());
            facture.setEmail(tfEmial.getText());
            facture.setNumTel(tfNumero.getText());
            facture.setTotalCost(this.commande.getTotalCost());
            facture.setDate(this.commande.getDate());
            this.factureService.ajouter(facture);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setContentText("Commande et facture ajoutées avec succès");
            alert.show();

            Mailing.sendHtmlNotification(commande.getEmail(), "Commande ajoutée", "Votre commande a été ajoutée avec succès",generateFacturePDF(commande));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MesCommandes.fxml"));
            try {
                this.BtnAjouter.getScene().setRoot(loader.load());
            } catch (Exception e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Passage à mes commandes impossible");
                alert.show();
            }
        }
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        tfNom.setText("");
        tfPrenom.setText("");
        tfEmial.setText("");
        tfAdresse.setText("");
        tfNumero.setText("");
    }

    public byte[] generateFacturePDF(Commande command) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add document information (optional)
        document.addAuthor("Freelancy");
        document.addTitle("Facture #" + command.getCommandeid());

        // Add heading (store name, logo, etc.)
        Paragraph header = new Paragraph("Your Store Name");
        header.setAlignment(Element.ALIGN_CENTER);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
        header.setFont(font);
        document.add(header);

        // Add customer information (optional)

        Paragraph customerInfo = new Paragraph("Customer Name: " + command.getNom());
        customerInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(customerInfo);
        customerInfo = new Paragraph("Customer Email: " + command.getEmail());
        customerInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(customerInfo);


        // Add date
        Paragraph date = new Paragraph("Date: " + command.getDate().toString());
        date.setAlignment(Element.ALIGN_RIGHT);
        document.add(date);

        // Add table for cart items
        PdfPTable table = new PdfPTable(3); // Three columns (product name, quantity, price)
        table.setWidthPercentage(100); // Set table width to 100%

        // Add table headers
        PdfPCell cell = new PdfPCell(new Phrase("Product Name"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantity"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Price (DT)"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        // Add table data from cart items
        for (Map.Entry<Products, Integer> entry : cart.getProducts().entrySet()) {
            Products product = entry.getKey();
            Integer quantity = entry.getValue();

            cell = new PdfPCell(new Phrase(product.getTitle()));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(quantity.toString()));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(product.getPrice()))); // Format price with two decimal places
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
        }

        document.add(table);

        // Add total price
        Paragraph totalPrice = new Paragraph("Total Price (DT): " + command.getTotalCost());
        totalPrice.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalPrice);

        document.close();

        return outputStream.toByteArray() ;
    }

}
