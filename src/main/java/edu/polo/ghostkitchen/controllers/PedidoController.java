package edu.polo.ghostkitchen.controllers;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;

import edu.polo.ghostkitchen.entidades.Detail;
import edu.polo.ghostkitchen.entidades.Order;
//import edu.polo.ghostkitchen.services.WhatsAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

//    @Autowired
//    private WhatsAppService whatsAppService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearPedido( Order pedido) {
        // Lógica para crear el pedido

        // Enviar mensaje al cliente
       // String mensajeCliente = "Su pedido está en marcha. Estamos preparando su comida.";
       // whatsAppService.enviarMensajeCliente(pedido., mensajeCliente);

        // Enviar mensaje a la cocina
       // String mensajeCocina = "Nuevo pedido: " + pedido.getDetallesPedido();
        //whatsAppService.enviarMensajeCocina(pedido.getNumeroCocina(), mensajeCocina);

        // Enviar mensaje al delivery
      //  String mensajeDelivery = "Entregar en: " + pedido.getDireccionEntrega();
       // whatsAppService.enviarMensajeDelivery(pedido.getNumeroDelivery(), mensajeDelivery);

        return ResponseEntity.ok("Pedido creado exitosamente.");
    }

}
