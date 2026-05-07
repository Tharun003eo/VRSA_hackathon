package com.virtusa.faculty.user.repository;

import com.virtusa.faculty.user.entity.Role;
import com.virtusa.faculty.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createUser(String email, Role role) {
        return User.builder()
                .name("Test")
                .email(email)
                .role(role)
                .password("pass")
                .build();
    }

    @Test
    @DisplayName("find by email")
    void testFindByEmail() {
        User user = createUser("test@mail.com", Role.STUDENT);
        userRepository.save(user);

        Optional<User> result = userRepository.findByEmail("test@mail.com");

        assertTrue(result.isPresent());
        assertEquals("test@mail.com", result.get().getEmail());
    }

    @Test
    @DisplayName("find by role")
    void testFindByRole() {
        userRepository.save(createUser("a@mail.com", Role.FACULTY));
        userRepository.save(createUser("b@mail.com", Role.FACULTY));
        userRepository.save(createUser("c@mail.com", Role.STUDENT));

        List<User> result = userRepository.findByRole(Role.FACULTY);

        assertEquals(2, result.size());
    }
}