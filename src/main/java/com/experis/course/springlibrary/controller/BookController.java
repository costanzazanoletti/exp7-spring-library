package com.experis.course.springlibrary.controller;

import com.experis.course.springlibrary.exceptions.BookNotFoundException;
import com.experis.course.springlibrary.exceptions.ISBNUniqueException;
import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.service.BookService;
import com.experis.course.springlibrary.service.CategoryService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

  // attributi
  @Autowired
  private BookService bookService;
  @Autowired
  private CategoryService categoryService;


  // metodo che mostra la lista di tutti i libri
  @GetMapping
  public String index(@RequestParam Optional<String> search,
      Model model) {
    // passo al template la lista di libri
    model.addAttribute("bookList", bookService.getBookList(search));
    return "books/list";
  }

  // metodo che mostra i dettagli di un libro preso per id
  @GetMapping("/show/{id}")
  public String show(@PathVariable Integer id, Model model) {
    try {
      Book book = bookService.getBookById(id);
      model.addAttribute("book", book);
      return "books/show";
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  // metodo che mostra il form di creazione del book
  @GetMapping("/create")
  public String create(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("categoryList", categoryService.getAll());
    return "books/form";
  }

  @PostMapping("/create")
  public String doCreate(@Valid @ModelAttribute("book") Book formBook,
      BindingResult bindingResult, Model model) {
    // validare che i dati siano corretti
    if (bindingResult.hasErrors()) {
      // ci sono errori, devo ricaricare il form
      model.addAttribute("categoryList", categoryService.getAll());
      return "books/form";
    }
    // salvo il libro su database
    try {
      Book savedBook = bookService.createBook(formBook);
      return "redirect:/books/show/" + savedBook.getId();
    } catch (ISBNUniqueException e) {
      // aggiungo un errore di validazione per isbn
      bindingResult.addError(new FieldError("book", "isbn", e.getMessage(), false, null, null,
          "ISBN must be unique"));
      // ti rimando alla pagina col form
      return "books/form";
    }
  }

  // metodo che mostra la pagina di modifica di un libro
  @GetMapping("/edit/{id}")
  public String edit(@PathVariable Integer id, Model model) {
    try {
      // aggiungo il book come attributo del Model
      model.addAttribute("book", bookService.getBookById(id));
      model.addAttribute("categoryList", categoryService.getAll());
      // proseguo a restituire la pagina di modifica
      return "/books/form";
    } catch (BookNotFoundException e) {
      // sollevo un'eccesione con HttpStatus 404
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  // metodo che riceve il submit del form di edit e salva il libro
  @PostMapping("/edit/{id}")
  public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("book") Book formBook,
      BindingResult bindingResult, Model model) {
    // valido il libro
    if (bindingResult.hasErrors()) {
      // se ci sono errori ricarico la pagina col form
      model.addAttribute("categoryList", categoryService.getAll());
      return "/books/form";
    }
    try {
      Book savedBook = bookService.editBook(formBook);
      return "redirect:/books/show/" + savedBook.getId();
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }

  // metodo per eliminare un libro da database
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    // recupero il libro con quell'id
    try {
      Book bookToDelete = bookService.getBookById(id);
      bookService.deleteBook(id);
      redirectAttributes.addFlashAttribute("message",
          "Book " + bookToDelete.getTitle() + " deleted!");
      return "redirect:/books";
    } catch (BookNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
    }
  }
}
