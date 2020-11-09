package edu.eci.tacs.persistence.repositories;

import edu.eci.tacs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users where username = :emailUser", nativeQuery = true)
    User getUserByUsername(@Param("emailUser") String emailUser);
}
