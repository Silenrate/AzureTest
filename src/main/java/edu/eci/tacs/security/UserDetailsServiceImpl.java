package edu.eci.tacs.security;

import edu.eci.tacs.model.User;
import edu.eci.tacs.services.ServiceException;
import edu.eci.tacs.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Services services;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User current = null;
        try {
            current = services.getUser(s);
        } catch (ServiceException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        if (current == null) {
            throw new UsernameNotFoundException("User not Found");
        }
        return new UserDetailsImpl(current.getUsername(), current.getPassword());
    }
}
