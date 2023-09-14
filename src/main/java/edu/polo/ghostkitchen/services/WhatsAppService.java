// package edu.polo.ghostkitchen.services;

// import org.springframework.stereotype.Service;

// import com.twilio.Twilio;
// import com.twilio.rest.api.v2010.account.Message;
// import com.twilio.type.PhoneNumber;

// @Service
// public class WhatsAppService {

//     private final String ACCOUNT_SID = "tu_account_sid";
//     private final String AUTH_TOKEN = "tu_auth_token";
//     private final String TWILIO_PHONE_NUMBER = "tu_numero_twilio";

//     public void enviarMensajeDelivery(String numeroDelivery, String mensaje) {
//         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//         Message message = Message.creator(
//                 new PhoneNumber("whatsapp:" + numeroDelivery),
//                 new PhoneNumber("whatsapp:" + TWILIO_PHONE_NUMBER),
//                 mensaje
//         ).create();
//     }

//     public void enviarMensajeCocina(String numeroCocina, String mensaje) {
//         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//         Message message = Message.creator(
//                 new PhoneNumber("whatsapp:" + numeroCocina),
//                 new PhoneNumber("whatsapp:" + TWILIO_PHONE_NUMBER),
//                 mensaje
//         ).create();
//     }

//     public void enviarMensajeCliente(String numeroCliente, String mensaje) {
//         Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//         Message message = Message.creator(
//                 new PhoneNumber("whatsapp:" + numeroCliente),
//                 new PhoneNumber("whatsapp:" + TWILIO_PHONE_NUMBER),
//                 mensaje
//         ).create();
//     }
// }
