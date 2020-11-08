package edu.eci.tacs.persistence.repositories;

import edu.eci.tacs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
