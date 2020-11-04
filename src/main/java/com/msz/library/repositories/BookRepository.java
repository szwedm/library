package com.msz.library.repositories;

import com.msz.library.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    Optional<BookEntity> findByName(String name);

    List<BookEntity> findByAuthor(String author);
}
