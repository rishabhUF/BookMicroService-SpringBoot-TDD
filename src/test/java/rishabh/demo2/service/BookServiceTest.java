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

import static org.junit.Assert.assertEquals;
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
    public void testfindAllBooks() throws Exception{
        List<Books> booksList = getBooksList();
        when(booksRepository.findAll()).thenReturn(booksList);
        assertEquals("First", booksList.get(0).getName());
        assertEquals("A",booksList.get(0).getName());
        assertEquals("Second",booksList.get(1).getName());
        assertEquals("B",getBooksList().get(1).getAuthor());
    }


    @Test
    public void testfindByName() throws Exception{

    }
    private Books getBook(){
        Books book = new Books("ris", "A");
        return book;
    }

    private List<Books> getBooksList()
    {
        List<Books> booksList = new ArrayList<>();
        booksList.add(new Books("First", "A"));
        booksList.add(new Books("Second", "B"));
        booksList.add(new Books("Third", "C"));
        booksList.add(new Books("Forth", "D"));
        booksList.add(new Books("Fifth", "E"));
        booksList.add(new Books("Sixth", "F"));
        return booksList;
    }
}
