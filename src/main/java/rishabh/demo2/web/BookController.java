package rishabh.demo2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rishabh.demo2.domain.Books;
import rishabh.demo2.service.BookService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/books/getDetails/{bookName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Books> getBooksDetailsByName(@PathVariable(value = "bookName") String bookName){
        Books resultBooks = bookService.findBookByName(bookName);
        System.out.println(resultBooks);
        try{
            if(resultBooks != null){
                return new ResponseEntity<>(resultBooks,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @RequestMapping(value = "/books/getAll", method = RequestMethod.GET)
   @ResponseStatus(HttpStatus.OK)
   @ResponseBody
   public ResponseEntity<List<Books>> getAllBooks(HttpServletRequest request) {
            List<Books> returnListBooks = bookService.findAll();
            if(returnListBooks != null){
                return new ResponseEntity<>(returnListBooks, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

    @RequestMapping(value = "/books/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Books addBook(@RequestBody Books book){
        return bookService.addBook(book);
    }
}
