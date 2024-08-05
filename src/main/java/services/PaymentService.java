package services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import javafx.scene.control.Alert;
import models.Facture;

public class PaymentService {
    private Alert a = new Alert(Alert.AlertType.NONE);

    public void processPayment(Facture facture) {

    }
}

