package edu.eci.tacs;

import com.google.gson.Gson;
import edu.eci.tacs.controllers.dtos.CreateFood;
import edu.eci.tacs.controllers.dtos.CreateUser;
import edu.eci.tacs.controllers.dtos.GetFood;
import edu.eci.tacs.controllers.dtos.GetUser;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql("/test-h2.sql")
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    private MockMvc mvc;

    private final Gson gson = new Gson();

    @Test
    public void shouldNotLoginWithANonExistentUser() throws Exception {
        CreateUser user = new CreateUser("noexiste@gmail.com", "123");
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("No existe un usuario con el nombre noexiste@gmail.com", bodyResult);
    }

    @Test
    public void shouldNotCreateAnUserWithARepeatedUsername() throws Exception {
        CreateUser user = new CreateUser("usuarioA@gmail.com", "123");
        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isConflict())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("Ya existe un usuario con el nombre usuarioA@gmail.com", bodyResult);
    }

    @Test
    public void shouldNotDoLoginWithBadCredentials() throws Exception {
        CreateUser user = new CreateUser("usuarioB@gmail.com", "123");
        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        user.setPassword("456");
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isUnauthorized())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("Credenciales Incorrectas", bodyResult);
    }

    @Test
    public void shouldDoLogin() throws Exception {
        CreateUser user = new CreateUser("usuarioC@gmail.com", "123");
        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("Login Exitoso", bodyResult);
    }

    @Test
    public void shouldNotGetANonExistingUser() throws Exception {
        String email = "noexiste@gmail.com";
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/users/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        assertEquals("No existe un usuario con el nombre " + email, bodyResult);
    }

    @Test
    public void shouldGetAUser() throws Exception {
        String email = "usuarioD@gmail.com";
        CreateUser user = new CreateUser(email, "123");
        mvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.get("/users/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isAccepted())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        GetUser returnedUser = gson.fromJson(bodyResult, GetUser.class);
        assertEquals(email, returnedUser.getUsername());
        assertTrue(returnedUser.getId() > 0);
    }

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
    public void shouldDeleteOneFood() throws Exception {
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
}