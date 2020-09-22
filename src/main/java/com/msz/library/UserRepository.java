package com.msz.library;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(long id);

    User findByEmail(String email);

    List<User> findByName(String name);
}
