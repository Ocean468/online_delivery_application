package com.delivery.app.online_delivery_application.wrapperClass;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.delivery.app.online_delivery_application.dao.UserDao;
import com.delivery.app.online_delivery_application.model.User; // Your custom User class

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao; // Custom repository for accessing the User data
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user by email (username)
        User user = userDao.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));  // Proper handling of Optional

        return new UserPrincipal(user);  // Wrap the User in a UserPrincipal
    }
}