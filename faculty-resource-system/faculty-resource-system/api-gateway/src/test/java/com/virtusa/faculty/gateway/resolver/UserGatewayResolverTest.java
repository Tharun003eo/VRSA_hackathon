package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.user.dto.UserInput;
import com.virtusa.faculty.user.entity.Role;
import com.virtusa.faculty.user.entity.User;
import com.virtusa.faculty.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGatewayResolverTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserGatewayResolver resolver;

    @Test
    void testGetUserById() {
        User user = User.builder().id(1L).build();

        when(userService.getUserById(1L)).thenReturn(user);

        User result = resolver.getUserById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetFacultyList() {
        when(userService.getFacultyList())
                .thenReturn(List.of(new User(), new User()));

        List<User> result = resolver.getFacultyList();

        assertEquals(2, result.size());
    }

    @Test
    void testRegisterUser() {
        UserInput input = new UserInput();
        input.setName("A");
        input.setEmail("a@mail.com");
        input.setRole("STUDENT");
        input.setPassword("1234");

        when(passwordEncoder.encode("1234")).thenReturn("encoded");
        when(userService.registerUser(any()))
                .thenAnswer(i -> i.getArgument(0));

        User result = resolver.registerUser(input);

        assertEquals("encoded", result.getPassword());
        assertEquals(Role.STUDENT, result.getRole());
    }

    @Test
    void testAssignRole() {
        User user = User.builder().role(Role.FACULTY).build();

        when(userService.assignRole(1L, "FACULTY")).thenReturn(user);

        User result = resolver.assignRole(1L, "FACULTY");

        assertEquals(Role.FACULTY, result.getRole());
    }

    @Test
    void testLinkFacultyToDepartment() {
        User user = User.builder().department("CSE").build();

        when(userService.linkFacultyToDepartment(1L, "CSE")).thenReturn(user);

        User result = resolver.linkFacultyToDepartment(1L, "CSE");

        assertEquals("CSE", result.getDepartment());
    }
}