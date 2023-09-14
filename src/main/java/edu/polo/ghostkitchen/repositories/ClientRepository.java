package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

     @Query(value = "SELECT * FROM client WHERE user_id = ?1", nativeQuery = true)
    Client findClientByUserId(Long userId);

    @Query("SELECT COUNT(*) FROM Client")
int countClients();
    
}
