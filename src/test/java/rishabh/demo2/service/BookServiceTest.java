package rishabh.demo2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestAttributes;
import rishabh.demo2.doa.BooksDAO;
import rishabh.demo2.domain.Books;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    @Mock
    private BooksDAO booksRepository;

    @Mock
    private RequestAttributes requestAttributes;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testfindAllBooks() throws Exception{
        List<Books> booksList = getBooksList();
        when(booksRepository.findAll()).thenReturn(booksList);
        assertEquals("First", booksList.get(0).getName());
        assertEquals("A",booksList.get(0).getAuthor());
        assertEquals("Second",booksList.get(1).getName());
        assertEquals("B",getBooksList().get(1).getAuthor());
    }


    @Test
    public void testfindByName() throws Exception{
        Books book = getBook();
        String bookName = "ris";
        when(booksRepository.findByName(bookName)).thenReturn(book);
        assertEquals("ris",book.getName());
    }

//    @Test
//    public void testAddBook() throws Exception{
//        Books book = getBook();
//
//    }
    private Books getBook(){
        Books book = new Books("ris", "A");
        book.setGenres("crime");
        book.setPage(100);
        book.setPrice(10.85);
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
