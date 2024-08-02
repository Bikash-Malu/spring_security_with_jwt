package com.security.Learning.controller;

import com.security.Learning.entity.Role;
import com.security.Learning.entity.User;
import com.security.Learning.repo.RoleRepo;
import com.security.Learning.repo.UserRepo;
import com.security.Learning.response.AuthResponse;
import com.security.Learning.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @GetMapping("/api/route1")
    public String route(Principal principal){
        String name = principal.getName();
        return name;
    }
    @GetMapping("/api/route2")
//    @PreAuthorize("hasRole('USER')")
    public String route2(){
        return "Hello World";
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws Exception {
        User byEmail = userRepo.findByEmail(user.getEmail()).orElse(null);
        Role roleUser = roleRepo.findByName("ROLE_USER");

        if (byEmail != null) {
            throw new Exception("email is already exist");
        }
        user.setUserid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleUser));
        User savedUser = userRepo.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);
        System.out.println("jwt"+jwt);
        AuthResponse registeredUser = AuthResponse.builder().token(jwt).status(true).message("registered user").build();
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
