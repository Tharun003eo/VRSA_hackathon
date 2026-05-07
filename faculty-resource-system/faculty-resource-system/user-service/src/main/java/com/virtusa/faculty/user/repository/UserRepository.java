package com.virtusa.faculty.user.repository;

import com.virtusa.faculty.user.entity.User;
import com.virtusa.faculty.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

}