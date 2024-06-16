package com.example.generatorsdiplomawork.repositories;

import com.example.generatorsdiplomawork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByLogin(String login);

    public User deleteUserByLogin(String login);
}
