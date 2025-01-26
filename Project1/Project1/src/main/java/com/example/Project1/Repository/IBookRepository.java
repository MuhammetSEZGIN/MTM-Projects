package com.example.Project1.Repository;
import com.example.Project1.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface IBookRepository extends JpaRepository<Book, Integer> {

}
