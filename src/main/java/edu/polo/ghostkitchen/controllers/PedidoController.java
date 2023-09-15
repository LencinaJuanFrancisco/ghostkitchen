package edu.polo.ghostkitchen.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import edu.polo.ghostkitchen.classes.CartAdm;
import edu.polo.ghostkitchen.entidades.Detail;
import edu.polo.ghostkitchen.entidades.Order;
import edu.polo.ghostkitchen.services.WhatsAppService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private WhatsAppService whatsAppService;
    @Autowired
    private CartAdm cartAdm;

    @GetMapping("/enviarMensaje")
    public String crearPedido(HttpSession session) throws InterruptedException {

        // Recupera los objetos de la sesión
        Order order = (Order) session.getAttribute("order");
        List<Detail> cart = cartAdm.getDetailList();

        System.out.println("===========================================");
        System.out.println(order);
        System.out.println("===========================================");
        // Enviar mensaje al cliente
        // Calcular el precio con el aumento del 10% y agregar 450
        double precioConAumentoYEnvio = order.getPrice() * 1.10 + 450;

        // Formatear el mensaje con el precio sin decimales
        String mensajeCliente = String.format(
                "Su pedido está en marcha. Estamos preparando su comida 🔥🔥🔥. %.0f con el envío incluido es 💲:%.0f pesos",
                order.getPrice(), precioConAumentoYEnvio);

        whatsAppService.enviarMensajeCliente("+5493794736274", mensajeCliente);

        // Enviar mensaje a la cocina
        // Construir el mensaje para la cocina incluyendo los detalles
        StringBuilder mensajeCocinaBuilder = new StringBuilder();
        mensajeCocinaBuilder.append("Nuevo pedido para cocina:\n");
        for (Detail detail : cart) {
            mensajeCocinaBuilder.append("✅ ")
                    .append(detail.getCantidad() + " - uni -" + detail.getDish().getName() + " --"
                            + detail.getDetalle())
                    .append("\n");
        }
        String mensajeCocina = mensajeCocinaBuilder.toString();
        whatsAppService.enviarMensajeCocina("+5493794736274", mensajeCocina);

        // Enviar mensaje al delivery
        String mensajeDelivery = "🍳 Buscar de: " + order.getChef().getUser().getAddress() + "\n" +
                " y enviar a 🛵 " + order.getClient().getUser().getAddress() + "\n" +
                " entregar a " + order.getClient().getUser().getName() + " \n";

        whatsAppService.enviarMensajeDelivery("+5493794736274", mensajeDelivery);

        System.out.println("++++++++++++++++++++ trespuesta del msg ++++++++++++++++++++++++++");
        System.out.println(ResponseEntity.ok("Pedido creado exitosamente."));
        System.out.println("++++++++++++++++++++ mmmmmmmmmmmmmmmmmmmmmmmmmm ++++++++++++++++++++++++++");

        Thread.sleep(6000);
        return "redirect:/remito";
    }

}
