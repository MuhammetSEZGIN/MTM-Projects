package com.example.Project1.Service;

import com.example.Project1.DTOs.BookDto;
import com.example.Project1.Models.Book;
import com.example.Project1.Repository.IBookRepository;
import com.example.Project1.Util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final IBookRepository _bookRepository;

    @Autowired
    public BookService(IBookRepository bookRepository) {
        _bookRepository = bookRepository;
    }
    public Book addBook(BookDto book) {
        Book newBook = new Book();
        Mapper.map( book,newBook);
        _bookRepository.save(newBook);
        return newBook;
    }

    public Book getBookById(int id) {
        return _bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooks(){
        return _bookRepository.findAll();
    }

    public Book updateBook(int Id, BookDto book) {
        Optional<Book> existingBook= _bookRepository.findById(Id);
        if(existingBook.isEmpty())
            return null;
        Book updatedBook = existingBook.get();
        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setGenre(book.getGenre());
        return _bookRepository.save(updatedBook);
    }

    public boolean deleteBook(int id) {
        Optional<Book> book = _bookRepository.findById(id);
        if(book.isEmpty())
            return false;
          _bookRepository.delete(book.get());
          return true;
    }



}
