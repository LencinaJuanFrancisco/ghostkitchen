package edu.polo.ghostkitchen.services;

import org.springframework.stereotype.Service;


import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppService {

    public static final String ACCOUNT_SID = "ACbb3bbc730d2711509128a82497ec4ae4";
    public static final String AUTH_TOKEN  = "d589a085e9095a55a4b0d8fd355f7031";
   

    

    public void enviarMensajeDelivery(String numeroDelivery, String mensaje) {
    System.out.println("--------------- Entrando al envío del delivery ---------------");

    // Utiliza el número de teléfono pasado como argumento
    com.twilio.type.PhoneNumber to = new com.twilio.type.PhoneNumber("whatsapp:" + numeroDelivery);

    // Utiliza el número de teléfono de Twilio como remitente
    com.twilio.type.PhoneNumber from = new com.twilio.type.PhoneNumber("whatsapp:+14155238886");

    try {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(to, from, mensaje).create();

        System.out.println("Mensaje enviado con SID: " + message.getSid());
    } catch (ApiException e) {
        System.err.println("Error al enviar el mensaje: " + e.getMessage());
    }

    System.out.println("--------------- Saliendo del envío del delivery ---------------");
}
public void enviarMensajeCocina(String numeroCocina, String mensaje) {
    System.out.println("--------------- Entrando al envío del cocina ---------------");

    // Utiliza el número de teléfono pasado como argumento
    com.twilio.type.PhoneNumber to = new com.twilio.type.PhoneNumber("whatsapp:" + numeroCocina);

    // Utiliza el número de teléfono de Twilio como remitente
    com.twilio.type.PhoneNumber from = new com.twilio.type.PhoneNumber("whatsapp:+14155238886");

    try {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(to, from, mensaje).create();

        System.out.println("Mensaje enviado con SID: " + message.getSid());
    } catch (ApiException e) {
        System.err.println("Error al enviar el mensaje: " + e.getMessage());
    }

    System.out.println("--------------- Saliendo del envío del cocina ---------------");
}

  
    public void enviarMensajeCliente(String numeroCliente, String mensaje) {
    System.out.println("--------------- Entrando al envío del cliente ---------------");

    // Utiliza el número de teléfono pasado como argumento
    com.twilio.type.PhoneNumber to = new com.twilio.type.PhoneNumber("whatsapp:" + numeroCliente);

    // Utiliza el número de teléfono de Twilio como remitente
    com.twilio.type.PhoneNumber from = new com.twilio.type.PhoneNumber("whatsapp:+14155238886");

    try {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(to, from, mensaje).create();

        System.out.println("Mensaje enviado con SID: " + message.getSid());
    } catch (ApiException e) {
        System.err.println("Error al enviar el mensaje: " + e.getMessage());
    }

    System.out.println("--------------- Saliendo del envío del cliente ---------------");
}

}