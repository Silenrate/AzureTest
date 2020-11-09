package edu.eci.tacs.controllers.dtos;

import java.io.Serializable;

public class GetUser implements Serializable {
    private long id;
    private String username;

    public GetUser() {
    }

    public GetUser(String username, long id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
