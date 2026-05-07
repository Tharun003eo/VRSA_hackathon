package com.virtusa.faculty.user.service;

import com.virtusa.faculty.user.entity.User;

import java.util.List;

public interface UserService {

    // registerUser
    User registerUser(User user);

    // updateUserProfile
    User updateUserProfile(Long id, User user);

    // assignRole
    User assignRole(Long id, String role);

    // linkFacultyToDepartment
    User linkFacultyToDepartment(Long id, String department);

    // getUserById
    User getUserById(Long id);

    // getFacultyList
    List<User> getFacultyList();

    // getStudentList
    List<User> getStudentList();

    //Security Feature
    User getUserByEmail(String email);
}