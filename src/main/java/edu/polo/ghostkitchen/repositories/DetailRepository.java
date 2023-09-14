package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends CrudRepository<Detail, Long> {

    @Query("SELECT d.dish.id, COUNT(d.dish.id) FROM Detail d GROUP BY d.dish.id ORDER BY COUNT(d.dish.id) DESC")
List<Object[]> platoMasVendido();
}
