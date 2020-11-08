package edu.eci.tacs.services;

import edu.eci.tacs.model.Greeting;
import edu.eci.tacs.model.User;

public interface Services {

    public Greeting getGreeting(String name);

    void addUser(User user);
}
