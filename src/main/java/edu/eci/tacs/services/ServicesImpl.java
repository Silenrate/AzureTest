package edu.eci.tacs.services;

import edu.eci.tacs.model.Food;
import edu.eci.tacs.model.User;
import edu.eci.tacs.persistence.Persistence;
import edu.eci.tacs.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void addFood(Food food, String username) throws ServiceException {
        try {
            persistence.addFood(food,username);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Food> getFoodsOfAUser(String username) throws ServiceException{
        try {
            return persistence.getFoodsOfAUser(username);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteFood(long foodId, String username) throws ServiceException {
        try {
            persistence.deleteFood(foodId,username);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
