package rishabh.demo2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rishabh.demo2.doa.BooksRepository;
import rishabh.demo2.domain.Books;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BooksRepository repository;

    public Books findBookByName(String bookName) {
        return null;
    }

    public List<Books> findAll(){
        return repository.findAll();
    }

    public Books addBook(Books book){
        return repository.save(book);
    }
}
