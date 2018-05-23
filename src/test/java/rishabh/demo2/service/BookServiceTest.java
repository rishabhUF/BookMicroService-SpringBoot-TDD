package rishabh.demo2.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rishabh.demo2.doa.BooksDAO;
import rishabh.demo2.domain.Books;

import java.util.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookServiceTest {

    @Mock
    private BooksDAO booksDAO;

    @InjectMocks
    private BookService bookService = new BookServiceImpl();

    @Test
    public void testfindAllBooks() throws Exception{
        List<Books> booksList = getBooksList();
        Books first = new Books("First","A");
        when(booksDAO.findAll()).thenReturn(booksList);
        when(bookService.findAll()).thenReturn(booksList);
        assertThat(bookService.findAll(), is(booksList));
    }


    @Test
    public void testfindByName() throws Exception{
        Books book = getBook();
        String bookName = "ris";
        when(booksDAO.findByName(bookName)).thenReturn(book);
        when(bookService.findBookByName(bookName)).thenReturn(book);
        assertThat(bookService.findBookByName("ris"), is(book));
    }


    @Test
    public void testAddBooksListIfNoBooksPassed() throws Exception{
        assertThat(bookService.addBooks(Collections.emptyMap()), is(empty()));
    }

    @Test
    public void testForEveryPairOfTitleAndAuthorBookIsCreatedAndStored()
    {
        Books first = new Books("First", "A");
        Books second = new Books("Second", "B") ;
        when(booksDAO.save(notNull(Books.class))).thenReturn(first).thenReturn(second);
        Map<String, String> books = new HashMap<>();
        books.put("First", "A");
        books.put("Second", "B");
        assertThat(bookService.addBooks(books), hasItems(first,second));
    }


    @Test
    public void testIfNoBooksFoundForAuthorReturnEmptyList(){
        when(booksDAO.findByAuthor("a")).thenReturn(emptyList());
        assertNoBooksFound("a");
        verify(booksDAO,times(1)).findByAuthor("a");

    }

    @Test
    public void testIfAuthorNameIsNull(){
        when(booksDAO.findByAuthor("")).thenReturn(null);
        assertThat(bookService.findBooksByAuthor(""),is(nullValue()));
        verify(booksDAO,times(0)).findByAuthor("");
    }

    @Test
    public void testBooksByAuthorShouldBeCached(){
        Books book = new Books("The Book", "author");
        when(booksDAO.findByAuthor("a")).thenReturn(singletonList(book));
        when(booksDAO.findByAuthor("a a")).thenReturn(emptyList());

        assertBooksByAuthor("a",book);
        assertBooksByAuthor("a",book);
        assertNoBooksFound("a a");
        verify(booksDAO,times(1)).findByAuthor("a");
    }

    @Test
    public void testIfCamelCaseDetectedThenSplitInvalidAuthorNameOnFirstAndLastName() {
        Books book = new Books("The book", "Mikalai Alimenkou");
        when(booksDAO.findByAuthor("Mikalai Alimenkou")).thenReturn(singletonList(book));

        assertBooksByAuthor("MikalaiAlimenkou", book);
    }

    @Test
    public void testPunctuationShouldBeIgnored() {
        Books book = new Books("The book", "Who cares");
        when(booksDAO.findByAuthor("Who cares?")).thenReturn(singletonList(book));

        assertBooksByAuthor("Who cares?", book);
    }

    @Test
    public void testCompositeLastNameIsNotSplit() {
        Books book = new Books("The book", "Alfred McGregor");
        when(booksDAO.findByAuthor("Alfred McGregor")).thenReturn(singletonList(book));

        assertBooksByAuthor("Alfred McGregor", book);
    }

    @Test
    public void testAuthorNameShouldBeTrimmedBeforeUsage() {
        Books book = new Books("The book", "Mikalai Alimenkou");
        when(booksDAO.findByAuthor("Mikalai Alimenkou")).thenReturn(singletonList(book));

        assertBooksByAuthor(" \t Mikalai \n Alimenkou \t ", book);
    }

    @Test
    public void testAddBookShouldBeSaved() {
        Books first = new Books("First", "A");
        when(booksDAO.save(notNull(Books.class))).thenReturn(first);
        assertThat(bookService.addBook(first), is(first));
    }
    private void assertBooksByAuthor(String author, Books book) {
        assertThat(bookService.findBooksByAuthor(author), hasItem(book));
    }

    private void assertNoBooksFound(String author){
        assertThat(bookService.findBooksByAuthor(author), is(empty()));
    }

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
