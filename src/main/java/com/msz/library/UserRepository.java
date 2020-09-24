package com.msz.library;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findById(long id);

    UserEntity findByEmail(String email);

    List<UserEntity> findByName(String name);
}
