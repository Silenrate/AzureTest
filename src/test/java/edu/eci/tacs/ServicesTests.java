package edu.eci.tacs;

import edu.eci.tacs.model.Food;
import edu.eci.tacs.model.User;
import edu.eci.tacs.services.ServiceException;
import edu.eci.tacs.services.Services;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql("/test-h2.sql")
@AutoConfigureTestDatabase
public class ServicesTests {

    @Autowired
    private Services services;

    @Test
    public void shouldNotCreateAnUserWithARepeatedUsername() {
        String email = "clienteA@gmail.com";
        User user = new User(email, "123");
        try {
            services.addUser(user);
        } catch (ServiceException e) {
            fail("No debió fallar al agregar este usuario");
        }
        try {
            services.addUser(user);
            fail("Debió fallar al agregar este usuario");
        } catch (ServiceException e) {
            assertEquals("Ya existe un usuario con el nombre " + email, e.getMessage());
        }
    }

    @Test
    public void shouldNotGetANonExistingUser() {
        String email = "noexiste@gmail.com";
        try {
            services.getUser(email);
            fail("Debió fallar al consultar este usuario");
        } catch (ServiceException e) {
            assertEquals("No existe un usuario con el nombre " + email, e.getMessage());
        }
    }

    @Test
    public void shouldGetAUser() throws ServiceException {
        String email = "clienteB@gmail.com";
        User user = new User(email, "123");
        try {
            services.addUser(user);
        } catch (ServiceException e) {
            fail("No debió fallar al agregar este usuario");
        }
        User returnedUser = services.getUser(email);
        assertEquals(email, returnedUser.getUsername());
        assertTrue(returnedUser.getId() > 0);
    }

    @Test
    public void shouldNotAddFoodToANonExistingUser() {
        String email = "noexiste@gmail.com";
        Food food = new Food("Pizza");
        try {
            services.addFood(food, email);
            fail("Debió fallar al agregar el alimento a un usuario que no existe");
        } catch (ServiceException e) {
            assertEquals("No existe un usuario con el nombre " + email, e.getMessage());
        }
    }

    @Test
    public void shouldNotGetTheFoodsOfANonExistingUser() {
        String email = "noexiste@gmail.com";
        try {
            services.getFoodsOfAUser(email);
            fail("Debió fallar al consultar los alimentos de un usuario que no existe");
        } catch (ServiceException e) {
            assertEquals("No existe un usuario con el nombre " + email, e.getMessage());
        }
    }

    @Test
    public void shouldAddAndGetFood() throws ServiceException {
        String email = "clienteC@gmail.com";
        User user = new User(email, "123");
        try {
            services.addUser(user);
        } catch (ServiceException e) {
            fail("No debió fallar al agregar este usuario");
        }
        Food food = new Food("Pizza");
        services.addFood(food, email);
        List<Food> foodList = services.getFoodsOfAUser(email);
        assertEquals(1, foodList.size());
        Food returnedFood = foodList.get(0);
        assertEquals(food, returnedFood);
    }

    @Test
    public void shouldNotDeleteAFoodOfANonExistingUser() {
        String email = "noexiste@gmail.com";
        try {
            services.deleteFood(0, email);
            fail("Debió fallar al intentar eliminar un alimento de un usuario que no existe");
        } catch (ServiceException e) {
            assertEquals("No existe un usuario con el nombre " + email, e.getMessage());
        }
    }

    @Test
    public void shouldNotDeleteANonExistingFood() {
        String email = "clienteE@gmail.com";
        User user = new User(email, "123");
        try {
            services.addUser(user);
        } catch (ServiceException e) {
            fail("No debió fallar al agregar el usuario");
        }
        try {
            services.deleteFood(0, email);
            fail("Debió fallar al intentar eliminar un alimento que no existe");
        } catch (ServiceException e) {
            assertEquals("No existe un alimento con el id 0", e.getMessage());
        }
    }

    @Test
    public void shouldNotDeleteAFoodOfAnotherUser() {
        String email = "clienteF@gmail.com";
        User user = new User(email, "123");
        User user2 = new User("clienteF2@gmail.com", "123");
        Food food = new Food("Pizza");
        try {
            services.addUser(user);
            services.addUser(user2);
            services.addFood(food, email);
        } catch (ServiceException e) {
            fail("No debió fallar al agregar los usuarios ni el alimento");
        }
        List<Food> foodList = null;
        try {
            foodList = services.getFoodsOfAUser(email);
        } catch (ServiceException e) {
            fail("No debió fallar al consultar los alimentos de ese usuario");
        }
        assertEquals(1, foodList.size());
        Food returnedFood = foodList.get(0);
        try {
            services.deleteFood(returnedFood.getId(), "clienteF2@gmail.com");
            fail("Debió fallar al intentar eliminar un alimento que no es suyo");
        } catch (ServiceException e) {
            assertEquals("Este usuario no tiene permiso de eliminar este alimento", e.getMessage());
        }
    }

    @Test
    public void shouldDeleteOneFood() throws ServiceException {
        String email = "clienteD@gmail.com";
        User user = new User(email, "123");
        Food food = new Food("Pizza");
        try {
            services.addUser(user);
            services.addFood(food, email);
        } catch (ServiceException e) {
            fail("No debió fallar al agregar el usuario ni el alimento");
        }
        List<Food> foodList = services.getFoodsOfAUser(email);
        assertEquals(1, foodList.size());
        Food returnedFood = foodList.get(0);
        services.deleteFood(returnedFood.getId(), email);
        List<Food> newFoodList = services.getFoodsOfAUser(email);
        assertEquals(0, newFoodList.size());
    }
}
