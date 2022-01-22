package com.lilium.springsecurityangular.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService implements UserDetailsService { // Suggestion for naming this something like CurrentUserDetailsService or alike, helps w/consept
    private final UserInMemoryRepository inMemoryRepository;
    private final UserRepository repository;

    @Autowired
    public CurrentUserService(UserInMemoryRepository inMemoryRepository,
                              UserRepository repository) {
        this.inMemoryRepository = inMemoryRepository;
        this.repository = repository;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        //Referring to my previous suggestion in entity, could be better to use Optional when searching user by username.
        final UserEntity user = repository.findByUsername(username);
        if (user != null) {
            final CurrentUser currentUser = new CurrentUser();
            currentUser.setUsername(user.getUsername());
            currentUser.setPassword(user.getPassword());

            return currentUser;
        }

        final CurrentUser currentUser = inMemoryRepository.findUserByUsername(username);
        if (currentUser == null) {
            throw new UsernameNotFoundException("Failed to find user with username: " + username);
        }

        return currentUser;
    }
}
