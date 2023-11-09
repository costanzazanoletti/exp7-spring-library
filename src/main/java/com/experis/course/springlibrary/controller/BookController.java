package com.experis.course.springlibrary.controller;

import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.repository.BookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

  // attributi
  @Autowired
  private BookRepository bookRepository;


  // metodo che mostra la lista di tutti i libri
  @GetMapping
  public String index(Model model) {
    // bookRepository recupera da database la lista di tutti i libri
    List<Book> bookList = bookRepository.findAll();
    // passo al template la lista di libri
    model.addAttribute("bookList", bookList);
    return "books/list";
  }
}
