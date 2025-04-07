package com.task.management.system.service;

import com.task.management.system.domain.User;
import com.task.management.system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("A user with this name already exists: " + user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("A user with this email already exists: " + user.getEmail());
        }

        return save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

}
