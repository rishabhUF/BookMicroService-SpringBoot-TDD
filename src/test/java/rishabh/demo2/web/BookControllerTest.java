package rishabh.demo2.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestAttributes;
import rishabh.demo2.domain.Books;
import rishabh.demo2.service.BookService;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    RequestAttributes requestAttributes;

    private List<Books> books = asList(new Books("First","A"), new Books("Second","B"));


    @Before
    public void init()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getBookDetails_shouldReturnOk() throws Exception {
        String bookName = "First";
        when(bookService.findBookByName(any(String.class))).thenReturn(new Books(bookName,"A"));
        ResponseEntity<Books> result = bookController.getBooksDetailsByName(bookName);
        Assert.assertTrue(result.getStatusCode().is2xxSuccessful());
    }

    @Test(expected = Exception.class)
    public void getBookDetails_shouldReturn500() throws Exception {
        String bookName = "First";
        //System.out.println("hello1");
        when(bookService.findBookByName(any())).thenThrow(new Exception());
        //System.out.println("hello2");
        ResponseEntity<Books> responseEntity = bookController.getBooksDetailsByName(bookName);
        //System.out.println("hello3");
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        //System.out.println("hello4");
    }

    @Test
    public void getBookDetails_shouldReturnAuthor() throws Exception {
        String bookName = "First";
        when(bookService.findBookByName(anyString())).thenReturn(new Books(bookName,"A"));
        ResponseEntity<Books> responseEntity = bookController.getBooksDetailsByName(bookName);
        Assert.assertEquals(responseEntity.getBody().getAuthor(), "A");
    }
    @Test
    public void testGetBookDetails() throws Exception {
        String bookName = "First";
        when(bookService.findBookByName(bookName)).thenReturn(new Books(bookName,"A"));
        mockMvc.perform(get("/books/getDetails/{bookName}", bookName))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("author").value("A"));
        verify(bookService,times(1)).findBookByName(bookName);

    }

    @Test
    public void testGetBookDetailsException() throws Exception {
        String bookName = "First";
        when(bookService.findBookByName(any())).thenReturn(null);
        mockMvc.perform(get("/books/getDetails/{bookName}", bookName))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(bookService,times(1)).findBookByName(bookName);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/books/getAll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("First"))
                .andExpect(jsonPath("$[0].author").value("A"));
        verify(bookService,times(1)).findAll();
    }

    @Test
    public void testGetAllBooksException() throws Exception{
        when(bookService.findAll()).thenReturn(null);
        mockMvc.perform(get("/books/getAll"))
                .andExpect(status().isNotFound());
        verify(bookService,times(1)).findAll();
    }

    @Test
    public void addBook_shouldReturnOK() throws Exception {
        Books book = setBooks();
        when(bookService.addBook(book)).thenReturn(book);
        ResponseEntity<Books> responseEntity = bookController.addBook(book);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test(expected = Exception.class)
    public void addBook_shouldReturn500() throws Exception {
//        doThrow(new RuntimeException("User Data is not there")).when(bookService).addBook(setBooks());
//       // when(bookService.addBook(any())).thenThrow(new Exception());
//        ResponseEntity<Books> responseEntity = bookController.addBook(setBooks());
//        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        when(requestAttributes.getAttribute("book", RequestAttributes.SCOPE_REQUEST)).thenThrow(new Exception());
        when(bookService.addBook(any())).thenReturn(setBooks());
        ResponseEntity<Books> responseEntity = bookController.addBook(setBooks());
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test(expected = Exception.class)
    public void addBook_shouldReturn500Exce() throws Exception{
        when(bookService.addBook(setBooks())).thenThrow(new Exception());
        mockMvc.perform(post("/books/add").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testAddBook() throws Exception{
        Books book = setBooks();
        String json = "{\n" +
                "  \"id\":\"ris\",\n" +
                "  \"name\":\"ris\",\n" +
                "  \"author\":\"A\",\n" +
                "  \"price\":\"0.0\",\n" +
                "  \"page\":\"0\",\n" +
                "  \"genres\":\"null\"\n" +
                "}";
        when(bookService.addBook(any())).thenReturn(book);
        mockMvc.perform(post("/books/add").contentType(MediaType.APPLICATION_JSON_VALUE).content(json))
                .andExpect(status().isCreated()).andDo(print());
        verify(bookService,times(1)).addBook(any());
    }

    private Books setBooks(){
        Books book = new Books("ris", "A");
        return book;
    }
}
