package edu.eci.tacs.persistence;

import edu.eci.tacs.model.Food;
import edu.eci.tacs.model.User;

public interface Persistence {
    void addUser(User user);

    User getUser(String username) throws PersistenceException;

    void addFood(Food food, String username) throws PersistenceException;
}
