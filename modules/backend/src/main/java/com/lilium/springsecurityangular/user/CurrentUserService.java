package com.lilium.springsecurityangular.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService implements UserDetailsService {
    private final UserInMemoryRepository repository;

    @Autowired
    public CurrentUserService(UserInMemoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        final CurrentUser currentUser = repository.findUserByUsername(username);
        if (currentUser == null) {
            throw new UsernameNotFoundException("Failed to find user with username: " + username);
        }

        return currentUser;
    }
}
