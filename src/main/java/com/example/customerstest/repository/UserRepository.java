package com.example.customerstest.repository;

import com.example.customerstest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     @Query("SELECT u FROM User u WHERE u.login = :login")
     User findUserByLogin(@Param("login") String login);

    // Optional<User> findUserByLogin(String username);
}
