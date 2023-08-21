package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long>{
    
}
