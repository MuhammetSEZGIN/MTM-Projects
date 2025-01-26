package com.example.Project1.Controller;

import com.example.Project1.DTOs.BookDto;
import com.example.Project1.Service.BookService;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Project1.Models.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
@InjectMocks
BookController bookController;
@Mock
BookService bookService;
    @Test
    void getBookById() {
        bookController = new BookController(bookService);
        Book book = new Book();
        book.setId(1);
        Mockito.when(bookService.getBookById(1)).thenReturn(book);
        assertEquals(book, bookController.getBookById(1));
    }
    @Test
    void getAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        Mockito.when(bookService.getAllBooks()).thenReturn(books);
        assertEquals(books, bookController.getAllBooks());
    }

    @Test
    void addBook() {
        BookDto bookDto = new BookDto();
        Book book = new Book();
        Mockito.when(bookService.addBook(bookDto)).thenReturn(book);
        assertEquals(book, bookController.addBook(bookDto));
    }

    @Test
    void updateBook() {
        int id = 1;
        BookDto bookDto = new BookDto();
        Book book = new Book();
        Mockito.when(bookService.updateBook(id, bookDto)).thenReturn(book);
        assertEquals(book, bookController.updateBook(id, bookDto));
    }

    @Test
    void deleteBook() {
        int id = 1;
        Mockito.when(bookService.deleteBook(id)).thenReturn(true);
        assertTrue(bookController.deleteBook(id));
    }

    @Test
    void getBookById_NotFound() {
        int id = 1;
        Mockito.when(bookService.getBookById(id)).thenReturn(null);
        assertNull(bookController.getBookById(id));
    }

    @Test
    void deleteBook_NotFound() {
        int id = 1;
        Mockito.when(bookService.deleteBook(id)).thenReturn(false);
        assertFalse(bookController.deleteBook(id));
    }
}