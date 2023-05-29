package com.springtodo.spring_todo_api.security.auth;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.springtodo.spring_todo_api.security.config.JwtService;

import com.springtodo.spring_todo_api.security.user.Role;

import com.springtodo.spring_todo_api.security.user.User;

import com.springtodo.spring_todo_api.security.user.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()

                .firstname(request.getFirstname())

                .lastname(request.getLastName())

                .email(request.getEmail())

                .password(passwordEncoder.encode(request.getPassword()))

                .role(Role.USER)

                .build();

        userRepo.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getEmail(), request.getPassword()

                )

        );

        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();

    }

}
