package com.experis.course.springlibrary.repository;

import com.experis.course.springlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

// nei generics devo mettere il tipo di dato dell'entità e il tipo di dato dell'attributo annotato come @Id
public interface BookRepository extends JpaRepository<Book, Integer> {

}
