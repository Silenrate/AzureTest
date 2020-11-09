package edu.eci.tacs.services;

import edu.eci.tacs.model.User;
import edu.eci.tacs.persistence.Persistence;
import edu.eci.tacs.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.tacs.model.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ServicesImpl implements Services {

    @Autowired
    private Persistence persistence;


    @Override
    public void addUser(User user) {
        persistence.addUser(user);
    }

    @Override
    public User getUser(String username) throws ServiceException {
        try {
            return persistence.getUser(username);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
