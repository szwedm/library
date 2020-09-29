package com.msz.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<UserResponse>> getAllUsers() {
        logger.info("GET request - get all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        logger.info("POST request - create a new user");
        return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        logger.info("GET request - get user by ID");
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUser(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable String id) {
        logger.info("DELETE request - deactivate user by ID");
        userService.deactivateUser(id);
    }
}
