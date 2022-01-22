package com.lilium.springsecurityangular.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /*How about Optional<UserEntity> here for further use elsewhere? */
    UserEntity findByUsername(String username);
}
