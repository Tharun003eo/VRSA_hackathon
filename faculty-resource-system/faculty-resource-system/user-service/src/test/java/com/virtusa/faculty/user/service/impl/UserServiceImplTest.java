package com.virtusa.faculty.user.service.impl;

import com.virtusa.faculty.user.entity.Role;
import com.virtusa.faculty.user.entity.User;
import com.virtusa.faculty.user.exception.DuplicateUserException;
import com.virtusa.faculty.user.exception.UserNotFoundException;
import com.virtusa.faculty.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    //registerUser success
    @Test
    void testRegisterUserSuccess() {
        User user = User.builder()
                .email("test@mail.com")
                .build();

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals(Role.STUDENT, result.getRole());
        verify(userRepository).save(user);
    }

    // duplicate user
    @Test
    void testRegisterUserDuplicate() {
        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(new User()));

        User user = User.builder().email("test@mail.com").build();

        assertThrows(DuplicateUserException.class,
                () -> userService.registerUser(user));
    }

    // update profile
    @Test
    void testUpdateUserProfile() {
        User existing = User.builder()
                .id(1L)
                .name("Old")
                .build();

        User updated = User.builder()
                .name("New")
                .department("CSE")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any())).thenReturn(existing);

        User result = userService.updateUserProfile(1L, updated);

        assertEquals("New", result.getName());
    }

    // assign role
    @Test
    void testAssignRole() {
        User user = User.builder()
                .id(1L)
                .role(Role.STUDENT)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        User result = userService.assignRole(1L, "FACULTY");

        assertEquals(Role.FACULTY, result.getRole());
    }

    // link department wrong role
    @Test
    void testLinkFacultyToDepartmentInvalid() {
        User user = User.builder()
                .id(1L)
                .role(Role.STUDENT)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class,
                () -> userService.linkFacultyToDepartment(1L, "CSE"));
    }

    // link department success
    @Test
    void testLinkFacultyToDepartmentSuccess() {
        User user = User.builder()
                .id(1L)
                .role(Role.FACULTY)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        User result = userService.linkFacultyToDepartment(1L, "CSE");

        assertEquals("CSE", result.getDepartment());
    }

    // user not found
    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(1L));
    }

    // get a faculty list
    @Test
    void testGetFacultyList() {
        when(userRepository.findByRole(Role.FACULTY))
                .thenReturn(List.of(new User(), new User()));

        List<User> result = userService.getFacultyList();

        assertEquals(2, result.size());
    }

    // get a student list
    @Test
    void testGetStudentList() {
        when(userRepository.findByRole(Role.STUDENT))
                .thenReturn(List.of(new User()));

        List<User> result = userService.getStudentList();

        assertEquals(1, result.size());
    }

    // getUserByEmail
    @Test
    void testGetUserByEmail() {
        User user = User.builder().email("test@mail.com").build();

        when(userRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("test@mail.com");

        assertEquals("test@mail.com", result.getEmail());
    }
}