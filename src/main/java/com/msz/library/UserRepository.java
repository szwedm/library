package com.msz.library;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);

    User findByEmail(String email);

    List<User> findByName(String name);
}
