package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.Ghosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GhostsRepository extends JpaRepository<Ghosts, Long> {

    Ghosts findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRole(Ghosts.GhostRole role);

    Ghosts findById(long id);

    @Query("SELECT COUNT(*) FROM Ghosts")
int countGhosts();
     

}
