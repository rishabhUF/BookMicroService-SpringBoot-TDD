package rishabh.demo2.doa;

import org.springframework.data.mongodb.repository.MongoRepository;
import rishabh.demo2.domain.Books;

public interface BooksRepository extends MongoRepository<Books,String> {

    public Books findByName(String name);

    Books save(Books book);
}
