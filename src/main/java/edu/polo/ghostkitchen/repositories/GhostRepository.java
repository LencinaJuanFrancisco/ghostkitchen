package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GhostRepository extends CrudRepository<Ghost, Long>{
    
    Ghost findByEmail(String email);
    
    boolean existsByEmail(String email);
    
}
