package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Delivery getById(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public Delivery getByLicencePlate(String licencePlate) {
        return deliveryRepository.findByLicencePlate(licencePlate);
    }

}
