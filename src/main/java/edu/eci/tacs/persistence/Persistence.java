package edu.eci.tacs.persistence;

import edu.eci.tacs.model.Food;
import edu.eci.tacs.model.User;

import java.util.List;

public interface Persistence {
    void addUser(User user) throws PersistenceException;

    User getUser(String username) throws PersistenceException;

    void addFood(Food food, String username) throws PersistenceException;

    List<Food> getFoodsOfAUser(String username) throws PersistenceException;

    void deleteFood(long foodId, String username) throws PersistenceException;
}
