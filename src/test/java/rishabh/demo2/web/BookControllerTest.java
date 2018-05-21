package rishabh.demo2.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;
import rishabh.demo2.doa.Books;
import rishabh.demo2.service.BookService;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("author").value("A"));

    }

    @Test
    public void testGetBookDetailsException() throws Exception {
        String bookName = "First";
        when(bookService.findBookByName(bookName)).thenReturn(null);
        mockMvc.perform(get("books/getDetails/{bookName}", bookName))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        mockMvc.perform(get("/books/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("First"))
                .andExpect(jsonPath("$[0].author").value("A"));
    }
}
