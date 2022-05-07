package com.example.demo.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Beans.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByemail(String username);
}