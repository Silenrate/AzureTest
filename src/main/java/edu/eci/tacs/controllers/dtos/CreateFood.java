package edu.eci.tacs.controllers.dtos;

import java.io.Serializable;

public class CreateFood implements Serializable {

    private String name;

    public CreateFood() {
    }

    public CreateFood(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "createFood{" +
                "name='" + name + '\'' +
                '}';
    }
}
