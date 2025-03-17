package csd230.lab2.respositories;

import csd230.lab2.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsbn(String isbn);
    Book findById(long id);

}
