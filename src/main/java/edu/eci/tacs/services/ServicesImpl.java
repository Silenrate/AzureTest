package edu.eci.tacs.services;

import org.springframework.stereotype.Service;
import edu.eci.tacs.model.Greeting;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ServicesImpl implements Services {
    private final AtomicLong counter = new AtomicLong();

    @Override
    public Greeting getGreeting(String name) {
        counter.incrementAndGet();
        String content = "Hello, " + name + "!";
        return new Greeting(counter.get(), content);
    }
}
