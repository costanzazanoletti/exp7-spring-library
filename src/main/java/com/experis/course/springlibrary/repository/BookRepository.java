package com.experis.course.springlibrary.repository;

import com.experis.course.springlibrary.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// nei generics devo mettere il tipo di dato dell'entità e il tipo di dato dell'attributo annotato come @Id
public interface BookRepository extends JpaRepository<Book, Integer> {

  // query custom per ricerca su title e authors
  List<Book> findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(String titleKeyword,
      String authorsKeyword);

}
