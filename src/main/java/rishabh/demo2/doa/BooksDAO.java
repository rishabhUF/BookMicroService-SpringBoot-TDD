package rishabh.demo2.doa;

import org.springframework.data.mongodb.repository.MongoRepository;
import rishabh.demo2.domain.Books;

import java.util.List;

public interface BooksDAO extends MongoRepository<Books,String> {

    public Books findByName(String name);

    Books save(Books book);

    public List<Books> findByAuthor(String author);
}
