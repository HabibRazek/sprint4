package com.habib.Drugs.repos;

import com.habib.Drugs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);
}