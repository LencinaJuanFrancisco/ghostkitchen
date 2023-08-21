package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
}
