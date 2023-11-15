package com.experis.course.springlibrary.service;

import com.experis.course.springlibrary.exceptions.BookNotFoundException;
import com.experis.course.springlibrary.exceptions.ISBNUniqueException;
import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;


  // metodo che restituisce la lista di tutti i libri eventualmente filtrati
  public List<Book> getBookList(Optional<String> search) {
    if (search.isPresent()) {
      // se il parametro di ricerca è presente filtro la lista dei libri
      return bookRepository.findByTitleContainingIgnoreCaseOrAuthorsContainingIgnoreCase(
          search.get(),
          search.get());
    } else {
      // altrimenti prendo tutti i libri non filtrati
      // bookRepository recupera da database la lista di tutti i libri
      return bookRepository.findAll();
    }
  }

  // metodo che restituisce un libro preso per id, se non lo trova solleva un'eccezione
  public Book getBookById(Integer id) throws BookNotFoundException {
    Optional<Book> result = bookRepository.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      throw new BookNotFoundException("Book with id " + id + " not found");
    }
  }

  // metodo per creare un nuovo libro
  public Book createBook(Book book) throws ISBNUniqueException {
    try {
      return bookRepository.save(book);
    } catch (RuntimeException e) {
      // ISBN non univoco -> eccezione
      throw new ISBNUniqueException(book.getIsbn());
    }
  }

  // metodo per modificare un libro con un id
  public Book editBook(Book book) throws BookNotFoundException {
    Book bookToEdit = getBookById(book.getId());
    // sostituisco i valori dei campi previsti
    bookToEdit.setTitle(book.getTitle());
    bookToEdit.setAuthors(book.getAuthors());
    bookToEdit.setPublisher(book.getPublisher());
    bookToEdit.setYear(book.getYear());
    bookToEdit.setSynopsis(book.getSynopsis());
    bookToEdit.setNumberOfCopies(book.getNumberOfCopies());

    return bookRepository.save(bookToEdit);
  }

  // metodo che elimina un libro da database
  public void deleteBook(Integer id) {
    bookRepository.deleteById(id);
  }
}
