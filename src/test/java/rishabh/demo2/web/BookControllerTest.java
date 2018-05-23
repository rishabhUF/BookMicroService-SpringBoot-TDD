package rishabh.demo2.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rishabh.demo2.domain.Books;
import rishabh.demo2.service.BookService;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

    private List<Books> books = asList(new Books("First","A"), new Books("Second","B"));


    @Before
    public void init()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
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

    }

    @Test
    public void testGetBookDetailsException() throws Exception {
        String bookName = "First";
        when(bookService.findBookByName(any())).thenReturn(null);
        mockMvc.perform(get("/books/getDetails/{bookName}", bookName))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/books/getAll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("First"))
                .andExpect(jsonPath("$[0].author").value("A"));
    }

    @Test
    public void testGetAllBooksException() throws Exception{
        when(bookService.findAll()).thenReturn(null);
        mockMvc.perform(get("/books/getAll"))
                .andExpect(status().isNotFound());
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
    }

    private Books setBooks(){
        Books book = new Books("ris", "A");
        return book;
    }
}
