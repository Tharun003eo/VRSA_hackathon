package com.virtusa.faculty.user.service.impl;

import com.virtusa.faculty.user.entity.Role;
import com.virtusa.faculty.user.entity.User;
import com.virtusa.faculty.user.exception.UserNotFoundException;
import com.virtusa.faculty.user.exception.DuplicateUserException;
import com.virtusa.faculty.user.repository.UserRepository;
import com.virtusa.faculty.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // registerUser
    @Override
    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateUserException(user.getEmail());
        }

        user.setRole(Role.STUDENT);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // updateUserProfile
    @Override
    public User updateUserProfile(Long id, User updatedUser) {
        User user = getUserById(id);

        user.setName(updatedUser.getName());
        user.setDepartment(updatedUser.getDepartment());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // assignRole
    @Override
    public User assignRole(Long id, String role) {
        User user = getUserById(id);

        user.setRole(Role.valueOf(role.toUpperCase()));
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // linkFacultyToDepartment
    @Override
    public User linkFacultyToDepartment(Long id, String department) {
        User user = getUserById(id);

        if (user.getRole() != Role.FACULTY) {
            throw new RuntimeException("Only FACULTY can be linked to department");
        }

        user.setDepartment(department);
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    // getUserById
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // getFacultyList
    @Override
    public List<User> getFacultyList() {
        return userRepository.findByRole(Role.FACULTY);
    }

    // getStudentList
    @Override
    public List<User> getStudentList() {
        return userRepository.findByRole(Role.STUDENT);
    }

    //Spring Security Feature
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}