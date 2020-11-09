package edu.eci.tacs.persistence.repositories;

import edu.eci.tacs.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from foods c where c.id = :id", nativeQuery = true)
    void deleteFromId(@Param("id") Long id);
}
