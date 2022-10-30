package com.greenfoxacademy.reddit.service;

import com.greenfoxacademy.reddit.model.User;
import com.greenfoxacademy.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private User loggedIn;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.loggedIn = null;
    }

    public User getLoggedIn() {
        return this.loggedIn;
    }

    public void addUser(User user) {
        this.userRepository.save(user);
    }

    public Boolean validateUsername(User user) {
        return this.userRepository.findByUsername(user.getUsername()) == null;
    }

    public Boolean validatePassword(User user) {
        return user.getPassword().equals(user.getPasswordConfirmation());
    }

    public Boolean validateUser(User user) {
        if (this.userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()) == null) return false;
        this.loggedIn = user;
        return true;
    }

    public Boolean isLoggedIn(String username) {
        if (this.loggedIn == null) return false;
        return this.loggedIn.getUsername().equals(username);
    }

    public void logout() {
        this.loggedIn = null;
    }
}