package com.example.Project1.Controller;
import com.example.Project1.DTOs.BookDto;
import com.example.Project1.Models.Book;
import com.example.Project1.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService _bookService;

    @Autowired
    public BookController(BookService bookService) {
        _bookService = bookService;
    }
    @GetMapping
    public List<Book> getAllBooks(){
        return _bookService.getAllBooks();
    }
    @PostMapping()
    public Book addBook(@RequestBody BookDto book) {
        return _bookService.addBook(book);
    }
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return _bookService.getBookById(id);
    }


    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id,  @RequestBody BookDto book) {
        return _bookService.updateBook(id, book);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteBook(@PathVariable int id) {
        return _bookService.deleteBook(id);
    }

}
