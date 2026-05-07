package com.virtusa.faculty.gateway.resolver;


import com.virtusa.faculty.user.dto.UserInput;
import com.virtusa.faculty.user.entity.Role;
import com.virtusa.faculty.user.entity.User;
import com.virtusa.faculty.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserGatewayResolver {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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
                .role(Role.valueOf(input.getRole()))
                .password(passwordEncoder.encode(input.getPassword()))
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
