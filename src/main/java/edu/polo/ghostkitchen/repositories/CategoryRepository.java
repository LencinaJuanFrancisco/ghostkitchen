package edu.polo.ghostkitchen.repositories;

import edu.polo.ghostkitchen.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(String categoryCategory);

    @Query("SELECT c FROM Category c WHERE c.category = :categoryCategory")
    Category getByCategoryCategory(@Param("categoryCategory") String categoryCategory);

}
