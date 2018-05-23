package rishabh.demo2.service;

import org.springframework.stereotype.Service;
import rishabh.demo2.domain.Books;

import java.util.List;
import java.util.Map;

@Service
public interface BookService {

    List<Books> findAll();

    List<Books> addBooks(Map<String, String> books);

    List<Books> findBooksByAuthor(String author);

    Books addBook(Books book);

    Books findBookByName(String name);

}
