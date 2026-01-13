package com.usersproyect.users.persistence;



import org.springframework.data.jpa.repository.JpaRepository;

import com.usersproyect.users.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
