package com.msz.library.bootstrap;

import com.msz.library.domain.BookEntity;
import com.msz.library.domain.UserEntity;
import com.msz.library.repositories.BookRepository;
import com.msz.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DataLoader(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadDataToDB();
    }

    private void loadDataToDB() {
        UserEntity user1 = new UserEntity(
                "James Sunderland",
                "james.sunderland@email.com",
                "pyramid".toCharArray()
        );

        UserEntity user2 = new UserEntity(
                "Mary Shepherd",
                "mary.shepherd@email.com",
                "judgement".toCharArray()
        );

        userRepository.save(user1);
        userRepository.save(user2);

        BookEntity book1 = new BookEntity(
                "Crime and Punishment",
                "Fyodor Dostoevsky"
        );

        BookEntity book2 = new BookEntity(
                "Pale Rider",
                "Laura Spinney"
        );

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
