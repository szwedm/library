package com.msz.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    List<User> getAllUsers() {
        //service call to get all users
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(@RequestBody User newUser) {
        //service call to save user in db
    }

    @GetMapping("/{id}")
    User getUser(@PathVariable String id) {
        //service call to get one user by id
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    User updateUser(@PathVariable Long id) {
        //service call to get one user by id
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@PathVariable String id) {

    }
}
