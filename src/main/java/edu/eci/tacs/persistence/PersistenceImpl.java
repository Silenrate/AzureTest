package edu.eci.tacs.persistence;

import edu.eci.tacs.model.User;
import edu.eci.tacs.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceImpl implements Persistence {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
}
