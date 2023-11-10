package com.experis.course.springlibrary.controller;

import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/books")
public class BookController {

  // attributi
  @Autowired
  private BookRepository bookRepository;


  // metodo che mostra la lista di tutti i libri
  @GetMapping
  public String index(@RequestParam Optional<String> search,
      Model model) {
    List<Book> bookList;

    if (search.isPresent()) {
      // se il parametro di ricerca è presente filtro la lista dei libri
      bookList = bookRepository.findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(
          search.get(),
          search.get());
    } else {
      // altrimenti prendo tutti i libri non filtrati
      // bookRepository recupera da database la lista di tutti i libri
      bookList = bookRepository.findAll();
    }

    // passo al template la lista di libri
    model.addAttribute("bookList", bookList);
    return "books/list";
  }

  // metodo che mostra i dettagli di un libro preso per id
  @GetMapping("/show/{id}")
  public String show(@PathVariable Integer id, Model model) {
    Optional<Book> result = bookRepository.findById(id);
    // verifico se il risultato è presente
    if (result.isPresent()) {
      // passo al template l'oggetto Book
      model.addAttribute("book", result.get());
      return "books/show";
    } else {
      // se non ho trovato il libro sollevo un'eccezione
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book with id " + id + " not found");
    }
/*    model.addAttribute("book", bookRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "book with id " + id + " not found")));
    return "books/show";*/
  }
}
