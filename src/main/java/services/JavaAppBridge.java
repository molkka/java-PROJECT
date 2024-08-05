package services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.scene.control.Alert;
import models.Facture;

public class JavaAppBridge {
    private Facture facture;

    private Alert a = new Alert(Alert.AlertType.NONE);
    public void processPayment(String token) {
        System.out.println("here in the bridge");
        try {
            // Set your secret key here
            Stripe.apiKey = "sk_test_51Oq1hhIdAEfnvdfNCgtHXIUslnGYASMUNSOJ8HPb8rzjNlVbpnsvByO6Tm9JJhzAwUV3rDdhUU0INtcPLPj9NgB500LScvSU5s";

            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(getTotalAmount() * 100)
                            .setCurrency("usd") // Set your currency here
                            .setDescription("Example payment")
                            // set the payment method as visa card
                            .setPaymentMethod("pm_card_visa")
                            .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                            .setConfirm(true)
                            .putMetadata("integration_check", "accept_a_payment")
                            .putMetadata("customer_name", getCustomerName())
                            .setReturnUrl("http://localhost:8080")
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);
            System.out.println("Payment successful. Session ID: " + intent.getId());
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setTitle("Payment Successful");
            a.setHeaderText(null);
            a.setContentText("Payment of " + getCustomerName() + " was successful.");
            a.showAndWait();

        } catch (StripeException e) {
            // If there was an error processing the payment, display the error message
            System.out.println("Payment failed. Error: " + e.getMessage());
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Payment Failed");
            a.setHeaderText(null);
            a.setContentText("Payment of " + getCustomerName() + " failed. Please try again.");
            a.showAndWait();
        }

    }

    public String getCustomerName() {
        return facture.getNom();
    }

    public long getTotalAmount() {
        return facture.getTotalCost();
    }

    public void SetFacture(Facture facture) {
        this.facture = facture;
    }
}