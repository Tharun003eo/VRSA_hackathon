package com.virtusa.faculty.gateway.resolver;

import com.virtusa.faculty.gateway.security.JwtUtil;
import com.virtusa.faculty.gateway.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthResolver {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @MutationMapping
    public String login(@Argument String email, @Argument String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return jwtUtil.generateToken(email,
                userDetails.getAuthorities().iterator().next().getAuthority());
    }
}