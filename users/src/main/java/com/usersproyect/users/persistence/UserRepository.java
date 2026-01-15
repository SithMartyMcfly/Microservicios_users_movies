package com.usersproyect.users.persistence;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.usersproyect.users.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u from USERS U JOIN u.movies m WHERE m.id = :idMovie")
    List<User> getUsersByMovie (Long movieId);
}
