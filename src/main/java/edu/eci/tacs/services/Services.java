package edu.eci.tacs.services;

import edu.eci.tacs.model.Food;
import edu.eci.tacs.model.User;

public interface Services {

    void addUser(User user);

    User getUser(String username) throws ServiceException;

    void addFood(Food food, String username) throws ServiceException;
}
