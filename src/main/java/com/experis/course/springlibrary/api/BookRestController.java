package com.experis.course.springlibrary.api;

import com.experis.course.springlibrary.exceptions.BookNotFoundException;
import com.experis.course.springlibrary.exceptions.ISBNUniqueException;
import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin
public class BookRestController {

  @Autowired
  private BookService bookService;

  // endpoint per la lista di tutti i libri
  @GetMapping
  public List<Book> index(@RequestParam Optional<String> search) {
    return bookService.getBookList(search);
  }

  // endpoint per i dettagli del libro preso per id
  @GetMapping("/{id}")
  public Book details(@PathVariable Integer id) {
    try {
      return bookService.getBookById(id);
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  // endopoint per creare un nuovo libro
  @PostMapping
  public Book create(@Valid @RequestBody Book book) {
    try {
      return bookService.createBook(book);
    } catch (ISBNUniqueException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  // endpoint per modificare un libro
  @PutMapping("/{id}")
  public Book update(@PathVariable Integer id, @Valid @RequestBody Book book) {
    book.setId(id);
    try {
      return bookService.editBook(book);
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    try {
      Book bookToDelete = bookService.getBookById(id);
      bookService.deleteBook(id);
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  // endpoint di test per lista paginata
  @GetMapping("/page")
  public Page<Book> pagedIndex(
      @RequestParam(name = "size", defaultValue = "2") Integer size,
      @RequestParam(name = "page", defaultValue = "0") Integer page) {

    return bookService.getPage(PageRequest.of(page, size));
  }

  @GetMapping("/page/v2")
  public Page<Book> pagedIndexV2(@PageableDefault(page = 0, size = 2) Pageable pageable) {
    return bookService.getPage(pageable);
  }
}
