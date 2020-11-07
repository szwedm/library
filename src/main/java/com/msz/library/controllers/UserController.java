package com.msz.library.controllers;

import com.msz.library.requests.ChangePasswordRequest;
import com.msz.library.requests.CreateUserRequest;
import com.msz.library.responses.UserResponse;
import com.msz.library.security.CustomUserDetails;
import com.msz.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = Logger.getLogger("UserController");

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers(Authentication auth) {
        logger.info("GET request - get all users");
        if (auth.getName().equals("admin")) {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest userRequest, Authentication auth) {
        logger.info("POST request - create a new user");
        if (auth.getName().equals("admin")) {
            return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable String id, Authentication auth) {
        logger.info("GET request - get user by ID");
        if (auth.getName().equals("admin")) {
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.FOUND);
        }
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        if (user.getId().equals(id)) {
            return new ResponseEntity<>(userService.getUser(id), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/{id}/change-password")
    ResponseEntity<UserResponse> updateUserPassword(@PathVariable String id, @RequestBody ChangePasswordRequest passwordRequest, Authentication auth) {
        logger.info("PUT request - update user by ID");
        if (auth.getName().equals("admin")) {
            return new ResponseEntity<>(userService.changeUserPassword(id, passwordRequest), HttpStatus.ACCEPTED);
        }
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        if (user.getId().equals(id)) {
            return new ResponseEntity<>(userService.changeUserPassword(id, passwordRequest), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable String id, Authentication auth) {
        logger.info("DELETE request - deactivate user by ID");
        if (auth.getName().equals("admin")) {
            userService.deactivateUser(id);
        }
    }
}
