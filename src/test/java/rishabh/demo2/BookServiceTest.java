package rishabh.demo2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestAttributes;
import rishabh.demo2.doa.BooksRepository;
import rishabh.demo2.domain.Books;
import rishabh.demo2.service.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private RequestAttributes requestAttributes;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBookDetailsByName() throws Exception{
        Books book = getBook();
        List<Books> booksList = new ArrayList<>();
        booksList.add(book);
        when(booksRepository.findAll()).thenReturn(booksList);


    }

    private Books getBook(){
        Books book = new Books("ris", "A");
        return book;
    }
}
