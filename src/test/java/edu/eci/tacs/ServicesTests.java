package edu.eci.tacs;

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

    /*
    @Test
    public void shouldNotAddFoodToANonExistingUser() throws Exception {
        String email = "noexiste@gmail.com";
        CreateFood food = new CreateFood("Pizza");
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/foods").header("x-userName", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(food)))
                .andExpect(status().isConflict())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("No existe un usuario con el nombre " + email, bodyResult);
    }

    @Test
    public void shouldNotGetTheFoodsOfANonExistingUser() throws Exception {
        String email = "noexiste@gmail.com";
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/foods/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("No existe un usuario con el nombre " + email, bodyResult);
    }

    @Test
    public void shouldAddAndGetFood() throws Exception {
        String email = "usuarioE@gmail.com";
        CreateUser user = new CreateUser(email, "123");
        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        CreateFood food = new CreateFood("Pizza");
        mvc.perform(
                MockMvcRequestBuilders.post("/foods").header("x-userName", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(food)))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/foods/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(bodyResult);
        assertEquals(1, array.length());
        GetFood returnedFood = gson.fromJson(array.getJSONObject(0).toString(), GetFood.class);
        assertEquals(food.getName(), returnedFood.getName());
        assertEquals(email, returnedFood.getUser().getUsername());
    }

    @Test
    public void shouldNotDeleteAFoodOfANonExistingUser() throws Exception {
        String email = "noexiste@gmail.com";
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.delete("/foods/0").header("x-userName", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("No existe un usuario con el nombre " + email, bodyResult);
    }

    @Test
    public void shouldNotDeleteOneFood() throws Exception {
        String email = "usuarioF@gmail.com";
        CreateUser user = new CreateUser(email, "123");
        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        CreateFood food = new CreateFood("Pizza");
        mvc.perform(
                MockMvcRequestBuilders.post("/foods").header("x-userName", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(food)))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/foods/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(bodyResult);
        assertEquals(1, array.length());
        GetFood returnedFood = gson.fromJson(array.getJSONObject(0).toString(), GetFood.class);
        long foodId = returnedFood.getId();
        mvc.perform(
                MockMvcRequestBuilders.delete("/foods/" + foodId).header("x-userName", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted());
        result = mvc.perform(
                MockMvcRequestBuilders.get("/foods/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted())
                .andReturn();
        bodyResult = result.getResponse().getContentAsString();
        array = new JSONArray(bodyResult);
        assertEquals(0, array.length());
    }
     */
}
