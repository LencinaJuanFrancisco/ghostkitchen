package edu.polo.ghostkitchen.services;

import edu.polo.ghostkitchen.entidades.*;
import edu.polo.ghostkitchen.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class DetailService {

    @Autowired
    DetailRepository detailRepository;

    public List<Detail> getAll() {
        List<Detail> lista = new ArrayList<Detail>();
        detailRepository.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }
    
    public Detail getById(Long id) {
        return detailRepository.findById(id).get();
    }
    
    public void save(Detail detail) {
        detailRepository.save(detail);
    }
    
    public void delete(Long id){
        detailRepository.deleteById(id);
    }
}
