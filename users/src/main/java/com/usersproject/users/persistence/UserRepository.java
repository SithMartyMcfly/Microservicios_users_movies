package com.usersproject.users.persistence;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.usersproject.users.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from USER u JOIN u.movies m WHERE m.id = :idMovie")
    List<User> getUsersByMovie (Long idMovie);
    boolean existsByEmail(String email);
}
