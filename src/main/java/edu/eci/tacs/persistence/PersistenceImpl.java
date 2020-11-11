package edu.eci.tacs.persistence;

import edu.eci.tacs.model.Food;
import edu.eci.tacs.model.User;
import edu.eci.tacs.persistence.repositories.FoodRepository;
import edu.eci.tacs.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersistenceImpl implements Persistence {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public void addUser(User user) throws PersistenceException {
        String username = user.getUsername();
        if(userRepository.getUserByUsername(username)!=null){
            throw new PersistenceException("Ya existe un usuario con el nombre " + username);
        }
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
        food.setUsername(user);
        user.addFood(food);
        userRepository.save(user);
    }

    @Override
    public List<Food> getFoodsOfAUser(String username) throws PersistenceException {
        User user = getUser(username);
        return user.getFoods();
    }

    @Override
    public void deleteFood(long foodId, String username) throws PersistenceException {
        User user = getUser(username);
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (!optionalFood.isPresent()) {
            throw new PersistenceException("No existe un alimento con el id " + foodId);
        }
        Food food = optionalFood.get();
        if (!user.getUsername().equals(food.getUsername().getUsername())) {
            throw new PersistenceException("Este usuario no tiene permiso de eliminar este alimento");
        }
        foodRepository.deleteFromId(foodId);
    }
}
