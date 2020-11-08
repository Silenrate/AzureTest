package edu.eci.tacs.services;

import edu.eci.tacs.model.User;
import edu.eci.tacs.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.tacs.model.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ServicesImpl implements Services {

    @Autowired
    private Persistence persistence;

    private final AtomicLong counter = new AtomicLong();

    @Override
    public Greeting getGreeting(String name) {
        counter.incrementAndGet();
        String content = "Hello, " + name + "!";
        return new Greeting(counter.get(), content);
    }

    @Override
    public void addUser(User user) {
        persistence.addUser(user);
    }
}
