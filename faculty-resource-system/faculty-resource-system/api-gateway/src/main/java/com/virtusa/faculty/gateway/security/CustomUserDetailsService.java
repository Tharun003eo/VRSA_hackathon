package com.virtusa.faculty.gateway.security;

import com.virtusa.faculty.user.entity.User;
import com.virtusa.faculty.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.getUserByEmail(email); // you must have this

        return new CustomUserDetails(user);
    }
}