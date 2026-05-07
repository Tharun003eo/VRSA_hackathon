package com.virtusa.faculty.user.resolver;

import com.virtusa.faculty.user.dto.UserInput;
import com.virtusa.faculty.user.entity.User;
import com.virtusa.faculty.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

//@Controller
@RequiredArgsConstructor
public class UserResolver {

    private final UserService userService;

    // ----------- Queries -----------

    // getUserById
    @QueryMapping
    public User getUserById(@Argument Long id) {
        return userService.getUserById(id);
    }

    // getFacultyList
    @QueryMapping
    public List<User> getFacultyList() {
        return userService.getFacultyList();
    }

    // getStudentList
    @QueryMapping
    public List<User> getStudentList() {
        return userService.getStudentList();
    }

    // ----------- Mutations -----------

    // registerUser
    @MutationMapping
    public User registerUser(@Argument UserInput input) {
        User user = User.builder()
                .name(input.getName())
                .email(input.getEmail())
                .role(null) // will be assigned separately
                .department(input.getDepartment())
                .build();

        return userService.registerUser(user);
    }

    // updateUserProfile
    @MutationMapping
    public User updateUserProfile(@Argument Long id, @Argument UserInput input) {
        User updated = User.builder()
                .name(input.getName())
                .department(input.getDepartment())
                .build();

        return userService.updateUserProfile(id, updated);
    }

    // assignRole
    @MutationMapping
    public User assignRole(@Argument Long id, @Argument String role) {
        return userService.assignRole(id, role);
    }

    // linkFacultyToDepartment
    @MutationMapping
    public User linkFacultyToDepartment(@Argument Long id, @Argument String department) {
        return userService.linkFacultyToDepartment(id, department);
    }
}