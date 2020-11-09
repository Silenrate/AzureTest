package edu.eci.tacs.persistence;

import edu.eci.tacs.model.Food;
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

    @Override
    public User getUser(String username) throws PersistenceException {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            throw new PersistenceException("No existe un usuario con el nombre " + username);
        }
        return user;
    }

    @Override
    public void addFood(Food food, String username) throws PersistenceException {
        User user = getUser(username);
        food.setUser(user);
        user.addFood(food);
        userRepository.save(user);
    }
}
