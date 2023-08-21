package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public List<Client> getAll() {
        List<Client> lista = new ArrayList<Client>();
        clientRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
    
    public Client getById(Long id) {
        return clientRepository.findById(id).get();
    }
    
    public void save(Client client) {
        clientRepository.save(client);
    }
    
    public void delete(Long id){
        clientRepository.deleteById(id);
    }
}
