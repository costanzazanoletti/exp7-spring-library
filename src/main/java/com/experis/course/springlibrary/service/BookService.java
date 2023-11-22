package com.experis.course.springlibrary.service;

import com.experis.course.springlibrary.dto.BookDto;
import com.experis.course.springlibrary.exceptions.BookNotFoundException;
import com.experis.course.springlibrary.exceptions.ISBNUniqueException;
import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.repository.BookRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public List<Book> getBookList() {
    return bookRepository.findAll();
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
    book.setId(null);
    try {
      return bookRepository.save(book);
    } catch (RuntimeException e) {
      // ISBN non univoco -> eccezione
      throw new ISBNUniqueException(book.getIsbn());
    }
  }

  public Book createBook(BookDto bookDto) throws IOException, ISBNUniqueException {
    // converto il BookDto in Book
    Book book = convertDtoToBook(bookDto);
    // chiamo il metodo che salva il Book su db
    return createBook(book);
  }

  private static Book convertDtoToBook(BookDto bookDto) throws IOException {
    Book book = new Book();
    book.setTitle(bookDto.getTitle());
    book.setIsbn(bookDto.getIsbn());
    book.setSynopsis(bookDto.getSynopsis());
    book.setAuthors(bookDto.getAuthors());
    book.setPublisher(bookDto.getPublisher());
    book.setYear(bookDto.getYear());
    book.setNumberOfCopies(bookDto.getNumberOfCopies());
    book.setCategories(bookDto.getCategories());
    book.setId(bookDto.getId());
    if (bookDto.getCoverFile() != null && !bookDto.getCoverFile().isEmpty()) {
      // trasformo il MultipartFile in byte[]
      byte[] bytes = bookDto.getCoverFile().getBytes();
      book.setCover(bytes);
    }
    return book;
  }

  private static BookDto convertBookToDto(Book book) {
    BookDto bookDto = new BookDto();
    bookDto.setTitle(book.getTitle());
    bookDto.setIsbn(book.getIsbn());
    bookDto.setSynopsis(book.getSynopsis());
    bookDto.setAuthors(book.getAuthors());
    bookDto.setPublisher(book.getPublisher());
    bookDto.setYear(book.getYear());
    bookDto.setNumberOfCopies(book.getNumberOfCopies());
    bookDto.setCategories(book.getCategories());
    bookDto.setId(book.getId());
    return bookDto;
  }


  public BookDto getBookDtoById(Integer id) throws BookNotFoundException {
    // prendo il book da database
    Book book = getBookById(id);
    return convertBookToDto(book);
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
    bookToEdit.setCategories(book.getCategories());
    if (book.getCover() != null && book.getCover().length > 0) {
      bookToEdit.setCover(book.getCover());
    }

    return bookRepository.save(bookToEdit);
  }

  public Book editBook(BookDto bookDto) throws IOException {
    // converto il BookDto in Book
    Book book = convertDtoToBook(bookDto);
    // salvo il Book su db
    return editBook(book);
  }

  // metodo che elimina un libro da database
  public void deleteBook(Integer id) {
    bookRepository.deleteById(id);
  }

  // metodo che prende in ingresso un Pageable e restituisce la Page di libri
  public Page<Book> getPage(Pageable pageable) {
    return bookRepository.findAll(pageable);
  }
}
