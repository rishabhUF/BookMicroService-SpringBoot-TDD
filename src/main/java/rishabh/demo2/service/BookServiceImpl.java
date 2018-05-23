package rishabh.demo2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import rishabh.demo2.Utils.ServiceUtils;
import rishabh.demo2.doa.BooksDAO;
import rishabh.demo2.domain.Books;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final ConcurrentMap<String, List<Books>> cache = new ConcurrentHashMap<>();

    @Autowired
    private BooksDAO booksDAO;

    private ServiceUtils utils = new ServiceUtils();

    @Override
    public Books findBookByName(String bookName) {
        return booksDAO.findByName(bookName);
    }

    @Override
    public List<Books> findAll(){
        return booksDAO.findAll();
    }

    @Override
    public List<Books> addBooks(Map<String, String> books) {
        return books.entrySet().stream()
                .map(entry -> new Books(entry.getKey(), entry.getValue()))
                .map(booksDAO::save)
                .collect(Collectors.toList());
    }

    @Override
    public List<Books> findBooksByAuthor(String author) {
        if(author == null || author.length() == 0) {
            return null;
        }
        else{
            String normalizedAuthor = utils.normalizeAuthorName(author);
            return cache.computeIfAbsent(normalizedAuthor, booksDAO::findByAuthor);
        }
    }

    @Override
    public Books addBook(Books book){
        return booksDAO.save(book);
    }
}
