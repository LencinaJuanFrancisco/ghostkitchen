package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {

    Chef findByWeb(String web);
    
     @Query(value = "SELECT * FROM chef WHERE user_id = ?1", nativeQuery = true)
    Chef findChefsByUserId(Long userId);

    @Query("SELECT COUNT(*) FROM Chef")
int countChefs();
    
}
