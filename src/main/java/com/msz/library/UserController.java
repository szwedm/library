package com.msz.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    ResponseEntity<List<UserEntity>> getAllUsers() {
        return null;
    }

    @PostMapping("/")
    ResponseEntity<String> createUser(@RequestBody CreateUserRequest userRequest) {
        return new ResponseEntity(userService.createUser(userRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return null;
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUser(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable String id) {

    }
}
