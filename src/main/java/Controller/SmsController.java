package Controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SmsController {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC0ae3f7f8050150a2c116f77425eb808f";
    public static final String AUTH_TOKEN = "2e6ca1331a128dd9216b3f242ceaf3dc";

    public static void Sms() {
        System.out.println("Sending SMS now");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(

                new com.twilio.type.PhoneNumber("+21650230314"),
                new com.twilio.type.PhoneNumber("+14352649551"),



                " un nouveau produit éte ajouter "
        ).create();

        System.out.println(message.getSid());
    }
}